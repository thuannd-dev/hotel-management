<%-- 
    Document: receptionist_dashboard_guest_list
    Created on: Oct 19, 2025
--%>

<%@page import="com.hotel_management.domain.dto.staff.StaffViewModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receptionist Dashboard</title>
        <style>
            /* Coastal Elegance Color Palette */
            :root {
                --color-primary: #1B4965; /* Deep Navy - Headers, Buttons */
                --color-secondary: #75C3B3; /* Teal/Aqua - Table Header/Accent */
                --color-background: #F8F9FA; /* Off-White */
                --color-text: #333333;
                --color-accent-sand: #D4B483; /* Sand/Gold */
                --color-danger: #D34E51;
            }
            body {
                font-family: Arial, sans-serif;
                background-color: var(--color-background);
                color: var(--color-text);
                margin: 25px;
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
            
            h2 {
                color: #ffffff;
                border-bottom: 3px solid var(--color-secondary);
                padding-bottom: 10px;
            }
            /* --- Search Form --- */
            .search-bar {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
                padding: 15px;
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .search-bar input[type="text"] {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                flex-grow: 1;
            }
            .search-bar button {
                background-color: var(--color-primary);
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .search-bar button:hover {
                background-color: #0E2938;
            }
            /* --- Data Table --- */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
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
            tr:nth-child(even) {
                background-color: #f7f7f7;
            }
            /* --- Action Button --- */
            .action-link {
                background-color: var(--color-accent-sand);
                color: var(--color-primary);
                text-decoration: none;
                padding: 5px 10px;
                border-radius: 4px;
                font-size: 14px;
                font-weight: bold;
                transition: opacity 0.3s;
            }
            .action-link:hover {
                opacity: 0.8;
            }
            .no-data {
                text-align: center;
                color: var(--color-danger);
                margin-top: 20px;
                font-size: 1.1em;
            }
        </style>
    </head>
    <body>
        <div class="navbar">                                
            <h2>Receptionist - Guest List</h2>     
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
        

        <form action="find-check-in-booking" method="get" class="search-bar">
            <input type="text" name="guestName" placeholder="Search by Guest Name" value="${param.guestName}">
            <input type="text" name="roomNumber" placeholder="Search by Room Number" value="${param.roomNumber}">
            <button type="submit">Search</button>
        </form>

        <c:set var="guests" value="${requestScope['guests']}" />

        <c:if test="${not empty guests}">
            <table>
                <thead>
                    <tr>
                        <th>Guest ID</th>
                        <th>Full Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>ID Number</th>
                        <th>Date of Birth</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Mapping based on your original columns and headers --%>
                    <c:forEach var="g" items="${guests}">
                        <tr>
                            <td>${g.guestId}</td>
                            <td>${g.fullName}</td>
                            <td>${g.phone}</td>
                            <td>${g.email}</td>
                            <td>${g.address}</td>
                            <td>${g.idNumber}</td>
                            <td>
                                ${g.dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}
                            </td>
                            <td>
                                <a href="receptionist-dashboard/create-booking?guestId=${g.guestId}" class="action-link">Create booking</a>
                                <a href="receptionist-dashboard/detail-booking?guestId=${g.guestId}" class="action-link">Booking information</a>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty guests}">
            <p class="no-data">No guests were found.</p>
        </c:if>
        <!--...-->
        <script>
           
            function showSuccessPopup(message) {
                var popupElement = document.getElementById('successPopup');

                if (popupElement) {
                    popupElement.textContent = "Success: " + message;

                    popupElement.style.display = 'block';
                    setTimeout(() => {
                        popupElement.classList.add('show');
                    }, 10); 
                    setTimeout(function () {
                        popupElement.classList.remove('show');
                        setTimeout(() => {
                            popupElement.style.display = 'none';
                        }, 500); 
                    }, 5000); 
                } else {
                    alert("Success2: " + message);
                    location.reload();
                }
            }

            <%
            String successMessage = (String) session.getAttribute("popupMessage");

            if (successMessage != null) {
                session.removeAttribute("popupMessage");
            %>
            var message = "<%= successMessage%>";
            showSuccessPopup(message);
            <%
            }
            %>
        </script>
    </body>
</html>