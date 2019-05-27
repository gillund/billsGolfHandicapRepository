package com.argonne.tournamentService;

import java.util.ArrayList;
import java.util.Hashtable;

import com.argonne.playerService.Player;

public interface TournamentService 
{
 public Hashtable getAllTournaments();

 public Hashtable addPlayersToTournament(ArrayList  tournaments) throws Exception;
 
 public Hashtable removeTournament(String planyerName) throws Exception;
 
 public Boolean arePlayersAssignedToTournament();
 
 public void updateTournamentAverages();
 
}
