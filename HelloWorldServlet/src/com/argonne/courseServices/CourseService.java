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

import java.util.Hashtable;

/**
*
* @author Mark Woyna
* @since 11/14/2004
* @version 1.0
*/

public interface CourseService {

    public Hashtable getAllCourses();
    
    public Course getCourse(String courseName);

    public Hashtable addCourse(String aName, float aRating, int aSlope, int theYardage, String theComments) throws Exception;

    public Hashtable removeCourse(String courseName) throws Exception;

}
