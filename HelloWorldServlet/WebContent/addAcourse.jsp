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
           Vector sortedCourses 				= getCourseList(courses);
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
  <% 
  String outCourse = request.getParameter("outCourseName"); 
  String outSlope = request.getParameter("outSlope"); 
  String outRating = request.getParameter("outRating"); 
  String outYardage = request.getParameter("outYardage"); 
  String outComment = request.getParameter("outComments"); 
  
  
  
  if (outCourse == null){
	outCourse = "";
  }
  if (outSlope == null){
		outSlope = "";
  }
  if (outRating == null){
		outRating = "";
  }
  if (outYardage == null){
		outYardage = "";
  }
  if (outComment == null){
		outComment = "";
  }

  
  %>


<form action="addAcourse2.jsp" method="get">
	<table cellspacing="1" cellpadding="1" border="0">
        <tr>
            <td align="right">Course Name:</td>
            <td><input type="text" name="CourseName" size="20" value="<%= outCourse %>" > </td>
            <td align="right">Slope:</td>
            <td><input type="text" name="slope" size="5"    value="<%= outSlope %>">  </td>
            <td align="right">Rating:</td>
            <td><input type="text" name=rating size="5"  value="<%= outRating %>"></td>
            <td align="right">Yardage:</td>
            <td><input type="text" name="yardage" size="5"  value="<%= outYardage %>"></td>
            <td align="right">Comments</td>
            <td><input type="text" name="comments" size="10" value="<%= outComment %>"> </td>
           <td> <input type="submit" value="Submit" ></td>
        </tr>
    </table>
 </form>
</center>
   

</p>
     <form action="jhandicap.html" method="post">
        <input type="submit" value="Return">
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
 
 
