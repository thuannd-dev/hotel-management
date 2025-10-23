<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel_management.domain.dto.staff.StaffViewModel" %>
<%
    HttpSession userSession = request.getSession(false);
    String fullName = "Guest";
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
    <title>Housekeeping Reports</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Coastal Elegance Color Palette */
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

        /* Screen Reader Only */
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

        /* Header with User Info */
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        h2 {
            color: var(--color-primary);
            border-bottom: 3px solid var(--color-secondary);
            padding-bottom: 10px;
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

        h3 {
            color: var(--color-primary);
            margin-top: 20px;
            margin-bottom: 15px;
        }

        /* Navigation Menu */
        .nav-menu {
            background-color: white;
            padding: 15px;
            margin-bottom: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            gap: 10px;
        }

        .nav-menu a {
            padding: 10px 20px;
            text-decoration: none;
            color: var(--color-text);
            border-radius: 4px;
            transition: all 0.3s;
            font-weight: 500;
        }

        .nav-menu a:hover {
            background-color: var(--color-background);
        }

        .nav-menu a.active {
            background-color: var(--color-primary);
            color: white;
            font-weight: bold;
        }

        /* Report Selector */
        .report-selector {
            background-color: white;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .report-selector h3 {
            margin-top: 0;
            margin-bottom: 15px;
        }

        .report-selector form {
            display: inline-block;
            margin-right: 10px;
            margin-bottom: 10px;
        }

        .report-selector button {
            padding: 10px 20px;
            cursor: pointer;
            border: 2px solid var(--color-primary);
            background-color: white;
            color: var(--color-primary);
            border-radius: 4px;
            font-weight: 500;
            transition: all 0.3s;
        }

        .report-selector button:hover {
            background-color: var(--color-background);
        }

        .report-selector .active-report {
            background-color: var(--color-primary);
            color: white;
            border-color: var(--color-primary);
        }

        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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

        table tr:nth-child(even) {
            background-color: #f7f7f7;
        }

        table tr:hover {
            background-color: #f0f0f0;
        }

        /* Status and Priority Badges */
        .no-data {
            padding: 30px;
            text-align: center;
            color: #999;
            font-style: italic;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .status-badge {
            padding: 6px 12px;
            border-radius: 15px;
            display: inline-block;
            font-size: 13px;
            font-weight: 500;
        }

        .status-badge.completed {
            background-color: var(--color-success);
            color: white;
        }

        .status-badge.pending {
            background-color: #ff9800;
            color: white;
        }

        .status-badge.in-progress {
            background-color: var(--color-primary);
            color: white;
        }

        .status-badge.resolved {
            background-color: var(--color-success);
            color: white;
        }

        .status-badge.reported {
            background-color: var(--color-danger);
            color: white;
        }

        .priority-high {
            color: var(--color-danger);
            font-weight: bold;
        }

        .priority-medium {
            color: #ff9800;
            font-weight: bold;
        }

        .priority-low {
            color: var(--color-success);
        }

        hr {
            border: none;
            border-top: 2px solid var(--color-secondary);
            margin: 25px 0;
        }
    </style>
</head>
<body>
<!-- Navigation Menu -->
<div class="nav-menu">
    <a href="${pageContext.request.contextPath}/housekeeping">Dashboard</a>
    <a href="${pageContext.request.contextPath}/housekeeping/reports" class="active">Housekeeping Reports</a>
</div>

<!-- Header with User Info -->
<div class="header-container">
    <h2>Housekeeping Reports</h2>
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

<!-- Report Type Selector -->
<div class="report-selector">
    <h3>Select Report Type:</h3>

    <form action="reports" method="get">
        <input type="hidden" name="reportType" value="dailyRoomCleaning" />
        <button type="submit" class="${param.reportType == 'dailyRoomCleaning' || empty param.reportType ? 'active-report' : ''}">
            Daily Room Cleaning
        </button>
    </form>

    <form action="reports" method="get">
        <input type="hidden" name="reportType" value="staffPerformance" />
        <button type="submit" class="${param.reportType == 'staffPerformance' ? 'active-report' : ''}">
            Staff Performance
        </button>
    </form>

    <form action="reports" method="get">
        <input type="hidden" name="reportType" value="maintenanceIssue" />
        <button type="submit" class="${param.reportType == 'maintenanceIssue' ? 'active-report' : ''}">
            Maintenance Issues
        </button>
    </form>

    <form action="reports" method="get">
        <input type="hidden" name="reportType" value="pendingCleaningTask" />
        <button type="submit" class="${param.reportType == 'pendingCleaningTask' ? 'active-report' : ''}">
            Pending Cleaning Tasks
        </button>
    </form>

    <form action="reports" method="get">
        <input type="hidden" name="reportType" value="roomStatus" />
        <button type="submit" class="${param.reportType == 'roomStatus' ? 'active-report' : ''}">
            Room Status
        </button>
    </form>
</div>

<hr>

<!-- Daily Room Cleaning Report -->
<c:if test="${param.reportType == 'dailyRoomCleaning' || empty param.reportType}">
    <h3>Daily Room Cleaning Report</h3>
    <c:set var="report" value="${requestScope['dailyRoomCleaningReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Room Number</th>
                        <th>Cleaning Type</th>
                        <th>Staff Name</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.housekeeping.DailyRoomCleaningReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.date}</td>
                            <td>${r.roomNumber}</td>
                            <td>${r.cleaningType}</td>
                            <td>${r.staffName}</td>
                            <td><span class="status-badge ${r.status == 'Completed' ? 'completed' : 'pending'}">${r.status}</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No daily room cleaning records were found.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Staff Performance Report -->
<c:if test="${param.reportType == 'staffPerformance'}">
    <h3>Housekeeping Staff Performance Report</h3>
    <c:set var="report" value="${requestScope['staffPerformanceReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Staff Name</th>
                        <th>Rooms Cleaned</th>
                        <th>Deep Cleanings</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.housekeeping.HousekeepingStaffPerformanceReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.staffName}</td>
                            <td>${r.roomsCleaned}</td>
                            <td>${r.deepCleanings}</td>
                            <td>${r.date}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No staff performance data available.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Maintenance Issue Report -->
<c:if test="${param.reportType == 'maintenanceIssue'}">
    <h3>Maintenance Issue Report</h3>
    <c:set var="report" value="${requestScope['maintenanceIssueReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Room Number</th>
                        <th>Issue Description</th>
                        <th>Report Date</th>
                        <th>Status</th>
                        <th>Fixed By</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.housekeeping.MaintenanceIssueReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.roomNumber}</td>
                            <td>${r.issueDescription}</td>
                            <td>${r.reportDate}</td>
                            <td>
                                <span class="status-badge ${r.status == 'Resolved' ? 'resolved' : r.status == 'In Progress' ? 'in-progress' : 'reported'}">
                                    ${r.status}
                                </span>
                            </td>
                            <td>${r.fixedBy != null ? r.fixedBy : 'N/A'}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No maintenance issues were reported.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Pending Cleaning Task Report -->
<c:if test="${param.reportType == 'pendingCleaningTask'}">
    <h3>Pending Cleaning Task Report</h3>
    <c:set var="report" value="${requestScope['pendingCleaningTaskReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Room Number</th>
                        <th>Room Status</th>
                        <th>Priority</th>
                        <th>Task Type</th>
                        <th>Assigned Staff</th>
                        <th>Task Status</th>
                        <th>Assigned Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.housekeeping.PendingCleaningTaskReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.roomNumber}</td>
                            <td>${r.roomStatus}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${r.priority == 'High'}">
                                        <span class="priority-high">${r.priority}</span>
                                    </c:when>
                                    <c:when test="${r.priority == 'Medium'}">
                                        <span class="priority-medium">${r.priority}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="priority-low">${r.priority}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${r.taskType}</td>
                            <td>${r.assignedStaff != null ? r.assignedStaff : 'Unassigned'}</td>
                            <td><span class="status-badge ${r.taskStatus == 'Completed' ? 'completed' : r.taskStatus == 'In Progress' ? 'in-progress' : 'pending'}">${r.taskStatus}</span></td>
                            <td>${r.assignedDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">Don't have pending cleaning tasks were found.</p>
        </c:otherwise>
    </c:choose>
</c:if>

<!-- Room Status Report -->
<c:if test="${param.reportType == 'roomStatus'}">
    <h3>Room Status Report</h3>
    <c:set var="report" value="${requestScope['roomStatusReport']}" />
    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                    <tr>
                        <th>Room Number</th>
                        <th>Room Type</th>
                        <th>Status</th>
                        <th>Last Cleaned Date</th>
                        <th>Next Check In</th>
                    </tr>
                </thead>
                <tbody>
                    <%--@elvariable id="r" type="com.hotel_management.domain.dto.housekeeping.RoomStatusReportViewModel"--%>
                    <c:forEach var="r" items="${report}">
                        <tr>
                            <td>${r.roomNumber}</td>
                            <td>${r.roomType}</td>
                            <td>${r.status}</td>
                            <td>${r.lastCleanedDate != null ? r.lastCleanedDate : 'N/A'}</td>
                            <td>${r.nextCheckIn != null ? r.nextCheckIn : 'N/A'}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-data">No room status data available.</p>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
