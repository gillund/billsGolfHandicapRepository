<%-- 
    Document   : enumeration
    Created on : Jan 21, 2009, 7:13:28 PM
    Author     : slytinen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <%@ page import="java.util.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <% Enumeration e = request.getParameterNames(); %>
      <table border="1">
      <% while (e.hasMoreElements( )) { 
          String pName = (String) e.nextElement( ); %>
          <tr><td><%= pName %></td><td><%= request.getParameter(pName) %></td></tr>
      <% } %>
  </table>
    </body>
</html>








