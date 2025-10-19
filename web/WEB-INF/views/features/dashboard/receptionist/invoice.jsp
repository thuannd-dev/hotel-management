<%-- 
    Document   : invoice
    Created on : Oct 16, 2025, 3:24:35 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment For check-out Page</title>
    </head>
    <body>
    <c:set var="booking" value="${requestScope['booking']}" />
    <%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
    <div>
        <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
        <p><strong>Guest Name:</strong> ${booking.guestFullName}</p>
        <p><strong>Room Number:</strong> ${booking.roomNumber}</p>
        <p><strong>Status:</strong> ${booking.status}</p>
        <p><strong>Service:</strong> ${booking.status}</p>
        <p><strong>Check-in date:</strong> ${booking.checkInDate}</p>
        <p><strong>Check-out date:</strong> ${booking.checkOutDate}</p>
        <!--<p><strong>Rental days:</strong> ${{booking.checkOutDate} - {booking.checkInDate}}</p>-->
        <p><strong>Special Request</strong> ${booking.specialRequest}</p>        
    </div>
    </body>
</html>
