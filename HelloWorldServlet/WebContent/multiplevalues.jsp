<%-- 
    Document   : multiplevalues
    Created on : Jan 21, 2009, 2:50:03 PM
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
    // returns the values of items selected in a list box.
    String[] selectedCountries = 
        request.getParameterValues("country");
    for (int i = 0; i < selectedCountries.length; i++)
    {
%>
        <%= selectedCountries[i] %> <br>
<%
    }
%>
    </body>
</html>
