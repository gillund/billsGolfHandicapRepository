package com.argonne.tournamentService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.argonne.playerService.Player.AllTests;

public class Tournament {
	
	    public String playerName;
	    public float avg;
	    public float handicap;
	   
	    public Tournament(String aplayerName, float aavg, float ahandicap) {
	        
	    	playerName = aplayerName;
	        avg = aavg;
	        handicap = ahandicap;
	        
	    }
	    
	   
}
