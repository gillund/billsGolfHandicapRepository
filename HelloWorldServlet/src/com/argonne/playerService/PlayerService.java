package com.argonne.playerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Vector;

public interface PlayerService 
{
 public Hashtable getAllPlayers();
	    
 public Player getPlayer(String playerName);

 public PlayerAverage getPlayerAverage(String playerName );
 
 public Hashtable addPlayer(String afirstName, String alastName,  String aphoneNumber, String auserId, String apassWord) throws Exception;
 
 public Hashtable removePlayer(String name) throws Exception;
 
 public ArrayList<PlayerHandicap> getPlayersByHandicap();
 
 public ArrayList<PlayerAverage> getPlayersByAverageRound();
 
 public ArrayList<PlayerHandicap> getPlayersByHandicap(ArrayList <String> playerList);
 
 public ArrayList<PlayerHandicap> getPlayersByHandicapNoRank(ArrayList<String> playerList) ;

 public ArrayList<PlayerAverage> getPlayersByAverageRound(ArrayList <String> playerList);
 
 public boolean isValidUserId(String userId);
 
 public Player getPlayerByUserId(String userId);
 
 public int getPlayerId(String playerName) ; 
 
 
}
