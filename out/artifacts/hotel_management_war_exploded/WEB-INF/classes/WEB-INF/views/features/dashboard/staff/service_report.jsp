<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Service Reports</title>
    <style>
        .nav-menu {
            background-color: #f0f0f0;
            padding: 10px;
            margin-bottom: 20px;
            border-bottom: 2px solid #ddd;
        }
        .nav-menu a {
            padding: 10px 15px;
            text-decoration: none;
            color: #333;
            margin-left: 10px;
            border-radius: 4px;
        }
        .nav-menu a:first-child {
            margin-left: 0;
        }
        .nav-menu a.active {
            background-color: #fff;
            font-weight: bold;
        }
        .report-selector {
            background-color: #f9f9f9;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .report-selector form {
            display: inline-block;
            margin-right: 10px;
        }
        .report-selector button {
            padding: 8px 15px;
            cursor: pointer;
            border: 1px solid #ccc;
            background-color: #fff;
            border-radius: 4px;
        }
        .report-selector button:hover {
            background-color: #e9e9e9;
        }
        .report-selector .active-report {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
        }
        .date-filter {
            margin-top: 10px;
            padding: 10px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        table th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .no-data {
            padding: 20px;
            text-align: center;
            color: #666;
            font-style: italic;
        }
    </style>
</head>
<body>
<!-- Navigation Menu -->
<div class="nav-menu">
    <a href="${pageContext.request.contextPath}/service-staff">Dashboard</a>
    <a href="service-staff/statistics" class="active">Service Reports</a>
</div>

<h2>Service Reports</h2>

<!-- Report Type Selector -->
<div class="report-selector">
    <h3>Select Report Type:</h3>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesProvidedToday" />
        <button type="submit" class="${param.reportType == 'servicesProvidedToday' || empty param.reportType ? 'active-report' : ''}">
            Services Provided Today
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesProvidedPeriod" />
        <button type="submit" class="${param.reportType == 'servicesProvidedPeriod' ? 'active-report' : ''}">
            Services Provided Period
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesRequest" />
        <button type="submit" class="${param.reportType == 'servicesRequest' ? 'active-report' : ''}">
            Services Request
        </button>
    </form>

    <form action="statistics" method="get">
        <input type="hidden" name="reportType" value="servicesCompleted" />
        <button type="submit" class="${param.reportType == 'servicesCompleted' ? 'active-report' : ''}">
            Services Completed
        </button>
    </form>

    <!-- Date Filter for Period Report -->
    <c:if test="${param.reportType == 'servicesProvidedPeriod'}">
        <div class="date-filter">
            <form action="statistics" method="get">
                <input type="hidden" name="reportType" value="servicesProvidedPeriod" />
                <label for="startDate">Start Date:</label>
                <input type="date" id="startDate" name="startDate" value="${requestScope['startDate']}" required />

                <label for="endDate">End Date:</label>
                <input type="date" id="endDate" name="endDate" value="${requestScope['endDate']}" required />

                <button type="submit">Filter by Date Range</button>
            </form>
        </div>
    </c:if>
</div>

<hr>

<!-- Services Provided Today Report -->
<c:if test="${param.reportType == 'servicesProvidedToday' || empty param.reportType}">
    <h3>Services Provided Today</h3>
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
            <p class="no-data">No services provided today.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Provided Period Report -->
<c:if test="${param.reportType == 'servicesProvidedPeriod'}">
    <h3>Services Provided Period Report</h3>
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
                            <td>${r.totalRevenue}</td>
                            <td>${r.period}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No data available for the selected period. Please select a date range.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Request Report -->
<c:if test="${param.reportType == 'servicesRequest'}">
    <h3>Services Request Report</h3>
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
            <p class="no-data">No service requests found.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Services Completed Report -->
<c:if test="${param.reportType == 'servicesCompleted'}">
    <h3>Services Completed Report</h3>
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
                            <td>${r.totalCompleted}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No completed services found.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<hr>
<a href="${pageContext.request.contextPath}/service-staff">← Back to Dashboard</a>
</body>
</html>
