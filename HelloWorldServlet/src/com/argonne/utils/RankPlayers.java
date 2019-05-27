package com.argonne.utils;

import java.util.ArrayList;
import com.argonne.tournamentService.SingleMatchPlayer;

public class RankPlayers {
	public static ArrayList rankSMPByHandicap(ArrayList<SingleMatchPlayer> vPlayerHandicap ){
		   
		   float 	numOfPlayers = 0; 		numOfPlayers 		    = vPlayerHandicap.size();
		   int 		wholeNumOfPlayers =0; 	wholeNumOfPlayers 	= vPlayerHandicap.size();
		   float 	ranking =0;
		   
		   
		   if (numOfPlayers != 0)  { 
			   ranking = numOfPlayers/4; 
		    }
		    int 	wholeRanking 	= wholeNumOfPlayers/4;
		    float percentageLeft 	= ranking-wholeRanking;
		    int addAPlayer =wholeRanking; int addBPlayer =wholeRanking; int addCPlayer =wholeRanking;
		    if (percentageLeft == 0.75){
			  addAPlayer =wholeRanking +1 ;
			  addBPlayer =wholeRanking +1;
			  addCPlayer =wholeRanking +1;
		    }
		    else if (percentageLeft == 0.50){
			  addAPlayer =wholeRanking +1;
			  addBPlayer =wholeRanking +1;
		    }
		    else if (percentageLeft == 0.25){
			  addAPlayer =wholeRanking +1;
		    }
		  
		    System.out.println("************** After Sort ************************");
		   
		    for (int i=0; i<vPlayerHandicap.size();i++){
		    	SingleMatchPlayer spa =(SingleMatchPlayer) vPlayerHandicap.get(i);
			   if (addAPlayer > 0){
				   spa.setRank('A');
				   addAPlayer--;
			   }else if (addBPlayer > 0){
				   spa.setRank('B');
				   addBPlayer--;
			   }else if (addCPlayer > 0){
				   spa.setRank('C');
				   addCPlayer--;
			   }else{
				   spa.setRank('D');
			   }
		   }
		
		   return vPlayerHandicap;
		
		
		}
}
