<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
   
  <br>
   

   <form action="jhandicap.html" method="post">
        <input type="submit" value="Main Menu">
    </form>


</body>
</html>