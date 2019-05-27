package com.argonne.playerService;

import java.util.Vector;

import com.argonne.handicapServices.Round;
import com.argonne.utils.DuplicateException;

public interface PlayerHandicapService {
	 public Vector 	addScore(String aDate, String aPlayer, String aCourse, int aScore) throws Exception, DuplicateException;
	 public void 	deleteScore(String aPlayer, Round aRound) throws Exception;
	 public Vector 	getScoresForPlayer(String aPlayer);
	 public float 	getHandicap(String aPlayer) ;
     public float 	getAverageDelta(String aPlayer) ;
     public float 	getAverage(String aPlayer) ;
     
}
