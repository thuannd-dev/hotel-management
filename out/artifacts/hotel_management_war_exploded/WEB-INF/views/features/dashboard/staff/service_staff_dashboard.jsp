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
<form action="service-staff" method="get" class="search-bar">
    <input type="text" name="searchValue" placeholder="Enter search value" value="${param.searchValue}">
    <select name="searchType">
        <option value="guestName" ${param.searchType == 'guestName' ? 'selected' : ''}>Guest Name</option>
        <option value="roomNumber" ${param.searchType == 'roomNumber' ? 'selected' : ''}>Room Number</option>
        <option value="guestPhone" ${param.searchType == 'guestPhone' ? 'selected' : ''}>Guest Phone</option>
        <option value="guestIdNumber" ${param.searchType == 'guestIdNumber' ? 'selected' : ''}>Guest Id Number</option>
    </select>
    <button type="submit">Search</button>
</form>
<c:if test="${not empty param.searchType and not empty param.searchValue}">
    <form action="service-staff" method="get" style="margin: 10px 0;">
        <button type="submit">Back to Dashboard</button>
    </form>
</c:if>
<p>Tip: Select a search type and enter the value.</p>

<hr>

<!-- Result List -->
<c:set var="bookings" value="${requestScope['listCheckInBookingDetails']}" />
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

            <%--@elvariable id="b" type="edu.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
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
                    <a href="service-staff/services?bookingId=${b.bookingId}">Record Service</a>
                </td>
                <td>
                    <a href="service-staff/service-usage-detail?bookingId=${b.bookingId}">Update service status</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty bookings}">
    <c:if test="${not empty param.searchType and not empty param.searchValue}">
        <p>No results found for
            <strong>
                <c:choose>
                    <c:when test="${param.searchType == 'guestName'}">Guest Name</c:when>
                    <c:when test="${param.searchType == 'roomNumber'}">Room Number</c:when>
                    <c:when test="${param.searchType == 'guestPhone'}">Guest Phone</c:when>
                    <c:when test="${param.searchType == 'guestIdNumber'}">Guest Id Number</c:when>
                    <c:otherwise><c:out value="${param.searchType}"/></c:otherwise>
                </c:choose>:
                <c:out value="${param.searchValue}"/>
            </strong>.
        </p>
    </c:if>
    <c:if test="${empty param.searchType or empty param.searchValue}">
        <p>No checked-in bookings were found.</p>
    </c:if>
</c:if>
</body>
</html>
