<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Service Dashboard</title>
</head>
<body>

<!-- Search Form -->
<form action="find-check-in-booking" method="get" class="search-bar">
    <input type="text" name="guestName" placeholder="Search by Guest Name" value="${param.guestName}">
    <input type="text" name="roomNumber" placeholder="Search by Room Number" value="${param.roomNumber}">
    <button type="submit">Search</button>
</form>

<hr>

<!-- Result List -->
<c:set var="bookings" value="${requestScope['checkInBookingDetails']}" />
<c:if test="${not empty bookings}">
    <table style="border-collapse: separate; border-spacing: 2px;">
        <tr>
            <th>Booking ID</th>
            <th>Guest ID</th>
            <th>Guest Full Name</th>
            <th>Guest Phone</th>
            <th>Guest Id Number</th>
            <th>Room ID</th>
            <th>Room Number</th>
            <th>Check In Date</th>
            <th>Check Out Date</th>
            <th>Booking Date</th>
            <th>Status</th>
        </tr>

            <%--@elvariable id="b" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
        <c:forEach var="b" items="${bookings}">
            <tr>
                <td>${b.bookingId}</td>
                <td>${b.guestId}</td>
                <td>${b.guestFullName}</td>
                <td>${b.guestPhone}</td>
                <td>${b.guestIdNumber}</td>
                <td>${b.roomId}</td>
                <td>${b.roomNumber}</td>
                <td>${b.checkInDate}</td>
                <td>${b.checkOutDate}</td>
                <td>${b.bookingDate}</td>
                <td>${b.status}</td>
                <td>
                    <a href="service-staff/add-service?bookingId=${b.bookingId}">Record Service</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty bookings}">
    <p>No checked-in bookings were found.</p>
</c:if>
</body>
</html>
