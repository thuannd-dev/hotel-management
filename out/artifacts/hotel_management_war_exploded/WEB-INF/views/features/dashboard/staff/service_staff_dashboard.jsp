<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ page import="com.hotel_management.domain.dto.staff.StaffViewModel" %>
<%
    HttpSession userSession = request.getSession(false);
    String fullName = "User";
    if (userSession != null) {
        Object currentUser = userSession.getAttribute("currentUser");
        if (currentUser instanceof StaffViewModel) {
            fullName = ((StaffViewModel) currentUser).getFullName();
        }
    }
%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Service Staff Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        :root {
            --color-primary: #1B4965;
            --color-secondary: #75C3B3;
            --color-background: #F8F9FA;
            --color-text: #333333;
            --color-accent-sand: #D4B483;
            --color-danger: #D34E51;
            --color-success: #28a745;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: var(--color-background);
            color: var(--color-text);
            margin: 25px;
        }
        h2 {
            color: var(--color-primary);
            border-bottom: 3px solid var(--color-secondary);
            padding-bottom: 10px;
            margin: 0;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            border-bottom: 3px solid var(--color-secondary);
            padding-bottom: 10px;
        }
        .header-container h2 {
            border-bottom: none;
            padding-bottom: 0;
        }
        .user-info-bar {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .user-name {
            color: var(--color-primary);
            font-weight: bold;
            font-size: 16px;
        }
        .btn-logout {
            background-color: var(--color-danger);
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
            transition: background-color 0.3s;
        }
        .btn-logout:hover {
            background-color: #a83e41;
        }
        .nav-tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            border-bottom: 2px solid var(--color-secondary);
            padding-bottom: 10px;
        }
        .nav-tab {
            padding: 10px 20px;
            background-color: white;
            color: var(--color-primary);
            text-decoration: none;
            border-radius: 4px 4px 0 0;
            transition: all 0.3s;
            border: 1px solid #ddd;
        }
        .nav-tab:hover {
            background-color: var(--color-secondary);
            color: white;
        }
        .nav-tab.active {
            background-color: var(--color-primary);
            color: white;
        }
        .search-container {
            background-color: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .search-bar {
            display: flex;
            gap: 10px;
            align-items: center;
            flex-wrap: wrap;
        }
        .search-bar input[type="text"] {
            flex: 1;
            min-width: 200px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .search-bar select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            background-color: white;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
            font-size: 14px;
        }
        .btn-primary {
            background-color: var(--color-primary);
            color: white;
        }
        .btn-primary:hover {
            background-color: #0E2938;
        }
        .btn-secondary {
            background-color: var(--color-secondary);
            color: white;
        }
        .btn-secondary:hover {
            background-color: #5ba89a;
        }
        .btn-warning {
            background-color: var(--color-accent-sand);
            color: white;
        }
        .btn-sm {
            padding: 5px 10px;
            font-size: 14px;
        }
        .tip {
            color: #666;
            font-style: italic;
            margin-top: 10px;
            font-size: 14px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #e0e0e0;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: var(--color-secondary);
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            display: flex;
            gap: 5px;
            flex-wrap: wrap;
        }
        .no-data {
            background-color: white;
            padding: 40px;
            text-align: center;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            color: #666;
        }
        .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            white-space: nowrap;
            border-width: 0;
        }
    </style>
</head>
<body>
<div class="header-container">
    <h2>Service Staff Management</h2>
    <div class="user-info-bar">
        <span class="user-name">
            <i class="fas fa-user" aria-hidden="true"></i>
            <span class="sr-only">Current user: </span><%= fullName %>
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout" aria-label="Logout from the system">
            <i class="fas fa-sign-out-alt" aria-hidden="true"></i>
            <span>Logout</span>
        </a>
    </div>
</div>

<!-- Navigation Tabs -->
<div class="nav-tabs">
    <a href="${pageContext.request.contextPath}/service-staff" class="nav-tab active">Dashboard</a>
    <a href="${pageContext.request.contextPath}/service-staff/statistics" class="nav-tab">Service Reports</a>
</div>

<!-- Search Form -->
<div class="search-container">
    <form action="service-staff" method="get" class="search-bar">
        <input type="text" name="searchValue" placeholder="Enter search value" value="${param.searchValue}">
        <select name="searchType">
            <option value="guestName" ${param.searchType == 'guestName' ? 'selected' : ''}>Guest Name</option>
            <option value="roomNumber" ${param.searchType == 'roomNumber' ? 'selected' : ''}>Room Number</option>
            <option value="guestPhone" ${param.searchType == 'guestPhone' ? 'selected' : ''}>Guest Phone</option>
            <option value="guestIdNumber" ${param.searchType == 'guestIdNumber' ? 'selected' : ''}>Guest Id Number</option>
        </select>
        <button type="submit" class="btn btn-primary">
            <i class="fas fa-search"></i> Search
        </button>
    </form>
    <c:if test="${not empty param.searchType and not empty param.searchValue}">
        <form action="service-staff" method="get" style="margin-top: 10px;">
            <button type="submit" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </button>
        </form>
    </c:if>
    <p class="tip"><i class="fas fa-info-circle"></i> Tip: Select a search type and enter the value.</p>
</div>

<!-- Result List -->
<c:set var="bookings" value="${requestScope['listCheckInBookingDetails']}" />
<c:if test="${not empty bookings}">
    <table>
        <thead>
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
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
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
                <td><span style="color: var(--color-success); font-weight: bold;">${b.status}</span></td>
                <td class="actions">
                    <a href="service-staff/services?bookingId=${b.bookingId}" class="btn btn-primary btn-sm">
                        <i class="fas fa-concierge-bell"></i> Record Service
                    </a>
                    <a href="service-staff/service-usage-detail?bookingId=${b.bookingId}" class="btn btn-warning btn-sm">
                        <i class="fas fa-edit"></i> Update Status
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty bookings}">
    <div class="no-data">
        <c:if test="${not empty param.searchType and not empty param.searchValue}">
            <i class="fas fa-search" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
            <p style="font-size: 18px; margin-bottom: 10px;">No results found</p>
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
            <i class="fas fa-bed" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
            <p style="font-size: 18px;">No checked-in bookings were found.</p>
        </c:if>
    </div>
</c:if>
</body>
</html>
