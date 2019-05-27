
package com.argonne.client;

import com.argonne.handicapServices.Round;
import com.argonne.courseServices.CourseService;
import com.argonne.courseServices.CourseServiceImpl;
import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerHandicapServiceImpl;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceImpl;
import com.argonne.playerService.PlayerUpdateConsumer;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
*
* @fyi use -DataFile=<filename> to override the default input file, scores.dat
*
*/

public class JhandicapMainMenu extends JFrame  implements  PlayerUpdateConsumer { 

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JTextField date = new JTextField(8);
    //JTextField course = new JTextField(15);
    JTextField score = new JTextField(3);
    JTextField slope = new JTextField(3);
    JTextField rating = new JTextField(5);
    JTextField handicap = new JTextField(3);
    JTextField averageDelta = new JTextField(3);
    JTextField average = new JTextField(3);
    JLabel dateLabel = new JLabel();
 //   JLabel playerLabel = new JLabel();
    JLabel courseLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel slopeLabel = new JLabel();
    JLabel ratingLabel = new JLabel();
    JLabel handicapLabel = new JLabel();
    JLabel averageDeltaLabel = new JLabel();
    JLabel averageLabel = new JLabel();
    
    JButton addCourseButton = new JButton();
    JButton addRankButton = new JButton();
    JButton addRankHcapButton = new JButton();
    JButton addPlayerButton = new JButton();
    JButton addHandicapButton = new JButton();
    JButton closeButton = new JButton();
    JButton deleteScoreButton = new JButton ();
    JButton addTournamentButton = new JButton();

    // storage for scores data
    JTable scores;
    private AbstractTableModel scoresDataTableModel;
    private Object[][] scoresData = {};
    private String[] scoresDataColumnNames = { "Date", "Course", "Rating", "Slope", "Score", "Delta", "Used" };
    
    String[] coursesData = new String[0];
    String[] playerData = new String[0];
    
   
    JComboBox players = new JComboBox(playerData);

    CourseService courseService = null;
    PlayerService playerService = null;
    PlayerHandicapService playerHandicapService = null;
    CourseClientFrame courseClient = null;
    HandicapClientFrame handicapClient = null;
    PlayerClientFrame playerClient = null;
    TournamentClientFrame tournamentClient = null;
    PlayerHandicapServiceImpl playerHandicapServiceImpl = null;
    RankPlayerClient rankPlayerClient = null;
    RankPlayerByHandicapClient rankPlayerByHandicapClient = null;
    
    // Construct the frame
    public JhandicapMainMenu(String[] args) {
        try {
            init();
            initializeService(args);
            loadPlayers();
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
        this.setSize(new Dimension(700/*width*/, 125));
        this.setTitle("Shagnasty Open Main Menu");

        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
/*
        playerLabel.setText("Selet Player From List:");
        playerLabel.setBounds(new Rectangle(28, 50, 140, 25));
  */
        players.setBounds(new Rectangle(173, 50, 213, 29));
        players.setEditable(false);

        addCourseButton.setText("Add Course...");
        addCourseButton.setBounds(new Rectangle(49, 160, 150, 35));
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addCourseButton_actionPerformed(e);
            }
        });
        addRankButton.setText("All Player Avg's");
        addRankButton.setBounds(new Rectangle(49, 160, 150, 35));
        addRankButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addRankButton_actionPerformed(e);
            }
        });
        addRankHcapButton.setText("All Player Handicap's");
        addRankHcapButton.setBounds(new Rectangle(49, 160, 150, 35));
        addRankHcapButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addRankHcapButton_actionPerformed(e);
            }
        });
        addPlayerButton.setText("Add Player");
        addPlayerButton.setBounds(new Rectangle(49, 160, 150, 35));
        addPlayerButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addPlayerButton_actionPerformed(e);
            }
        });
        addTournamentButton.setText("2009 Shagnasty Open ");
        addTournamentButton.setBounds(new Rectangle(49, 160, 150, 35));
        addTournamentButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addTournamentButton_actionPerformed(e);
            }
        });
        
        
        addHandicapButton.setText("Add Score...");
        addHandicapButton.setBounds(new Rectangle(49, 160, 150, 35));
        addHandicapButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addHandicapButton_actionPerformed(e);
            }
        });
        
       
        closeButton.setText("Close...");
        closeButton.setBounds(new Rectangle(49, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });
   
        
        jPanel1.setToolTipText("");
        contentPane.add(jPanel1, null);
        jPanel1.add(addPlayerButton,null);
        jPanel1.add(addCourseButton, null);
        jPanel1.add(addRankButton, null);
        jPanel1.add(addRankHcapButton, null);
        jPanel1.add(addTournamentButton, null);
        
        
        jPanel1.setToolTipText("");
        
        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
  //      jPanel4.add(playerLabel, null);
        jPanel4.add(players, null);
        jPanel4.add(addHandicapButton,null);
        jPanel4.add(closeButton, null);
       
    }

    private void initializeService(String[] args) {
        try {
      //      handicapService =  getHandicapService();
            Log.debug("Loading Services....");
            
            courseService 			= JhandicapFactory.getCourseService();
            playerService 			= JhandicapFactory.getPlayerService();
            playerHandicapService 	=  JhandicapFactory.getPlayerHandicapService();
            rankPlayerClient = JhandicapFactory.getRankPlayerClient();
            rankPlayerByHandicapClient = JhandicapFactory.getRankPlayerByHandicapClient();
            
           
            Log.debug("Loading clients....");
             
            handicapClient = JhandicapFactory.getHandicapClientFrame();
            courseClient = new CourseClientFrame(courseService);
            courseClient.addConsumer(handicapClient);
            playerClient = new PlayerClientFrame(playerService);
            playerClient.addConsumer(this);
            tournamentClient = JhandicapFactory.getTournamentClientFrame();
           
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    

    protected void loadPlayers(Hashtable tempPlayers) {
        Vector sortedPlayers = sortPlayers(tempPlayers);
        players.removeAllItems();
        playerData = new String[sortedPlayers.size()];
        Enumeration enumeration = sortedPlayers.elements();
        int i = 0;
        while ( enumeration.hasMoreElements() ) {
            Player player = (Player) enumeration.nextElement();
            String fullName = player.firstName ;
            playerData[i++] = fullName;
            players.addItem(player.firstName) ;
        }
    }
     protected void loadPlayers() {
        Hashtable tempPlayers = playerService.getAllPlayers();
        loadPlayers(tempPlayers);
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
    /*
    public void acceptCourses(Hashtable courseCollection) {
        loadCourses(courseCollection);
    }
    */
    
    public void acceptPlayer(Hashtable playerCollection) {
		loadPlayers(playerCollection);
		
	}
    protected void loadScoresData(String aPlayer) {
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
        System.exit(0);
    }

    void addPlayerButton_actionPerformed(ActionEvent event) {
    	playerClient.setVisible(true);
    }
    void addTournamentButton_actionPerformed(ActionEvent event) {
    	tournamentClient.loadAllTournamentPlayers();
    	tournamentClient.setVisible(true);
    }
    
    
    void addHandicapButton_actionPerformed(ActionEvent event) {
    	
    	String playerName = playerData[players.getSelectedIndex()];
      	handicapClient.initNewPlayer(playerName);
    	handicapClient.loadCourses(); 
    	 
    	 handicapClient.setVisible(true);
    }
    
    void addCourseButton_actionPerformed(ActionEvent event) {
        Log.debug("Add Course Button Clicked");
        courseClient.setVisible(true);
    }
    
    void addRankButton_actionPerformed(ActionEvent event) {
    	rankPlayerClient.loadPlayerData( playerService.getPlayersByAverageRound());
    	rankPlayerClient.setVisible(true);
    }
    void addRankHcapButton_actionPerformed(ActionEvent event) {
        rankPlayerByHandicapClient.loadPlayerData( playerService.getPlayersByHandicap());
        rankPlayerByHandicapClient.setVisible(true);
    }
    
    
    void deleteScoreButton_actionPerformed (ActionEvent event){
    	
    	//int rowNumberToDelete = scores.getSelectedRow();
    	
    	// temp until add player working with to heading.
    	String playerName = playerData[players.getSelectedIndex()];
    	
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
        boolean packFrame = false;
        try {
            printCopyright();
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
// initialize service, loads courses, loads handicap scores 
            JhandicapMainMenu jHandicapMainMenu = new JhandicapMainMenu(args);

            // Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = jHandicapMainMenu.getSize();
            if ( frameSize.height > screenSize.height ) {
                frameSize.height = screenSize.height;
            }
            if ( frameSize.width > screenSize.width ) {
                frameSize.width = screenSize.width;
            }

            jHandicapMainMenu.setLocation(
                                    (screenSize.width - frameSize.width) / 2,
                                    (screenSize.height - frameSize.height) / 2);
            jHandicapMainMenu.setVisible(true);
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

