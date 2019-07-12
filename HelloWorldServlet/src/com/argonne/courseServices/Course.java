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
 
package com.argonne.courseServices;

public class Course {
	private int courseId;
	public String name;
	public int slope;
	public float rating;
	public int yardage;
	public String comments;
	
	public Course(String aName, float aRating, int aSlope, int theYardage, String theComments) {
	    name = aName;
	    rating = aRating;
	    slope = aSlope;
	    yardage = theYardage;
	    comments = theComments;
	}
	public Course() {
		
	}
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getYardage() {
		return yardage;
	}

	public void setYardage(int yardage) {
		this.yardage = yardage;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String toString()
	{
		return name;
	}
    
    
}