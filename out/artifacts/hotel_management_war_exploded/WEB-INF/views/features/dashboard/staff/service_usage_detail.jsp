<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Service Usage Detail</title>
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
        h2, h3, h4 {
            color: var(--color-primary);
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
            margin: 0;
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
        .info-card {
            background-color: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .info-card h3 {
            margin-top: 0;
            margin-bottom: 15px;
            border-bottom: 2px solid var(--color-secondary);
            padding-bottom: 10px;
        }
        .info-card h4 {
            margin-top: 20px;
            margin-bottom: 15px;
            color: var(--color-primary);
            font-size: 18px;
        }
        .info-row {
            display: flex;
            padding: 8px 0;
            border-bottom: 1px solid #f0f0f0;
        }
        .info-row:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: bold;
            width: 150px;
            color: var(--color-primary);
        }
        .info-value {
            flex: 1;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
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
        .total-row {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        .total-row td {
            border-top: 2px solid var(--color-primary);
        }
        .grand-total {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: right;
        }
        .grand-total-label {
            font-size: 20px;
            font-weight: bold;
            color: var(--color-primary);
        }
        .grand-total-value {
            font-size: 24px;
            font-weight: bold;
            color: var(--color-success);
            margin-left: 15px;
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
        .btn-warning {
            background-color: var(--color-accent-sand);
            color: white;
        }
        .btn-warning:hover {
            background-color: #c5a573;
        }
        .btn-success {
            background-color: var(--color-success);
            color: white;
        }
        .btn-sm {
            padding: 5px 10px;
            font-size: 13px;
        }
        .action-buttons {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .no-data {
            background-color: white;
            padding: 40px;
            text-align: center;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            color: #666;
        }
        .status-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-completed {
            background-color: #d4edda;
            color: #155724;
        }
        .status-pending {
            background-color: #fff3cd;
            color: #856404;
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
    <h2>Service Usage Detail</h2>
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

<c:set var="newBookingServiceUsageModels" value="${requestScope['listNewBookingServiceUsage']}" />
<c:set var="oldBookingServiceUsageModels" value="${requestScope['listOldBookingServiceUsage']}" />
<c:set var="booking" value="${requestScope['checkInBookingDetails']}" />
<%--@elvariable id="booking" type="edu.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>

<div class="info-card">
    <h3><i class="fas fa-info-circle"></i> Booking Information</h3>
    <div class="info-row">
        <div class="info-label">Booking ID:</div>
        <div class="info-value">${booking.bookingId}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Guest:</div>
        <div class="info-value">${booking.guestFullName}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Room:</div>
        <div class="info-value">${booking.roomNumber}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Status:</div>
        <div class="info-value"><span style="color: var(--color-success); font-weight: bold;">${booking.status}</span></div>
    </div>
    <div class="info-row">
        <div class="info-label">Check-in:</div>
        <div class="info-value">${booking.checkInDate}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Check-out:</div>
        <div class="info-value">${booking.checkOutDate}</div>
    </div>
</div>

<div class="info-card">
    <h3><i class="fas fa-concierge-bell"></i> Service Usage</h3>
    <!-- New Booking Service Usage -->
    <c:if test="${not empty newBookingServiceUsageModels}">
        <h4><i class="fas fa-clock"></i> New Service Usage</h4>
        <table>
            <thead>
            <tr>
                <th style="width: 40px;">#</th>
                <th>Service Name</th>
                <th>Type</th>
                <th>Service Price</th>
                <th>Quantity</th>
                <th>Date</th>
                <th>Status</th>
                <th>Request Time</th>
                <th>Completion Time</th>
                <th>SubPrice</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="newSubTotal" value="0"/>
            <%--@elvariable id="s" type="edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel"--%>
            <c:forEach var="s" items="${newBookingServiceUsageModels}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${s.serviceName}</td>
                    <td>${s.serviceType}</td>
                    <td>$${s.servicePrice}</td>
                    <td>${s.quantity}</td>
                    <td>${s.serviceDate}</td>
                    <td>
                        <span class="status-badge ${s.bookingServiceStatus == 'Completed' ? 'status-completed' : 'status-pending'}">
                            ${s.bookingServiceStatus}
                        </span>
                    </td>
                    <td><c:out value="${s.requestTime != null ? s.requestTime : '-'}"/></td>
                    <td><c:out value="${s.completionTime != null ? s.completionTime : '-'}"/></td>
                    <td><strong>$${s.subPrice}</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${s.bookingServiceStatus != 'Completed'}">
                                <a href="booking-services/status?id=${s.bookingServiceId}" class="btn btn-success btn-sm">
                                    <i class="fas fa-check"></i> Mark Completed
                                </a>
                            </c:when>
                            <c:otherwise>
                                <span style="color: var(--color-success); font-weight: bold;">
                                    <i class="fas fa-check-circle"></i> Completed
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="newSubTotal" value="${newSubTotal + s.subPrice}"/>
            </c:forEach>
            <tr class="total-row">
                <td colspan="9" style="text-align:right;">Total of new service usage:</td>
                <td colspan="2"><strong>$${newSubTotal}</strong></td>
            </tr>
            </tbody>
        </table>
    </c:if>

    <!-- Old Booking Service Usage -->
    <c:if test="${not empty oldBookingServiceUsageModels}">
        <h4><i class="fas fa-history"></i> Services Used Previously</h4>
        <table>
            <thead>
            <tr>
                <th style="width: 40px;">#</th>
                <th>Service Name</th>
                <th>Type</th>
                <th>Service Price</th>
                <th>Quantity</th>
                <th>Date</th>
                <th>Status</th>
                <th>Request Time</th>
                <th>Completion Time</th>
                <th>SubPrice</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="oldSubTotal" value="0"/>
            <%--@elvariable id="s" type="edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel"--%>
            <c:forEach var="s" items="${oldBookingServiceUsageModels}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${s.serviceName}</td>
                    <td>${s.serviceType}</td>
                    <td>$${s.servicePrice}</td>
                    <td>${s.quantity}</td>
                    <td>${s.serviceDate}</td>
                    <td>
                        <span class="status-badge ${s.bookingServiceStatus == 'Completed' ? 'status-completed' : 'status-pending'}">
                            ${s.bookingServiceStatus}
                        </span>
                    </td>
                    <td><c:out value="${s.requestTime != null ? s.requestTime : '-'}"/></td>
                    <td><c:out value="${s.completionTime != null ? s.completionTime : '-'}"/></td>
                    <td><strong>$${s.subPrice}</strong></td>
                    <td>
                        <c:choose>
                            <c:when test="${s.bookingServiceStatus != 'Completed'}">
                                <a href="booking-services/status?id=${s.bookingServiceId}" class="btn btn-success btn-sm">
                                    <i class="fas fa-check"></i> Mark Completed
                                </a>
                            </c:when>
                            <c:otherwise>
                                <span style="color: var(--color-success); font-weight: bold;">
                                    <i class="fas fa-check-circle"></i> Completed
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="oldSubTotal" value="${oldSubTotal + s.subPrice}"/>
            </c:forEach>
            <tr class="total-row">
                <td colspan="9" style="text-align:right;">Total of services used previously:</td>
                <td colspan="2"><strong>$${oldSubTotal}</strong></td>
            </tr>
            </tbody>
        </table>
    </c:if>

    <!-- If both are empty -->
    <c:if test="${empty newBookingServiceUsageModels and empty oldBookingServiceUsageModels}">
        <div class="no-data">
            <i class="fas fa-box-open" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
            <p style="font-size: 18px;">No services recorded yet.</p>
        </div>
    </c:if>
</div>

<!-- Grand Total for all services -->
<c:if test="${(not empty newBookingServiceUsageModels) or (not empty oldBookingServiceUsageModels)}">
    <c:set var="grandTotal" value="${(newSubTotal != null ? newSubTotal : 0) + (oldSubTotal != null ? oldSubTotal : 0)}"/>
    <div class="grand-total">
        <span class="grand-total-label"><i class="fas fa-calculator"></i> Grand Total:</span>
        <span class="grand-total-value">$${grandTotal}</span>
    </div>
</c:if>

<div class="action-buttons">
    <a href="services?bookingId=${booking.bookingId}" class="btn btn-primary">
        <i class="fas fa-plus-circle"></i> Add More Service
    </a>
    <a href="${pageContext.request.contextPath}/service-staff" class="btn btn-warning">
        <i class="fas fa-arrow-left"></i> Back to Dashboard
    </a>
</div>

</body>
</html>
