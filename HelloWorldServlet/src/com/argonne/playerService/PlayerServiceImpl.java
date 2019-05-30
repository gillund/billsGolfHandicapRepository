package com.argonne.playerService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

public class PlayerServiceImpl implements PlayerService 
{
	/*
	* @author William Gillund
	* @since 11/9/2004
	* @version 1.0
	*/
	   public static String DEFAULT_FILENAME  = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "players.dat";
	   public static   Hashtable tempPlayers = new Hashtable();
	   ConcurrentHashMap <String, Player>  playerCacheByUserId = null;
	    private String filename;
	    private Hashtable players ;
	    PlayerHandicapService playerHandicapService = null;

	    public PlayerServiceImpl() {
	        this(DEFAULT_FILENAME);
	    }

	    public PlayerServiceImpl(String aFilename) {
	        filename = aFilename;
	    }

	    public Hashtable getAllPlayers() {
	        return (Hashtable) getPlayers().clone();
	    }
	    
	    public Player getPlayer(String playerName) {
	        return (Player) getPlayers().get(playerName);
	    }

	    public Hashtable addPlayer(String afirstName,  String aeMail, String aphoneNumber,String auserId, String apassWord) throws Exception {
	        if ( afirstName == null || afirstName.equals("") ) {
	            throw new Exception("Illegal Player Name: Player Missing");
	        }
	        if ( aeMail == null || aeMail.equals("") ) {
	            throw new Exception("email address missing");
	        }
	       	if ( aphoneNumber == null || aphoneNumber.equals("") ) {
	            throw new Exception("Pone number required");
	        }
	       	if (afirstName.contains("<")  || afirstName.contains(">")){
	       		throw new Exception("No Special characters allowed");
	       	}
	    	if (players == null)
	       	{
	       		getPlayers();
	       	}
	       	
	       	if (players.get(afirstName) != null)
	       	{
	       	 throw new Exception("Player exists, name must be unique ");
	       	}
	       		       
	       	int playerId=1;
	        Player newPlayer = new Player(playerId, afirstName, aeMail, aphoneNumber,auserId,apassWord);
	        String name = newPlayer.firstName;
	        
	        String validLogin = newPlayer.getUserId()+newPlayer.getPassword();
	    	if (playerCacheByUserId.get(validLogin) != null)
	       	{
	       	 throw new Exception("UserId and Password taken ");
	       	}
	        players.put(name, newPlayer);
	        // update user Id cache
	        validLogin = newPlayer.getUserId()+newPlayer.getPassword();
			playerCacheByUserId.put(validLogin, newPlayer);
			
	        writePlayers(getFilename(), players,newPlayer,false);
	        return getPlayers();
	    }
	    public Hashtable removePlayer(String name) throws Exception {
	    	
	    	Player p =null;
	    	
	    	try{
	    	   if (players.containsKey(name)){
	    		  p = (Player) players.get(name);
	    		  players.remove(name);
	    	   }
	    	   else
	    	   {
	    		throw  new Exception("Not Found");
	    	   }
	          
	    	}
	    	catch (Exception e){
	    		throw e;
	    	}
	    	
	    	 writePlayers(getFilename(), players , p, true );
	    	 return getPlayers();
	    } 

	    protected String getFilename() {
	         return filename;
	    }
	    
	    protected Hashtable getPlayers() {
	        if ( players == null ) {
	            players = readPlayers(getFilename());
	        }
	        
	        if (playerCacheByUserId == null)
	        {
	        	playerCacheByUserId = new ConcurrentHashMap<String, Player>();
	        	this.buildUserIdMap();
	        }
	        return players;
	    
	    }
	    private void buildUserIdMap()
		 {
		    	Set<String> keys = players.keySet();
				for(String key:keys) {
					
					Player p = (Player) players.get(key);
					String validLogin = p.getUserId()+p.getPassword();
					playerCacheByUserId.put(validLogin, p);
				}
		 }

	    protected Hashtable readPlayers(String aFilename) {
	      
	        BufferedReader input = null;
	        String line = null;

	        try {
	            input = getInputStream(aFilename);

	            // read the first record of the file
	            while ( (line = input.readLine()) != null ) {
	                try {
	                    Player player = createPlayer(line);
	                    tempPlayers.put(player.firstName, player);
	                    Log.debug(line);
	                }
	                catch(NumberFormatException exp) {
	                    Log.warning(exp + " Illegal Numeric Value on line: " + line);
	                }
	             }
	         }
	         catch (IOException e) {
	             Log.warning("IOException error: " + e.getMessage());
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

	        return tempPlayers;
	    }
	   

	    protected BufferedReader getInputStream(String aFilename) throws IOException {
	        return new BufferedReader(new FileReader(aFilename));
	    }

	    protected Player createPlayer(String line) throws NumberFormatException {
	        StringTokenizer st = new StringTokenizer(line, ",");
	        String fname = st.nextToken();
	        String email = st.nextToken();
	        String phone = st.nextToken();
	        
	        Player p = new Player();
	        p.setfirstName(fname);
	        p.setEmail(phone);
	        p.setPhoneNumber(phone);
	        
	        return p;
	    }

	   	    protected void writePlayers(String aFilename, Hashtable aPlayerCollection,Player p,boolean remove) {
	    	
	        Log.debug("Writing " + aPlayerCollection.size() + " players to " + aFilename);
	        BufferedWriter writer = null;

	        try {
	            writer = getOutputStream(aFilename);
	            Enumeration enumeration = aPlayerCollection.elements();
	            while ( enumeration.hasMoreElements() ) {
	                Player player = (Player) enumeration.nextElement();
	                writePlayer(player, writer);
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

	    protected BufferedWriter getOutputStream(String aFilename) throws IOException {
	        return new BufferedWriter(new FileWriter(aFilename));
	    }
	    
	    protected void writePlayer(Player player, BufferedWriter writer) throws IOException {
	        StringBuffer line = new StringBuffer();
	        line.append(player.firstName); line.append(",");
	        line.append(player.eMail); line.append(",");
	        line.append(player.phoneNumber); 
	        
	        writer.write(line.toString());
	        writer.newLine();
	    }

	    public void printCourses(Hashtable aPlayerCollection) {
	        Log.debug("Players: ");
	        Enumeration enumeration = players.elements();
	        while ( enumeration.hasMoreElements() ) {
	            Player player = (Player) enumeration.nextElement();
	            Log.debug(player.firstName + ", " + player.eMail + ", " + player.phoneNumber);
	        }
	        Log.debug("");
	    }

	    public static void main(String[] args) {
	        try {
	            PlayerServiceImpl playerService = new PlayerServiceImpl();
	            Hashtable players = playerService.getAllPlayers();
	            playerService.printCourses(players);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

		public ArrayList <PlayerAverage> getPlayersByAverageRound() {
			
		   Hashtable <String, Player> h = getAllPlayers();
		   Enumeration e =  h.keys();
		   
		   playerHandicapService = JhandicapFactory.getPlayerHandicapService();
		   ArrayList <PlayerAverage> vPlayerAvg = new ArrayList();
		   
		   while(e.hasMoreElements()){
	         String p = (String) e.nextElement();
	         float playerAverage = playerHandicapService.getAverage(p);
	         PlayerAverage pa = new PlayerAverage();
	         pa.Player = p;
	         pa.avg = playerAverage;
	         vPlayerAvg.add(pa);
	       }
		   
		   System.out.println("************** Before Sort ************************");
		   for (int i=0; i<vPlayerAvg.size();i++){
			   PlayerAverage spa =(PlayerAverage) vPlayerAvg.get(i);
			   System.out.println("'" + spa.Player+ "' " + spa.avg );
		   }
		   
		   PlayerAverage playAverageComparator = new PlayerAverage();
		   Collections.sort(vPlayerAvg, playAverageComparator);
		   vPlayerAvg = rankPlayersAVG(vPlayerAvg);
		  
		   
		   return vPlayerAvg;
			
		}

		public ArrayList<PlayerHandicap> getPlayersByHandicap() {
			 Hashtable <String, Player> h = getAllPlayers();
             Enumeration e =  h.keys();
			 playerHandicapService = JhandicapFactory.getPlayerHandicapService();
			 System.out.println("print player keys : \n");
		     //  player average and Handicap Vectors
			 ArrayList <PlayerHandicap> vPlayerHandicap = new ArrayList();
		       
			 while(e.hasMoreElements()){
		       
				  String p = (String) e.nextElement();
		          float handicap = playerHandicapService.getHandicap(p);
		          
		          PlayerHandicap ph = new PlayerHandicap();
		          ph.setPlayer(p);
		          ph.sethandicap(handicap);
		          vPlayerHandicap.add(ph);

//		          System.out.println( "'" + p + "'" +  "handicap = " + handicap + "\n");
		        }
			    
			   PlayerHandicap playAverageComparator = new PlayerHandicap();
			   Collections.sort(vPlayerHandicap, playAverageComparator);
			   vPlayerHandicap = rankPlayersHCAP(vPlayerHandicap);
			   return vPlayerHandicap;
				
			
		}
		public PlayerAverage getPlayerAverage(String playerName){
			playerHandicapService = JhandicapFactory.getPlayerHandicapService();
			float playerAverage = playerHandicapService.getAverage(playerName);
			PlayerAverage pa = new PlayerAverage();
			pa.Player = playerName;
			pa.avg = playerAverage;
			pa.getRank();
			return pa;
		}

		public ArrayList<PlayerAverage> getPlayersByAverageRound(ArrayList<String> playerList) {
			  
			   playerHandicapService = JhandicapFactory.getPlayerHandicapService();
			   ArrayList <PlayerAverage> vPlayerAvg = new ArrayList();
			   
			   for (int i=0; i<playerList.size(); i++){
		         String p = (String) playerList.get(i);
		         float playerAverage = playerHandicapService.getAverage(p);
		         PlayerAverage pa = new PlayerAverage();
		         pa.Player = p;
		         pa.avg = playerAverage;
		         vPlayerAvg.add(pa);
		       }
			   
			   System.out.println("************** Before Sort ************************");
			   for (int i=0; i<vPlayerAvg.size();i++){
				   PlayerAverage spa =(PlayerAverage) vPlayerAvg.get(i);
				   System.out.println("'" + spa.Player+ "' " + spa.avg );
			   }
			   
			   PlayerAverage playAverageComparator = new PlayerAverage();
			   Collections.sort(vPlayerAvg, playAverageComparator);
			   vPlayerAvg = rankPlayersAVG(vPlayerAvg);
			   
			  
			   
			   return vPlayerAvg;
		}

		public ArrayList<PlayerHandicap> getPlayersByHandicap(ArrayList<String> playerList) {
			
			playerHandicapService = JhandicapFactory.getPlayerHandicapService();
			 ArrayList <PlayerHandicap> vPlayerHandicap = new ArrayList();
		       
			 for (int i=0; i<playerList.size(); i++){
			         
				  String p = (String) playerList.get(i);
		          float handicap = playerHandicapService.getHandicap(p);
		          
		          PlayerHandicap ph = new PlayerHandicap();
		          ph.setPlayer(p);
		          ph.sethandicap(handicap);
		          vPlayerHandicap.add(ph);

//		          System.out.println( "'" + p + "'" +  "handicap = " + handicap + "\n");
		        }
			    
			   PlayerHandicap playAverageComparator = new PlayerHandicap();
			   Collections.sort(vPlayerHandicap, playAverageComparator);
			   vPlayerHandicap = rankPlayersHCAP(vPlayerHandicap);
			   
			   return vPlayerHandicap;
		}
        
		
		public ArrayList<PlayerHandicap> getPlayersByHandicapNoRank(ArrayList<String> playerList) {
			
			playerHandicapService = JhandicapFactory.getPlayerHandicapService();
			 ArrayList <PlayerHandicap> vPlayerHandicap = new ArrayList();
		       
			 for (int i=0; i<playerList.size(); i++){
			         
				  String p = (String) playerList.get(i);
		          float handicap = playerHandicapService.getHandicap(p);
		          
		          PlayerHandicap ph = new PlayerHandicap();
		          ph.setPlayer(p);
		          ph.sethandicap(handicap);
		          vPlayerHandicap.add(ph);

//		          System.out.println( "'" + p + "'" +  "handicap = " + handicap + "\n");
		        }
			    
			   PlayerHandicap playAverageComparator = new PlayerHandicap();
			   Collections.sort(vPlayerHandicap, playAverageComparator);
			   
			   return vPlayerHandicap;
		}
		
		private ArrayList rankPlayersHCAP(ArrayList<PlayerHandicap> vPlayerHandicap ){
		   
		   float 	numOfPlayers = 0; 		numOfPlayers 		    = vPlayerHandicap.size();
		   int 		wholeNumOfPlayers =0; 	wholeNumOfPlayers 	= vPlayerHandicap.size();
		   float 	ranking =0;
		   
		   
		   if (numOfPlayers != 0)  { 
			   ranking = numOfPlayers/4; 
		    }
		    int 	wholeRanking 	= wholeNumOfPlayers/4;
		    float percentageLeft 	= ranking-wholeRanking;
		    int addAPlayer =wholeRanking; int addBPlayer =wholeRanking; int addCPlayer =wholeRanking;
		    if (percentageLeft == 0.75){
			  addAPlayer =wholeRanking +1 ;
			  addBPlayer =wholeRanking +1;
			  addCPlayer =wholeRanking +1;
		    }
		    else if (percentageLeft == 0.50){
			  addAPlayer =wholeRanking +1;
			  addBPlayer =wholeRanking +1;
		    }
		    else if (percentageLeft == 0.25){
			  addAPlayer =wholeRanking +1;
		    }
		  
		    System.out.println("************** After Sort ************************");
		   
		    for (int i=0; i<vPlayerHandicap.size();i++){
			   PlayerHandicap spa =(PlayerHandicap) vPlayerHandicap.get(i);
			   if (addAPlayer > 0){
				   spa.setRank('A');
				   addAPlayer--;
			   }else if (addBPlayer > 0){
				   spa.setRank('B');
				   addBPlayer--;
			   }else if (addCPlayer > 0){
				   spa.setRank('C');
				   addCPlayer--;
			   }else{
				   spa.setRank('D');
			   }
		   }
		
		   return vPlayerHandicap;
		
		
		}
		
     private ArrayList rankPlayersAVG(ArrayList <PlayerAverage>vPlayerHandicap ){
			   
			   float 	numOfPlayers = 0; 		numOfPlayers 		    = vPlayerHandicap.size();
			   int 		wholeNumOfPlayers =0; 	wholeNumOfPlayers 	= vPlayerHandicap.size();
			   float 	ranking =0;
			   
			   
			   if (numOfPlayers != 0)  { 
				   ranking = numOfPlayers/4; 
			    }
			    int 	wholeRanking 	= wholeNumOfPlayers/4;
			    float percentageLeft 	= ranking-wholeRanking;
			    int addAPlayer =wholeRanking; int addBPlayer =wholeRanking; int addCPlayer =wholeRanking;
			    if (percentageLeft == 0.75){
				  addAPlayer =wholeRanking +1 ;
				  addBPlayer =wholeRanking +1;
				  addCPlayer =wholeRanking +1;
			    }
			    else if (percentageLeft == 0.50){
				  addAPlayer =wholeRanking +1;
				  addBPlayer =wholeRanking +1;
			    }
			    else if (percentageLeft == 0.25){
				  addAPlayer =wholeRanking +1;
			    }
			  
			    System.out.println("************** After Sort ************************");
			   
			    for (int i=0; i<vPlayerHandicap.size();i++){
			       PlayerAverage spa =(PlayerAverage) vPlayerHandicap.get(i);
				   if (addAPlayer > 0){
					   spa.setRank('A');
					   addAPlayer--;
				   }else if (addBPlayer > 0){
					   spa.setRank('B');
					   addBPlayer--;
				   }else if (addCPlayer > 0){
					   spa.setRank('C');
					   addCPlayer--;
				   }else{
					   spa.setRank('D');
				   }
			   }
			
			   return vPlayerHandicap;
			
			
			}

	@Override
	public boolean isValidUserId(String userId) {
		if (players== null)  // any time put also put in playerCacheByUserId
		{
			getAllPlayers();
		}
		
		Player valid = playerCacheByUserId.get(userId);
		if (valid !=null)
		{
			return true;
		}
		return false;
	}
	@Override
	public Player getPlayerByUserId(String userId) {
		
		if (players== null)  // any time put also put in playerCacheByUserId
		{
			getAllPlayers();
		}
		
		Player validPlayer = playerCacheByUserId.get(userId);
		
		return validPlayer;
	}

	@Override
	public int getPlayerId(String playerName) {
		// TODO Auto-generated method stub
		return 0;
	}
     
  
		
}	
		
		
	
	

