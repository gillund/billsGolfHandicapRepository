package com.argonne.playerService;

import java.io.Serializable;
import java.util.Comparator;

public class PlayerAverage implements Comparator, Serializable {
	public String Player;
	public float  avg;
	private char rank;
	public int compare(Object playerAvg1, Object playerAvg2) {
	    PlayerAverage p1 = (PlayerAverage) playerAvg1;
	    PlayerAverage p2 = (PlayerAverage) playerAvg2;
	    
		Float compareAvg1 = new Float(p1.avg);
		Float compareAvg2 = new Float(p2.avg);
		
		return compareAvg1.compareTo(compareAvg2);
	}
	public void setPlayer(String aPlayer){
		Player = aPlayer;
	}
	public void setAvg(float aavg){
		avg = aavg;
	}
	public void setRank(char arank){
		rank = arank;
	}
	public char getRank(){
		return rank;
		
	}
}
