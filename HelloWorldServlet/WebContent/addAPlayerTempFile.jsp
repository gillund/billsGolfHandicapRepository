<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="lightgreen"  >
 <i><h1 align="center" > Add Players  </h1></i>
  <center>
   <div style="width: 600px; height: 400px; overflow: scroll; border: 5px " >
  	 <table align="left" border="2" cellspacing="2" cellpadding="2">
  	 <tr>
  	        <th align="left"> Player Number </th>
      		<th align="left"> Player Name </th>
      		<th align="left"> Email </th>
      		<th align="left"> Phone </th>
     
     </tr>
     
   	 <tr align="left" >
   	 <%@ page import="com.argonne.playerService.*, com.argonne.utils.*, java.util.*" %>
   	<%
   	   Vector sortedPlayers = (Vector) request.getAttribute("Players");
   	   if (sortedPlayers!=null){
   	       Iterator i = sortedPlayers.iterator();
   	       int playerNumber=1;
   	       while(i.hasNext()){
   		      Player p 			= (Player) i.next();
   	       %>
   	          
   	      <td class="row-text" align="left"> <%= playerNumber++ %> 		</td >
              <td class="row-text" align="left"> <%= p.firstName %> 		</td >
      	      <td class="row-text" align="left"> <%= p.eMail %> 		</td>
      	      <td class="row-text" align="left"> <%= p.phoneNumber %> 	</td>
   		      </tr>
 	        <%
           }
   	   }
	%>
  </table>
</div>
 
  <%
         // get parameters from the request
        Player p = (Player) request.getAttribute("Player");
  
        String 	playerName 		= p.firstName;
        String  email 			= p.eMail;
        String  phone           = p.phoneNumber; 
        
        String message = (String) request.getAttribute("Message");
  %>
 
 
<form action="jhandicapPlayerServlet" method="get" >
	<table cellspacing="1" cellpadding="1" border="0">
	<input type="hidden" name="function", value="ADD"> 
        <tr>
            <td align="right">Player Name:</td>
            <td><input type="text" name="playerName" size="20" value="<%= playerName %>" > </td>
            <td align="right">Email:</td>
            <td><input type="text" name="email" size="15"    value="<%=email %>">  </td>
            <td align="right">Phone:</td>
            <td><input type="text" name=phone size="10"  value="<%=phone %>"></td>
           <td> <input type="submit" value="Submit" ></td>
        </tr>
    </table>
 </form>
</center>

<font color=red size=4 > 
   <%= message %>
</font>


</p>
     <form action="/jhandicap.html" method="post">
        <input type="submit" value="Main Menu">
    </form>
</body>
</html>