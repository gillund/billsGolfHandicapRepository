package com.argonne.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.argonne.courseServices.CourseService;
import com.argonne.playerService.PlayerAverage;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.tournamentService.RyderCupPlayers;
import com.argonne.tournamentService.RyderCupService;
import com.argonne.tournamentService.SingleMatchPlayer;
import com.argonne.tournamentService.SinglesMatchService;
import com.argonne.tournamentService.Tournament;
import com.argonne.tournamentService.TournamentService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.JhandicapObjectPersist;
import com.argonne.utils.Log;

public class RyderCupManualFrame extends JFrame {
	
    JPanel contentPane;
    JTextField title = new JTextField(20);
    
    JLabel ryderCupPlayerLabel = new JLabel();
    
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JLabel messageLabel = new JLabel();
   
    JButton closeButton = new JButton();
    JButton redButton = new JButton();
    JButton blueButton = new JButton();
    JButton saveButton = new JButton();
    JButton getSaveButton = new JButton();
    String[] ryderCupPlayers = new String[0];
    JComboBox ryderCupPlayerBox = new JComboBox(ryderCupPlayers);

//  storage for player data
    RyderCupPlayers rcp =null;
    Hashtable playerRank = new Hashtable();
    
    JTable blueplayer;
    private AbstractTableModel blueplayerDataTableModel;
    private Object[][] blueplayerData = {};
    private String[] blueplayerDataColumnNames = { "Blue Team", "AVG score", "Handicap", "Ranking", "BLUE/RED", "Red Team", "AVG score","Handicap", "Ranking"};

    
    PlayerService playerService = null;
    TournamentService tournamentService = null;
    PlayerHandicapService playerHandicapService =null;
    CourseService courseService =null;
    SinglesMatchService singlesMatchService =null;
    RyderCupService ryderCupService = null;
    Hashtable playerUpdateConsumers = new Hashtable();
    HandicapClientFrame handicapClient = null;
    
    // Construct the frame
    
    public RyderCupManualFrame() {
        try {
            init();
            initializeService();
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
        this.setSize(new Dimension(1000 /*width*/, 700));
        this.setTitle("Shagnasty Open Ryder Cup Matches");
         
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
              
        blueplayerDataTableModel = new PlayerDataTableModel();
        blueplayer = new JTable(blueplayerDataTableModel);
        blueplayer.setPreferredScrollableViewportSize(new Dimension(900, 400));
        blueplayer.setShowGrid(true);
        JScrollPane blueplayerSrollPane = new JScrollPane(blueplayer);
        ryderCupPlayerLabel.setText("Ryder Cup Players: ");
        title.setText("   Shagnasty Ryder Cup Draft");
        title.setEditable(false);
        ryderCupPlayerBox.setBounds(new Rectangle(173, 50, 213, 29));
        ryderCupPlayerBox.setEditable(false);

        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });
        
        redButton.setText("Add Player Red Team");
        redButton.setBounds(new Rectangle(229, 160, 150, 35));
        redButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addRedButton_actionPerformed(e);
            }
        });
        
        blueButton.setText("Add Player Blue Team");
        blueButton.setBounds(new Rectangle(229, 160, 150, 35));
        blueButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addBlueButton_actionPerformed(e);
            }
        });
        saveButton.setText("Save Ryder Cup Team");
        saveButton.setBounds(new Rectangle(229, 160, 150, 35));
        saveButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveButton_actionPerformed(e);
            }
        });
        
        getSaveButton.setText("Retrieve Saved Ryder Cup Team");
        getSaveButton.setBounds(new Rectangle(229, 160, 150, 35));
        getSaveButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getSaveButton_actionPerformed(e);
            }
        });
        
        
        
        jPanel2.setToolTipText("");
       
        contentPane.add(jPanel2,null);
        jPanel2.add(title,null);
        jPanel3.setToolTipText("");
        
        contentPane.add(jPanel3, null);
   //     jPanel3.add(redBlueLabel,null);
        jPanel3.add(blueplayerSrollPane, null);
        jPanel3.add(messageLabel,null);
        jPanel3.add(blueplayerSrollPane, null);
        
        
        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(ryderCupPlayerLabel,null);
        jPanel4.add(ryderCupPlayerBox,null);
        jPanel4.add(blueButton,null);
        jPanel4.add(redButton,null);
        jPanel4.add(saveButton,null);
        jPanel4.add(getSaveButton,null);
        jPanel4.add(closeButton, null);
        
        jPanel5.setToolTipText("");
        contentPane.add(jPanel5, null);
        jPanel5.add(ryderCupPlayerLabel,null);
    }

    private void initializeService() {
       playerService = JhandicapFactory.getPlayerService();
       playerHandicapService = JhandicapFactory.getPlayerHandicapService();
       handicapClient = JhandicapFactory.getHandicapClientFrame();
       courseService = JhandicapFactory.getCourseService();
       singlesMatchService = JhandicapFactory.getSinglesMatchService();
       ryderCupService = JhandicapFactory.getRyderCupService();
       tournamentService = JhandicapFactory.getTournamentService();
      
       
    }
    
    public void loadPlayerData(RyderCupPlayers loadrcp ) {
    	
    	rcp = loadrcp;
    	
    	PlayerAverage[] paRed = rcp.getRedTeam();
        PlayerAverage[] paBlue = rcp.getBlueTeam();
        int l =0;
        if (paRed.length > paBlue.length){
        	l=paRed.length;
        }
        else{
        	l=paBlue.length;
        }
        Object[][] newData = new Object[l+1][blueplayerDataColumnNames.length ];
        
        for ( int i = 0; i < paBlue.length; i++ ) {
            newData[i][0] =  paBlue[i].Player;
            float a = paBlue[i].avg; 
            Float oA = new Float(a);
            newData[i][1] =  oA;
            
            float playerHandicap = playerHandicapService.getHandicap(paBlue[i].Player);
            newData[i][2] = playerHandicap;
            
            newData[i][3] = paBlue[i].getRank();
          }
        
        newData[rcp.getBlueNbrPlayers()][0] = "<Blue Average>";
        float blueAvg  = rcp.getTotalBlueTeamAverage();
        Float blueFloatAvg = new Float(blueAvg);
        newData [rcp.getBlueNbrPlayers()][1] = blueFloatAvg; 
        newData [rcp.getBlueNbrPlayers()] [2] = "";
        
         
        for ( int i = 0; i < paRed.length; i++ ) {
        	newData[i][4] = "<-BLUE/RED->";
            newData[i][5] =  paRed[i].Player;
            float a = paRed[i].avg; ;
            Float oA = new Float(a);
            newData[i][6] =  oA;
            
            float playerHandicap = playerHandicapService.getHandicap(paRed[i].Player);
            newData[i][7] = playerHandicap;
            
            newData[i][8] = paRed[i].getRank();
            
          }

        newData[rcp.getRedNbrPlayers()][4] = "<Red Average>";
        float RedAvg = rcp.getTotalRedTeamAveragge();
        Float RedFloatAvg = new Float(RedAvg);
        newData [rcp.getRedNbrPlayers()][5] = RedFloatAvg; 
        newData [rcp.getRedNbrPlayers()] [6] = "";
     
        // reset model
        blueplayerData = newData;
        // instruct table to refresh its view since the underlying model has changed
        blueplayerDataTableModel.fireTableDataChanged();
        
    }
    protected void initReLoadPlayerData(ArrayList <String> plyByAvg) {
    	 
    	     
        ArrayList newList = null;
        
        if (ryderCupService.getNumberOfRyderCupPlayers() == 0)
        {
            reLoadPlayerData(plyByAvg);
        }
        else
        {
        	newList = createNewList(plyByAvg);
            reLoadPlayerData(newList);
        }  
        
    }
    protected Boolean ryderCupTeamSaved(){
    	JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
	    RyderCupPlayers rcp = (RyderCupPlayers) jop.deserializeObject(jop.getRyderCupPlayerFile());
	    if (rcp == null){
	    	return false;
	    }
	    return true;
	   
    }
      private ArrayList createNewList(ArrayList<String> plyByAvg){
      
        // input to method is list of all players.  Then look in combo box.  If plyer is still there ADD him
        // if Not Do not add
        //
    	  
    	ArrayList newList = new ArrayList();
        for (int i=0; i<plyByAvg.size();i++){
    	  String plyer = plyByAvg.get(i);
    	  boolean addPlayer = false;
    	  for (int j=0; j<ryderCupPlayers.length; j++){
    		  if (ryderCupPlayers[j].startsWith(plyer)){
    			  addPlayer=true;
    		  }
    	  }
    	  if (addPlayer){
    		  newList.add(plyer);
    	  }
        }
        
        return newList;
    }
        
        
    protected void reLoadPlayerData(ArrayList <String> plyByAvg) {
	 
       ArrayList ph = null;
       ph = (ArrayList) playerService.getPlayersByAverageRound((plyByAvg));
   	  
   	   if (ryderCupService.getNumberOfRyderCupPlayers() == 0 )
       {
    	 for (int i=0; i<ph.size(); i++){
    		 PlayerAverage pa = (PlayerAverage) ph.get(i);
    		 playerRank.put(pa.Player,pa.getRank());
    	 }
       }
       
      
	   ryderCupPlayerBox.removeAllItems();
       ryderCupPlayers = new String[ph.size()];
       Iterator i = ph.iterator();
       int ndx =0;
       while ( i.hasNext() ) {
         PlayerAverage player = (PlayerAverage) i.next();
         String fullName = player.Player + "<" + player.avg + "," + playerRank.get(player.Player) + ">" ;
         ryderCupPlayers[ndx] = fullName;
         ndx++;
         ryderCupPlayerBox.addItem(fullName);
       }
       
       // set text info
       JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
       ryderCupPlayerLabel.setText( " Shagnasty Players To Be Selected --> " + ryderCupPlayers.length + "<-- " );
       
       if (blueplayerData.length == 0 ){
    	   if(jop.areRyderCupPlaersPersisted(jop.getRyderCupPlayerFile())){
    		   ryderCupPlayerLabel.setText("!!!!!!  WARNING !!!!!!! --> YOU HAVE PLAYERS SAVED <-- !!!!!!  WARNING !!!!!!!");
    	   }
    	   
       }

}
       
    protected void loadPlayerDataFromSmp(ArrayList < SingleMatchPlayer> singleMatchPlayer, String inCourseName ) {
        
    }  
  
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addBlueButton_actionPerformed(ActionEvent event) {
    	
    	int playerNdx = ryderCupPlayerBox.getSelectedIndex();
    	if (playerNdx < 0){
    		JOptionPane.showMessageDialog(this, " All Players Have been Assigned a team " ); 
            
    	}
    	else {
    		goAddBluePlayerToTeam();
    	}
    }
    
    private void goAddBluePlayerToTeam(){
    	
    	RyderCupPlayers ryderCupPlayersadded = null;
    	int playerNdx = ryderCupPlayerBox.getSelectedIndex();
    	String playerName =  ryderCupPlayers[playerNdx];
    	int ndx = playerName.indexOf('<');
    	String player = playerName.substring(0,ndx);
    	int rankNdx = playerName.lastIndexOf(',');
    	String rank = playerName.substring(rankNdx+1, playerName.length()-1);
    	PlayerAverage pa = playerService.getPlayerAverage(player);
    	char charRank = rank.charAt(0);
    	pa.setRank(charRank);
    	if (pa != null){
    		try{
    	      ryderCupPlayersadded = ryderCupService.addManualBlueTeamPlayer(pa);
    	      loadPlayerData(ryderCupPlayersadded); 
    	    	
    		}
    		catch (Exception e){
    			JOptionPane.showMessageDialog(this, player + " is on " + ryderCupService.playerTeam(player) + "Team");
              	  		
    		}
    	}
    	else{
    		JOptionPane.showMessageDialog(this, "Player Name not found = " + player);
    		
    	}
    
    	reLoadPlayerData(reLoadPlayerComboBox(player));
    	
    }
    	
    void addRedButton_actionPerformed(ActionEvent event) {
    	
    	int playerNdx = ryderCupPlayerBox.getSelectedIndex();
    	if (playerNdx < 0){
    		JOptionPane.showMessageDialog(this, " All Players Have been Assigned a team " ); 
            
    	}
    	else {
    		goAddRedPlayerToTeam();
    	}
    }
    	
    
    private void goAddRedPlayerToTeam(){
        
    	
    	RyderCupPlayers ryderCupPlayersadded = null;
    	int playerNdx = ryderCupPlayerBox.getSelectedIndex();
    	
    	String playerName =  ryderCupPlayers[playerNdx];
    	int ndx = playerName.indexOf('<');
    	String player = playerName.substring(0,ndx);
    	
    	int rankNdx = playerName.lastIndexOf(',');
    	String rank = playerName.substring(rankNdx+1, playerName.length()-1);
    	
    	PlayerAverage pa = playerService.getPlayerAverage(player);
    	char charRank = rank.charAt(0);
    	pa.setRank(charRank);
    	
    	if (pa != null){
    		try{
      	      ryderCupPlayersadded = ryderCupService.addManualRedTeamPlayer(pa);
      	 	  loadPlayerData(ryderCupPlayersadded);
      		}
      		catch (Exception e){
      			JOptionPane.showMessageDialog(this, player + " is on " + ryderCupService.playerTeam(player) + "Team");
          		
      		}
     	}
    	else{
    		JOptionPane.showMessageDialog(this, "Player Name not found = " + player);
    		
    	}   
    	
    	reLoadPlayerData(reLoadPlayerComboBox(player));
    	
   
    }
    
    private ArrayList reLoadPlayerComboBox (String player){
    	
       ArrayList newList = new ArrayList();
	   for (int i=0 ; i<ryderCupPlayers.length; i++){
		 if (!ryderCupPlayers[i].startsWith(player)){
			 
			 String playerName =  ryderCupPlayers[i];
		     int ndx = playerName.indexOf('<');
		     String newplayer = playerName.substring(0,ndx);
		     newList.add(newplayer);
			
		 }
	   }
	   return newList;
    }
	
   void saveButton_actionPerformed(ActionEvent event) {
	   
    	JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
    	jop.serializeObject(ryderCupService.getManualRyderCupTeam(), jop.getRyderCupPlayerFile());
    	
    	RyderCupPlayers object = (RyderCupPlayers) jop.deserializeObject(jop.getRyderCupPlayerFile());
    	
    	JOptionPane.showMessageDialog(this, "Shagnasty Ryder Cup Saved ");
    	
    	
    	
    }
    
   void getSaveButton_actionPerformed(ActionEvent event) {
	   
   	   JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
   	   RyderCupPlayers rcp = (RyderCupPlayers) jop.deserializeObject(jop.getRyderCupPlayerFile());
   	   
   	   if (rcp != null)
   	   {
   		  ryderCupService.resetManualRyderCupTeam(rcp);
      	  loadPlayerData(rcp);
   	      reLoadPlayerData(removePlayersInComboBoxLoadedToPlayerData());
   	   }
   	   else
   	   {
   		JOptionPane.showMessageDialog(this, "No Players Have Been Saved ");   
   	   }
   	
   	
   }
     
     public ArrayList removePlayersInComboBoxLoadedToPlayerData() {
    	 
    	 Hashtable tempPlayers = null;
    	 tempPlayers = tournamentService.getAllTournaments();
    	 
    	 RyderCupPlayers rcp = ryderCupService.getManualRyderCupTeam();
    	 PlayerAverage[] paBlue = rcp.getBlueTeam();
    	 PlayerAverage[] paRed =  rcp.getRedTeam();
    	 
    	 for (int i=0; i<paBlue.length; i++){
    		 tempPlayers.remove(paBlue[i].Player);
    	 }
    	 for (int i=0; i<paRed.length; i++){
    		 tempPlayers.remove(paRed[i].Player);
    	 }
    	 ArrayList al = new ArrayList();
    	 Enumeration e = tempPlayers.elements();
    	 
    	 while(e.hasMoreElements()){
    		 Tournament o = (Tournament) e.nextElement();
    		 al.add(o.playerName);
    	 }
    	 return al;
    	 
    	 
    	 
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
            return blueplayerData.length;
        }

        public int getColumnCount() {
            return blueplayerDataColumnNames.length;
        }

        public Object getValueAt(int row, int column) {
            return blueplayerData[row][column];
        }

        public String getColumnName(int columnIndex) {

            return blueplayerDataColumnNames[columnIndex];
        }
    }
}
