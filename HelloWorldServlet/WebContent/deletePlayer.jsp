<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/nav.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Player from System</title>
<style>
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
<body>
<center>
<h1>Select to Delete </h1>
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
 </body>
</html>