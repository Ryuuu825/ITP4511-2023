<%-- 
    Document   : index
    Created on : 2023年4月7日, 上午1:30:03
    Author     : jyuba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if ( application.getAttribute("uservisited") == null ) {
                application.setAttribute("uservisited", 1);
            } else {
                int count = java.lang.Integer.parseInt(application.getAttribute("uservisited").toString());
                application.setAttribute("uservisited", count + 1);
            }
        %>
        <jsp:forward page="welcome.jsp" />
    </body>
</html>
