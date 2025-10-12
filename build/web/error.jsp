<%-- 
    Document   : error
    Created on : Oct 11, 2025, 10:34:47 PM
    Author     : TR_NGHIA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>LOGIN THẤT BẠI!</h1>
        <hr>
        <h3>Lý do:</h3>
        <p style="color: red;">
            <%
                if (request.getAttribute("ERROR_MESSAGE") != null) {
                    out.print(request.getAttribute("ERROR_MESSAGE"));
                } else {
                    out.print("Unknown error.");
                }
            %>
        </p>
    </body>
</html>
