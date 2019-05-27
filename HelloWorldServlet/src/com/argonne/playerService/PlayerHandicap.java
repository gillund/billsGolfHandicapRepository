package com.argonne.playerService;

import java.util.Comparator;

public class PlayerHandicap implements Comparator {
	String player;
	float  handicap;
	char   rank;
	public int compare(Object playerAvg1, Object playerAvg2) {
		PlayerHandicap p1 = (PlayerHandicap) playerAvg1;
		PlayerHandicap p2 = (PlayerHandicap) playerAvg2;
	    
		Float compareAvg1 = new Float(p1.handicap);
		Float compareAvg2 = new Float(p2.handicap);
		
		return compareAvg1.compareTo(compareAvg2);
	}
	public String getPlayer(){
		return player;
	}
	public float gethandicap(){
		return handicap;
	}
	public  void setPlayer(String aplayer){
		player = aplayer;
	}
	public void sethandicap(Float ahandicap){
		handicap= ahandicap;
	}
	public void setRank(char aRank){
		rank=aRank;
	}
	public char getRank(){
		return rank;
		
	}
}