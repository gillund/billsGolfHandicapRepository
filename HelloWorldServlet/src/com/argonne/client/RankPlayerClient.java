package com.argonne.client;

import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.playerService.*;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceImpl;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.playerService.PlayerUpdateSupplier;
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
* @fyi use -DataFile=<filename> to override the default input file, players.dat
*
* @author William Gillund
* @since 11/2/2008
* @version 1.0
*/

public class RankPlayerClient extends JFrame  {

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JLabel messageText = new JLabel();

    JButton closeButton = new JButton();
    
//  storage for player data
    
    JTable player;
    private AbstractTableModel playerDataTableModel;
    private Object[][] playerData = {};
    private String[] playerDataColumnNames = { "Name", "Average Score", "Ranking" };
   
    PlayerService playerService = null;
    Hashtable playerUpdateConsumers = new Hashtable();

    // Construct the frame
    public RankPlayerClient() {
        try {
            init();
            initializeService();
            loadPlayerData(playerService.getPlayersByAverageRound());
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
        this.setTitle("Shagnasty Open average Ranking");

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

        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(playerSrollPane, null);
        jPanel3.add(messageText,null);

        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(closeButton, null);
       
    }

    private void initializeService() {
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

        
    protected void loadPlayerData(ArrayList plyByAvg ) {
    	
       
        Object[][] newData = new Object[plyByAvg.size()][playerDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
        	PlayerAverage pa = (PlayerAverage) plyByAvg.get(i);        
            newData[i][0] =  pa.Player;
            float a = pa.avg;
            Float oA = new Float(a);
            newData[i][1] =  oA;
            newData[i][2] = pa.getRank();
         
        }
        messageText.setText("Total number of Players = " + newData.length);
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
    

	
}



