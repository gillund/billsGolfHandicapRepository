<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/nav.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
 <%@ page import="java.text.SimpleDateFormat, com.argonne.playerService.*,com.argonne.courseServices.*,com.argonne.handicapServices.*, com.argonne.utils.*, java.util.* , servlets.*" %>

<body  >
<center>
<h1> Delete Player Scores </h1>
</center>
<center>
<%
   SelectedPlayer selectedPlayer = (SelectedPlayer) request.getAttribute("Player");
  
%>
 
<table cellspacing="1" cellpadding="5">
   <tr>
     <td align="right">Player:  </td>
     <td > <font size=4 >  <%=selectedPlayer.getName() %> </font>  </td>
     <td align="right">Handicap:  </td>
     <td><font size=4 >  <%=selectedPlayer.getHandicap() %> </font> </td>
     <td align="right">Average Delta: </td>
     <td><font size=4 > <%= selectedPlayer.getDelta() %> </font></td>
     <td align="right">Average Score:  </td>
     <td><font ize=4 > <%= selectedPlayer.getAvgScore() %> </font></td>
   </tr>
</table>

<form  action="DeleteScoreServlet" method="get">
  <select size="20" name=playerScoresToDelete multiple >
  	<%
  	   String message = (String) request.getAttribute("Message");
  	   if (message == null){
  		   message = "";
  	   }
   	   Vector playerScores = (Vector) request.getAttribute("playerScores");
   	   Iterator i = playerScores.iterator();
   	   
   	   while(i.hasNext()){
   		  Round r 			= (Round) i.next();
   		  String newName = r.player.replace(' ','+');
   		  String newCourse = r.course.replace(' ','+');
   		  
   	 %>
  
      <option  value= <%= r.getRoundId() + ","  + r.date + "," + newName +"," + newCourse + "," + r.score  %> > <%= r.date  + ", " + r.course + ", " + r.score %></option>
       
    <%
      
     }
	%> 
		
 </select>
<br> </br>
      <input type="hidden" name="function", value="DELETE"> 
      <input type="submit" value="Delete" >
</form>
</center>

<font color=red size=4 > 
   <%= message %>
</font>
   
</body>
</html>