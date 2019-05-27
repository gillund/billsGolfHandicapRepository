/*
 * ====================================================================
 *
 * Copyright (c) 2004 Argonne Technologies.  All rights reserved.
 *
 * This file is part of JHandicap.
 *
 * JHandicap is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 * JHandicap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with JHandicap; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * Contact Mark Woyna at woyna at argonne dot com
 *
 */

package com.argonne.handicapServices;

public class Round {
	private int roundId;
	private int playerId;
    public String date;
    public String player;
    public String course;
    public int score;
    public int slope;
    public float rating;
    public float delta;
    public boolean inUse = false;
    

    public static int AVG_SLOPE = 113; // Average slope of a golf course
    public Round() {
    	
    }
    
    

    public Round(String aDate, String aPlayer, String aCourse, int aScore, int aSlope, float aRating) {
        date = aDate;
        course = aCourse;
        score = aScore;
        slope = aSlope;
        rating = aRating;
        player = aPlayer;
        delta = calculateDelta(aScore, aSlope, aRating);
        inUse = false;
    }
    public Round(String aDate, String aCourse, int aScore, int aSlope, float aRating) {
        date = aDate;
        course = aCourse;
        score = aScore;
        slope = aSlope;
        rating = aRating;
        delta = calculateDelta(aScore, aSlope, aRating);
        inUse = false;
    }
    protected static float calculateDelta(int aScore, int aSlope, float aRating) {
        return (aScore - aRating) * ((float) AVG_SLOPE / (float) aSlope);
    }
    public boolean equals(Object thatObject) {
  	  
      if (thatObject == this)
	  		return true;

	  if (!(thatObject instanceof Round))
	   	  	return false;

	  Round rO = (Round) thatObject;

	  return rO.date.equals(date)         &&
	         rO.course.equals(course)     &&
	         rO.player.equals(player)     &&
    	     rO.score == score ;
	  
    }
    public int hashCode() {
        int result = 17;
        
    	result = 37*result + date.hashCode();
    	result = 37*result + course.hashCode();
    	result = 37*result + player.hashCode();
    	result = 37*result + score;

    	return result;
      }

    public int compareTo(Object thatObject) {

        Round r = (Round) thatObject;
        
        if (date.compareTo(r.date) < 0)
  	      	return -1;
        if (date.compareTo(r.date) > 0)
  	      	return 1;
        
        if (course.compareTo(r.course) < 0)
  	      	return -1;
  		if (course.compareTo(r.course) > 0)
  	      	return 1;
  		
  	    if (player.compareTo(r.player) < 0)
	   		return -1;
  	    if (player.compareTo(r.player) > 0)
       		return -1;
	    
  	  	if (score < r.score)
  	  		return -1;

  	  	if (score > r.score)
  	  		return 1;

       return 0;

     }
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSlope() {
		return slope;
	}
	public void setSlope(int slope) {
		this.slope = slope;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public float getDelta() {
		return delta;
	}
	public void setDelta(float delta) {
		this.delta = delta;
	}
        
}