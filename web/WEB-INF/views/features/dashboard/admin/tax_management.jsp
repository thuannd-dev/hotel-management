<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tax Configuration Management</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            :root {
                --color-primary: #1B4965;
                --color-secondary: #75C3B3;
                --color-background: #F8F9FA;
                --color-text: #333333;
                --color-accent-sand: #D4B483;
                --color-danger: #D34E51;
                --color-success: #28a745;
                --color-info: #17a2b8;
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
            .btn-secondary {
                background-color: #6c757d;
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
                flex-wrap: wrap;
            }
            .badge {
                padding: 4px 8px;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                display: inline-block;
            }
            .badge-success {
                background-color: #28a745;
                color: white;
            }
            .badge-info {
                background-color: #17a2b8;
                color: white;
            }
            .badge-secondary {
                background-color: #6c757d;
                color: white;
            }
            .badge-danger {
                background-color: #dc3545;
                color: white;
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
            <h2>Admin Management</h2>
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

        <div class="nav-tabs">
            <a href="${pageContext.request.contextPath}/admin/staff" class="nav-tab">Staff Management</a>
            <a href="${pageContext.request.contextPath}/admin/tax" class="nav-tab active">Tax Configuration</a>
            <a href="${pageContext.request.contextPath}/admin/service" class="nav-tab">Service Management</a>
        </div>

        <div class="header-actions">
            <div>
                <a href="${pageContext.request.contextPath}/admin/tax/create" class="btn btn-primary">Add New Tax Configuration</a>
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

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tax Name</th>
                    <th>Tax Rate (%)</th>
                    <th>Description</th>
                    <th>Effective From</th>
                    <th>Effective To</th>
                    <th>Status</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="tax" items="${taxConfigs}">
                    <tr>
                        <td>${tax.taxConfigId}</td>
                        <td>${tax.taxName}</td>
                        <td><fmt:formatNumber value="${tax.taxRate}" pattern="#0.00"/></td>
                        <td>${tax.description != null ? tax.description : 'N/A'}</td>
                        <td>${tax.effectiveFrom}</td>
                        <td>
                            <c:choose>
                                <c:when test="${tax.effectiveTo != null}">
                                    ${tax.effectiveTo}
                                </c:when>
                                <c:otherwise>
                                    <strong>No End Date</strong>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span class="badge ${tax.statusBadgeClass}">
                                ${tax.statusLabel}
                            </span>
                        </td>
                        <td>${tax.createdDate}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/admin/tax/edit?id=${tax.taxConfigId}"
                               class="btn btn-warning btn-sm">Edit</a>
                            <c:if test="${tax.active}">
                                <a href="${pageContext.request.contextPath}/admin/tax/deactivate?id=${tax.taxConfigId}"
                                   class="btn btn-secondary btn-sm"
                                   onclick="return confirm('Are you sure you want to deactivate this tax configuration?');">Deactivate</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/admin/tax/delete?id=${tax.taxConfigId}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure you want to delete this tax configuration? This action cannot be undone.');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty taxConfigs}">
                    <tr>
                        <td colspan="9" style="text-align: center;">No tax configurations found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>

