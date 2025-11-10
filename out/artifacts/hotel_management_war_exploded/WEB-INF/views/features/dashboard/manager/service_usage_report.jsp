<%@page import="com.hotel_management.domain.dto.manager.ServiceUsageViewModel"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<ServiceUsageViewModel> reportData = (List<ServiceUsageViewModel>) request.getAttribute("reportData");
    String period = (String) request.getAttribute("period");
    LocalDate startDate = (LocalDate) request.getAttribute("startDate");
    LocalDate endDate = (LocalDate) request.getAttribute("endDate");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Most Used Services - Manager Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
        }

        .navbar {
            background-color: #2b4c7e;
            color: white;
            padding: 15px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h2 {
            margin: 0;
        }

        .btn-back {
            background-color: #5a7ba8;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-back:hover {
            background-color: #4a6a98;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .filter-section {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .filter-tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .filter-tab {
            padding: 10px 20px;
            border: none;
            background-color: #e0e0e0;
            cursor: pointer;
            border-radius: 4px;
        }

        .filter-tab.active {
            background-color: #2b4c7e;
            color: white;
        }

        .filter-form {
            display: flex;
            gap: 15px;
            align-items: flex-end;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            margin-bottom: 5px;
            font-weight: 600;
        }

        .form-group input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .btn-filter {
            background-color: #2b4c7e;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn-filter:hover {
            background-color: #1a3a5e;
        }

        .report-table {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #2b4c7e;
            color: white;
            font-weight: 600;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .summary {
            background: #e3f2fd;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .summary-item {
            display: inline-block;
            margin-right: 30px;
        }

        .summary-label {
            font-weight: 600;
            color: #2b4c7e;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h2><i class="fas fa-concierge-bell"></i> Most Used Services</h2>
        <a href="${pageContext.request.contextPath}/manager" class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <div class="container">
        <div class="filter-section">
            <div class="filter-tabs">
                <button class="filter-tab <%= !"custom".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=service-usage'">
                    All Time
                </button>
                <button class="filter-tab <%= "custom".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=service-usage&period=custom'">
                    Custom Date Range
                </button>
            </div>

            <% if ("custom".equals(period)) { %>
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="service-usage">
                <input type="hidden" name="period" value="custom">

                <div class="form-group">
                    <label>Start Date:</label>
                    <input type="date" name="startDate" value="<%= startDate != null ? startDate : "" %>" required>
                </div>
                <div class="form-group">
                    <label>End Date:</label>
                    <input type="date" name="endDate" value="<%= endDate != null ? endDate : "" %>" required>
                </div>

                <button type="submit" class="btn-filter">
                    <i class="fas fa-search"></i> Generate Report
                </button>
            </form>
            <% } %>
        </div>

        <div class="report-table">
            <% if (reportData != null && !reportData.isEmpty()) {
                double totalRevenue = 0;
                int totalUsage = 0;
                for (ServiceUsageViewModel item : reportData) {
                    totalRevenue += item.getTotalRevenue().doubleValue();
                    totalUsage += item.getUsageCount();
                }
            %>
                <div class="summary">
                    <div class="summary-item">
                        <span class="summary-label">Total Service Revenue:</span>
                        <span>$<%= String.format("%,.2f", totalRevenue) %></span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Total Service Requests:</span>
                        <span><%= totalUsage %></span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Unique Services:</span>
                        <span><%= reportData.size() %></span>
                    </div>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>Service Name</th>
                            <th>Service Type</th>
                            <th>Usage Count</th>
                            <th>Total Quantity</th>
                            <th>Total Revenue</th>
                            <th>Avg Revenue per Use</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ServiceUsageViewModel item : reportData) { %>
                            <tr>
                                <td><%= item.getServiceName() %></td>
                                <td><%= item.getServiceType() != null ? item.getServiceType() : "N/A" %></td>
                                <td><%= item.getUsageCount() %></td>
                                <td><%= item.getTotalQuantity() %></td>
                                <td>$<%= String.format("%,.2f", item.getTotalRevenue()) %></td>
                                <td>$<%= String.format("%,.2f", item.getTotalRevenue().doubleValue() / item.getUsageCount()) %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <i class="fas fa-inbox" style="font-size: 48px; color: #ccc;"></i>
                    <p>No service usage data available.</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>

