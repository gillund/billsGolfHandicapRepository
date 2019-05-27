package com.argonne.tournamentService;

	import java.io.*;
import java.util.ArrayList;
	import java.util.Hashtable;
	import java.util.Enumeration;
	import java.util.StringTokenizer;

import com.argonne.playerService.PlayerHandicapService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

	public class TournamentServiceImpl implements TournamentService 
	{
		/*
		* @author William Gillund
		* @since 11/9/2004
		* @version 1.0
		*/
		    public static String DEFAULT_FILENAME = "tournament.dat";
	        public static   Hashtable tempTournament = new Hashtable();
		    private String filename;
		    private Hashtable tournamentHash ;
		  
		    public TournamentServiceImpl() {
		        this(DEFAULT_FILENAME);
		    }

		    public TournamentServiceImpl(String aFilename) {
		        filename = aFilename;
		    }

		    protected String getFilename() {
		         return filename;
		    }
		    
		    protected Hashtable getTournaments() {
		        if ( tournamentHash == null ) {
		            tournamentHash = readTournaments(getFilename());
		        }
		        return tournamentHash;
		    
		    }

		    protected Hashtable readTournaments(String aFilename) {
		      
		        BufferedReader input = null;
		        String line = null;

		        try {
		            input = getInputStream(aFilename);

		            // read the first record of the file
		            while ( (line = input.readLine()) != null ) {
		                try {
		                    Tournament tournament = createTournament(line);
		                    tempTournament.put(tournament.playerName, tournament);
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

		        return tempTournament;
		    }

		    protected BufferedReader getInputStream(String aFilename) throws IOException {
		        return new BufferedReader(new FileReader(aFilename));
		    }

		    protected Tournament createTournament(String line) throws NumberFormatException {
		        PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
		    	
		        StringTokenizer st = new StringTokenizer(line, ",");
		        String name = st.nextToken();
		        String avg = st.nextToken();
		        String hcap = st.nextToken();
		       
		        return new Tournament(name, Float.parseFloat(avg), Float.parseFloat(hcap));
		    }

		    protected void savePlayers(Hashtable aTournamentCollection) {
		        writeTournaments(getFilename(), aTournamentCollection);
		    }

		    protected void writeTournaments(String aFilename, Hashtable aTournamentCollection) {
		    	
		        Log.debug("Writing " + aTournamentCollection.size() + " touranments to " + aFilename);
		        BufferedWriter writer = null;

		        try {
		            writer = getOutputStream(aFilename);
		            Enumeration enumeration = aTournamentCollection.elements();
		            while ( enumeration.hasMoreElements() ) {
		                Tournament tournament = (Tournament) enumeration.nextElement();
		                writeTournament(tournament, writer);
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
		    
		    protected void writeTournament(Tournament tournament, BufferedWriter writer) throws IOException {
		    	PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
		        StringBuffer line = new StringBuffer();
		        line.append(tournament.playerName); line.append(",");
		        line.append(tournament.avg); line.append(",");
		        line.append(tournament.handicap); 
		        
		        writer.write(line.toString());
		        writer.newLine();
		    }

	
		
			public Hashtable addPlayersToTournament(ArrayList  tournaments) throws Exception {
			
				if (tournamentHash == null)
					tournamentHash = new Hashtable();
				else
				    tournamentHash.clear();
				
			    for (int i=0; i<tournaments.size();i++){
			    	Tournament t = (Tournament) tournaments.get(i);
			        	tournamentHash.put(t.playerName, t);
			       
			    }
			    writeTournaments(getFilename(), tournamentHash);
		        return getTournaments();
		    }	

			public Hashtable getAllTournaments() {
			       return (Hashtable) getTournaments().clone();
			}
			
			public Hashtable removeTournament(String playerName) throws Exception {
		   	
			  try{
			    if (tournamentHash.containsKey(playerName)){
			       tournamentHash.remove(playerName);
			    }
			    else
			    {
			   		throw  new Exception("Not Found");
			    }
			          
			    	}
		   	  catch (Exception e){
		   		 throw e;
		   	  }
		   	  writeTournaments(getFilename(), tournamentHash);
		   	  return getTournaments();
		    }

			public Boolean arePlayersAssignedToTournament() {

				BufferedReader input = null;
		        try {
		            input = getInputStream(getFilename());
		            return true;		         
		            }
		         catch (IOException e) {
 		        }
		            return false;
			    }

			public void updateTournamentAverages() {
				Hashtable h = getAllTournaments();
				if (h.size() > 0)
				{
					updateTournamentAverages(h);
				}
			}
			
			public void updateTournamentAverages(Hashtable tournamentPlayers)
			{
				PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
				Enumeration e = tournamentPlayers.elements();
				while ( e.hasMoreElements() ) {
		                Tournament tournament = (Tournament) e.nextElement();
		                tournament.avg = phs.getAverage(tournament.playerName);
		                tournament.handicap = phs.getHandicap(tournament.playerName);
		                tournamentHash.put(tournament.playerName, tournament);
		            }
				 
				 writeTournaments(getFilename(), tournamentHash);
			}
		
	}	
