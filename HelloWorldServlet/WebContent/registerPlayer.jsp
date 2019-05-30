<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<link rel="stylesheet" href="css/nav.css">
<head>
<meta charset="ISO-8859-1">
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
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>
</header>
<body>

	<div class="centered" >
	   <div class="myDate">
		<!-- displays date and time for today --> 
		
		 <%
         // get parameters from the request
        String message = (String) request.getAttribute("message");
		if (message == null) message="";
		
  		%>
 
 		<h1><%=	displayDate() %></h1>
		</div>
 		<div class="register">
			<form action="RegisterAPlayerServlet" method="post" >
				<h3>Username</h3> 
				<input type="text" name="playerName" id="playerName">
				<br> 
				<h3>Email</h3> 
				<input type="text" name="email" id="password">
				<br> 
				<h3>Phone</h3> 
				<input type="text" name="phone" id="phone">
				<br> 
				<h3>Userid</h3> 
				<input type="text" name="userid" id="userid">
				<br> 
				<h3>Password</h3> 
				<input type="text" name="password" id="password">
				<br> 
				<input type="submit" name="function" value="Register" id="Register">
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