<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shagnasty 2009 Open</title>
<body >

  <%@ page import="com.argonne.courseServices.*, com.argonne.utils.*, java.util.*" %>
    <%
           CourseService courseService 			= JhandicapFactory.getCourseService();
           Hashtable courses 					=  courseService.getAllCourses();
           List	 sortedCourses 					= getCourseList(courses);
    %>

  <i><h1 align="center" > Add Courses  </h1></i>
  <center>
   <div style="width: 550px; height: 400px; overflow: scroll; border: 5px " >
  	 <table align="left" border="2" cellspacing="2" cellpadding="2">
  	 <tr>
      		<th align="left"> Course Name </th>
      		<th align="left"> Slope </th>
      		<th align="left"> Rating </th>
      		<th align="left"> Yardage </th>
      		<th align="left"> Comments </th>
     
     </tr>
     
   	 <tr align="left" >
   	<%
   	   Iterator i = sortedCourses.iterator();
   	   while(i.hasNext()){
   		  Course c 			= (Course) i.next();
   	 %>
  		  <td align="left"> <%= c.name %> 		</td >
      	  <td align="left"> <%= c.slope %> 		</td>
      	  <td align="left"> <%= c.rating %> 	</td>
      	  <td align="left"> <%= c.yardage %> 	</td> 
      	  <td align="left"> <%= c.comments %>  	</td > 
   		</tr>
 	<%
     }
	%>
  </table>
</div>
<br></br>
 <%
         // get parameters from the request
        String courseName 	= request.getParameter("CourseName").trim();
        String slope 		= request.getParameter("slope").trim();
        String rating 		= request.getParameter("rating").trim();
        String yardage 		= request.getParameter("yardage").trim();
        String comments		= request.getParameter("comments").trim();
        String messageLine  = "";
        
        if (courseName.equals("")){
            messageLine = "Course not added -  Course name blank";
        }
        else if (slope.equals("")){
        	 messageLine = "Course not added -  Sope not entered";
        }
        else if (rating.equals("")){
       	 messageLine = "Course not added -  Rating not entered ";
       }
        else if (yardage.equals("")){
          	 messageLine = "Course not added -  Yardage not entered ";
       }
        int slopInint = 0; int yardageInInt = 0; float ratingInFloat = 0;
        try {
        	Integer i1  = new Integer(slope);
        	slopInint = i1.intValue(); 
        
        	Float f2  = new Float(rating);
       	    ratingInFloat = f2.floatValue();
      
        	
        
        	Integer I = new Integer(yardage);
        	 yardageInInt = I.intValue();
        }
        catch (Exception e) {
        	messageLine = "Course not added - Slope, Rating, Yardage must be numeric";
        }
        
        if (messageLine.equals("")){
        	messageLine = "Course ADDED";
        	
        	try {
        	   courseService.addCourse(courseName,ratingInFloat,slopInint,yardageInInt,comments);
               courseName= ""; slope= ""; rating= ""; yardage= ""; comments= "";
        	}
        	catch(Exception e){
        		messageLine = "Course not Added - Possiple Duplicate";
        	}
        }
        
 %>
 <h3 ><%= messageLine %>  </h3>
 

</center>
</p>
     <form action="addAcourse.jsp" method="get">
        <input type="submit" value="Return">
        <td> <input type="hidden" name="outCourseName" value="<%= courseName %> ">
        <td> <input type="hidden" name=outSlope value="<%= slope %> ">
        <td> <input type="hidden" name="outRating" value="<%= rating %> ">
        <td> <input type="hidden" name="outYardage" value="<%= yardage %> ">
        <td> <input type="hidden" name="outComments" value="<%= comments %> ">
        
    </form>
 </body>
 </html>
 
 
 <%!
   public Vector getCourseList(Hashtable courses) {
            
           Vector sortedCourses 				= new Vector();
           Enumeration enumeration 				= courses.elements();
           
           while ( enumeration.hasMoreElements() ) {
               Course course1 = (Course) enumeration.nextElement();
               boolean notInserted = true;
               for ( int i = 0 ; i < sortedCourses.size(); i++ ) {
                   Course course2 = (Course) sortedCourses.elementAt(i);
                   if ( course1.name.compareTo(course2.name) < 0 ) {
                       sortedCourses.add(i, course1);
                       notInserted = false;
                       break;
                   }
               }
               if ( notInserted ) {
                   sortedCourses.add(course1);
               }
           }
           return sortedCourses;
 }
    %>
 
 
