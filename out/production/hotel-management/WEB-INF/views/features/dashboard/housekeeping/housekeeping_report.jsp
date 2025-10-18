<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Housekeeping Reports</title>
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
        .status-badge {
            padding: 4px 8px;
            border-radius: 12px;
            display: inline-block;
            font-size: 12px;
        }
        .status-badge.completed { background-color: #4CAF50; color: white; }
        .status-badge.pending { background-color: #ff9800; color: white; }
        .status-badge.in-progress { background-color: #2196F3; color: white; }
        .status-badge.resolved { background-color: #4CAF50; color: white; }
        .status-badge.reported { background-color: #f44336; color: white; }
        .priority-high { color: #f44336; font-weight: bold; }
        .priority-medium { color: #ff9800; font-weight: bold; }
        .priority-low { color: #4CAF50; }
    </style>
</head>
<body>
<!-- Navigation Menu -->
<div class="nav-menu">
    <a href="${pageContext.request.contextPath}/housekeeping">Dashboard</a>
    <a href="${pageContext.request.contextPath}/housekeeping/reports" class="active">Housekeeping Reports</a>
</div>

<h2>Housekeeping Reports</h2>

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
            <p class="no-data">No daily room cleaning records found.</p>
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
            <p class="no-data">No maintenance issues reported.</p>
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
            <p class="no-data">No pending cleaning tasks found.</p>
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
