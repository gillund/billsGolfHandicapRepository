<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Player from System</title>
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
<h1>List of Registered Players - Select to Delete </h1>
</center>
<center>
 <%@ page import="com.argonne.playerService.*, com.argonne.utils.*, java.util.*" %>
   
<form  action="jhandicapPlayerServlet" method="get">
  <select size="20" name="playerList" multiple >
  	<%
   	   Vector sortedPlayers = (Vector) request.getAttribute("Players");
   	   Iterator i = sortedPlayers.iterator();
   	   while(i.hasNext()){
   		  Player p 			= (Player) i.next();
   		  String name = p.firstName;
   		  String newName = name.replace(' ','+');
   		  
   	 %>
  
      <option value= <%= newName %> > <%= p.firstName  + ", " + p.eMail + ", " + p.phoneNumber  %></option>
    
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