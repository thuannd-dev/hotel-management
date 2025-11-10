<%@page import="com.hotel_management.domain.dto.manager.FrequentGuestViewModel"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<FrequentGuestViewModel> reportData = (List<FrequentGuestViewModel>) request.getAttribute("reportData");
    Integer topN = (Integer) request.getAttribute("topN");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Top Frequent Guests - Manager Dashboard</title>
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
            width: 100px;
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

        .rank {
            font-weight: bold;
            font-size: 1.2em;
            color: #2b4c7e;
            width: 50px;
            text-align: center;
        }

        .gold { color: #FFD700; }
        .silver { color: #C0C0C0; }
        .bronze { color: #CD7F32; }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h2><i class="fas fa-users"></i> Top Frequent Guests</h2>
        <a href="${pageContext.request.contextPath}/manager" class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <div class="container">
        <div class="filter-section">
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/manager/report">
                <input type="hidden" name="type" value="frequent-guests">

                <div class="form-group">
                    <label>Top N Guests:</label>
                    <input type="number" name="topN" value="<%= topN != null ? topN : 10 %>" min="1" max="100" required>
                </div>

                <button type="submit" class="btn-filter">
                    <i class="fas fa-search"></i> Generate Report
                </button>
            </form>
        </div>

        <div class="report-table">
            <% if (reportData != null && !reportData.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>Rank</th>
                            <th>Guest Name</th>
                            <th>Phone</th>
                            <th>Email</th>
                            <th>Total Bookings</th>
                            <th>Total Nights</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        int rank = 1;
                        for (FrequentGuestViewModel guest : reportData) {
                            String rankClass = "";
                            if (rank == 1) rankClass = "gold";
                            else if (rank == 2) rankClass = "silver";
                            else if (rank == 3) rankClass = "bronze";
                        %>
                            <tr>
                                <td class="rank <%= rankClass %>">
                                    <% if (rank <= 3) { %>
                                        <i class="fas fa-trophy"></i>
                                    <% } %>
                                    #<%= rank %>
                                </td>
                                <td><%= guest.getFullName() %></td>
                                <td><%= guest.getPhone() != null ? guest.getPhone() : "N/A" %></td>
                                <td><%= guest.getEmail() != null ? guest.getEmail() : "N/A" %></td>
                                <td><%= guest.getBookingCount() %></td>
                                <td><%= guest.getTotalNights() %></td>
                            </tr>
                        <%
                            rank++;
                        }
                        %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <i class="fas fa-inbox" style="font-size: 48px; color: #ccc;"></i>
                    <p>No guest data available.</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>

