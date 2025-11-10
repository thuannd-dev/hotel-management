<%@page import="com.hotel_management.domain.dto.manager.RoomOccupancyViewModel"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object reportDataObj = request.getAttribute("reportData");
    String period = (String) request.getAttribute("period");
    Integer selectedYear = (Integer) request.getAttribute("selectedYear");
    Integer selectedMonth = (Integer) request.getAttribute("selectedMonth");

    List<RoomOccupancyViewModel> yearlyData = null;
    RoomOccupancyViewModel monthlyData = null;

    if ("yearly".equals(period)) {
        yearlyData = (List<RoomOccupancyViewModel>) reportDataObj;
    } else if ("monthly".equals(period)) {
        monthlyData = (RoomOccupancyViewModel) reportDataObj;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Room Occupancy Report - Manager Dashboard</title>
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

        .occupancy-bar {
            height: 20px;
            background-color: #e0e0e0;
            border-radius: 10px;
            overflow: hidden;
            position: relative;
        }

        .occupancy-fill {
            height: 100%;
            background: linear-gradient(to right, #4CAF50, #8BC34A);
            transition: width 0.3s ease;
        }

        .occupancy-text {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 12px;
            font-weight: bold;
            color: #333;
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

        .monthly-detail {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
        }

        .detail-label {
            font-weight: 600;
            color: #555;
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
        <h2><i class="fas fa-bed"></i> Room Occupancy Report</h2>
        <a href="${pageContext.request.contextPath}/manager" class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <div class="container">
        <div class="filter-section">
            <div class="filter-tabs">
                <button class="filter-tab <%= "yearly".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=room-occupancy&period=yearly'">
                    Yearly
                </button>
                <button class="filter-tab <%= "monthly".equals(period) ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/manager/report?type=room-occupancy&period=monthly'">
                    Monthly
                </button>
            </div>

            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="room-occupancy">
                <input type="hidden" name="period" value="<%= period %>">

                <div class="form-group">
                    <label>Year:</label>
                    <input type="number" name="year" value="<%= selectedYear != null ? selectedYear : LocalDate.now().getYear() %>" min="2000" max="2100" required>
                </div>

                <% if ("monthly".equals(period)) { %>
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
                <% } %>

                <button type="submit" class="btn-filter">
                    <i class="fas fa-search"></i> Generate Report
                </button>
            </form>
        </div>

        <div class="report-table">
            <% if ("yearly".equals(period) && yearlyData != null && !yearlyData.isEmpty()) {
                double avgOccupancy = 0;
                for (RoomOccupancyViewModel item : yearlyData) {
                    avgOccupancy += item.getOccupancyRate();
                }
                avgOccupancy /= yearlyData.size();
            %>
                <div class="summary">
                    <div class="summary-item">
                        <span class="summary-label">Average Annual Occupancy:</span>
                        <span><%= String.format("%.2f", avgOccupancy) %>%</span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Total Rooms:</span>
                        <span><%= yearlyData.get(0).getTotalRooms() %></span>
                    </div>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Days in Month</th>
                            <th>Occupied Room-Days</th>
                            <th>Max Possible Room-Days</th>
                            <th>Occupancy Rate</th>
                            <th>Visual</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (RoomOccupancyViewModel item : yearlyData) {
                            int maxRoomDays = item.getTotalRooms() * item.getTotalDaysInMonth();
                        %>
                            <tr>
                                <td><%= java.time.Month.of(item.getMonth()).name() %> <%= item.getYear() %></td>
                                <td><%= item.getTotalDaysInMonth() %></td>
                                <td><%= item.getOccupiedRoomDays() %></td>
                                <td><%= maxRoomDays %></td>
                                <td><strong><%= String.format("%.2f", item.getOccupancyRate()) %>%</strong></td>
                                <td>
                                    <div class="occupancy-bar">
                                        <div class="occupancy-fill" style="width: <%= item.getOccupancyRate() %>%"></div>
                                        <div class="occupancy-text"><%= String.format("%.1f", item.getOccupancyRate()) %>%</div>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else if ("monthly".equals(period) && monthlyData != null) {
                int maxRoomDays = monthlyData.getTotalRooms() * monthlyData.getTotalDaysInMonth();
            %>
                <div class="monthly-detail">
                    <h3><%= java.time.Month.of(monthlyData.getMonth()).name() %> <%= monthlyData.getYear() %> - Detailed Occupancy</h3>

                    <div class="detail-row">
                        <span class="detail-label">Total Rooms:</span>
                        <span><%= monthlyData.getTotalRooms() %></span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Days in Month:</span>
                        <span><%= monthlyData.getTotalDaysInMonth() %></span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Maximum Possible Room-Days:</span>
                        <span><%= maxRoomDays %></span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Occupied Room-Days:</span>
                        <span><%= monthlyData.getOccupiedRoomDays() %></span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Available Room-Days:</span>
                        <span><%= maxRoomDays - monthlyData.getOccupiedRoomDays() %></span>
                    </div>

                    <div class="detail-row" style="border-bottom: none; font-size: 1.2em;">
                        <span class="detail-label">Occupancy Rate:</span>
                        <span style="color: #2b4c7e; font-weight: bold;"><%= String.format("%.2f", monthlyData.getOccupancyRate()) %>%</span>
                    </div>
                </div>

                <div class="occupancy-bar" style="height: 40px; margin-top: 20px;">
                    <div class="occupancy-fill" style="width: <%= monthlyData.getOccupancyRate() %>%"></div>
                    <div class="occupancy-text" style="font-size: 16px;"><%= String.format("%.2f", monthlyData.getOccupancyRate()) %>%</div>
                </div>
            <% } else { %>
                <div class="no-data">
                    <i class="fas fa-inbox" style="font-size: 48px; color: #ccc;"></i>
                    <p>No occupancy data available for the selected period.</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>

