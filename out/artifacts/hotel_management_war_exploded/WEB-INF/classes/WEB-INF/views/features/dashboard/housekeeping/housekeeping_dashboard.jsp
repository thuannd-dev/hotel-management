<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <title>Housekeeping Dashboard - Room Status</title>
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

        /* Alert Messages */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            text-align: center;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        /* Status Filter Links */
        .status-filter {
            background: white;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
        }

        .status-filter-label {
            font-weight: bold;
            color: var(--color-primary);
            margin-right: 15px;
        }

        .status-links {
            display: inline-flex;
            gap: 5px;
            align-items: center;
        }

        .status-links a {
            color: var(--color-primary);
            text-decoration: none;
            padding: 5px 12px;
            border-radius: 4px;
            transition: all 0.3s;
            font-weight: 500;
        }

        .status-links a:hover {
            background-color: var(--color-background);
            text-decoration: none;
        }

        .status-links strong {
            color: white;
            background-color: var(--color-primary);
            padding: 5px 12px;
            border-radius: 4px;
        }

        .status-links .sep {
            color: #999;
            margin: 0 5px;
        }

        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #e0e0e0;
            text-align: center;
        }

        th {
            background-color: var(--color-secondary);
            color: white;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #f7f7f7;
        }

        tr:hover {
            background-color: #f0f0f0;
        }

        /* Status Labels */
        .status-label {
            padding: 6px 12px;
            border-radius: 15px;
            display: inline-block;
            font-weight: 500;
            font-size: 13px;
        }

        .status-label.available {
            background-color: var(--color-success);
            color: white;
        }

        .status-label.dirty {
            background-color: #ff9800;
            color: white;
        }

        .status-label.maintenance {
            background-color: var(--color-danger);
            color: white;
        }

        .status-label.occupied {
            background-color: var(--color-primary);
            color: white;
        }

        /* Form Controls */
        select {
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: white;
            cursor: pointer;
            font-size: 14px;
        }

        select:disabled {
            background-color: #f0f0f0;
            cursor: not-allowed;
        }

        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
            background-color: var(--color-primary);
            color: white;
            font-size: 14px;
            margin-left: 8px;
        }

        .btn:hover {
            background-color: #0E2938;
        }

        .btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
<!-- Navigation Menu -->
<div class="nav-menu">
    <a href="${pageContext.request.contextPath}/housekeeping" class="active">Dashboard</a>
    <a href="${pageContext.request.contextPath}/housekeeping/reports">Housekeeping Reports</a>
</div>

<!-- Header with User Info -->
<div class="header-container">
    <h2>Housekeeping - Room Status Management</h2>
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

<!-- Alert Messages -->
<c:if test="${param.msg == 'updated'}">
    <div class="alert alert-success">Room status updated successfully!</div>
</c:if>
<c:if test="${param.msg == 'invalid_transition'}">
    <div class="alert alert-error">Invalid status transition: <c:out value="${param.error}" /></div>
</c:if>

<!-- Status Filter -->
<div class="status-filter">
    <span class="status-filter-label">Filter by Status:</span>
    <div class="status-links">
        <c:choose>
            <c:when test="${param.status == '' || param.status == 'all' || param.status == null}">
                <strong>All</strong>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/housekeeping">All</a>
            </c:otherwise>
        </c:choose>
        <span class="sep">|</span>
        <c:choose>
            <c:when test="${param.status == 'available'}">
                <strong>Available</strong>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/housekeeping?status=available">Available</a>
            </c:otherwise>
        </c:choose>
        <span class="sep">|</span>
        <c:choose>
            <c:when test="${param.status == 'occupied'}">
                <strong>Occupied</strong>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/housekeeping?status=occupied">Occupied</a>
            </c:otherwise>
        </c:choose>
        <span class="sep">|</span>
        <c:choose>
            <c:when test="${param.status == 'dirty'}">
                <strong>Dirty</strong>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/housekeeping?status=dirty">Dirty</a>
            </c:otherwise>
        </c:choose>
        <span class="sep">|</span>
        <c:choose>
            <c:when test="${param.status == 'maintenance'}">
                <strong>Maintenance</strong>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/housekeeping?status=maintenance">Maintenance</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<table>
    <tr>
        <th>Room No</th>
        <th>Type</th>
        <th>Status</th>
        <th>Capacity</th>
        <th>Action</th>
    </tr>
    <%--@elvariable id="r" type="com.hotel_management.domain.dto.room.RoomDetailViewModel"--%>
    <c:forEach var="r" items="${rooms}">
        <tr>
            <td>${r.roomNumber}</td>
            <td>${r.typeName}</td>
            <td><span class="status-label ${fn:toLowerCase(r.status)}">${r.status}</span></td>
            <td>${r.capacity}</td>
            <td>
                <form action="housekeeping/room/status" method="post">
                    <input type="hidden" name="roomId" value="${r.roomId}" />
                    <c:choose>
                        <c:when test="${r.status == 'Occupied'}">
                            <select name="status" aria-label="Room status" disabled>
                                <option value="Occupied" selected>Occupied</option>
                            </select>
                            <button type="submit" class="btn" disabled>Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Available'}">
                            <select name="status" aria-label="Room status">
                                <option value="Available" selected>Available</option>
                                <option value="Dirty">Dirty</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Dirty'}">
                            <select name="status" aria-label="Room status">
                                <option value="Dirty" selected>Dirty</option>
                                <option value="Available">Available</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Maintenance'}">
                            <select name="status" aria-label="Room status">
                                <option value="Maintenance" selected>Maintenance</option>
                                <option value="Available">Available</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>