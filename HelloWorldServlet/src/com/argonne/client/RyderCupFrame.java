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
import com.argonne.playerService.PlayerAverage;
import com.argonne.playerService.PlayerHandicap;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.tournamentService.RyderCupPlayers;
import com.argonne.tournamentService.RyderCupService;
import com.argonne.tournamentService.SingleMatchPlayer;
import com.argonne.tournamentService.SinglesMatchService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.JhandicapObjectPersist;
import com.argonne.utils.Log;

	/**
	*
	* @author William Gillund
	* @since 11/2/2008
	* @version 1.0
	*/

	public class RyderCupFrame extends JFrame  {
	    
	    JPanel contentPane;
	    JLabel redBlueLabel = new JLabel();
	
	    JPanel jPanel1 = new JPanel();
	    JPanel jPanel2 = new JPanel();
	    JPanel jPanel3 = new JPanel();
	    JPanel jPanel4 = new JPanel();
	    JPanel jPanel5 = new JPanel();
	    JLabel messageLabel = new JLabel();
	   
	    JButton closeButton = new JButton();
	//    JButton addScoreButton = new JButton();
	//    JButton removeButton = new JButton();
	    
	//  storage for player data
	    
	    
	    JTable blueplayer;
	    private AbstractTableModel blueplayerDataTableModel;
	    private Object[][] blueplayerData = {};
	    private String[] blueplayerDataColumnNames = { "Blue Team", "AVG score","Ranking", "BLUE/RED", "Red Team", "AVG score","Ranking"};
	
	    RyderCupPlayers rcp = null;
	    PlayerService playerService = null;
	    CourseService courseService =null;
	    SinglesMatchService singlesMatchService =null;
	    RyderCupService ryderCupService = null;
	    Hashtable playerUpdateConsumers = new Hashtable();
	    HandicapClientFrame handicapClient = null;
	    
	    // Construct the frame
	    
	    public RyderCupFrame() {
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
	        this.setSize(new Dimension(650 /*width*/, 525));
	        this.setTitle("Shagnasty Open Ryder Cup Matches");
		     
	        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
	        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
	        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
	              
	        blueplayerDataTableModel = new PlayerDataTableModel();
	        blueplayer = new JTable(blueplayerDataTableModel);
	        blueplayer.setPreferredScrollableViewportSize(new Dimension(620, 319));
	        blueplayer.setShowGrid(true);
	        JScrollPane blueplayerSrollPane = new JScrollPane(blueplayer);

	        closeButton.setText("Close");
	        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
	        closeButton.addActionListener(new java.awt.event.ActionListener() {

	            public void actionPerformed(ActionEvent e) {
	                closeButton_actionPerformed(e);
	            }
	        });
	        
	       
	        jPanel2.setToolTipText("");
	       
	        contentPane.add(jPanel2,null);
	        
	        jPanel3.setToolTipText("");
	        
	        contentPane.add(jPanel3, null);
	        jPanel3.add(redBlueLabel,null);
	        jPanel3.add(blueplayerSrollPane, null);
	        jPanel3.add(messageLabel,null);
	        
	        jPanel4.setToolTipText("");
	        contentPane.add(jPanel4, null);
	        jPanel4.add(closeButton, null);
	        jPanel3.add(blueplayerSrollPane, null);
	        
	    }

	    private void initializeService() {
	       playerService = JhandicapFactory.getPlayerService();
	       handicapClient = JhandicapFactory.getHandicapClientFrame();
	       courseService = JhandicapFactory.getCourseService();
	       singlesMatchService = JhandicapFactory.getSinglesMatchService();
	       ryderCupService = JhandicapFactory.getRyderCupService();
	      
	       
	    }
	    
	    protected void loadPlayerData(ArrayList plyByAvg ) {
	    	try {
	    	   rcp = ryderCupService.generateRyderCupTeamByAvgScorev2(plyByAvg);
	    	}
	    	catch (Exception e){
	    		JOptionPane.showMessageDialog(this, "Error trying to generate ryder cup" );
	    	}
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
	            float a = paBlue[i].avg; ;
	            Float oA = new Float(a);
	            newData[i][1] =  oA;
	            newData[i][2] = paBlue[i].getRank();
	          }
	        newData[rcp.getBlueNbrPlayers()][0] = "<Blue Average>";
	        float blueAvg  = rcp.getTotalBlueTeamAverage();
	        Float blueFloatAvg = new Float(blueAvg);
	        newData [rcp.getBlueNbrPlayers()][1] = blueFloatAvg; 
	        newData [rcp.getBlueNbrPlayers()] [2] = "";
	        
	         
	        for ( int i = 0; i < paRed.length; i++ ) {
	        	newData[i][3] = "<-BLUE/RED->";
	            newData[i][4] =  paRed[i].Player;
	            float a = paRed[i].avg; ;
	            
	            
	            Float oA = new Float(a);
	            newData[i][5] =  oA;
	            newData[i][6] = paRed[i].getRank();
	            
	          }
	
	        newData[rcp.getRedNbrPlayers()][3] = "<Red Average>";
	        float RedAvg = rcp.getTotalRedTeamAveragge();
	        Float RedFloatAvg = new Float(RedAvg);
	        newData [rcp.getRedNbrPlayers()][4] = RedFloatAvg; 
	        newData [rcp.getRedNbrPlayers()] [5] = "";
	     
	        // reset model
	        blueplayerData = newData;
	        // instruct table to refresh its view since the underlying model has changed
	        blueplayerDataTableModel.fireTableDataChanged();
	        
	    }
	    protected void loadPlayerDataFrompersistance(ArrayList plyByAvg ) throws Exception {
	    	
	        JhandicapObjectPersist jop = JhandicapFactory.getObjectPersistance();
	        rcp = (RyderCupPlayers) jop.deserializeObject(jop.getRyderCupPlayerFile());
	        if (rcp == null)
	        	throw new Exception();
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
	            float a = paBlue[i].avg; ;
	            Float oA = new Float(a);
	            newData[i][1] =  oA;
	            newData[i][2] = paBlue[i].getRank();
	          }
	        newData[rcp.getBlueNbrPlayers()][0] = "<Blue Average>";
	        float blueAvg  = rcp.getTotalBlueTeamAverage();
	        Float blueFloatAvg = new Float(blueAvg);
	        newData [rcp.getBlueNbrPlayers()][1] = blueFloatAvg; 
	        newData [rcp.getBlueNbrPlayers()] [2] = "";
	        
	         
	        for ( int i = 0; i < paRed.length; i++ ) {
	        	newData[i][3] = "<-BLUE/RED->";
	            newData[i][4] =  paRed[i].Player;
	            float a = paRed[i].avg; ;
	            
	            
	            Float oA = new Float(a);
	            newData[i][5] =  oA;
	            newData[i][6] = paRed[i].getRank();
	            
	          }
	
	        newData[rcp.getRedNbrPlayers()][3] = "<Red Average>";
	        float RedAvg = rcp.getTotalRedTeamAveragge();
	        Float RedFloatAvg = new Float(RedAvg);
	        newData [rcp.getRedNbrPlayers()][4] = RedFloatAvg; 
	        newData [rcp.getRedNbrPlayers()] [5] = "";
	     
	        // reset model
	        blueplayerData = newData;
	        // instruct table to refresh its view since the underlying model has changed
	        blueplayerDataTableModel.fireTableDataChanged();
	        
	    }
	    
	    
	 protected void loadPlayerData() {
	
	
	
	}
	    
	       
	    protected void loadPlayerDataFromSmp(ArrayList < SingleMatchPlayer> singleMatchPlayer, String inCourseName ) {
	        
	    }  
	  
	    void closeButton_actionPerformed(ActionEvent event) {
	        Log.debug("Closing Client...");
	        this.setVisible(false);
	    }

	    void addScoreButton_actionPerformed(ActionEvent event) {
	    	
	    	int rowNumber = 0 ; String Stringscore;	Boolean good =false; int score =0;
	    	
	    	rowNumber = blueplayer.getSelectedRow();
	    	if ( rowNumber < 0 ){
	               JOptionPane.showMessageDialog(this,"Must select row to add score " );
	      	 }
	    	else {
	    		processSelectedRow(rowNumber);
	    	}
	    	
	    	
	    }
	    void removeButton_actionPerformed(ActionEvent event) {
	    	
	    	int rowNumber = 0 ; String Stringscore;	Boolean good =false; int score =0;
	    	
	    	rowNumber = blueplayer.getSelectedRow();
	    	if ( rowNumber < 0 ){
	               JOptionPane.showMessageDialog(this,"Must select row delete player " );
	      	 }
	    	else {
	    		processSelectedRowForDelete(rowNumber);
	    	}
	    	
	    	
	    }
	    
	    public void processSelectedRow(int aRowNumber) {
	    	int rowNumber = aRowNumber ; String Stringscore;	Boolean good =false; int score =0;
	  
	    }
	    
	    public void processSelectedRowForDelete(int aRowNumber){
	    	
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




