<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<meta charset="ISO-8859-1">
<style>

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
div.myDate {
	
	font-weight: 600;
	color: #fff;
	text-decoration: none;
	padding: 0 1rem;
	-webkit-transition: 1s ease-out;
	transition: 1s ease-out
	
}
div.login {
	
	font-weight: 600;
	color: #fff;
	text-decoration: none;
	padding: 0 1rem;
	-webkit-transition: 1s ease-out;
	transition: 1s ease-out
	
}

</style>
</head>

<header id="home" class="header">
 <nav class="nav" role="navigation">
    <div class="container nav-elements">
      <div class="branding">
      </div><!-- branding -->
      <ul class="navbar">
      <h1> <li><a href="registerPlayer.jsp">Register a Player</a></li> </h1> 
       <h1>      <li><a href="index.html">Home</a></li> </h1> 
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>
</header>

  <br>
<body>

	<div class="centered" >
	   <div class="myDate">
		<!-- displays date and time for today --> 
		
		 <%
         // get parameters from the request
        String message = (String) request.getAttribute("message");
		if (message == null) message="";
		System.err.println("*****" + message + "*********");
  		%>
 
 		<h1><%=	displayDate() %></h1>
		</div>
	
		<div class="login">
			<form action="LoginServlet" method="post" >
				<h2>Username</h2> 
				<input type="text" name="username" id="username">
				<br> 
				<h2>Password</h2> 
				<input type="password" name="password" id="password">
				<br> 
				<br>
				<input type="submit" name="function" value="Login" id="login">
			</form>
		</div>
		<br>
		
   </div>
<%!
	public String displayDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
		Date date = Calendar.getInstance().getTime();
		return dateFormat.format(date);
	}
%>
<br>
 <h1>

 <%= message %>
</h1> 

</body>
</html>