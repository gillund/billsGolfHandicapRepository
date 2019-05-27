<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<center>
<h2> Select A Player To Enter Handicap </h2>
</center>
<center>
 <%@ page import="com.argonne.playerService.*, com.argonne.utils.*, java.util.*" %>
   
<form  action="jhandicapPlayerHandicapServlet" method="get">
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
      <input type="hidden" name="function", value="PLAYERSELECTED"> 
      <input type="submit" value="Select Player " >
</form>
</center>
</p>
     <form action="jhandicap.html" method="post">
        <input type="submit" value="Return">
    </form>


</body>
</html>