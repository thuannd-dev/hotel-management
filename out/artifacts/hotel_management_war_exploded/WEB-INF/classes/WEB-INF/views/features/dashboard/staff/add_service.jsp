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
    <title>Record Service Usage</title>
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
        h2, h3 {
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
        input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
        }
        input[type="number"] {
            width: 80px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="number"]:disabled {
            background-color: #f0f0f0;
            cursor: not-allowed;
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
        .form-actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: var(--color-primary);
            text-decoration: none;
            font-weight: bold;
        }
        .back-link:hover {
            color: var(--color-secondary);
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
    <h2>Record Service Usage</h2>
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

<c:set var="booking" value="${requestScope['checkInBookingDetails']}" />
<%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
<div class="info-card">
    <h3><i class="fas fa-info-circle"></i> Booking Information</h3>
    <div class="info-row">
        <div class="info-label">Booking ID:</div>
        <div class="info-value">${booking.bookingId}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Guest Name:</div>
        <div class="info-value">${booking.guestFullName}</div>
    </div>
    <div class="info-row">
        <div class="info-label">Room Number:</div>
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

<!-- Form add service -->
<div class="info-card">
    <h3><i class="fas fa-concierge-bell"></i> Select Services</h3>
    <form action="services" method="post">
        <input type="hidden" name="bookingId" value="${booking.bookingId}" />
        <c:set var="services" value="${requestScope['services']}" />
        <%--@elvariable id="services" type="com.hotel_management.domain.dto.service.ServiceViewModel"--%>
        <table>
            <thead>
            <tr>
                <th style="width: 60px;">Select</th>
                <th>Service</th>
                <th style="width: 120px;">Price ($)</th>
                <th style="width: 120px;">Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${services}">
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" name="serviceId[]" value="${s.serviceId}" id="service_${s.serviceId}" onclick="toggleQuantity(this, '${s.serviceId}')" />
                    </td>
                    <td><label for="service_${s.serviceId}" style="cursor: pointer;">${s.serviceName}</label></td>
                    <td>${s.price}</td>
                    <td>
                        <input type="number" name="quantity_${s.serviceId}" min="1" value="1" disabled required />
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-plus-circle"></i> Add Services
            </button>
            <a href="${pageContext.request.contextPath}/service-staff" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Booking List
            </a>
        </div>
    </form>
</div>

<script>
    function toggleQuantity(checkbox, serviceId) {
        var quantityInput = checkbox.closest('tr').querySelector('input[type=number]');
        quantityInput.disabled = !checkbox.checked;
        if (!checkbox.checked) quantityInput.value = 1;
    }
</script>
</body>
</html>
