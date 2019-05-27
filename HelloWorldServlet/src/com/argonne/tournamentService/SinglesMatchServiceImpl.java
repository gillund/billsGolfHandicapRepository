package com.argonne.tournamentService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.argonne.playerService.PlayerAverage;
import com.argonne.playerService.PlayerHandicap;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;
import com.argonne.utils.RankPlayers;

public class SinglesMatchServiceImpl implements SinglesMatchService{

	/*
	* @author William Gillund
	* @since 11/9/2004
	* @version 1.0
	* 
	*    N O T E  all single mathch files are written with extension ".sm"
	* 
	* 
	*/
	    public static   Hashtable tempSinglesMatch = new Hashtable();
	    public static   Hashtable tempPlayerNames  =  null;
	   
	    public SinglesMatchServiceImpl() {
	    }
	   
	    public Boolean singlesMatchExist(String filename){
	    	return tempSinglesMatch.contains(filename);
	    }
	    
	    protected Hashtable getSingleMatchPlayers(String filename) {
	    	return (Hashtable) readTournaments(filename);
	    	
	    }

	    protected Hashtable readTournaments(String aFilename) {
	    	
	    	Hashtable t = (Hashtable) tempSinglesMatch.get(aFilename);
	        if (t==null){
	        	tempSinglesMatch.put(aFilename, new Hashtable());
	        }
	        BufferedReader input = null;
	        String line = null;
           
	        try {
	            input = getInputStream(aFilename);

	            // read the first record of the file
	            while ( (line = input.readLine()) != null ) {
	                try {
	                    SingleMatchPlayer singleMatchPlayer = createTournament(line);
	                    tempPlayerNames = (Hashtable) tempSinglesMatch.get(aFilename);
	                    tempPlayerNames.put(singleMatchPlayer.getPlayerName(),singleMatchPlayer);
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

	        return (Hashtable) tempSinglesMatch.get(aFilename);
	    }

	    protected BufferedReader getInputStream(String aFilename) throws IOException {
	    	String fileName = aFilename.replace('/', ' ' );
	        return new BufferedReader(new FileReader(fileName + ".sm"));
	    }

	    protected SingleMatchPlayer createTournament(String line) throws NumberFormatException {
	        StringTokenizer st = new StringTokenizer(line, ",");
	        String name = st.nextToken();
	        String avg = st.nextToken();
	        String hcap = st.nextToken();
	        String rank = st.nextToken();
	        String score = st.nextToken();
	        String adjScore = st.nextToken();
	       
	        return new SingleMatchPlayer(name, Float.parseFloat(avg), Float.parseFloat(hcap),rank.charAt(0),Integer.parseInt(score),Float.parseFloat(adjScore));
	  
	    }

	    protected void savePlayers(String fileName, Hashtable aSingleMatchCollection) {
	        writeTournaments(fileName, aSingleMatchCollection);
	    }

	    protected void writeTournaments(String aFilename, Hashtable aSingleMatchCollection) {
	    	
	    	Log.debug("Writing " + aSingleMatchCollection.size() + " Single Match players " + aFilename);
	        BufferedWriter writer = null;

	        try {
	            writer = getOutputStream(aFilename);
	            Enumeration enumeration = aSingleMatchCollection.elements();
	            while ( enumeration.hasMoreElements() ) {
	                SingleMatchPlayer singleMatchPlayer = (SingleMatchPlayer) enumeration.nextElement();
	                writeSingleMatch(singleMatchPlayer, writer);
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
	    	String fileName = aFilename.replace('/', ' ');
	        return new BufferedWriter(new FileWriter(fileName + ".sm"));
	    }
	    
	    protected void writeSingleMatch(SingleMatchPlayer singleMatchPlayer, BufferedWriter writer) throws IOException {
	      
	    	StringBuffer line = singleMatchPlayer.writeSingleMatchPlayer();
	        writer.write(line.toString());
	    	Log.debug("Writing " + line.toString());
	 	   
	        writer.newLine();
	    }


	
		public Hashtable addPlayersToMatch(String fileName, ArrayList <PlayerHandicap>  singleMatchPlayer) throws Exception
		{
			
			PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
			Hashtable mp = (Hashtable) tempSinglesMatch.get(fileName);
			if (mp == null)
			{
				mp = new Hashtable();
				tempSinglesMatch.put(fileName, mp);
			}
			
			mp.clear();   // clear hash
			for (int i=0; i<singleMatchPlayer.size();i++){
			    	PlayerHandicap p = (PlayerHandicap) singleMatchPlayer.get(i);
			    	SingleMatchPlayer s = new SingleMatchPlayer(p.getPlayer(),phs.getAverage(p.getPlayer()),p.gethandicap(),p.getRank(),0,0);
			    	mp.put(s.getPlayerName(), s);
             }
			
			tempSinglesMatch.put(fileName,mp);
			 
			writeTournaments(fileName, mp);
		    return mp;
	    }	
   
		public Hashtable <String, SingleMatchPlayer> getAllSingleMatchPlayers(String fileName) {
		    return getSingleMatchPlayers(fileName);
		}
		
		public ArrayList removePlayersFromMatch(String fileName, String playerName) throws Exception {
		    Hashtable mp = (Hashtable) tempSinglesMatch.get(fileName);
			if (mp == null)
			{
			   throw new Exception();
			}
			
			if(mp.containsKey(playerName)){
				mp.remove(playerName);
			}
			
			ArrayList arrayOfPlayers = setPlayerRanking(fileName);
			
			writeTournaments(fileName, mp);
		    
			return arrayOfPlayers;
	   }
		
		public Boolean arePlayersAssignedToMatches(String fileName) {

			BufferedReader input = null;
	        try {
	            input = getInputStream(fileName);
	            return true;		         
	         }
	         catch (IOException e) {
		     }
	         return false;
		}
		
		public Boolean arePlayersAssingedToSinglesMatch(String aFilename) {
			BufferedReader input = null;
	        try {
	            input = getInputStream(aFilename);
	            return true;		         
	            }
	        catch (IOException e) 
	        {
	            return false;
		    }
		}
		public ArrayList<SingleMatchPlayer> getPlayersByHandicap(ArrayList<SingleMatchPlayer> playerList) {
			
			   SingleMatchPlayer playAverageComparator = new SingleMatchPlayer();
			   Collections.sort(playerList, playAverageComparator);
			   RankPlayers.rankSMPByHandicap(playerList);
			  
			   
			   return playerList;

		}
		public ArrayList<SingleMatchPlayer> setPlayerRanking(String aFileName) {
			
			tempPlayerNames = (Hashtable) tempSinglesMatch.get(aFileName);
          	ArrayList simpleMatchPlayer = new ArrayList();
	        Enumeration enumeration = tempPlayerNames.elements();
	        
	        while ( enumeration.hasMoreElements() ) {
	            SingleMatchPlayer smp1 = (SingleMatchPlayer) enumeration.nextElement();
	            simpleMatchPlayer.add(smp1);
	           
	        }
	        
			SingleMatchPlayer playAverageComparator = new SingleMatchPlayer();
			Collections.sort(simpleMatchPlayer, playAverageComparator);
			RankPlayers.rankSMPByHandicap(simpleMatchPlayer);
			return simpleMatchPlayer;
	
		}
		public ArrayList<SingleMatchPlayer> addScore(String fileName, String playerName, int score) throws Exception {
			
			tempPlayerNames = (Hashtable) tempSinglesMatch.get(fileName);
			if (!(tempPlayerNames.containsKey(playerName))){
				throw new Exception();
			}
			
			SingleMatchPlayer smp = (SingleMatchPlayer) tempPlayerNames.get(playerName);
			smp.setScore(score);
			float ajdScore = score - smp.handicap;
			smp.setAdjScore(ajdScore);
			
			tempPlayerNames.put(playerName, smp);
			
			writeTournaments(fileName,tempPlayerNames);

			ArrayList simpleMatchPlayer = new ArrayList();
	        simpleMatchPlayer = setPlayerRanking(fileName);
	    	
			return simpleMatchPlayer;
		}
		public Hashtable addPlayersToMatchSMP(String fileName, ArrayList<SingleMatchPlayer> singleMatchPlayer) throws Exception {
			
			Hashtable mp = (Hashtable) tempSinglesMatch.get(fileName);
			if (mp == null)
			{
				mp = new Hashtable();
				tempSinglesMatch.put(fileName, mp);
			}
			
			mp.clear();   // clear hash
			for (int i=0; i<singleMatchPlayer.size();i++){
			    	SingleMatchPlayer p = (SingleMatchPlayer) singleMatchPlayer.get(i);
			    	SingleMatchPlayer s = new SingleMatchPlayer(p.playerName,p.avg,p.handicap,p.rank,p.score,p.adjScore);
			    	mp.put(s.getPlayerName(), s);
             }
			
			tempSinglesMatch.put(fileName,mp);
			 
			writeTournaments(fileName, mp);
		    return mp;
	    }
		
		public ArrayList<SingleMatchPlayer> calculateLowScoreByRank(String aFileName) {
			
			ArrayList playerByRank = setPlayerRanking(aFileName);
			SingleMatchPlayer smp = null;
			
			ArrayList lowestScoresByRank = new ArrayList();
			float aScore = 999; SingleMatchPlayer aSMP = null;
			float bScore = 999; SingleMatchPlayer bSMP =null;
			float cScore = 999; SingleMatchPlayer cSMP =null;
			float dScore = 999; SingleMatchPlayer dSMP =null;

			for (int i=0; i<playerByRank.size(); i++){
			    
				smp = (SingleMatchPlayer) playerByRank.get(i);
				if(smp.rank == 'A'){
				      if (aScore > smp.adjScore  && smp.adjScore > 0){
				       	  aSMP = smp;
				       	  aScore =smp.adjScore;
				      }
				    }
				    else if (smp.rank == 'B' && smp.adjScore > 0 )
				    {
				        if (bScore > smp.adjScore){
					        bSMP = smp;
					        bScore =smp.adjScore;
					   }
				    }
				    else if (smp.rank == 'C' && smp.adjScore >0 )
				    {
				        if (cScore >  smp.adjScore){
					       cSMP = smp;
					       cScore = smp.adjScore;
					    }
					 }
				     else if (smp.rank == 'D' & smp.adjScore > 0 ){
				         if (dScore > smp.adjScore){
					   	   dSMP = smp;
					       dScore = smp.adjScore;
					    }
				     }
				
			}
			if (aSMP != null){
				lowestScoresByRank.add(aSMP);
			}
			if (bSMP != null){
				lowestScoresByRank.add(bSMP);
			}
			if (cSMP != null){
				lowestScoresByRank.add(cSMP);
			}
			if (dSMP != null){
				lowestScoresByRank.add(dSMP);
			}
			
			return lowestScoresByRank;
			
			
		}	
		
		private void computeRankForPlayer(SingleMatchPlayer smp)
		{
			     
			
		}
		
		
		
		
		
		
		
}
		
		

		
		
	


