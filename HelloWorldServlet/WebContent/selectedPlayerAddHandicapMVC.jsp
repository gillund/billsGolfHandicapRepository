<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD><style type="text/css">

@import url(https://fonts.googleapis.com/css?family=Raleway:300,600,700);
@media screen and (min-width:600px) {
	.header .tagline {
		display: block;
		color: #fff;
		max-width: 40em;
		line-height: 140%;
		font-size: 2.5rem
	}
	.header .tagline h1 {
		display: none
	}
	.header .tagline em {
		font-size: 140%;
		color: #f3d350;
		font-style: normal
	}
}

.nav {
	margin-top: 1.75rem /* navelements */
}

.nav .nav-elements { /* navbar */
	
}

.nav .nav-elements .branding {
	max-width: 360px;
	margin: 0 auto
}

.nav .nav-elements .navbar {
	margin: 0;
	padding: 0;
	line-height: 240%;
	list-style-type: none;
	margin-top: 2rem;
	width: 100%
}

.nav .nav-elements .navbar a {
	font-weight: 600;
	color: #2EFE2E;
	text-decoration: none;
	padding: 0 1rem;
	-webkit-transition: 1s ease-out;
	transition: 1s ease-out
}

.nav .nav-elements .navbar a:after {
	content: '';
	display: block;
	height: 5px;
	width: 0;
	margin: 0 .5rem;
	-webkit-transition: width .3s ease-out, background-color .3s ease-out;
	transition: width .3s ease-out, background-color .3s ease-out
}

.nav .nav-elements .navbar a:hover {
	color: #f3d350
}

.nav .nav-elements .navbar a:hover:after {
	width: 100%;
	background: #f3d350
}
@media screen and (min-width:600px) {
	.nav .nav-elements {
		display: -webkit-box;
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex
	}
}

@media screen and (min-width:600px) {
	.nav .nav-elements .branding {
		width: 30%;
		margin: 0
	}
}

@media screen and (min-width:600px) {
	.nav .nav-elements .navbar {
		display: -webkit-box;
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex;
		-webkit-box-pack: end;
		-webkit-justify-content: flex-end;
		-ms-flex-pack: end;
		justify-content: flex-end;
		-webkit-align-content: flex-start;
		-ms-flex-line-pack: start;
		align-content: flex-start;
		-webkit-flex-wrap: wrap;
		-ms-flex-wrap: wrap;
		flex-wrap: wrap
	}
}

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
  
  <h1> Add Score For Handicap </h1>
  
</header>


 <%@ page import="java.text.SimpleDateFormat, com.argonne.playerService.*,com.argonne.courseServices.*,com.argonne.handicapServices.*, com.argonne.utils.*, java.util.* , servlets.*" %>

<body>

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
     <td><font size=4 > <%= selectedPlayer.getAvgScore() %> </font></td>
   </tr>
</table>

<center>
   <div >
  	 <table>
  	 <tr>
      		<th align="left"> Date </th>
      		<th align="left"> Course </th>
      		<th align="left"> Rating </th>
      		<th align="left"> Slope </th>
      		<th align="left"> Score </th>
      		<th align="left"> delta </th>
             <th align="left"> Used </th>
              <th align="left"> Round # </th>
     </tr>
     
   	 <tr align="left" >
 <%
   Vector ps = (Vector) request.getAttribute("playerScores");
  
%>
<%
	   Iterator i = ps.iterator();
       int numberRound = 0;
	   while(i.hasNext()){
		  numberRound++;
   		  Round  r 			= (Round) i.next();
   		  String usedInCalc = "";
   		  if (r.inUse)
   		  {
   			  usedInCalc = "YES";
   		  }
   		  else
   		  { 
   			  usedInCalc = "";
	      }
   	 %>
  		  <td align="left"> <%= r.date %> 		</td >
      	  <td align="left"> <%= r.course %> 	</td>
      	  <td align="left"> <%= r.rating %> 	</td>
      	  <td align="left"> <%= r.slope %> 	    </td> 
      	  <td align="left"> <%= r.score %>  	</td > 
      	  <td align="left"> <%= r.delta %> 	    </td> 
      	  <td align="left"> <%= usedInCalc %>  	</td > 
      	   <td align="left"> <%= numberRound %>  	</td >
      	  
   		</tr>
 	<%
     }
	%>
  </table>
</div>
</center>
<%
         // get parameters from the request
        Round r = (Round) request.getAttribute("newRound");
        
        String 	date 		    = r.date;
        String 	course 			= r.course;
        int 	score 			= r.score;
        
        String message = (String) request.getAttribute("Message");
        if (message == null){
        	message = "";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        String todaysDate = sdf.format(cal.getTime());
  
        
        
  %>

<form action="AddScoreServlet" method="get" >
	<table cellspacing="1" cellpadding="5" border="0">
	<input type="hidden" name="function", value="ADDSCORE"> 
        <tr>
            <td align="right">Date:</td>
            
            <td><input type="date" name="Date" size="5" style="cursor: text" /></td>
            <td align="right">Course:</td>
      
            <td> <select size =5 name="courses" multiple>
            <%
        	   Vector sortedCourses = (Vector) request.getAttribute("Courses");
        	   if (sortedCourses != null) {
        	      Iterator i2 = sortedCourses.iterator();
        	      while(i2.hasNext()){
        		     Course c 			= (Course) i2.next();
        		     String s = c.getName() + "-" + c.getComments() + "," + " Slope= "+ c.getSlope() + " Rating= " + c.getRating();
             %>
               <option  value="<%= c.getCourseId() + "," +c.getName()   %>" > <%= s %>  </option>
               
            <%
        	      }
        	   }
            %>
            </td>
      
            <td align="right">Score:</td>
            <td><input type="text" name=Score size="5"  value="<%= score %>"></td>
            <td> <input type="submit" value="Add Score" ></td>
        </tr>
    </table>
 </form>
</center>
<br>
<h1 > 
   <%= message %>
</h1>
</center>
</body>
</html>


      









