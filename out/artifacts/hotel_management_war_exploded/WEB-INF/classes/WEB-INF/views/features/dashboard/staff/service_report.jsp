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
    <title>Service Reports</title>
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
        .report-selector {
            background-color: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .report-selector h3 {
            margin-top: 0;
            margin-bottom: 15px;
            border-bottom: 2px solid var(--color-secondary);
            padding-bottom: 10px;
        }
        .report-selector form {
            display: inline-block;
            margin-right: 10px;
            margin-bottom: 10px;
        }
        .report-selector button {
            padding: 10px 20px;
            cursor: pointer;
            border: 1px solid #ddd;
            background-color: white;
            color: var(--color-primary);
            border-radius: 4px;
            transition: all 0.3s;
            font-size: 14px;
        }
        .report-selector button:hover {
            background-color: var(--color-secondary);
            color: white;
        }
        .report-selector .active-report {
            background-color: var(--color-primary);
            color: white;
            border-color: var(--color-primary);
        }
        .date-filter {
            margin-top: 15px;
            padding: 15px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .date-filter form {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        .date-filter label {
            font-weight: bold;
            color: var(--color-primary);
        }
        .date-filter input[type="date"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 15px;
        }
        table th, table td {
            border: 1px solid #e0e0e0;
            padding: 12px;
            text-align: left;
        }
        table th {
            background-color: var(--color-secondary);
            color: white;
            font-weight: bold;
        }
        table tr:hover {
            background-color: #f5f5f5;
        }
        .no-data {
            background-color: white;
            padding: 40px;
            text-align: center;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            color: #666;
            margin-top: 15px;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: var(--color-secondary);
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .back-link:hover {
            background-color: #5ba89a;
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

<!-- Navigation Menu -->
<div class="nav-tabs">
    <a href="${pageContext.request.contextPath}/service-staff" class="nav-tab">Dashboard</a>
    <a href="${pageContext.request.contextPath}/service-staff/statistics" class="nav-tab active">Service Reports</a>
</div>

<h2><i class="fas fa-chart-bar"></i> Service Reports</h2>

<!-- Report Type Selector -->
<div class="report-selector">
    <h3>Select Report Type:</h3>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesProvidedToday" />
        <button type="submit" class="${param.reportType == 'servicesProvidedToday' || empty param.reportType ? 'active-report' : ''}">
            <i class="fas fa-calendar-day"></i> Services Provided Today
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesProvidedPeriod" />
        <button type="submit" class="${param.reportType == 'servicesProvidedPeriod' ? 'active-report' : ''}">
            <i class="fas fa-calendar-alt"></i> Services Provided Period
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesRequest" />
        <button type="submit" class="${param.reportType == 'servicesRequest' ? 'active-report' : ''}">
            <i class="fas fa-clipboard-list"></i> Services Request
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesCompleted" />
        <button type="submit" class="${param.reportType == 'servicesCompleted' ? 'active-report' : ''}">
            <i class="fas fa-check-circle"></i> Services Completed
        </button>
    </form>

    <!-- Date Filter for Period Report -->
    <c:if test="${param.reportType == 'servicesProvidedPeriod'}">
        <div class="date-filter">
            <form action="statistics" method="get">
                <input type="hidden" name="reportType" value="servicesProvidedPeriod" />
                <label for="startDate"><i class="fas fa-calendar"></i> Start Date:</label>
                <input type="date" id="startDate" name="startDate" value="${requestScope['startDate']}" required />

                <label for="endDate"><i class="fas fa-calendar"></i> End Date:</label>
                <input type="date" id="endDate" name="endDate" value="${requestScope['endDate']}" required />

                <button type="submit" style="background-color: var(--color-primary); color: white; border-color: var(--color-primary);">
                    <i class="fas fa-filter"></i> Filter by Date Range
                </button>
            </form>
        </div>
    </c:if>
</div>


<!-- Services Provided Report today -->
<c:if test="${param.reportType == 'servicesProvidedToday' || empty param.reportType}">
    <h3><i class="fas fa-calendar-day"></i> Services Provided Today</h3>
    <c:set var="report" value="${requestScope['servicesProvidedTodayReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Service Date</th>
                        <th>Guest Name</th>
                        <th>Room Number</th>
                        <th>Service Name</th>
                        <th>Quantity</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.booking_service.BookingServicesProvidedReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.serviceDate}</td>
                            <td>${r.guestName}</td>
                            <td>${r.roomNumber}</td>
                            <td>${r.serviceName}</td>
                            <td>${r.quantity}</td>
                            <td>${r.status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                <i class="fas fa-calendar-times" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
                <p style="font-size: 18px;">No services are provided today.</p>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Provided Period Report -->
<c:if test="${param.reportType == 'servicesProvidedPeriod'}">
    <h3><i class="fas fa-calendar-alt"></i> Services Provided Period Report</h3>
    <c:set var="report" value="${requestScope['servicesProvidedPeriodReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Service Name</th>
                        <th>Total Quantity</th>
                        <th>Total Revenue ($)</th>
                        <th>Period</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.booking_service.BookingServiceProvidedPeriodReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.serviceName}</td>
                            <td>${r.totalQuantity}</td>
                            <td><strong>$${r.totalRevenue}</strong></td>
                            <td>${r.period}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                <i class="fas fa-calendar-times" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
                <p style="font-size: 18px;">No data is available for the selected period.</p>
                <p>Please select a date range.</p>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Request Report -->
<c:if test="${param.reportType == 'servicesRequest'}">
    <h3><i class="fas fa-clipboard-list"></i> Services Request Report</h3>
    <c:set var="report" value="${requestScope['servicesRequestReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Request Time</th>
                        <th>Guest Name</th>
                        <th>Room Number</th>
                        <th>Service Name</th>
                        <th>Quantity</th>
                        <th>Assigned Staff</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.booking_service.BookingServiceRequestReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.requestTime}</td>
                            <td>${r.guestName}</td>
                            <td>${r.roomNumber}</td>
                            <td>${r.serviceName}</td>
                            <td>${r.quantity}</td>
                            <td>${r.assignedStaffName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                <i class="fas fa-inbox" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
                <p style="font-size: 18px;">No service requests were found.</p>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Completed Report -->
<c:if test="${param.reportType == 'servicesCompleted'}">
    <h3><i class="fas fa-check-circle"></i> Services Completed Report</h3>
    <c:set var="report" value="${requestScope['servicesCompletedReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Staff Name</th>
                        <th>Service Name</th>
                        <th>Total Completed</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.booking_service.BookingServiceCompletedReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.date}</td>
                            <td>${r.staffName}</td>
                            <td>${r.serviceName}</td>
                            <td><strong>${r.totalCompleted}</strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                <i class="fas fa-tasks" style="font-size: 48px; color: #ccc; margin-bottom: 15px;"></i>
                <p style="font-size: 18px;">No completed services were found.</p>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<a href="${pageContext.request.contextPath}/service-staff" class="back-link">
    <i class="fas fa-arrow-left"></i> Back to Dashboard
</a>
</body>
</html>
