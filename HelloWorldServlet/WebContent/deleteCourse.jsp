<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Course from System</title>
<style>
body {
	margin:0;
	padding:0;
	text-align:center;
	background-image: url('images/agusta13.jpg');

}
table {
	font-weight: 600;
	color: #fff;
	text-shadow:black 0 0 1px;
	text-decoration: none;
	padding: 0;
	border: none;
	border-collapse:collapse;
	width:80%;
	transition: 1s ease-out;
	margin:auto

}
h1 {
 font-weight: 800;
 color:#2EFE2E;
 display: block;

}

th {
 color:#2EFE2E;
}

</style>
</head>
<body>
<center>
<h1> List of Courses - Select to remove </h1>
</center>
<center>
 <%@ page import="com.argonne.courseServices.*, com.argonne.utils.*, java.util.*" %>
   
<form  action="jhandicapCourseServlet" method="get">
  <select size="20" name="courseList" multiple >
  	<%
   	   Vector sortedCourses = (Vector) request.getAttribute("Courses");
   	   Iterator i = sortedCourses.iterator();
   	   while(i.hasNext()){
   		  Course c 			= (Course) i.next();
   		  String name = c.name;
   		  String newName = name.replace(' ','+');
   		  
   	 %>
  
      <option value= <%= newName %> > <%= c.name  + ", " + c.slope + ", " + c.rating + ", " + c.comments %></option>
    
    <%
     }
	%> 
		
 </select>
<br> </br>
      <input type="hidden" name="function", value="DELETE"> 
      <input type="submit" value="Delete" >
</form>
</center>
</p>
     <form action="jhandicap.html" method="post">
        <input type="submit" value="Return">
    </form>



</body>
</html>