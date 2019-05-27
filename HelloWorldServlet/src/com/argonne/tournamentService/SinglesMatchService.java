package com.argonne.tournamentService;

import java.util.ArrayList;
import java.util.Hashtable;

import com.argonne.playerService.PlayerHandicap;

public interface SinglesMatchService {

 public Hashtable<String, SingleMatchPlayer> getAllSingleMatchPlayers(String fileName);

 public Hashtable addPlayersToMatch (String fileName ,ArrayList <PlayerHandicap>  SingleMatchPlayer) throws Exception;
 
 public Hashtable addPlayersToMatchSMP (String fileName ,ArrayList <SingleMatchPlayer>  SingleMatchPlayer) throws Exception;
  
 public ArrayList removePlayersFromMatch(String fileName, String playerName) throws Exception;
 
 public ArrayList<SingleMatchPlayer> getPlayersByHandicap(ArrayList <SingleMatchPlayer> playerList);
 
 public ArrayList<SingleMatchPlayer> addScore(String fileName, String playerName, int score)throws Exception;

 public ArrayList<SingleMatchPlayer> setPlayerRanking(String aFileName);   
 
 public ArrayList<SingleMatchPlayer> calculateLowScoreByRank(String aFileName);
 
 
 public Boolean arePlayersAssingedToSinglesMatch(String fileName);
 
 
}
