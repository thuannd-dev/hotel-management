<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Management</title>
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
            .header-actions {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }
            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s;
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
            .btn-danger {
                background-color: var(--color-danger);
                color: white;
            }
            .btn-sm {
                padding: 5px 10px;
                font-size: 14px;
            }
            .alert {
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
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
        </style>
    </head>
    <body>
        <div class="header-container">
            <h2>Admin Management</h2>
            <div class="user-info-bar">
                <span class="user-name">👤 <%= fullName %></span>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    🚪 Logout
                </a>
            </div>
        </div>

        <div class="nav-tabs">
            <a href="${pageContext.request.contextPath}/admin/staff" class="nav-tab active">Staff Management</a>
            <a href="${pageContext.request.contextPath}/admin/tax" class="nav-tab">Tax Configuration</a>
            <a href="${pageContext.request.contextPath}/admin/service" class="nav-tab">Service Management</a>
        </div>

        <div class="header-actions">
            <div>
                <a href="${pageContext.request.contextPath}/admin/staff/create" class="btn btn-primary">Add New Staff</a>
            </div>
        </div>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success">
                ${param.success}
            </div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                ${param.error}
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Role</th>
                    <th>Username</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${staffs}">
                    <tr>
                        <td>${staff.staffId}</td>
                        <td>${staff.fullName}</td>
                        <td>${staff.role}</td>
                        <td>${staff.username}</td>
                        <td>${staff.phone != null ? staff.phone : 'N/A'}</td>
                        <td>${staff.email != null ? staff.email : 'N/A'}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/admin/staff/edit?id=${staff.staffId}"
                               class="btn btn-warning btn-sm">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/staff/delete?id=${staff.staffId}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure you want to delete staff member: ${staff.fullName}?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty staffs}">
                    <tr>
                        <td colspan="7" style="text-align: center;">No staff members found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>

