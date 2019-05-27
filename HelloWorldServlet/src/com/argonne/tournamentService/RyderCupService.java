package com.argonne.tournamentService;

import java.util.ArrayList;

import com.argonne.playerService.PlayerAverage;
public interface RyderCupService {
	
	public RyderCupPlayers 	generateRyderCupTeamByAvgScore (ArrayList <String> Players) throws Exception ;
	public RyderCupPlayers 	generateRyderCupTeamByAvgScorev2(ArrayList<String> players) throws Exception ;
	public RyderCupPlayers 	createRyderCupTeam () ;
	public RyderCupPlayers 	addManualBlueTeamPlayer(PlayerAverage pa)throws Exception;
	public RyderCupPlayers 	addManualRedTeamPlayer(PlayerAverage pa)throws Exception;
	public String 			playerTeam(String playerName);
	public int 				getNumberOfRyderCupPlayers();
	public RyderCupPlayers 	getManualRyderCupTeam ();
	public void 			resetManualRyderCupTeam(RyderCupPlayers rcp);
	public void 			removeRyderCupFromCache();
	public void             updatedRyderCupAverages();
	
	
	
}
