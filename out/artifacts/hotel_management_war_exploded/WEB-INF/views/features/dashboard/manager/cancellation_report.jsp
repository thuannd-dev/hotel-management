<%@page import="com.hotel_management.domain.dto.manager.CancellationStatViewModel"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CancellationStatViewModel overallStats = (CancellationStatViewModel) request.getAttribute("overallStats");
    List<CancellationStatViewModel> reportData = (List<CancellationStatViewModel>) request.getAttribute("reportData");
    String period = (String) request.getAttribute("period");
    Integer selectedYear = (Integer) request.getAttribute("selectedYear");
    LocalDate startDate = (LocalDate) request.getAttribute("startDate");
    LocalDate endDate = (LocalDate) request.getAttribute("endDate");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Cancellation Statistics - Manager Dashboard</title>
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

        .overall-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .stat-card.warning {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        .stat-card.info {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        .stat-card h3 {
            font-size: 14px;
            margin-bottom: 10px;
            opacity: 0.9;
        }

        .stat-card .value {
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .stat-card .label {
            font-size: 12px;
            opacity: 0.8;
        }

        .cancellation-rate {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-weight: bold;
            font-size: 14px;
        }

        .rate-low {
            background-color: #4CAF50;
            color: white;
        }

        .rate-medium {
            background-color: #FF9800;
            color: white;
        }

        .rate-high {
            background-color: #F44336;
            color: white;
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
        <h2><i class="fas fa-ban"></i> Cancellation Statistics</h2>
        <a href="${pageContext.request.contextPath}/manager" class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <div class="container">
        <div class="filter-section">
            <div class="filter-tabs">
                <button class="filter-tab <%= "overall".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=cancellation&period=overall'">
                    Overall
                </button>
                <button class="filter-tab <%= "monthly".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=cancellation&period=monthly'">
                    Monthly
                </button>
                <button class="filter-tab <%= "custom".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=cancellation&period=custom'">
                    Custom Range
                </button>
            </div>

            <% if ("monthly".equals(period)) { %>
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="cancellation">
                <input type="hidden" name="period" value="monthly">

                <div class="form-group">
                    <label>Year:</label>
                    <input type="number" name="year" value="<%= selectedYear != null ? selectedYear : LocalDate.now().getYear() %>" min="2000" max="2100" required>
                </div>

                <button type="submit" class="btn-filter">
                    <i class="fas fa-search"></i> Generate Report
                </button>
            </form>
            <% } else if ("custom".equals(period)) { %>
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="cancellation">
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

        <% if ("overall".equals(period) && overallStats != null) { %>
            <div class="overall-stats">
                <div class="stat-card">
                    <h3><i class="fas fa-calendar-alt"></i> TOTAL BOOKINGS</h3>
                    <div class="value"><%= overallStats.getTotalBookings() %></div>
                    <div class="label">All time bookings</div>
                </div>

                <div class="stat-card warning">
                    <h3><i class="fas fa-times-circle"></i> TOTAL CANCELLATIONS</h3>
                    <div class="value"><%= overallStats.getCancellationCount() %></div>
                    <div class="label">Canceled bookings</div>
                </div>

                <div class="stat-card info">
                    <h3><i class="fas fa-percentage"></i> CANCELLATION RATE</h3>
                    <div class="value"><%= String.format("%.2f", overallStats.getCancellationRate()) %>%</div>
                    <div class="label">Overall rate</div>
                </div>
            </div>

            <div class="report-table">
                <h3 style="margin-bottom: 15px;">Top Cancellation Reason</h3>
                <p style="font-size: 18px; color: #555; padding: 15px; background: #f9f9f9; border-radius: 4px;">
                    <i class="fas fa-quote-left" style="color: #ccc;"></i>
                    <%= overallStats.getTopReason() != null ? overallStats.getTopReason() : "No reasons provided" %>
                    <i class="fas fa-quote-right" style="color: #ccc;"></i>
                </p>
            </div>
        <% } %>

        <% if (reportData != null && !reportData.isEmpty()) { %>
        <div class="report-table" style="margin-top: 20px;">
            <table>
                <thead>
                    <tr>
                        <% if ("monthly".equals(period)) { %>
                            <th>Month</th>
                        <% } else { %>
                            <th>Date</th>
                        <% } %>
                        <th>Cancellations</th>
                        <th>Total Bookings</th>
                        <th>Cancellation Rate</th>
                        <th>Top Reason</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (CancellationStatViewModel item : reportData) {
                        String rateClass = "rate-low";
                        if (item.getCancellationRate() > 20) rateClass = "rate-high";
                        else if (item.getCancellationRate() > 10) rateClass = "rate-medium";
                    %>
                        <tr>
                            <td>
                                <% if ("monthly".equals(period) && item.getCancellationDate() != null) { %>
                                    <%= java.time.Month.of(item.getCancellationDate().getMonthValue()).name() %> <%= item.getCancellationDate().getYear() %>
                                <% } else if (item.getCancellationDate() != null) { %>
                                    <%= item.getCancellationDate().format(formatter) %>
                                <% } %>
                            </td>
                            <td><%= item.getCancellationCount() %></td>
                            <td><%= item.getTotalBookings() %></td>
                            <td>
                                <span class="cancellation-rate <%= rateClass %>">
                                    <%= String.format("%.2f", item.getCancellationRate()) %>%
                                </span>
                            </td>
                            <td><%= item.getTopReason() != null ? item.getTopReason() : "N/A" %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        <% } else if (!"overall".equals(period)) { %>
        <div class="report-table">
            <div class="no-data">
                <i class="fas fa-inbox" style="font-size: 48px; color: #ccc;"></i>
                <p>No cancellation data available for the selected period.</p>
            </div>
        </div>
        <% } %>
    </div>
</body>
</html>

