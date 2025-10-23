<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Service Management</title>
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
    </style>
</head>
<body>
<h2>Admin Management</h2>

<div class="nav-tabs">
    <a href="${pageContext.request.contextPath}/admin/staff" class="nav-tab">Staff Management</a>
    <a href="${pageContext.request.contextPath}/admin/tax" class="nav-tab">Tax Configuration</a>
    <a href="${pageContext.request.contextPath}/admin/service" class="nav-tab active">Service Management</a>
</div>

<div class="header-actions">
    <div>
        <a href="${pageContext.request.contextPath}/admin/service/create" class="btn btn-primary">Add New Service</a>
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
        <th>Service Name</th>
        <th>Service Type</th>
        <th>Price</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="service" items="${services}">
        <tr>
            <td>${service.serviceId}</td>
            <td>${service.serviceName}</td>
            <td>${service.serviceType != null ? service.serviceType : 'N/A'}</td>
            <td><fmt:formatNumber value="${service.price}" pattern="#,##0.00"/> VND</td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/service/edit?id=${service.serviceId}"
                   class="btn btn-warning btn-sm">Edit</a>
                <a href="${pageContext.request.contextPath}/admin/service/delete?id=${service.serviceId}"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Are you sure you want to delete service: ${service.serviceName}?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty services}">
        <tr>
            <td colspan="5" style="text-align: center;">No services found</td>
        </tr>
    </c:if>
    </tbody>
</table>
</body>
</html>