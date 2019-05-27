<%-- 
    Document   : display_email_entry
    Created on : Jan 21, 2009, 2:42:51 PM
    Author     : slytinen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Murach's Java Servlets and JSP</title>
</head>
    <%
   
        // get parameters from the request
        String firstName = 
            request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = 
            request.getParameter("emailAddress");
    %>

    <h1>Thanks for joining our email list</h1>

    <p>Here is the information that you entered:</p>
    <table border="1" cellspacing="1" cellpadding="1">
        <tr>
            <td align="right">First name:</td>
            <td><%= firstName %></td>
        </tr>
        <tr>
            <td align="right">Last name:</td>
            <td><%= lastName %></td>
        </tr>
        <tr>
            <td align="right">Email address:</td>
            <td><%= emailAddress %></td>
        </tr>
    </table>

    <p>To enter another email address, click on the Back <br>
    button in your browser or the Return button shown <br>
    below.</p>
        <form action="join_email_list.html" method="post">
        <input type="submit" value="Return">
    </form>



</html>

<body>