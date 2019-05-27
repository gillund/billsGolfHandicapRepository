package com.argonne.utils;

import com.argonne.client.HandicapClientFrame;
import com.argonne.client.RankPlayerByHandicapClient;
import com.argonne.client.RankPlayerClient;
import com.argonne.client.RyderCupFrame;
import com.argonne.client.RyderCupManualFrame;
import com.argonne.client.SinglesMatchFrame;
import com.argonne.client.TournamentClientFrame;
import com.argonne.courseServices.CourseService;
import com.argonne.courseServices.CourseServiceDBImpl;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerHandicapServiceDBImpl;
import com.argonne.playerService.PlayerHandicapServiceImpl;
import com.argonne.playerService.PlayerService;
import com.argonne.playerService.PlayerServiceDBImpl;
import com.argonne.tournamentService.RyderCupService;
import com.argonne.tournamentService.RyderCupServiceImpl;
import com.argonne.tournamentService.SinglesMatchService;
import com.argonne.tournamentService.SinglesMatchServiceImpl;
import com.argonne.tournamentService.TournamentService;
import com.argonne.tournamentService.TournamentServiceImpl;
public class JhandicapFactory
 {
	
	static CourseService courseService = null;
	static PlayerService playerService = null;
	static PlayerHandicapService playerHandicapService = null;
	static TournamentService tournamentService =null;
	static RankPlayerClient rankPlayerClient = null;
	static RankPlayerByHandicapClient rankPlayerByHandicapClient = null;
	static SinglesMatchFrame singlesMatchFrame =null;
	static SinglesMatchService singlesMatchService =null;
	static RyderCupService ryderCupService =null;
	static RyderCupFrame ryderCupFrame =null;
	static RyderCupManualFrame ryderCupManualFrame =null;
	static TournamentClientFrame tournamentClientFrame = null;
	static HandicapClientFrame handicapClientFrame = null;
	static JhandicapObjectPersist jhandicapObjectPersisit =null;
	public static CourseService getCourseService(){
	
	if ( courseService == null ) {
        Log.information("Using Courses Default DataFile.");
        courseService = new CourseServiceDBImpl();
    }

    return courseService;
	}
	
	 public static PlayerService getPlayerService() {
	        
	    	if ( playerService == null ) {
	            Log.information("Using Players Default DataFile.");
	            playerService = new PlayerServiceDBImpl();
	        }

	        return playerService;
	    }
	 public static TournamentService getTournamentService() {
	        
	    	if ( tournamentService == null ) {
	            Log.information("Using Players Default DataFile.");
	            tournamentService = new TournamentServiceImpl();
	        }

	        return tournamentService;
	    }
	 
	 
	 public static  PlayerHandicapService getPlayerHandicapService() {
    	 
	        if ( playerHandicapService == null ){
	        
	        	playerHandicapService = new PlayerHandicapServiceDBImpl(getCourseService(),getPlayerService());
	        }
	        return playerHandicapService;
	    }
	 
	 public static  HandicapClientFrame getHandicapClientFrame() {
    	 
	        if ( handicapClientFrame == null ){
	        
	        	handicapClientFrame = new HandicapClientFrame("");
	        }
	        return handicapClientFrame;
	 }
	 
	 public static  TournamentClientFrame getTournamentClientFrame() 
	 {
    	 
	     if ( tournamentClientFrame == null )
	     {
	        	tournamentClientFrame = new TournamentClientFrame("Shagnasty 2009 Tournament");
	     }
	     
	     return tournamentClientFrame;
	     
	 }
	 public static  RankPlayerClient getRankPlayerClient() {
		   
	        if ( rankPlayerClient == null ){
	        
	        	rankPlayerClient = new RankPlayerClient();
	        }
	        return rankPlayerClient;
	    }
	 
	 
	 public static  RankPlayerByHandicapClient getRankPlayerByHandicapClient() {
		   
	        if ( rankPlayerByHandicapClient == null ){
	        
	        	rankPlayerByHandicapClient = new RankPlayerByHandicapClient();
	        }
	        return rankPlayerByHandicapClient;
	    }
	 
	 public static  SinglesMatchFrame getSinglesMatchFrame() {
		   
	        if ( singlesMatchFrame == null ){
	        
	        	singlesMatchFrame = new SinglesMatchFrame();
	        }
	        return singlesMatchFrame;
	    }
	 
	 public static  SinglesMatchService getSinglesMatchService() {
		   
	        if ( singlesMatchService== null ){
	        
	        	singlesMatchService = new SinglesMatchServiceImpl();
	        }
	        return singlesMatchService;
	    }
	 public static  RyderCupService getRyderCupService() {
		   
	        if ( ryderCupService== null ){
	        
	        	ryderCupService = new RyderCupServiceImpl();
	        }
	        return ryderCupService;
	    }
	 
	 public static  RyderCupFrame getRyderCupFrame() {
		   
	        if ( ryderCupFrame == null ){
	        
	        	ryderCupFrame = new RyderCupFrame();
	        }
	        return ryderCupFrame;
	    }
	 
	 public static  RyderCupManualFrame getRyderCupManualFrame() {
		   
	        if ( ryderCupManualFrame == null ){
	        
	        	ryderCupManualFrame = new RyderCupManualFrame();
	        }
	        return ryderCupManualFrame;
	    }
	 
	 public static  JhandicapObjectPersist getObjectPersistance() {
		   
	        if ( jhandicapObjectPersisit == null ){
	        
	        	jhandicapObjectPersisit = new JhandicapObjectPersist();
	        }
	        return jhandicapObjectPersisit;
	    }
	 
	 
}
