<%-- 
    Document   : fivetimes
    Created on : Jan 21, 2009, 2:38:20 PM
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
<%
    int numOfTimes = 1;
    while (numOfTimes <= 5)
    {
%>
    <h1>This line is shown <%= numOfTimes %> 
        of 5 times in a JSP.</h1>
<%
        numOfTimes++;
    }
%>
    </body>
</html>
