package com.argonne.tournamentService;
import java.io.Serializable;
import java.util.ArrayList;

import com.argonne.playerService.PlayerAverage;
public class RyderCupPlayers  implements Serializable{
	
	private PlayerAverage[] blueTeam = null;
	private ArrayList blueTeamList = new ArrayList(); 
	private int   blueNumberOfPlayers =0;
	private float blueAverageScore =0;
	private float blueTotalScore =0;
	
	private PlayerAverage[] redTeam  = null;
	private ArrayList redTeamList = new ArrayList(); 
	private float redAverageScore =0;
	private int   redNumberOfPlayers =0;
	private float redTotalScore =0;
	
	private int pointsBlueTeam =0;
	private int pointsRedTeam =0;
	
	RyderCupPlayers (int numberOfPlayers){
		blueTeam = new PlayerAverage[0];
		redTeam  = new PlayerAverage[0];
	}
	
	public void setBlueTeam (PlayerAverage[] smp){
		
		blueTeam = smp;
	}
	public void setRedTeam (PlayerAverage [] smp){
		redTeam = smp;
	}
	
	public PlayerAverage[] getBlueTeam() {
		blueTeam = new PlayerAverage[blueTeamList.size()];
		for (int i=0; i<blueTeamList.size();i++){
			blueTeam[i] = (PlayerAverage) blueTeamList.get(i);
		}
		return blueTeam;
		
	}
	public void  setBlueTeam(PlayerAverage pa) throws Exception {
		
		if (isPlayerAssignedToTeam(pa)){
			throw new Exception();
		}
	
		blueNumberOfPlayers = blueNumberOfPlayers +1;	
	    blueTotalScore = blueTotalScore + pa.avg;
	    blueAverageScore = blueTotalScore/blueNumberOfPlayers;
	    
		blueTeamList.add(pa);
		
	}
	public void  setRedTeam(PlayerAverage pa)  throws Exception {
		
		if (isPlayerAssignedToTeam(pa)){
				throw new Exception();
			}
		
		redNumberOfPlayers = redNumberOfPlayers +1;	
	    redTotalScore 		= redTotalScore + pa.avg;
		redAverageScore 	= redTotalScore/redNumberOfPlayers;
		redTeamList.add(pa);
		
	}
	
	public PlayerAverage [] getRedTeam() {
		redTeam = new PlayerAverage[redTeamList.size()];
		for (int i=0; i<redTeamList.size();i++){
			redTeam[i] = (PlayerAverage) redTeamList.get(i);
		}
		return redTeam;
	}
	public void addPointsBlueTeam (int points){
		pointsBlueTeam = pointsBlueTeam + points;
	}
	public void addPointsRedTeam (int points){
		pointsRedTeam =pointsRedTeam + points;
	}
	public float getTotalRedTeamAveragge(){
		return redAverageScore;
	}
	public float getTotalBlueTeamAverage(){
		return blueAverageScore;
	}
	public int getRedNbrPlayers(){
		return redNumberOfPlayers;
	}
	public int getBlueNbrPlayers(){
		return blueNumberOfPlayers;
	}
	
	public boolean  isPlayerAssignedToTeam (PlayerAverage pa)  {
		
	    boolean AssignedToTeam = false;
		PlayerAverage[] playerAverage = getBlueTeam();
		for (int i =0; i<playerAverage.length;i++){
			if (pa.Player.equals(playerAverage[i].Player)){
				AssignedToTeam = true;
			}
			
	    }
		
		PlayerAverage[] playerAverageRed = getRedTeam();
		for (int i =0; i<playerAverageRed.length;i++){
			if (pa.Player.equals(playerAverageRed[i].Player)){
				AssignedToTeam=true;
			}
		}
		
		
		return AssignedToTeam;
	}
	
    public String playerTeam (String pa)  {
		
	    boolean AssignedToTeam = false;
		PlayerAverage[] playerAverage = getBlueTeam();
		for (int i =0; i<playerAverage.length;i++){
			if (pa.equals(playerAverage[i].Player)){
				return "Blue" ;
			}
			
	    }
		
		PlayerAverage[] playerAverageRed = getRedTeam();
		for (int i =0; i<playerAverageRed.length;i++){
			if (pa.equals(playerAverageRed[i].Player)){
				return "Red" ;
			}
		}
		
		return "Neither";
		
		
	}
	
	
	
	
}
