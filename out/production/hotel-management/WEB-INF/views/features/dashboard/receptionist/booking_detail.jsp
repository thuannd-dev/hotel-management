<%-- 
    Document   : room_detail
    Created on : Oct 16, 2025, 3:19:15 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Detail Page</title>
    </head>
    <body>
        <c:set var="booking" value="${requestScope['booking']}" />
        <%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
        <div>        
            <p><strong>Status:</strong> ${booking.status}</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/invoice.jsp">Check-out</a>
        </div>
        <a href="${pageContext.request.contextPath}/receptionist">← Back to Booking List</a>
    </body>
</html>
