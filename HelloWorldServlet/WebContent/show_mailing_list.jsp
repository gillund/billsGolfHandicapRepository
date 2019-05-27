<%-- 
    Document   : show_mailing_list
    Created on : Jan 21, 2009, 3:23:29 PM
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
    <%@ page import="business.*, data.*, java.util.*" %>
    <%
        ServletContext sc = this.getServletContext();
        String path = 
            sc.getRealPath("/WEB-INF/EmailList.txt");
        List<String> users = UserIO.read(path);
        if (users == null) {
    %> 
    <center><h3>Our Mailing List is Down</h3></center>
    <% } else { %>
    <center><h3>Our Mailing List</h3>
    as of <%= new Date( ) %>
    <p>
    <table border="1">
        <% Iterator<String> it = users.iterator( );
           while (it.hasNext( )) { %>
           <tr> <td> <%= it.next() %> </td> </tr>
        <% } } %>
    </table>
    </body>
</html>
