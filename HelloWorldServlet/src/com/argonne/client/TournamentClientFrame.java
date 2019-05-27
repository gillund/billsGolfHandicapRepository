package com.argonne.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

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
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceImpl;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.tournamentService.RyderCupService;
import com.argonne.tournamentService.SingleMatchPlayer;
import com.argonne.tournamentService.SinglesMatchService;
import com.argonne.tournamentService.Tournament;
import com.argonne.tournamentService.TournamentService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.JhandicapObjectPersist;
import com.argonne.utils.Log;

public class TournamentClientFrame extends JFrame implements CourseUpdateConsumer {
	
	
	String courseNameSelected = null;
	String[] coursesData = new String[0];
	JComboBox courses = new JComboBox(coursesData);
	   
    JPanel contentPane;	
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    
    JButton addTournamentButton = new JButton();
    JButton removeTournamentButton = new JButton ();
    JButton removeWholeTournamentButton = new JButton ();
    
    JButton rankByAvgButton = new JButton ();
    JButton rankByHcpButton = new JButton ();
    JButton ryderCupButton = new JButton ();
    JButton manualRyderButton = new JButton();
    JButton displayRyderTeamButton  =new JButton();
    JButton deleteRyderTeamButton  =new JButton();
    JButton refreshAvgsButton  =new JButton();
    
    
    JButton closeButton = new JButton();
    JLabel messageLabel = new JLabel();
    JLabel ryderCupLabel = new JLabel();
    JLabel singlesMatchLabel = new JLabel();
    JLabel shangNastyTitleabel  = new JLabel();
    JTable tournament;
    private AbstractTableModel tournamentDataTableModel;
    private Object[][] tournamentData = {};
    private String[] tournamentDataColumnNames = { "Player", "Average Score", "Handicap" };
   
    TournamentService tournamentService = null;
    CourseService courseService = null;
    PlayerService playerService =null;
    PlayerHandicapService playerHandicapService = null;
    RankPlayerClient rankPlayerClient =null;
    RankPlayerByHandicapClient rankPlayerByHandicapClient =null;
    SinglesMatchFrame singlesMatchFrame =null;
    SinglesMatchService singlesMatchService =null;
    RyderCupFrame ryderCupFrame =null;
    RyderCupManualFrame ryderCupManualFrame =null;
    RyderCupService ryderCupService =null;
    Hashtable tournamentUpdateConsumers = new Hashtable();
    String title;
    // Construct the frame
    public TournamentClientFrame(String atitle) {
        try {
        	title =atitle;
            init();
            initializeService();
            loadAllTournamentPlayers();
            loadCourses();
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
        this.setSize(new Dimension(750 /*width*/,800));
        this.setTitle(title);

        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel5.setLayout(new FlowLayout(FlowLayout.CENTER));
        
  
        tournamentDataTableModel = new TournamentDataTableModel();
        tournament = new JTable(tournamentDataTableModel);
        tournament.setPreferredScrollableViewportSize(new Dimension(525, 400));
        tournament.setShowGrid(true);
        JScrollPane playerSrollPane = new JScrollPane(tournament);

        
        
        removeTournamentButton.setText("Remove Player from tournament");
        removeTournamentButton.setBounds(new Rectangle(19, 160, 150, 35));
        removeTournamentButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeTournamentButton_actionPerformed(e);
            }
        });
        removeWholeTournamentButton.setText("Remove All and restart ");
        removeWholeTournamentButton.setBounds(new Rectangle(19, 160, 150, 35));
        removeWholeTournamentButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeWholeTournamentButton_actionPerformed(e);
            }
        });
        refreshAvgsButton.setText("Refresh handicaps ");
        refreshAvgsButton.setBounds(new Rectangle(19, 160, 150, 35));
        refreshAvgsButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                refreshAvgsButton_actionPerformed(e);
            }
        });
        ryderCupLabel.setText("**************************************  Shagnasty Ryder Cup Draft  ***************************************");
        
        rankByAvgButton.setText("Rank Players by Avg");
        rankByAvgButton.setBounds(new Rectangle(19, 160, 150, 35));
        rankByAvgButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	rankByAvgButton_actionPerformed(e);
            }
        });
        rankByHcpButton.setText("Rank Players by Handicap");
        rankByHcpButton.setBounds(new Rectangle(19, 160, 150, 35));
        rankByHcpButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	rankByHcpButton_actionPerformed(e);
            }
        });
        addTournamentButton.setText("Create Singles Match");
        addTournamentButton.setBounds(new Rectangle(49, 160, 150, 35));
        addTournamentButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addSinglesMatchButton_actionPerformed(e);
            }
        });
        courses.setBounds(new Rectangle(173, 50, 213, 29));
        courses.setEditable(false);
        
        ryderCupButton.setText("Computer Selected RyderCup Team");
        ryderCupButton.setBounds(new Rectangle(19, 160, 150, 35));
        ryderCupButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	ryderCupButton_actionPerformed(e);
            }
        });
        manualRyderButton.setText("Manual RyderCup Team");
        manualRyderButton.setBounds(new Rectangle(19, 160, 150, 35));
        manualRyderButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	manualRyderCupButton_actionPerformed(e);
            }
        });

        
        
        displayRyderTeamButton.setText("Display Saved RyderCup Teams");
        displayRyderTeamButton.setBounds(new Rectangle(19, 160, 150, 35));
        displayRyderTeamButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	displayRyderTeamButton_actionPerformed(e);
            }
        });
        
        deleteRyderTeamButton.setText("Delete Saved RyderCup Team");
        deleteRyderTeamButton.setBounds(new Rectangle(19, 160, 150, 35));
        deleteRyderTeamButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	deleteRyderTeamButton_actionPerformed(e);
            }
        });
        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });
        

        jPanel1.setToolTipText("");
        shangNastyTitleabel.setText("Shagnasty 2009 Tournament");
        jPanel2.setToolTipText("");
        jPanel3.setToolTipText("");
        jPanel4.setToolTipText("");
        jPanel1.add(shangNastyTitleabel,null);
        jPanel2.add(playerSrollPane, null);
        jPanel2.add(messageLabel,null);
    
        jPanel3.add(removeTournamentButton, null);
        jPanel3.add(removeWholeTournamentButton,null);
        jPanel3.add(refreshAvgsButton,null);
        jPanel3.add(ryderCupLabel,null);
        
        // *** note *** to add in ranking tournament team by AVG and HDP just un-comment out lines below
        /*
        jPanel4.add(rankByAvgButton, null);
        jPanel4.add(rankByHcpButton, null);
        */
  
        jPanel4.add(ryderCupButton,null);
        jPanel4.add(manualRyderButton,null);
        jPanel4.add(displayRyderTeamButton,null);
        jPanel4.add(deleteRyderTeamButton,null);
        
        singlesMatchLabel.setText("************************************** Handicap Singles Matches  ***************************************");
        jPanel5.add(singlesMatchLabel,null);
        jPanel5.add(addTournamentButton, null);
        jPanel5.add(courses, null);
       
        
        jPanel5.add(closeButton, null);
        
        contentPane.add(jPanel1, null);
        contentPane.add(jPanel2, null);
        contentPane.add(jPanel3, null);
        contentPane.add(jPanel4, null);
        contentPane.add(jPanel5,null);
      
    }

    private void initializeService() {
    	
    	tournamentService = JhandicapFactory.getTournamentService();
    	playerService = JhandicapFactory.getPlayerService();
    	playerHandicapService = JhandicapFactory.getPlayerHandicapService();
    	courseService = JhandicapFactory.getCourseService();
    	rankPlayerClient   =  JhandicapFactory.getRankPlayerClient();
    	rankPlayerByHandicapClient = JhandicapFactory.getRankPlayerByHandicapClient();
    	singlesMatchFrame = JhandicapFactory.getSinglesMatchFrame();
    	singlesMatchService = JhandicapFactory.getSinglesMatchService();
    	ryderCupFrame = JhandicapFactory.getRyderCupFrame();
    	ryderCupManualFrame = JhandicapFactory.getRyderCupManualFrame();
    	ryderCupService = JhandicapFactory.getRyderCupService();
        
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

    
    
    protected void loadAllTournamentPlayers()  {
    	
    	Hashtable tempPlayers = null;
    	if (!(tournamentService.arePlayersAssignedToTournament())){
           tempPlayers = playerService.getAllPlayers();
           loadTournamaentDataFromAllPlayers(tempPlayers);
           ArrayList newPlayers = addPlayersToTournamentDB(tempPlayers);
           try{
              tournamentService.addPlayersToTournament(newPlayers);
           }
           catch (Exception e){
        	  Log.information("Error trying to save Tournament data to DB");   
           }
    	}
    	else{
    		tempPlayers = tournamentService.getAllTournaments();
    		loadTournamaentDataFromDB(tempPlayers);
    	}
    	
    	 messageLabel.setText( tempPlayers.size() + " Players registered for 2009 SHAGNASTY OPEN" );
         
    	
     }
       
    
    protected void loadTournamaentDataFromAllPlayers(Hashtable tmpPlayers) {
    	
        Vector sortedTournaments = sortTournaments(tmpPlayers);
     
        Object[][] newData = new Object[sortedTournaments.size()][tournamentDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
            Player atournament = (Player) sortedTournaments.elementAt(i);
            newData[i][0] =  atournament.firstName;
            newData[i][1] =  playerHandicapService.getAverage(atournament.firstName);
            newData[i][2] =  playerHandicapService.getHandicap(atournament.firstName);
           
        }

        // reset model
        tournamentData = newData;

        // instruct table to refresh its view since the underlying model has changed
        tournamentDataTableModel.fireTableDataChanged();
    }
    
    protected void loadTournamaentDataFromDB(Hashtable tmpPlayers) {
    	
        Vector sortedTournaments = sortTournamentsFromDB(tmpPlayers);
     
        Object[][] newData = new Object[sortedTournaments.size()][tournamentDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
            Tournament atournament = (Tournament) sortedTournaments.elementAt(i);
            newData[i][0] =  atournament.playerName;
            newData[i][1] =  atournament.avg;
            newData[i][2] =  atournament.handicap;
           
        }

        // reset model
        tournamentData = newData;

        // instruct table to refresh its view since the underlying model has changed
        tournamentDataTableModel.fireTableDataChanged();
    }
    
    
    protected Vector sortTournaments(Hashtable tempPlayers) {
        Vector sortedPlayers = new Vector();
        Enumeration enumeration = tempPlayers.elements();
        while ( enumeration.hasMoreElements() ) {
            Player tournament1 = (Player) enumeration.nextElement();
            boolean notInserted = true;
            for ( int i = 0 ; i < sortedPlayers.size(); i++ ) {
                Player tournament2 = (Player) sortedPlayers.elementAt(i);
                String tournament1FullName = tournament1.firstName ;
                String tournament2FullName =  tournament2.firstName;
                if ( tournament1FullName.compareTo(tournament2FullName) < 0 ) {
                    sortedPlayers.add(i, tournament1);
                    notInserted = false;
                    break;
                }
            }
            if ( notInserted ) {
                sortedPlayers.add(tournament1);
            }
        }
        return sortedPlayers;
    }
    
    protected Vector sortTournamentsFromDB(Hashtable tempPlayers) {
        Vector sortedPlayers = new Vector();
        Enumeration enumeration = tempPlayers.elements();
        while ( enumeration.hasMoreElements() ) {
            Tournament tournament1 = (Tournament) enumeration.nextElement();
            boolean notInserted = true;
            for ( int i = 0 ; i < sortedPlayers.size(); i++ ) {
                Tournament tournament2 = (Tournament) sortedPlayers.elementAt(i);
                String tournament1FullName = tournament1.playerName ;
                String tournament2FullName =  tournament2.playerName;
                if ( tournament1FullName.compareTo(tournament2FullName) < 0 ) {
                    sortedPlayers.add(i, tournament1);
                    notInserted = false;
                    break;
                }
            }
            if ( notInserted ) {
                sortedPlayers.add(tournament1);
            }
        }
        return sortedPlayers;
    }
    
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addSinglesMatchButton_actionPerformed(ActionEvent event)  {
    	
    	ArrayList playerList = new ArrayList();
    	ArrayList playersRanked = null;
    	courseNameSelected = coursesData[courses.getSelectedIndex()];
    	
    	if (singlesMatchService.arePlayersAssingedToSinglesMatch(courseNameSelected)){
    		Hashtable allPlayers = singlesMatchService.getAllSingleMatchPlayers(courseNameSelected);
    		Enumeration enumeration = allPlayers.elements();
	        while ( enumeration.hasMoreElements() ) {
	            SingleMatchPlayer player = (SingleMatchPlayer) enumeration.nextElement();
	            playerList.add(player);
	        }
	        try{
	     	   singlesMatchService.addPlayersToMatchSMP(courseNameSelected, playerList);
	     	   playersRanked = singlesMatchService.setPlayerRanking(courseNameSelected);
	     	}
	     	catch(Exception e){
	     		Log.alarm("BAD ERROR SINGLES MATCH HASHMAP NOT FOUND");
	     	}
	     	singlesMatchFrame.loadPlayerDataFromSmp(playersRanked,courseNameSelected);        
    	}
    	else
    	{  
    		//*
    		//**> initial... load ... need avg score and avg handicap
    		//*
    		
		   for ( int i = 0; i < tournamentData.length; i++ ) {
			   String name = (String) tournamentData[i][0] ;
	    	   playerList.add(name);
           }
		   try{
	    	   singlesMatchService.addPlayersToMatch(courseNameSelected, playerService.getPlayersByHandicap(playerList));
	    	}
	    	catch(Exception e){
	    		Log.alarm("BAD ERROR SINGLES MATCH HASHMAP NOT FOUND");
	    	}
	    	singlesMatchFrame.loadPlayerData( playerService.getPlayersByHandicap(playerList));
	   	 
    	}
    	
    	singlesMatchFrame.setCouseName(courseNameSelected);
    	singlesMatchFrame.setVisible(true);
    	
    	
    }
    
    void removeTournamentButton_actionPerformed(ActionEvent event) {
    	
    	 int rowNumberToDelete = tournament.getSelectedRow();
    	 if (rowNumberToDelete < 0 ){
    	    JOptionPane.showMessageDialog(this,"Must select Row to remove Tournament");
    	    return;
    	 }
    	 String name = (String) tournament.getValueAt(rowNumberToDelete,0);
    	 Hashtable newPlayers = null;
    	 try {
            newPlayers = getTournamentService().removeTournament(name);
         }
         catch (Exception e)
         {
         	JOptionPane.showMessageDialog(this,"Player Not found in Db");
            return;
         }
         
         messageLabel.setText(newPlayers.size() + " Players saved to tournament");
         loadAllTournamentPlayers();
 
    }
    void removeWholeTournamentButton_actionPerformed(ActionEvent event) {
    	 String value = JOptionPane.showInputDialog(this, "Type Confirm to Delete");
    	 if (value !=null){
    	    if (value.equalsIgnoreCase("Confirm")){
    		   JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
    	    	  if (jop.purgedRyderCupPlyers(jop.getTournamentFile())){
    	    		JOptionPane.showMessageDialog(this, "Tournament has been purged " );
    	    		 this.setVisible(false);
    	    	  }
    	    	  else
    	    	  {
    	    		JOptionPane.showMessageDialog(this,"Tournament already deleted");
    	    	  }
    	    }
    	  //  JOptionPane.showMessageDialog(this,"Not Deleted - need Confirm for delete");
    	 }
    	
   	 
   }
    
    void refreshAvgsButton_actionPerformed(ActionEvent event) {
    	
    	refreshHandicapsAndAverages();
        JOptionPane.showMessageDialog(this,"Tournament averages and handicaps have been reset");
     	
  }
    
    public void refreshHandicapsAndAverages(){
    	 tournamentService.updateTournamentAverages();
   	     loadAllTournamentPlayers();
   	    
    }
    
    
    void rankByAvgButton_actionPerformed(ActionEvent event) {
    	
    	ArrayList playerList = new ArrayList();
		for ( int i = 0; i < tournamentData.length; i++ ) {
			String name = (String) tournamentData[i][0] ;
	    	playerList.add(name);
        }
    	rankPlayerClient.loadPlayerData( playerService.getPlayersByAverageRound(playerList));
    	rankPlayerClient.setVisible(true);
   	 
   }
    
    void rankByHcpButton_actionPerformed(ActionEvent event) {
    	
    	ArrayList playerList = new ArrayList();
		for ( int i = 0; i < tournamentData.length; i++ ) {
			String name = (String) tournamentData[i][0] ;
	    	playerList.add(name);
        }
    	rankPlayerByHandicapClient.loadPlayerData( playerService.getPlayersByHandicap(playerList));
    	rankPlayerByHandicapClient.setVisible(true);
   	 
   }
    
     protected void ryderCupButton_actionPerformed(ActionEvent event) {
    	 
    	 ArrayList playerList = new ArrayList();
    	 
    	 for ( int i = 0; i < tournamentData.length; i++ ) {
			   String name = (String) tournamentData[i][0] ;
	    	   playerList.add(name);
         }
		 ryderCupFrame.loadPlayerData(playerList);
	     ryderCupFrame.setVisible(true);
     }
     protected void manualRyderCupButton_actionPerformed(ActionEvent event) {
    	 
    	 ArrayList playerList = new ArrayList();
    	 
    	 for ( int i = 0; i < tournamentData.length; i++ ) {
			   String name = (String) tournamentData[i][0] ;
	    	   playerList.add(name);
         }
    	 
		 ryderCupManualFrame.initReLoadPlayerData(playerList);
	     ryderCupManualFrame.setVisible(true);
     }
     
     protected void displayRyderTeamButton_actionPerformed(ActionEvent event) {
    	 
        ArrayList playerList = new ArrayList();
        for ( int i = 0; i < tournamentData.length; i++ ) {
			   String name = (String) tournamentData[i][0] ;
	    	   playerList.add(name);
         }
        try{
		 ryderCupFrame.loadPlayerDataFrompersistance(playerList);
	     ryderCupFrame.setVisible(true);
        }
        catch(Exception e){
        	JOptionPane.showMessageDialog(this, "RyderCup Team has not been Saved");
        }
     }
     
     
     
     protected void deleteRyderTeamButton_actionPerformed(ActionEvent event) {
    	 
    	 String value = JOptionPane.showInputDialog(this, "Type Confirm to Delete");
    	 if (value !=null){
    	    if (value.equalsIgnoreCase("Confirm")){
    		   JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
    	    	  if (jop.purgedRyderCupPlyers(jop.getRyderCupPlayerFile())){
    	    		ryderCupService.removeRyderCupFromCache();
    	    		ryderCupManualFrame.loadPlayerData(ryderCupService.getManualRyderCupTeam());
    	    		JOptionPane.showMessageDialog(this, "Ryder Cup Team has been Purged" );
    	    	  }
    	    	  else
    	    	  {
    	    		JOptionPane.showMessageDialog(this,"Ryder Cup Team Already Deleted");
    	    	  }
    	    }
    	    else
    	    {
    	    	JOptionPane.showMessageDialog(this,"Not Deleted - need Confirm for delete");
    	    }
    	  
    	 }
    		
     }
    
    protected PlayerService getPlayerService() {
        if ( playerService == null ) {
            playerService = new PlayerServiceImpl();
        }

        return playerService;
    }
    protected TournamentService getTournamentService() {
       return JhandicapFactory.getTournamentService();
    }

    protected void publishTournamentUpdates(Hashtable playerCollection) {
        Enumeration enumeration = tournamentUpdateConsumers.elements();
        while ( enumeration.hasMoreElements() ) {
        	PlayerUpdateConsumer player = (PlayerUpdateConsumer) enumeration.nextElement();
            player.acceptPlayer(playerCollection);
        }
    }
    private ArrayList addPlayersToTournamentDB(Hashtable players){
    	
    	ArrayList playerList = new ArrayList();
    	Enumeration enumeration = players.elements();
    	
    	while ( enumeration.hasMoreElements() ) {
    		  Player atournament = (Player) enumeration.nextElement();
    	      String name   =  atournament.firstName;
              Float avg     =  playerHandicapService.getAverage(atournament.firstName);
              Float hcap =     playerHandicapService.getHandicap(atournament.firstName);
              Tournament t = new Tournament(name,avg.floatValue(),hcap.floatValue());
              playerList.add(t); 
    	 }
    	
    	return playerList;
    }
    /**
    * Represents the underlying data model for the table.
    */
    class TournamentDataTableModel extends AbstractTableModel {
        public int getRowCount() {
            return tournamentData.length;
        }

        public int getColumnCount() {
            return tournamentDataColumnNames.length;
        }

        public Object getValueAt(int row, int column) {
            return tournamentData[row][column];
        }

        public String getColumnName(int columnIndex) {

            return tournamentDataColumnNames[columnIndex];
        }
    }
	public void acceptCourses(Hashtable courseCollection) {
	      loadCourses(courseCollection);
	     
		
	}
	
	protected void loadCourses() {
        Hashtable tempCourses = courseService.getAllCourses();
        loadCourses(tempCourses);
    }
	 protected void loadCourses(Hashtable tempCourses) {
	        Vector sortedCourses = sortCourses(tempCourses);
	        courses.removeAllItems();
	        coursesData = new String[sortedCourses.size()];
	        Enumeration enumeration = sortedCourses.elements();
	        int i = 0;
	        while ( enumeration.hasMoreElements() ) {
	            Course course = (Course) enumeration.nextElement();
	            Log.debug(course.name + ", " + course.slope + ", " + course.rating + ", " + course.yardage + ", " + course.comments);
	            coursesData[i++] = course.name;
	            courses.addItem(course.name + " (R: " + course.rating + "  S: " + course.slope + ")");
	        }
	    }
	   protected Vector sortCourses(Hashtable tempCourses) {
	        Vector sortedCourses = new Vector();
	        Enumeration enumeration = tempCourses.elements();
	        while ( enumeration.hasMoreElements() ) {
	            Course course1 = (Course) enumeration.nextElement();
	            boolean notInserted = true;
	            for ( int i = 0 ; i < sortedCourses.size(); i++ ) {
	                Course course2 = (Course) sortedCourses.elementAt(i);
	                if ( course1.name.compareTo(course2.name) < 0 ) {
	                    sortedCourses.add(i, course1);
	                    notInserted = false;
	                    break;
	                }
	            }
	            if ( notInserted ) {
	                sortedCourses.add(course1);
	            }
	        }
	        return sortedCourses;
	    }
	
}



