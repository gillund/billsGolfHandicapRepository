<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course Addition</title>
<link rel="stylesheet" href="css/nav.css">
<HEAD>
<style type="text/css">
</style>
</head>
<header id="home" class="header">
 <nav class="nav" role="navigation">
    <div class="container nav-elements">
      <div class="branding">
      </div><!-- branding -->
      <ul class="navbar">
       <h1>      <li><a href="jhandicap.html">Handicap Maintenance</a></li> </h1> 
       <h1>      <li><a href="index.html">Home</a></li> </h1> 
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>

</header>
<body >
  <center>
    <i><h1 align="center" > Course List </h1></i>
   <div class="playerContainer">
  	 <table align="center" border="2" cellspacing="2" cellpadding="2">
  	 <tr>
      		<th align="center"> Course Name </th>
      		<th align="center"> Slope </th>
      		<th align="center"> Rating </th>
      		<th align="center"> Yardage </th>
      		<th align="center"> Comments </th>
     
     </tr>
     
   	 <tr align="left" >
   	 <%@ page import="com.argonne.courseServices.*, com.argonne.utils.*, java.util.*" %>
   	<%
   	   Vector sortedCourses = (Vector) request.getAttribute("Courses");
   	   if (sortedCourses != null) {
   	      Iterator i = sortedCourses.iterator();
   	      while(i.hasNext()){
   		     Course c 			= (Course) i.next();
   	    %>
  		     <td align="center"> <%= c.name %> 		</td >
      	     <td align="center"> <%= c.slope %> 		</td>
      	     <td align="center"> <%= c.rating %> 	</td>
      	     <td align="center"> <%= c.yardage %> 	</td> 
      	     <td align="center"> <%= c.comments %>  	</td > 
   		   </tr>
 	   <%
        }
   	   }
	%>
  </table>
   <i><h1 align="center" > Add New Course </h1></i>
</div>
 
  <%
         // get parameters from the request
        Course c = (Course) request.getAttribute("Course");
        
        String 	courseName 		= c.name;
        int 	slope 			= c.slope;
        float 	rating 			= c.rating; 
        int 	yardage 		= c.yardage;
        String 	comments		= c.comments;
        
        String message = (String) request.getAttribute("Message");
  %>
 
 
<form action="jhandicapCourseServlet" method="get" >
	<table cellspacing="1" cellpadding="1" border="0">
	<input type="hidden" name="function", value="ADD"> 
        <tr>
            <td align="right">Course Name:</td>
            <td><input type="text" name="CourseName" size="20" value="<%= courseName %>" > </td>
            <td align="right">Slope:</td>
            <td><input type="text" name="slope" size="5"    value="<%=slope %>">  </td>
            <td align="right">Rating:</td>
            <td><input type="text" name=rating size="5"  value="<%=rating %>"></td>
            <td align="right">Yardage:</td>
            <td><input type="text" name="yardage" size="5"  value="<%=yardage %>"></td>
            <td align="right">Comments</td>
            <td><input type="text" name="comments" size="10" value="<%=comments %>"> </td>
           <td> <input type="submit" value="Submit" ></td>
        </tr>
    </table>
 </form>
</center>

<font color=red size=4 > 
   <%= message %>
</font>


</p>
 </body>
 </html>
 
 
 
 
