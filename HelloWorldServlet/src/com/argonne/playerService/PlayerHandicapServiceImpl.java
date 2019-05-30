package com.argonne.playerService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.handicapServices.Round;
import com.argonne.utils.DuplicateException;
import com.argonne.utils.Log;


public class PlayerHandicapServiceImpl implements PlayerHandicapService {
	public static String DEFAULT_FILENAME  = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "playerScores.dat";
	public static int MAX_ROUNDS = 20;      /* Maximum # of rounds to be used */
    public static int MIN_ROUNDS = 0;      /* Minimum # of rounds to be used */

    private String filename;
    private CourseService courseService;
    private PlayerService playerService;
    private Map <String, Vector> playerRounds  = new Hashtable();

    public PlayerHandicapServiceImpl(String afilename) {
        filename=afilename;
    }
    public PlayerHandicapServiceImpl(CourseService aCourseService, PlayerService aPlayerService) {
        this(DEFAULT_FILENAME, aCourseService, aPlayerService);
    }

    public PlayerHandicapServiceImpl(String aFilename, CourseService aCourseService,PlayerService aPlayerService) {
        filename = aFilename;
        courseService = aCourseService;
        playerService = aPlayerService;
    }
    
    public Vector addScore(String aDate, String aPlayer, String aCourseName, int aScore) throws Exception, DuplicateException {
        
    	Player player = playerService.getPlayer(aPlayer);
    	if (player == null)
    		throw new Exception("Player not found : " + aPlayer);
    	
    	Course aCourse = getCourseService().getCourse(aCourseName);
        if ( aCourse == null ) {
            throw new Exception("Course Not Found: " + aCourseName);
        }
        
        Round newRound = new Round(aDate, aPlayer, aCourseName, aScore, aCourse.slope, aCourse.rating);
        
       
        // get all rounds
        
        Vector rounds = readPlayerScores(aPlayer);
        
        Boolean isDuplicate = checkForDuplicatRound (newRound, rounds);
        
        if (isDuplicate){
        	throw new DuplicateException("Score already posted " +  aDate +" " + aCourseName +" "+ aScore );
        }
       
        // add new round
        rounds.add(0, newRound);
        
        playerRounds.put(aPlayer, rounds);
        
        writePlayerScores(aPlayer , rounds, 0);
        
    	return rounds;
    
	}
    
    private boolean checkForDuplicatRound (Round newRound, Vector rounds){
    	
    	for (int i=0; i< rounds.size(); i++){
    		Round oldRound = (Round) rounds.get(i);
    		
    		if (oldRound.equals(newRound)){
    			return true;
    		}
    	}
    	return false;
    }
    
    
    
    public void deleteScore(String aPlayer, Round aRound) throws Exception {
		if ((aPlayer == null) || aRound ==null){
    		throw new Exception("Player not found : " + aPlayer);
    	}
    	Player player = playerService.getPlayer(aPlayer);
    	if (player == null)
    		throw new Exception("Player not found : " + aPlayer);
    	
    	Vector oldRounds = readPlayerScores(aPlayer);
        Vector newRounds = removeScore(oldRounds,aRound); 
    	writePlayerScores(aPlayer , newRounds,aRound.getRoundId());
	}
    
    public Vector removeScore(Vector oldRounds, Round roundToDelete) {
    	
    	Vector newRounds = new Vector();
    	 Enumeration enumeration = oldRounds.elements();
         while ( enumeration.hasMoreElements() ) {
             Round round = (Round) enumeration.nextElement();
             if (!round.equals(roundToDelete)){
            	 newRounds.add(round);
             }
             
         }
    	
    	return newRounds;
    
    }
    
    public Vector getScoresForPlayer(String aPlayer) {
    	
    	
        Vector vPlayerScores = readPlayerScores(aPlayer);   
        calculateHandicap(vPlayerScores);
        return vPlayerScores;
 }


    protected String getFilename() {
         return filename;
    }
    
    protected CourseService getCourseService() {
         return courseService;
    }
    
    public float getHandicap(String aPlayer) {
        return calculateHandicap(getScoresForPlayer(aPlayer));
   }

   public float getAverage(String aPlayer) {
        return calculateAverage(getScoresForPlayer(aPlayer));
   }

   public float getAverageDelta(String aPlayer) {
        return calculateAverageDelta(getScoresForPlayer(aPlayer));
   }
    public float calculateHandicap(Vector rounds) {

        int numberOfRounds = rounds.size();
        if ( numberOfRounds < MIN_ROUNDS ) {
            Log.warning("Requires " + MIN_ROUNDS + " rounds to calculate handicap");
            return -999.0f;
        }

        if ( numberOfRounds >= MAX_ROUNDS ) {
            numberOfRounds = MAX_ROUNDS;
            Log.debug("Using last " + numberOfRounds + " of " + rounds.size() + " rounds");
        }

        float tempDelta = 0.0f;
        int index = 0;
        float totalDelta = 0.0f;

        resetRounds(rounds);

        // find 10 best deltas

        for ( int i = 0 ; i < (numberOfRounds/2) ; i++ ) {
            tempDelta = 999.0f;
            for ( int j = 0 ; j < numberOfRounds ; j++ ) {
                Round round = (Round) rounds.elementAt(j);
                if( (round.delta < tempDelta) && !round.inUse ) {
                    tempDelta = round.delta;
                    index = j;
                }
            }

            // This score was one of the 10 lowest so use it in the average
            ((Round) rounds.elementAt(index)).inUse = true;
            totalDelta += tempDelta;
        }
/*
 *  If you have one score your delta w 
 * 
 * */
 
        printBestDeltas(rounds);
        float handicap =0;
        if (numberOfRounds == 1){
        	Round r = (Round)rounds.get(0);
        	totalDelta = r.delta;
        	handicap = (float)(totalDelta / (numberOfRounds/1)) * 0.96f;
        }
        else
        {
        	handicap = (float)(totalDelta / (numberOfRounds/2)) * 0.96f;
        }
        Log.debug("Handicap for last " + numberOfRounds + " rounds = " + handicap);
        return handicap;
    }
    
    public float calculateAverage(Vector rounds) {
    	
        if ( rounds.isEmpty() ) {
        	return 0.0f;
        }
        else if ( rounds.size() == 1 ) {
        	Round aRound = (Round) rounds.elementAt(0);
        	return aRound.score;
        }

        float sum = 0.0f;
        int i = 0;
        Enumeration enumeration = rounds.elements();
        while ( enumeration.hasMoreElements() && i < MAX_ROUNDS ) {
            Round round = (Round) enumeration.nextElement();
            sum += round.score;
            i++;
        }

        Log.debug("Average Score for " + i + " rounds: " + sum/i);
        return sum / i;
    }

    public float calculateAverageDelta(Vector rounds) {
    	
        if ( rounds.isEmpty() ) {
        	return 0.0f;
        }
        else if ( rounds.size() == 1 ) {
        	Round aRound = (Round) rounds.elementAt(0);
        	return aRound.delta;
        }
        
        float sum = 0.0f;
        int i = 0;
        Enumeration enumeration = rounds.elements();
        while ( enumeration.hasMoreElements() && i < MAX_ROUNDS ) {
            Round round = (Round) enumeration.nextElement();
            sum += round.delta;
            i++;
        }

        Log.debug("Average Delta for " + i + " rounds: " + sum/i);
        return sum / i;
    }
    
    protected Vector getPlayerRounds(String aPlayer){
        
    	if (playerRounds == null)
    	{
    		playerRounds = new Hashtable();
    	}
    	
    	Vector rounds = playerRounds.get(aPlayer);
        
        if (rounds == null)
        {
        	rounds = new Vector();
        }
        
        return rounds;
    	
    }
    
    protected Vector readPlayerScores(String aPlayerName) {
    	
    	String playerFile = getPlayerFileName(aPlayerName);
    	
   	    Vector scores = new Vector();
        BufferedReader input = null;
        String line = null;

        try {
            input = getInputStream(playerFile);

            // read the first record of the file
            while ( (line = input.readLine()) != null ) {
                try {
                    Round round = createRound(aPlayerName,line);
                    scores.add(round);
                    Log.debug(line + "," + round.delta);
                }
                catch(NumberFormatException exp) {
                    Log.warning(exp + " Illegal Numeric Value on line: " + line);
                }
                catch(Exception exp) {
                    Log.warning(exp + " on line: " + line);
                }
             }
         }
         catch (IOException e) {
        	 if (e.getMessage().contains("cannot find")){
                Log.warning("First Score for NEW player " + aPlayerName );
        	 }
        	 else
        	 {
        		 Log.warning("IOException:" + e.getMessage());
        	 }
        }
        finally {
            // if the file opened, close it
            if ( input != null ) {
                try {
                     input.close();
                }
                catch (IOException ioe) {
                    Log.warning("IOException error trying to close the file: " + ioe.getMessage());
                }
            }
        }

        return scores;
    }
    protected BufferedReader getInputStream(String aFilename) throws IOException {
        return new BufferedReader(new FileReader(aFilename));
    }

    protected Round createRound(String aPlayerName,String line) throws NumberFormatException, Exception {
        StringTokenizer st = new StringTokenizer(line, ",");
        String date = st.nextToken();
        String courseName = st.nextToken();
        String score  = st.nextToken();
        
        Course aCourse = getCourseService().getCourse(courseName);
        if ( aCourse == null ) {
            throw new Exception("Course Not Found: " + courseName);
        }

        return new Round(date,aPlayerName, aCourse.name, Integer.parseInt(score), aCourse.slope, aCourse.rating);
    }

        
    protected void writePlayerScores(String aPlayer , Vector rounds, int remove) throws Exception{
    	
    	 String playerFile = getPlayerFileName(aPlayer);
    	 
    	 Log.debug("Writing " + rounds.size() + " Player rounds  " + playerFile);
         BufferedWriter writer = null;

         try {
             writer = getOutputStream(playerFile);
             Enumeration enumeration = rounds.elements();
             while ( enumeration.hasMoreElements() ) {
                 Round round = (Round) enumeration.nextElement();
                 writeRound(round, writer);
             }

             writer.close();
         }
         catch (IOException ioe) {
              Log.warning("IOException error: " + ioe.getMessage());
         }
         finally {
             // if the file opened, close it
             if ( writer != null ) {
                 try {
                      writer.close();
                 }
                 catch (Exception ioe) {
                     Log.warning("Exception error trying to close the file: " + ioe.getMessage());
                 }
             }
         }
    	
    }
    protected String getPlayerFileName(String aPlayer){
    	return  aPlayer.replace(' ', '$')+ ".dat";
    }
    
    protected BufferedWriter getOutputStream(String aFilename) throws IOException {
        return new BufferedWriter(new FileWriter(aFilename));
    }
    
    protected void writeRound(Round round, BufferedWriter writer) throws IOException {
        StringBuffer line = new StringBuffer();
        line.append(round.date); line.append(",");
        line.append(round.course); line.append(",");
        line.append(round.score);

        writer.write(line.toString());
        writer.newLine();
    }

    public void printBestDeltas(Vector rounds) {
        Log.debug("Best Rounds: ");
        Enumeration enumeration = rounds.elements();
        while ( enumeration.hasMoreElements() ) {
            Round round = (Round) enumeration.nextElement();
            if ( round.inUse ) {
                Log.debug(round.delta + ", ");
            }
        }
        Log.debug("");
    }

    protected void resetRounds(Vector rounds) {
        Enumeration enumeration = rounds.elements();
        while ( enumeration.hasMoreElements() ) {
            Round round = (Round) enumeration.nextElement();
            round.inUse = false;
        }
    }

}

	

	

	
	
