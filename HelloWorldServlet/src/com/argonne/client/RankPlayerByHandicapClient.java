package com.argonne.client;

import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.playerService.*;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
*
* @author William Gillund
* @since 11/2/2008
* @version 1.0
*/

public class RankPlayerByHandicapClient extends JFrame  {

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();

    JButton closeButton = new JButton();
    JButton addScoreButton = new JButton();
    JLabel messageLabel = new JLabel();
//  storage for player data
    
    JTable player;
    private AbstractTableModel playerDataTableModel;
    private Object[][] playerData = {};
    private String[] playerDataColumnNames = { "Name", "Handicap","Ranking" };
   
    PlayerService playerService = null;
    Hashtable playerUpdateConsumers = new Hashtable();
    HandicapClientFrame handicapClient = null;
    // Construct the frame
    public RankPlayerByHandicapClient() {
        try {
            init();
            initializeService();
            loadPlayerData(playerService.getPlayersByHandicap());
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
        this.setTitle("Shagnasty Open Handicap Rankings");

        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));

     
        playerDataTableModel = new PlayerDataTableModel();
        player = new JTable(playerDataTableModel);
        player.setPreferredScrollableViewportSize(new Dimension(525, 319));
        player.setShowGrid(true);
        JScrollPane playerSrollPane = new JScrollPane(player);

        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });
        
        addScoreButton.setText("Add Score");
        addScoreButton.setBounds(new Rectangle(229, 160, 150, 35));
        addScoreButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addScoreButton_actionPerformed(e);
            }
        });
        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(playerSrollPane, null);
        jPanel3.add(messageLabel,null);

        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(closeButton, null);
        jPanel4.add(addScoreButton,null);
       
    }

    private void initializeService() {
       playerService = JhandicapFactory.getPlayerService();
       handicapClient = JhandicapFactory.getHandicapClientFrame();
      
       
    }
    
    protected void loadPlayerData(ArrayList plyByAvg ) {
    	
       
        Object[][] newData = new Object[plyByAvg.size()][playerDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
        	PlayerHandicap ph = (PlayerHandicap) plyByAvg.get(i);        
            newData[i][0] =  ph.getPlayer();
            float a = ph.gethandicap();
            Float oA = new Float(a);
            newData[i][1] =  oA;
            newData[i][2] = ph.getRank();
         
        }

        messageLabel.setText("Total Number of Players = " + newData.length);
        
        // reset model
        playerData = newData;
        
        // instruct table to refresh its view since the underlying model has changed
        playerDataTableModel.fireTableDataChanged();
    }
    
    
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addScoreButton_actionPerformed(ActionEvent event) {
    	int rowNumber = player.getSelectedRow();
    	if (rowNumber < 0 ){
       	    JOptionPane.showMessageDialog(this,"Must select Row to remove Course");
       	    return;
       	 }
    	
     	String name = (String) player.getValueAt(rowNumber,0);
    	handicapClient.initNewPlayer(name);
    	handicapClient.loadCourses(); 
    	 
    	 handicapClient.setVisible(true);
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



