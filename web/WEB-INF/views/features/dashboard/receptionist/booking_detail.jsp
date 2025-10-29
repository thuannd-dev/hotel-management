<%-- 
    Document   : booking_detail
    Created on : Oct 16, 2025, 3:19:15 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guest Booking Details</title>
        <style>
            body {
                font-family: "Segoe UI", Arial, sans-serif;
                background-color: #f8fafc;
                padding: 30px;
            }
            h2 {
                color: #1a202c;
                margin-bottom: 10px;
            }
            p {
                color: #4a5568;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                margin-top: 20px;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 1px 8px rgba(0,0,0,0.05);
            }
            th, td {
                padding: 12px 16px;
                border-bottom: 1px solid #e2e8f0;
                text-align: left;
            }
            th {
                background-color: #edf2f7;
            }
            a {
                color: #3182ce;
                text-decoration: none;
            }
            a:hover {
                text-decoration: underline;
            }
            .back-link {
                display: inline-block;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>

        <h2>GUEST ROOMS</h2>

        <c:if test="${empty bookings}">
            <p>No bookings found for this guest.</p>
        </c:if>

        <c:if test="${not empty bookings}">
            <table>
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Room Name</th>
                        <th>Check-in Date</th>
                        <th>Check-out Date</th>
                        <th>Status</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${bookings}">
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.roomNumber}</td>
                            <td>${booking.checkInDate}</td>
                            <td>${booking.checkOutDate}</td>
                            <td>${booking.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/receptionist/create-payment?bookingId=${booking.bookingId}"
                                   class="action-link">Get Invoice</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/receptionist">← Back to Guest List</a>
        </div>

    </body>
</html>