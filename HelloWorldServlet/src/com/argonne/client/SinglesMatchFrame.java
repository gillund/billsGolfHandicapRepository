package com.argonne.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.playerService.PlayerHandicap;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.tournamentService.SingleMatchPlayer;
import com.argonne.tournamentService.SinglesMatchService;
import com.argonne.tournamentService.TournamentService;
import com.argonne.utils.DuplicateException;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

/**
*
* @author William Gillund
* @since 11/2/2008
* @version 1.0
*/

public class SinglesMatchFrame extends JFrame  {
    
	String courseNameOnly = null;
    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JLabel messageLabel = new JLabel();
    PlayerHandicapService playerHandicapService =null;
    
    JTextField dateText = new JTextField(7);
    JLabel dateLabel = new JLabel();
    
    JTextField courseName = new JTextField(35);
    JLabel courseLabel = new JLabel();
    
    
    JButton closeButton = new JButton();
    JButton addScoreButton = new JButton();
    JButton removeButton = new JButton();
    JButton postScoreButton = new JButton();
    
//  storage for player data
    
    JTable player;
    private AbstractTableModel playerDataTableModel;
    private Object[][] playerData = {};
    private String[] playerDataColumnNames = { "Name", "Handicap","Ranking","Score", "Adjusted Score", "Lowest Score" };
    TournamentClientFrame tournamentClientFrame = null;
    PlayerService playerService = null;
    CourseService courseService =null;
    SinglesMatchService singlesMatchService =null;
    Hashtable playerUpdateConsumers = new Hashtable();
    HandicapClientFrame handicapClient = null;
    TournamentService tournamentService = null;
    // Construct the frame
    
    public SinglesMatchFrame() {
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
        this.setSize(new Dimension(620 /*width*/, 525));
        this.setTitle("Shagnasty Open Singles Matches");

        dateLabel.setText("Date:");
        dateLabel.setBounds(new Rectangle(26, 93, 139, 23));
        dateText.setBounds(new Rectangle(172, 87, 215, 29));
        dateText.setEditable(false);
        dateText.setText("");
        
        courseLabel.setText("Course:");
        courseLabel.setBounds(new Rectangle(26, 93, 139, 23));
        courseName.setBounds(new Rectangle(172, 87, 215, 29));
        courseName.setEditable(false);
        courseName.setText("This is where the course name will go");
        
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        
        removeButton.setText("Remove Player");
        removeButton.setBounds(new Rectangle(229, 160, 150, 35));
        removeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeButton_actionPerformed(e);
            }
        });
        
        
        postScoreButton.setText("Post Scores Db");
        postScoreButton.setBounds(new Rectangle(229, 160, 150, 35));
        postScoreButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                postScoreButton_actionPerformed(e);
            }
        });
        
        jPanel2.setToolTipText("");
        jPanel2.add(dateLabel,null);
        jPanel2.add(dateText,null);
        jPanel2.add(courseLabel,null);
        jPanel2.add(courseName,null);
        
        contentPane.add(jPanel2,null);
        
        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(playerSrollPane, null);
        jPanel3.add(messageLabel,null);
        
        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(closeButton, null);
        jPanel4.add(addScoreButton,null);
        jPanel4.add(removeButton,null);
        jPanel4.add(postScoreButton,null);
        
    }

    private void initializeService() {
       playerService = JhandicapFactory.getPlayerService();
       handicapClient = JhandicapFactory.getHandicapClientFrame();
       courseService = JhandicapFactory.getCourseService();
       singlesMatchService = JhandicapFactory.getSinglesMatchService();
       playerHandicapService = JhandicapFactory.getPlayerHandicapService();
       tournamentService = JhandicapFactory.getTournamentService();
       
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
            newData[i][3] =0;
            newData[i][4] =0;
            newData[i][5] = "";
            
        }
        

        // reset model
        playerData = newData;
        messageLabel.setText( plyByAvg.size() + " Players playing Single Matches" );
        // instruct table to refresh its view since the underlying model has changed
        playerDataTableModel.fireTableDataChanged();
    }
    
       
    protected void loadPlayerDataFromSmp(ArrayList < SingleMatchPlayer> singleMatchPlayer, String inCourseName ) {
        
    	courseNameOnly=inCourseName;
        
        Object[][] newData = new Object[singleMatchPlayer.size()][playerDataColumnNames.length];
   
        for ( int i = 0; i < newData.length; i++ ) {
            SingleMatchPlayer atournament = (SingleMatchPlayer) singleMatchPlayer.get(i);
            newData[i][0] =  atournament.playerName;
            newData[i][1] =  atournament.handicap;
            newData[i][2] =  atournament.rank;
            newData[i][3] =  atournament.score;
            newData[i][4] =  atournament.adjScore;
        }
        
        ArrayList lowest = singlesMatchService.calculateLowScoreByRank(courseNameOnly);
        for (int i =0;i< lowest.size(); i++){
        	SingleMatchPlayer l = (SingleMatchPlayer) lowest.get(i);
        	for (int j=0; j<newData.length; j++){
        		SingleMatchPlayer data = (SingleMatchPlayer) singleMatchPlayer.get(j);
        		if (l.playerName.equals(data.playerName)){
        			newData[j][5] = "Lowest";
        		}
        	}
        }

        // reset model
        playerData = newData;
        messageLabel.setText( singleMatchPlayer.size() + " Players playing Single Matches" );
        // instruct table to refresh its view since the underlying model has changed
        playerDataTableModel.fireTableDataChanged();
    }
    
    
       
    public void setCouseName(String selectedCourseName ) {
    	
       courseNameOnly = (String) selectedCourseName;
    	
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Calendar cal = Calendar.getInstance();
   
       String date = sdf.format(cal.getTime());
       dateText.setText(date);
       
       Course course = courseService.getCourse(selectedCourseName);
       String newCourseName =  (selectedCourseName + " (R: " + course.rating + "  S: " + course.slope + ")");
       courseName.setText(newCourseName); 
       
    }
    
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addScoreButton_actionPerformed(ActionEvent event) {
    	
    	int rowNumber = 0 ; String Stringscore;	Boolean good =false; int score =0;
    	
    	rowNumber = player.getSelectedRow();
    	if ( rowNumber < 0 ){
               JOptionPane.showMessageDialog(this,"Must select row to add score " );
      	 }
    	else {
    		processSelectedRow(rowNumber);
    	}
    	
    	
    }
    
    void removeButton_actionPerformed(ActionEvent event) {
    	
    	int rowNumber = 0 ; String Stringscore;	Boolean good =false; int score =0;
    	
    	rowNumber = player.getSelectedRow();
    	if ( rowNumber < 0 ){
               JOptionPane.showMessageDialog(this,"Must select row delete player " );
      	 }
    	else {
    		processSelectedRowForDelete(rowNumber);
    	}
    	
    	
    }
    void postScoreButton_actionPerformed(ActionEvent event) {
    	
        for (int i=0; i<playerData.length; i++){
        	 
        	Integer   score = (Integer) playerData[i][3];
            int intScore = score.intValue();
        	if ( intScore > 0){
        		try{
        		  playerHandicapService.addScore(dateText.getText(), (String) playerData[i][0], courseNameOnly, intScore);
        		  Log.information("Posting...-> " + dateText.getText() + " " + (String) playerData[i][0]+ " " + courseNameOnly  +" " + intScore);
        		}
        		catch(DuplicateException e){
        			Log.information("Warning.. Score already Posted ----> " + dateText.getText() + " " + (String) playerData[i][0]+ " " + courseNameOnly  +" " + intScore);
                	
        		}
        		catch(Exception e){
        			
        		}
        	
        	}
         }
   
         // refresh all tournament averages
         tournamentService.updateTournamentAverages();
         tournamentClientFrame = JhandicapFactory.getTournamentClientFrame();
         tournamentClientFrame.refreshHandicapsAndAverages();
         
         JOptionPane.showMessageDialog(this,"Scores have been Posted");
    
    	}
    	
    	
    
    public void processSelectedRow(int aRowNumber) {
    	int rowNumber = aRowNumber ; String Stringscore;	Boolean good =false; int score =0;
  
    	while(!good){
      	   Stringscore = JOptionPane.showInputDialog("Enter Score: ");
            try{
    	          score = Integer.parseInt(Stringscore);
    	          good=true;
    	       }
            catch(Exception e){
             
            }
        }

    	ArrayList allPlayersUpdatedScore =null;
    	try{
             allPlayersUpdatedScore = singlesMatchService.addScore(courseNameOnly, (String) playerData[rowNumber][0], score);
    	}
    	 catch(Exception e){
    			JOptionPane.showMessageDialog(this,"ERROR Tring to add score for selected player " );
       }
    	 
    	 loadPlayerDataFromSmp(allPlayersUpdatedScore,courseNameOnly);
    	 
         playerDataTableModel.fireTableDataChanged();
    }
    
    public void processSelectedRowForDelete(int aRowNumber){
    	
    	int rowNumber = aRowNumber ; 
    
    	String playerName = (String) playerData[aRowNumber] [0];
    	ArrayList allSinglesPlayers = null ;
    	try{
    	  allSinglesPlayers = singlesMatchService.removePlayersFromMatch(courseNameOnly, playerName);
    	}
    	catch (Exception e){
    		JOptionPane.showMessageDialog(this,"ERROR Tring to delete player from Singles Match " );
    		
    	}
    	
        loadPlayerDataFromSmp(allSinglesPlayers,courseNameOnly);
        playerDataTableModel.fireTableDataChanged();
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



