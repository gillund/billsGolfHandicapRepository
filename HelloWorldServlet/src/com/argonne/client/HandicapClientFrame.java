/*
 * ====================================================================
 *
 * Copyright (c) 2004-2008 Argonne Technologies.  All rights reserved.
 *
 * This file is part of JHandicap.
 *
 * JHandicap is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 * JHandicap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with JHandicap; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * Contact Mark Woyna at woyna at argonne dot com
 *
 */

package com.argonne.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.courseServices.CourseServiceImpl;
import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.handicapServices.Round;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerHandicapServiceImpl;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceImpl;
import com.argonne.tournamentService.TournamentService;
import com.argonne.utils.DuplicateException;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

public class HandicapClientFrame extends JFrame implements CourseUpdateConsumer {

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JTextField date = new JTextField(8);
    //JTextField course = new JTextField(15);
    JTextField playerName = new JTextField(25);
    JTextField score = new JTextField(3);
    JTextField slope = new JTextField(3);
    JTextField rating = new JTextField(5);
    JTextField handicap = new JTextField(3);
    JTextField averageDelta = new JTextField(3);
    JTextField average = new JTextField(4);
    JLabel dateLabel = new JLabel();
    JLabel playerLabel = new JLabel();
    JLabel courseLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel slopeLabel = new JLabel();
    JLabel ratingLabel = new JLabel();
    JLabel handicapLabel = new JLabel();
    JLabel averageDeltaLabel = new JLabel();
    JLabel playerNameLabel = new JLabel();
    JLabel averageLabel = new JLabel();
    JLabel messageLabel = new JLabel();
    
    
    JButton addScoreButton = new JButton();
 //   JButton addCourseButton = new JButton();
 //   JButton addPlayerButton = new JButton();
    JButton closeButton = new JButton();
    JButton deleteScoreButton = new JButton ();
    // storage for scores data
    JTable scores;
    private AbstractTableModel scoresDataTableModel;
    private Object[][] scoresData = {};
    private String[] scoresDataColumnNames = { "Date", "Course", "Rating", "Slope", "Score", "Delta", "Used" };
    
    String[] coursesData = new String[0];
    JComboBox courses = new JComboBox(coursesData);
    CourseService courseService = null;
    PlayerService playerService = null;
    PlayerHandicapService playerHandicapService = null;
    CourseClientFrame courseClient = null;
    
    TournamentClientFrame tournamentClientFrame =null;
 
    PlayerClientFrame playerClient = null;

    String playerSelected = null;
    // Construct the frame
    
     
    public HandicapClientFrame(String[] args){
    	try{
            init();
            initializeService();
            loadCourses();
            loadScoresData(args[0]);
  //          loadPlayers();
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    public HandicapClientFrame(String aPlayer){
    	
    	playerSelected = aPlayer;
    	 try {
            init();
            initializeService();
            loadCourses();
            loadScoresData(aPlayer);
     //       loadPlayers();
        }
    	 catch(Exception e) {
             Log.warning(e);
         }      
    }
    public HandicapClientFrame(){
      }
    public void initNewPlayer(String aPlayer){
    	playerSelected = aPlayer;   
    	loadCourses();
        loadScoresData(aPlayer);
  //      loadPlayers();
    }

    // Component initialization
    private void init() throws Exception  {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEtchedBorder());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(775/*width*/,625));
        this.setTitle("Handicap Service Client");

        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));

        dateLabel.setText("Date:");
        dateLabel.setBounds(new Rectangle(28, 50, 140, 25));
        date.setBounds(new Rectangle(173, 50, 213, 29));

        courseLabel.setText("Course:");
        courseLabel.setBounds(new Rectangle(28, 50, 140, 25));
        courses.setBounds(new Rectangle(173, 50, 213, 29));
        courses.setEditable(false);
        
        
        ratingLabel.setText("Rating:");
        ratingLabel.setBounds(new Rectangle(28, 50, 140, 25));
        rating.setBounds(new Rectangle(173, 50, 213, 29));

        slopeLabel.setText("Slope:");
        slopeLabel.setBounds(new Rectangle(28, 50, 140, 25));
        slope.setBounds(new Rectangle(173, 50, 213, 29));

        scoreLabel.setText("Score:");
        scoreLabel.setBounds(new Rectangle(28, 50, 140, 25));
        score.setBounds(new Rectangle(173, 50, 213, 29));

        handicapLabel.setText("Handicap:");
        handicapLabel.setBounds(new Rectangle(26, 93, 139, 23));
        handicap.setBounds(new Rectangle(172, 87, 215, 29));
        handicap.setEditable(false);
        
        playerNameLabel.setText("Player:");
        playerNameLabel.setBounds(new Rectangle(26, 93, 139, 23));
        playerNameLabel.setBounds(new Rectangle(172, 87, 215, 29));
        playerName.setEditable(false);
        
        averageDeltaLabel.setText("Avg Delta (20 Rounds):");
        averageDeltaLabel.setBounds(new Rectangle(26, 93, 139, 23));
        averageDelta.setBounds(new Rectangle(172, 87, 215, 29));
        averageDelta.setEditable(false);
        
        averageLabel.setText("Avg Score (20 Rounds):");
        averageLabel.setBounds(new Rectangle(26, 93, 139, 23));
        average.setBounds(new Rectangle(172, 87, 215, 29));
        average.setEditable(false);

        scoresDataTableModel = new ScoresDataTableModel();
        scores = new JTable(scoresDataTableModel);
        scores.setPreferredScrollableViewportSize(new Dimension(525, 319));
        scores.setShowGrid(true);
        scores.setRowSelectionAllowed(true);
        JScrollPane scoresScrollPane = new JScrollPane(scores);
        
        deleteScoreButton.setText("Delete Score");
        deleteScoreButton.setBounds(new Rectangle(19, 160, 150, 35));
        deleteScoreButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                deleteScoreButton_actionPerformed(e);
            }
        });
        
        addScoreButton.setText("Add Score");
        addScoreButton.setBounds(new Rectangle(49, 160, 150, 35));
        addScoreButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addScoreButton_actionPerformed(e);
            }
        });
        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(79, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });

        jPanel2.setToolTipText("");
        contentPane.add(jPanel2, null);
        jPanel2.add(playerNameLabel,null);
        jPanel2.add(playerName,null);
        jPanel2.add(handicapLabel, null);
        jPanel2.add(handicap, null);
        jPanel2.add(averageDeltaLabel, null);
        jPanel2.add(averageDelta, null);
        jPanel2.add(averageLabel, null);
        jPanel2.add(average, null);

        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(scoresScrollPane, null);

        jPanel1.setToolTipText("");
        contentPane.add(jPanel1, null);
        jPanel1.add(dateLabel, null);
        jPanel1.add(date, null);
        
        jPanel1.setToolTipText("");
        jPanel1.add(courseLabel, null);
        jPanel1.add(courses, null);
        
        jPanel1.setToolTipText("");
        jPanel1.add(scoreLabel, null);
        jPanel1.add(score, null);

        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
  //      jPanel4.add(addPlayerButton,null);
    //    jPanel4.add(addCourseButton, null);
        
        jPanel4.add(addScoreButton, null);
        jPanel4.add(deleteScoreButton, null);
        jPanel4.add(closeButton, null);
    }

    private void initializeService() {
        try {
            courseService = getCourseService();
            playerService = getPlayerService();
            playerHandicapService =  getPlayerHandicapService();
      }
        catch(Exception e) {
            Log.warning(e);
        }
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
    protected void loadCourses() {
        Hashtable tempCourses = courseService.getAllCourses();
        loadCourses(tempCourses);
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
    protected Vector sortPlayers(Hashtable tempPlayers) {
        Vector sortedPlayers = new Vector();
        Enumeration enumeration = tempPlayers.elements();
        while ( enumeration.hasMoreElements() ) {
            Player player1 = (Player) enumeration.nextElement();
            boolean notInserted = true;
            for ( int i = 0 ; i < sortedPlayers.size(); i++ ) {
                Player player2 = (Player) sortedPlayers.elementAt(i);
                String fullName1 = player1.firstName ;
                String fullName2 = player2.firstName ;
                if ( fullName1.compareTo(fullName2) < 0 ) {
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
    public void acceptCourses(Hashtable courseCollection) {
        loadCourses(courseCollection);
    }
 /*   
    public void acceptPlayer(Hashtable playerCollection) {
		loadPlayers(playerCollection);
		
	}
	*/
    protected void loadScoresData(String aPlayer) {
    	
    	playerName.setText(aPlayer);
    	
        float hc = playerHandicapService.getHandicap(aPlayer);
        float avgDelta = playerHandicapService.getAverageDelta(aPlayer);
        float avg = playerHandicapService.getAverage(aPlayer);
        String hcValue = Float.toString(hc);
        String averageDeltaValue = Float.toString(avgDelta);
        String averageValue = Float.toString(avg);
        int point = hcValue.indexOf('.',0);
        handicap.setText(hcValue.substring(0, point + 2));

        point = averageValue.indexOf('.',0);
        average.setText(averageValue.substring(0, point + 2));

        point = averageDeltaValue.indexOf('.',0);
        averageDelta.setText(averageDeltaValue.substring(0, point + 2));

        Vector rounds = playerHandicapService.getScoresForPlayer(aPlayer);
        Log.debug("Displaying " + rounds.size() + " scores.");

        // scoresDataColumnNames = { "Date", "Course", "Rating", "Slope", "Score", "Delta", "Used" }
        Object[][] newData = new Object[rounds.size()][scoresDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
            Round round = (Round) rounds.elementAt(i);
            newData[i][0] = round.date;
            newData[i][1] = round.course;
            newData[i][2] = new Float(round.rating);
            newData[i][3] = new Integer(round.slope);
            newData[i][4] = new Integer(round.score);
            newData[i][5] = new Float(round.delta);
            if ( round.inUse ) {
                newData[i][6] = "Yes";
            }
            else {
                newData[i][6] = "";
            }
        }

        // reset model
        scoresData = newData;

        // instruct table to refresh its view since the underlying model has changed
        scoresDataTableModel.fireTableDataChanged();
    }

    void closeButton_actionPerformed(ActionEvent event) {
    	Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addPlayerButton_actionPerformed(ActionEvent event) {
    	
    	if ( playerClient == null ) {
            playerClient = new PlayerClientFrame(getPlayerService());
    //        playerClient.addConsumer(this);
        }
        playerClient.setVisible(true);
    }
    
    void addCourseButton_actionPerformed(ActionEvent event) {
    //    Log.debug("Add Course Button Clicked");
     //   if ( courseClient == null ) {
     //       courseClient = new CourseClientFrame(getCourseService());
     //      
    //    }
    //    courseClient.addConsumer(this);
    //    courseClient.setVisible(true);
    }
    
    void deleteScoreButton_actionPerformed (ActionEvent event){
    	
    	//int rowNumberToDelete = scores.getSelectedRow();
    	
    	// temp until add player working with to heading.
    	String playerName = playerSelected;
    	
    	String date = (String) scoresDataTableModel.getValueAt(scores.getSelectedRow(), 0);
    	String courseName = (String) scoresDataTableModel.getValueAt(scores.getSelectedRow(),1);
    	Integer bigIscore = (Integer) scoresDataTableModel.getValueAt(scores.getSelectedRow(),4);
        int score = bigIscore.intValue();
    	Round RoundToDelete = new Round (date,playerName,courseName,score,0,72.0f);
    	
        try {
        	playerHandicapService.deleteScore(playerName, RoundToDelete);
        }
        catch (Exception e)
        {
        	JOptionPane.showMessageDialog(this,"Delete Attempted without Score Being Selected");
        }
        
        loadScoresData(playerName);
        
        tournamentClientFrame = JhandicapFactory.getTournamentClientFrame();
        tournamentClientFrame.refreshHandicapsAndAverages();
   
    
    }
    void addScoreButton_actionPerformed(ActionEvent event)  {

        // get the values from the fields
        String scoreValue = score.getText();
        String slopeValue = slope.getText();
        String ratingValue = rating.getText();
        String dt           =  date.getText();
        String playerName = playerSelected;
        
        Date dt2 = null;     
        try
        {
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
                df.setLenient(false);  // this is important!
                dt2 = df.parse(dt);
                
               
        }
        catch (java.text.ParseException e)
        {
        	  JOptionPane.showMessageDialog(this,"Illegal Value: date " + dt);
              date.setText(dt); 
              return;
        }
        catch (IllegalArgumentException e)
        {
        	   JOptionPane.showMessageDialog(this,"Illegal Value: date " + dt);
        	   date.setText(dt);
        	   return;
        }

        // check to see the score field is not entered. it has to be checked here since
        // only the int value is taken as the argument in the server.
        
        int newScore = 0;
        if( scoreValue.length() != 0 ) {
            try {
                newScore = Integer.parseInt(scoreValue);
            }
            catch(NumberFormatException exp) {
                JOptionPane.showMessageDialog(this,"Illegal Value: Score");
                score.setText("0");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Illegal Value: Score");
            score.setText("0");
            return;
        }

        try {
            String courseName = coursesData[courses.getSelectedIndex()];
            
            // handicapService.addScore(date.getText(), courseName, newScore);
            
            Vector newRoundsForPlayer = playerHandicapService.addScore(date.getText(), playerName, courseName, newScore,0);
            
            score.setText("");
            date.setText("");

            loadScoresData(playerName);
           
            
            tournamentClientFrame = JhandicapFactory.getTournamentClientFrame();
            tournamentClientFrame.refreshHandicapsAndAverages();
        
        }
        catch(DuplicateException e) {
        	
        	JOptionPane.showMessageDialog(this,"Error adding score - This score has Already been Posted");
        	return;
           
        }
        catch(Exception e) {
        	
        	Log.warning(e);
        	JOptionPane.showMessageDialog(this,"Error adding score - score not added to DB");
        	return;
           
        }
        
        
        
        
    }

    protected CourseService getCourseService() {
        if ( courseService == null ) {
            Log.information("Using Courses Default DataFile.");
            courseService = new CourseServiceImpl();
        }

        return courseService;
    }
    
    protected PlayerService getPlayerService() {
        
    	if ( playerService == null ) {
            Log.information("Using Players Default DataFile.");
            playerService = new PlayerServiceImpl();
        }

        return playerService;
    }
  
     protected PlayerHandicapService getPlayerHandicapService() {
    	 
        PlayerHandicapService aPlayerHandicapService = null;
        
        aPlayerHandicapService = new PlayerHandicapServiceImpl(getCourseService(),getPlayerService());
        
        return aPlayerHandicapService;
    }
    
    
    /**
    * Represents the underlying data model for the table.
    */
    class ScoresDataTableModel extends AbstractTableModel {
        public int getRowCount() {
            return scoresData.length;
        }

        public int getColumnCount() {
            return scoresDataColumnNames.length;
        }

        public Object getValueAt(int row, int column) {
            return scoresData[row][column];
        }

        public String getColumnName(int columnIndex) {

            return scoresDataColumnNames[columnIndex];
        }
    }
    
    /**
    * The main driver program
    *
    * @fyi use -DataFile=<filename> to override the default input file, scores.dat
    */

    public static void main(String[] args) {
    	String playerName; 
        boolean packFrame = false;
        try {
            printCopyright();
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
// initialize service, loads courses, loads handicap scores
            if (args.length == 0 ){
            	playerName = "William Gillund";
            }
            else{
            	playerName  = args[0];
            }
            HandicapClientFrame handicapClientFrame = new HandicapClientFrame(playerName);
            // Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = handicapClientFrame.getSize();
            if ( frameSize.height > screenSize.height ) {
                frameSize.height = screenSize.height;
            }
            if ( frameSize.width > screenSize.width ) {
                frameSize.width = screenSize.width;
            }

            handicapClientFrame.setLocation(
                                    (screenSize.width - frameSize.width) / 2,
                                    (screenSize.height - frameSize.height) / 2);
            handicapClientFrame.setVisible(true);
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


