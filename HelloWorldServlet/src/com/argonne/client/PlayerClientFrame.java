package com.argonne.client;

import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceImpl;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.playerService.PlayerUpdateSupplier;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
*
* @fyi use -DataFile=<filename> to override the default input file, players.dat
*
* @author William Gillund
* @since 11/2/2008
* @version 1.0
*/

public class PlayerClientFrame extends JFrame implements PlayerUpdateSupplier {

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();

    JTextField firstName = new JTextField(20);
    JTextField eMail = new JTextField(20);
    JTextField phone = new JTextField(10);
        
    JLabel firstNameLabel = new JLabel();
    JLabel eMailLabel  = new JLabel();
    JLabel phoneLabel     = new JLabel();
    
    JButton addPlayerButton = new JButton();
    
    JButton removePlayerButton = new JButton ();
    JButton closeButton = new JButton();
    
//  storage for player data
    
    JTable player;
    private AbstractTableModel playerDataTableModel;
    private Object[][] playerData = {};
    private String[] playerDataColumnNames = { "Name", "eMail Address", "Phone Number" };
   
    PlayerService playerService = null;
    Hashtable playerUpdateConsumers = new Hashtable();

    // Construct the frame
    public PlayerClientFrame(String[] args) {
        try {
            init();
            initializeService(args);
            loadPlayerData();
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    
    public PlayerClientFrame(PlayerService aPlayerService) {
        try {
            init();
            initializeService(aPlayerService);
            loadPlayerData();
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }

    // Component initialization
    private void init() throws Exception  {
    	
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEtchedBorder());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(620 /*width*/, 485));
        this.setTitle("Player Service Client");

        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));

        firstNameLabel.setText("Name:");
        firstNameLabel.setBounds(new Rectangle(28, 50, 140, 25));
        firstName.setBounds(new Rectangle(173, 50, 213, 29));
      
 
        eMailLabel.setText("Email Address:");
        eMailLabel.setBounds(new Rectangle(28, 50, 140, 25));
        eMail.setBounds(new Rectangle(173, 50, 213, 29));

        phoneLabel.setText("Phone:");
        phoneLabel.setBounds(new Rectangle(28, 50, 140, 25));
        phone.setBounds(new Rectangle(173, 50, 213, 29));

        playerDataTableModel = new PlayerDataTableModel();
        player = new JTable(playerDataTableModel);
        player.setPreferredScrollableViewportSize(new Dimension(525, 319));
        player.setShowGrid(true);
        JScrollPane playerSrollPane = new JScrollPane(player);

        addPlayerButton.setText("Add Player");
        addPlayerButton.setBounds(new Rectangle(49, 160, 150, 35));
        addPlayerButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addPlayerButton_actionPerformed(e);
            }
        });
        
        removePlayerButton.setText("Remove Player");
        removePlayerButton.setBounds(new Rectangle(19, 160, 150, 35));
        removePlayerButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removePlayerButton_actionPerformed(e);
            }
        });
        
        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });

        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(playerSrollPane, null);

        jPanel1.setToolTipText("");
        contentPane.add(jPanel1, null);
        jPanel1.add(firstNameLabel, null);
        jPanel1.add(firstName, null);

        jPanel1.setToolTipText("");
        jPanel1.add(eMailLabel, null);
        jPanel1.add(eMail, null);

        jPanel1.setToolTipText("");
        jPanel1.add(phoneLabel, null);
        jPanel1.add(phone, null);

        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(addPlayerButton, null);
        jPanel4.add(removePlayerButton, null);
        jPanel4.add(closeButton, null);
       
    }

    private void initializeService(String[] args) {
    	playerService = JhandicapFactory.getPlayerService();
    
    }

    private void initializeService(PlayerService aPlayerService) {
        try {
            playerService = aPlayerService;
            Log.debug("Resolved PlayerService");
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }

    
    
    protected void loadPlayerData() {
        Hashtable tempPlayers = playerService.getAllPlayers();
        loadPlayerData(tempPlayers);
     }
    
    
    protected void loadPlayerData(Hashtable tempPlayers) {
    	
        Vector sortedPlayers = sortPlayers(tempPlayers);
        Log.debug("Displaying " + sortedPlayers.size() + " players.");
      
        Object[][] newData = new Object[sortedPlayers.size()][playerDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
            Player aPlayer = (Player) sortedPlayers.elementAt(i);
            newData[i][0] =  aPlayer.firstName;
            newData[i][1] =  aPlayer.eMail;
            newData[i][2] =  aPlayer.phoneNumber;
           
        }

        // reset model
        playerData = newData;

        // instruct table to refresh its view since the underlying model has changed
        playerDataTableModel.fireTableDataChanged();
    }
    
    protected Vector sortPlayers(Hashtable tempPlayers) {
        Vector sortedPlayers = new Vector();
        Enumeration enumeration = tempPlayers.elements();
        while ( enumeration.hasMoreElements() ) {
            Player player1 = (Player) enumeration.nextElement();
            boolean notInserted = true;
            for ( int i = 0 ; i < sortedPlayers.size(); i++ ) {
                Player player2 = (Player) sortedPlayers.elementAt(i);
                String player1FullName = player1.firstName;
                String player2FullName =  player2.firstName;
                if ( player1FullName.compareTo(player2FullName) < 0 ) {
                    sortedPlayers.add(i, player1);
                    notInserted = false;
                    break;
                }
            }
            if ( notInserted ) {
                sortedPlayers.add(player1);
            }
        }
        return sortedPlayers;
    }
    
    
    
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addPlayerButton_actionPerformed(ActionEvent event) {
        
    	try {
        	
          Hashtable newPlayers = getPlayerService().addPlayer(firstName.getText(), eMail.getText(), phone.getText(),null,null);
          loadPlayerData(newPlayers);
          publishPlayerUpdates(newPlayers);
          firstName.setText("");
          eMail.setText("");
          phone.setText("");
          
        }
        catch(Exception e) {
        	
        	 JOptionPane.showMessageDialog(this,"Invalid Input ");
           
             return;
        }
    }
    
    void removePlayerButton_actionPerformed(ActionEvent event) {
    	
    	 int rowNumberToDelete = player.getSelectedRow();
    	 if (rowNumberToDelete < 0 ){
    	    JOptionPane.showMessageDialog(this,"Must select Row to remove Player");
    	    return;
    	 }
    	 String name = (String) player.getValueAt(rowNumberToDelete,0);
    	 Hashtable newPlayers = null;
         try {
            newPlayers = playerService.removePlayer(name);
         }
         catch (Exception e)
         {
         	JOptionPane.showMessageDialog(this,"Player Not found in Db");
            return;
         }
         loadPlayerData(newPlayers);
         publishPlayerUpdates(newPlayers);
  
    }
    protected PlayerService getPlayerService() {
        if ( playerService == null ) {
            playerService = new PlayerServiceImpl();
        }

        return playerService;
    }

    public void addConsumer(PlayerUpdateConsumer player) {
        playerUpdateConsumers.put(player, player);
    }

    public void removeConsumer(PlayerUpdateConsumer player) {
        playerUpdateConsumers.remove(player);
    }

    protected void publishPlayerUpdates(Hashtable playerCollection) {
        Enumeration enumeration = playerUpdateConsumers.elements();
        while ( enumeration.hasMoreElements() ) {
        	PlayerUpdateConsumer player = (PlayerUpdateConsumer) enumeration.nextElement();
            player.acceptPlayer(playerCollection);
        }
    }

    /**
    * Represents the underlying data model for the table.
    */
    class PlayerDataTableModel extends AbstractTableModel {
        public int getRowCount() {
            return playerData.length;
        }

        public int getColumnCount() {
            return playerDataColumnNames.length;
        }

        public Object getValueAt(int row, int column) {
            return playerData[row][column];
        }

        public String getColumnName(int columnIndex) {

            return playerDataColumnNames[columnIndex];
        }
    }
    
    /**
    * The main driver program
    *
    */

    public static void main(String[] args) {
        boolean packFrame = false;
        try {
            printCopyright();
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            CourseClientFrame courseClientFrame = new CourseClientFrame(args);

            // Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = courseClientFrame.getSize();
            if ( frameSize.height > screenSize.height ) {
                frameSize.height = screenSize.height;
            }
            if ( frameSize.width > screenSize.width ) {
                frameSize.width = screenSize.width;
            }

            courseClientFrame.setLocation(
                                    (screenSize.width - frameSize.width) / 2,
                                    (screenSize.height - frameSize.height) / 2);
            courseClientFrame.setVisible(true);
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    
    protected static void printCopyright() {
        System.out.println("JHandicap V2.0 11/2008");
        System.out.println("JHandicap comes with ABSOLUTELY NO WARRANTY;");
        System.out.println("");
    }

	
}


