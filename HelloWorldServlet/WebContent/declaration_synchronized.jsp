<%-- 
    Document   : fiveoutputs.jsp
    Created on : Jan 21, 2009, 2:34:05 PM
    Author     : slytinen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<%! int count = 0; %>

<h3>Welcome!  You are visitor number

<%  synchronized (this)  { ++count; } %>
<%= count %>.</h3>
        
    </body>
</html>
