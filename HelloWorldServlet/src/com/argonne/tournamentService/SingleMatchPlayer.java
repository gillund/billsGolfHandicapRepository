package com.argonne.tournamentService;

import java.util.Comparator;

import com.argonne.playerService.PlayerHandicap;

public class SingleMatchPlayer implements Comparator{
	public String playerName;
    public float avg;
    public float handicap;
    public char  rank;
    public int   score;
    public float adjScore;
    public SingleMatchPlayer(){}
    public SingleMatchPlayer(String aplayerName, float aavg, float ahandicap,char arank, int ascore, float anAdjScore) {
        
    	playerName = aplayerName;
        avg = aavg;
        handicap = ahandicap;
        rank = arank;
        score=ascore;
        adjScore = anAdjScore;
    }
    public int compare(Object playerAvg1, Object playerAvg2) {
    	SingleMatchPlayer p1 = (SingleMatchPlayer) playerAvg1;
    	SingleMatchPlayer p2 = (SingleMatchPlayer) playerAvg2;
	    
		Float compareAvg1 = new Float(p1.handicap);
		Float compareAvg2 = new Float(p2.handicap);
		
		return compareAvg1.compareTo(compareAvg2);
	}
    
    public StringBuffer writeSingleMatchPlayer()
    {
    	 StringBuffer line = new StringBuffer();
    	 line.append(playerName); line.append(",");
	     line.append(avg); line.append(",");
	     line.append(handicap); line.append(",");
	     line.append(rank); line.append(",");
	     line.append(score);line.append(",");
	     line.append(adjScore);
	     return line;
	     
	        
    }
    public String getPlayerName(){
       return playerName;
    }
    public void setScore(int ascore){
    	score=ascore;
    }
    public void setRank(char arank){
    	rank=arank;
    }
    public void setAdjScore(float aAdjScore){
    	adjScore = aAdjScore;
    }
}