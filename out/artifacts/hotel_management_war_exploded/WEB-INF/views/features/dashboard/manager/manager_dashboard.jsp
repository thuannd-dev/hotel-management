<%-- 
    Document   : manager_dashboard
    Created on : Oct 20, 2025, 10:40:08 AM
    Author     : PC
--%>

<%@page import="com.hotel_management.domain.dto.staff.StaffViewModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<html>
    <head>
        <title>Manager Dashboard</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #F8F9FA;
                margin: 25px;
                padding: 0;
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
           
            .user-info-bar {
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .user-name {
                color: #ffffff;
                font-weight: bold;
                font-size: 16px;
            }

            .btn-logout {
                background-color: #D34E51;                                                                                                       
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

            .dashboard {
                padding: 30px;
            }

            .card-container {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
                gap: 20px;
            }

            .card {
                background-color: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                transition: transform 0.2s ease;
                text-align: center;
            }

            .card:hover {
                transform: translateY(-5px);
            }

            .card a {
                color: #2b4c7e;
                text-decoration: none;
                font-weight: bold;
                font-size: 1.1em;
            }

            .logout-btn {
                background-color: #e74c3c;
                border: none;
                padding: 8px 14px;
                color: white;
                border-radius: 5px;
                cursor: pointer;
            }

            .logout-btn:hover {
                background-color: #c0392b;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <h2>Manager - Reports Management</h2>            
            <div class="user-info-bar">
                <span class="user-name">
                    <i class="fas fa-user" aria-hidden="true"></i>
                    <span class="sr-only">Current user: </span><%=fullName%>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout" aria-label="Logout from the system">
                    <i class="fas fa-sign-out-alt" aria-hidden="true"></i>
                    <span>Logout</span>
                </a>
            </div>
        </div>

        <div class="dashboard">
            <h3>Reports & Statistics</h3>
            <div class="card-container">
                <div class="card">
                    <a href="${pageContext.request.contextPath}/manager/report?type=daily">Revenue Report</a>
                    <p>View daily, monthly, and yearly revenue.</p>
                </div>

                <div class="card">
                    <a href="#">Top 10 Frequent Guests</a>
                    <p>See guests who visit most frequently.</p>
                </div>

                <div class="card">
                    <a href="#">Most Used Services</a>
                    <p>Check which services are most popular.</p>
                </div>

                <div class="card">
                    <a href="#">Room Occupancy Rate</a>
                    <p>View room usage by month.</p>
                </div>

                <div class="card">
                    <a href="#">Cancellation Statistics</a>
                    <p>Track booking cancellations and trends.</p>
                </div>
            </div>
        </div>

    </body>
</html>
