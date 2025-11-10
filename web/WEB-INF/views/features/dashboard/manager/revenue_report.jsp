<%@page import="com.hotel_management.domain.dto.manager.RevenueReportViewModel"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<RevenueReportViewModel> reportData = (List<RevenueReportViewModel>) request.getAttribute("reportData");
    String period = (String) request.getAttribute("period");
    LocalDate startDate = (LocalDate) request.getAttribute("startDate");
    LocalDate endDate = (LocalDate) request.getAttribute("endDate");
    Integer selectedYear = (Integer) request.getAttribute("selectedYear");
    Integer selectedMonth = (Integer) request.getAttribute("selectedMonth");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Revenue Report - Manager Dashboard</title>
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
            transition: background-color 0.3s;
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

        .form-group input, .form-group select {
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
        <h2><i class="fas fa-chart-line"></i> Revenue Report</h2>
        <a href="${pageContext.request.contextPath}/manager" class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <div class="container">
        <div class="filter-section">
            <div class="filter-tabs">
                <button class="filter-tab <%= "daily".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=revenue&period=daily'">
                    Daily
                </button>
                <button class="filter-tab <%= "monthly".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=revenue&period=monthly'">
                    Monthly
                </button>
                <button class="filter-tab <%= "yearly".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=revenue&period=yearly'">
                    Yearly
                </button>
            </div>

            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="revenue">
                <input type="hidden" name="period" value="<%= period %>">

                <% if ("daily".equals(period)) { %>
                    <div class="form-group">
                        <label>Start Date:</label>
                        <input type="date" name="startDate" value="<%= startDate != null ? startDate : "" %>" required>
                    </div>
                    <div class="form-group">
                        <label>End Date:</label>
                        <input type="date" name="endDate" value="<%= endDate != null ? endDate : "" %>" required>
                    </div>
                <% } else if ("monthly".equals(period)) { %>
                    <div class="form-group">
                        <label>Year:</label>
                        <input type="number" name="year" value="<%= selectedYear != null ? selectedYear : LocalDate.now().getYear() %>" min="2000" max="2100" required>
                    </div>
                    <div class="form-group">
                        <label>Month:</label>
                        <select name="month" required>
                            <% for (int i = 1; i <= 12; i++) { %>
                                <option value="<%= i %>" <%= (selectedMonth != null && selectedMonth == i) ? "selected" : "" %>>
                                    <%= java.time.Month.of(i).name() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                <% } else if ("yearly".equals(period)) { %>
                    <div class="form-group">
                        <label>Year:</label>
                        <input type="number" name="year" value="<%= selectedYear != null ? selectedYear : LocalDate.now().getYear() %>" min="2000" max="2100" required>
                    </div>
                <% } %>

                <button type="submit" class="btn-filter">
                    <i class="fas fa-search"></i> Generate Report
                </button>
            </form>
        </div>

        <div class="report-table">
            <% if (reportData != null && !reportData.isEmpty()) {
                double totalRevenue = 0;
                int totalInvoices = 0;
                for (RevenueReportViewModel item : reportData) {
                    totalRevenue += item.getTotalRevenue().doubleValue();
                    totalInvoices += item.getInvoiceCount();
                }
            %>
                <div class="summary">
                    <div class="summary-item">
                        <span class="summary-label">Total Revenue:</span>
                        <span>$<%= String.format("%,.2f", totalRevenue) %></span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Total Invoices:</span>
                        <span><%= totalInvoices %></span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Average per Invoice:</span>
                        <span>$<%= String.format("%,.2f", totalInvoices > 0 ? totalRevenue / totalInvoices : 0) %></span>
                    </div>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Total Revenue</th>
                            <th>Invoice Count</th>
                            <th>Average per Invoice</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (RevenueReportViewModel item : reportData) { %>
                            <tr>
                                <td><%= item.getDate().format(formatter) %></td>
                                <td>$<%= String.format("%,.2f", item.getTotalRevenue()) %></td>
                                <td><%= item.getInvoiceCount() %></td>
                                <td>$<%= String.format("%,.2f", item.getTotalRevenue().doubleValue() / item.getInvoiceCount()) %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <i class="fas fa-inbox" style="font-size: 48px; color: #ccc;"></i>
                    <p>No revenue data available for the selected period.</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>

