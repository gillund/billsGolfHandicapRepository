<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/nav.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registered Players</title>
<style>
</style>
</head>
<header id="home" class="header">
 <nav class="nav" role="navigation">
    <div class="container nav-elements">
      <div class="branding">
      </div><!-- branding -->
      <ul class="navbar">
       <h1>      <li><a href="index.html">Home</a></li> </h1> 
       <h1>      <li><a href="jhandicap.html">Handicap Maintenance</a></li> </h1> 
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>
</header>

<body>
 <h1 align="center" > Registerd Players  </h1>


   <div class="playerContainer" > 
  	 <table align="center" border="2" cellspacing="2" cellpadding="2">
  	 <tr>
      		<th align="left"> Player Name </th>
      		<th align="left"> Email </th>
      		<th align="left"> Phone </th>
      		<th align="left"> UserId </th>
      		<th align="left"> Handicap </th>
     
     </tr>
     
   	 <tr align="left" >
   	 <%@ page import="com.argonne.playerService.*, com.argonne.utils.*, java.util.*" %>
   	<%
   	   Vector sortedPlayers = (Vector) request.getAttribute("Players");
   	   if (sortedPlayers!=null){
   	       Iterator i = sortedPlayers.iterator();
   	       PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
   	       while(i.hasNext()){
   		      Player p 			= (Player) i.next();
   		      float handicap = phs.getHandicap(p.getfirstName());
   		   %>
  		      <td align="left"> <%= p.getfirstName() %> 		</td >
      	      <td align="left"> <%= p.getEmail() %> 		</td>
      	      <td align="left"> <%= p.getPhoneNumber() %> 	</td>
      	      <td align="left"> <%= p.getUserId() %> 	</td>
      	      <td align="left"> <%= handicap  %> 	</td>
   		      </tr>
 	        <%
           }
   	   }
	%>
  </table>
 
</div>
<br>

  
  <%
         // get parameters from the request
        Player p = (Player) request.getAttribute("Player");
  
        String 	playerName 		= p.getfirstName() ;
        String  email 			= p.getEmail();
        String  phone           = p.getPhoneNumber(); 
        String userid      		= p.getUserId();
        String password			= p.getPassword();
        String message = (String) request.getAttribute("Message");
  %>
   



<br>
<h1 >
   <%= message %>
</h1>
</div>
<br>

    
</body>
</html>