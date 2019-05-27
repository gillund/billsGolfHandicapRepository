package com.argonne.tournamentService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.argonne.playerService.PlayerAverage;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.utils.Log;

public class RyderCupServiceImpl implements RyderCupService{
	/*
	* @author William Gillund
	* @since 11/9/2004
	* @version 1.0
	* 	
	*/
	   
	    private RyderCupPlayers ryderCupAutomatedPlayers = null;
	    private RyderCupPlayers ryderCupManualPlayers = null;
		   
	    private PlayerService playersService = null;
	    
	    public RyderCupServiceImpl() {
	    	playersService = JhandicapFactory.getPlayerService();
	    }

		public RyderCupPlayers generateRyderCupTeamByAvgScore(ArrayList<String> players) throws Exception {
			
			ArrayList pa = playersService.getPlayersByAverageRound(players);
			
			Iterator i = pa.iterator();
			ryderCupAutomatedPlayers  = createRyderCupTeam();
			
			if (ryderCupAutomatedPlayers.getBlueNbrPlayers() + ryderCupAutomatedPlayers.getRedNbrPlayers() > 0){
				Log.information("computer generated number of Players = " 
					 +	"Blue = "+ ryderCupAutomatedPlayers.getBlueNbrPlayers() + "Red = " + ryderCupAutomatedPlayers.getRedNbrPlayers());
				return ryderCupAutomatedPlayers;
			}
			int nbr  =0;
			while(i.hasNext()){
				PlayerAverage playerAverage = (PlayerAverage) i.next();
				if (nbr % 2 ==0)
				{
					ryderCupAutomatedPlayers.setBlueTeam(playerAverage);
				}
				else
				{
					ryderCupAutomatedPlayers.setRedTeam(playerAverage);
				}
				nbr++;
			}
			
			return ryderCupAutomatedPlayers;
		}
        public RyderCupPlayers generateRyderCupTeamByAvgScorev2(ArrayList<String> players) throws Exception {
			
			ArrayList pa = playersService.getPlayersByAverageRound(players);
			Iterator i = pa.iterator();
			
			ryderCupAutomatedPlayers  = createRyderCupTeam();
			
			if (ryderCupAutomatedPlayers.getBlueNbrPlayers() + ryderCupAutomatedPlayers.getRedNbrPlayers() > 0){
				Log.information("computer generated number of Players = " 
					 +	"Blue = "+ ryderCupAutomatedPlayers.getBlueNbrPlayers() + "Red = " + ryderCupAutomatedPlayers.getRedNbrPlayers());
				return ryderCupAutomatedPlayers;
			}
			LinkedList playerLinkList = new LinkedList();
			int nbr  =0;
			while(i.hasNext()){
				PlayerAverage playerAverage = (PlayerAverage) i.next();
				playerLinkList.add(playerAverage);
			}
			
			int numPlayers = playerLinkList.size();
			while(numPlayers > 0)
			{
				if (numPlayers > 3)
				{
				   PlayerAverage firstPlayerb = (PlayerAverage) playerLinkList.removeFirst();
				   PlayerAverage lastPlayerb  =  (PlayerAverage) playerLinkList.removeLast();
				   ryderCupAutomatedPlayers.setBlueTeam(firstPlayerb);
				   ryderCupAutomatedPlayers.setBlueTeam(lastPlayerb);
				   PlayerAverage firstPlayerr = (PlayerAverage) playerLinkList.removeFirst();
				   PlayerAverage lastPlayerr  =  (PlayerAverage) playerLinkList.removeLast();
				   ryderCupAutomatedPlayers.setRedTeam(firstPlayerr);
				   ryderCupAutomatedPlayers.setRedTeam(lastPlayerr);
				   numPlayers = numPlayers -4;
				}
				if (numPlayers == 3)
				{
					 PlayerAverage firstPlayer1 = (PlayerAverage) playerLinkList.removeFirst();
					 PlayerAverage lastPlayer2  =  (PlayerAverage) playerLinkList.removeLast();
					 PlayerAverage lastPlayer3  =  (PlayerAverage) playerLinkList.removeFirst();
					 
					 ryderCupAutomatedPlayers.setBlueTeam(firstPlayer1);
					 ryderCupAutomatedPlayers.setRedTeam(lastPlayer2);
					 ryderCupAutomatedPlayers.setBlueTeam(lastPlayer3);
					 
					 numPlayers = numPlayers -3;
					
				}
				if (numPlayers == 2)
				{
					PlayerAverage firstPlayer = (PlayerAverage) playerLinkList.removeFirst();
				    PlayerAverage lastPlayer2 = (PlayerAverage) playerLinkList.removeLast();
					ryderCupAutomatedPlayers.setBlueTeam(firstPlayer);
					ryderCupAutomatedPlayers.setRedTeam(lastPlayer2);
					numPlayers = numPlayers -2;
					
				}
				if (numPlayers == 1)
				{
					PlayerAverage firstPlayer = (PlayerAverage) playerLinkList.removeFirst();
				  	ryderCupAutomatedPlayers.setBlueTeam(firstPlayer);
					numPlayers = numPlayers -1;
					
				}
			}
			
			return ryderCupAutomatedPlayers;
		}
		public RyderCupPlayers createRyderCupTeam (){
		//	if (ryderCupAutomatedPlayers ==null){
				ryderCupAutomatedPlayers = new RyderCupPlayers(0);
	//		}
			return ryderCupAutomatedPlayers;
			
		}
		public RyderCupPlayers getManualRyderCupTeam (){
			if (ryderCupManualPlayers ==null){
				ryderCupManualPlayers = new RyderCupPlayers(0);
			}
			return ryderCupManualPlayers;
			
		}
		public void resetManualRyderCupTeam(RyderCupPlayers rcp){
			ryderCupManualPlayers = rcp;
		}
		
		public RyderCupPlayers addManualBlueTeamPlayer (PlayerAverage pa)  throws Exception{
			ryderCupManualPlayers = getManualRyderCupTeam();
			ryderCupManualPlayers.setBlueTeam(pa);
			return ryderCupManualPlayers;
		}
		public RyderCupPlayers addManualRedTeamPlayer (PlayerAverage pa)throws Exception{
			ryderCupManualPlayers = getManualRyderCupTeam();
			ryderCupManualPlayers.setRedTeam(pa);
			return ryderCupManualPlayers;
		}
		public String playerTeam(String playerName){
			ryderCupManualPlayers = getManualRyderCupTeam();
			return ryderCupManualPlayers.playerTeam(playerName);
		}

	    public int getNumberOfRyderCupPlayers() {
			ryderCupManualPlayers = getManualRyderCupTeam();
			int numRed = ryderCupManualPlayers.getRedNbrPlayers();
			int numBlue = ryderCupManualPlayers.getBlueNbrPlayers();
			return numRed + numBlue;
		}

		public void removeRyderCupFromCache() {
			ryderCupManualPlayers = null;
			
		}
		public void updatedRyderCupAverages() {
			//  no need to do this right  now.
			
			}
			
   
}
