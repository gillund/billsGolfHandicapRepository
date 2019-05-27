<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<meta charset="ISO-8859-1">
<h1>Login User</h1>
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
				<input type="text" name="username" id="username">> 
				<br> 
				<h2>Password</h2> 
				<input type="password" name="password" id="password">
				<br> 
				<br>
				<input type="submit" name="function" value="Login" id="login">
			</form>
		</div>
		<br>
		<form action="index.html">
	        <input type="submit" value="Return">
	    </form>
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