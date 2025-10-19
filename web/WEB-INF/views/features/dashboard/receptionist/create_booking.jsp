<%-- 
    Document : create_booking
    Created on : Oct 15, 2025, 4:07:20 AM
    Author : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Booking | Coastal Elegance</title>
        <style>
            /* Coastal Elegance Color Palette */
            :root {
                --color-primary: #1B4965; 
                --color-secondary: #75C3B3; 
                --color-background: #F8F9FA; 
                --color-text: #333333;
                --color-accent: #D4B483; 
            }
            body {
                font-family: Arial, sans-serif;
                background-color: var(--color-background);
                color: var(--color-text);
                margin: 20px;
            }
            .header-info, h2 {
                color: var(--color-primary);
                border-bottom: 2px solid var(--color-secondary);
                padding-bottom: 10px;
            }
            .form-section {
                margin-bottom: 30px;
                padding: 15px;
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: var(--color-secondary);
                color: white;
            }
            input[type="date"], input[type="number"], textarea {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 100%;
                box-sizing: border-box;
            }
            button[type="submit"] {
                width: 175px;
                background-color: var(--color-primary);
                color: white;
                padding: 12px 25px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }
            button[type="submit"]:hover {
                background-color: #0E2938; 
            }
            .error-message {
                color: var(--color-accent); 
                font-weight: bold;
                margin-bottom: 15px;
            }
            /* Style cho input number Quantity */
            .quantity-input {
                width: 70px; 
            }

            a{

                background-color: var(--color-primary);
                text-decoration: none;
                color: white;
                padding: 12px 25px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }
            a:hover {
                background-color: #0E2938; 
            }
        </style>
    </head>
    <body>
        <h2>Create new booking</h2>

        <%-- PRINT ERROR MESS FROM CONTROLLER --%>
        <c:if test="${not empty requestScope['error']}">
            <div class="error-message">
                Error: <c:out value="${requestScope['error']}" />
            </div>
        </c:if>

        <c:set var="guest" value="${requestScope['guests']}" />
        <div class="header-info form-section">
            <h3>Guest Information</h3>
            <p><strong>Guest Id:</strong> <c:out value="${guest.guestId}" /></p>
            <p><strong>Guest Name:</strong> <c:out value="${guest.fullName}" /></p>
        </div>

        <form action="create-booking" method="post">
            <%-- DỮ LIỆU BẮT BUỘC CHO BOOKING --%>
            <input type="hidden" name="guestId" value="${guest.guestId}" />

            <div class="form-section">
                <h3>Details</h3>
                <table>
                    <tr>
                        <td><label for="checkInDate">Check-in Date:</label></td>
                        <td><input type="date" name="checkInDate" id="checkInDate" required /></td>
                    </tr>
                    <tr>
                        <td><label for="checkOutDate">Check-out Date:</label></td>
                        <td><input type="date" name="checkOutDate" id="checkOutDate" required /></td>
                    </tr>
                    <tr>
                        <td><label for="totalGuest">Total Guests:</label></td>
                        <td><input type="number" name="totalGuest" id="totalGuest" min="1" required /></td>
                    </tr>
                    <tr>
                        <td><label for="specialRequests">Special Requests:</label></td>
                        <td><textarea name="specialRequests" id="specialRequests" rows="3"></textarea></td>
                    </tr>
                </table>
            </div>

            <div class="form-section">
                <h3>Choose room</h3>
                <c:set var="rooms" value="${requestScope['rooms']}" />
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>Room</th>
                            <th>Room Type</th>
                            <th>Price per night ($)</th>
                            <th>Total guests</th>
                            <th>Room status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${rooms}">
                            <tr>
                                <td>                                    
                                    <input type="radio" name="roomId" value="${r.roomId}" id="room_${r.roomId}" onclick="toggleQuantity(this, '${r.roomId}')" />
                                </td>
                                <td><label for="room_${r.roomId}">${r.roomNumber}</label></td>
                                <td>${r.typeName}</td>
                                <td>${r.pricePerNight}</td>
                                <td>${r.capacity}</td>
                                <td>${r.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="form-section">
                <h3>Service (Optional)</h3>
                <c:set var="services" value="${requestScope['services']}" />
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>Service</th>
                            <th>Price ($)</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${services}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="serviceId[]" value="${s.serviceId}" id="service_${s.serviceId}" 
                                           onclick="toggleQuantity(this, '${s.serviceId}')" />
                                </td>
                                <td><label for="service_${s.serviceId}">${s.serviceName}</label></td>
                                <td>${s.price}</td>
                                <td>
                                    <input type="number" name="quantity_${s.serviceId}" min="1" value="1" 
                                           class="quantity-input" disabled required />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <button type="submit">Create Booking</button>
        </form>

        <script>
           
            function toggleQuantity(checkbox) {
               
                var quantityInput = checkbox.closest('tr').querySelector('input[type=number]');
                if (quantityInput) {
                    quantityInput.disabled = !checkbox.checked;
                    if (!checkbox.checked) {
                        quantityInput.value = 1;
                    }
                }
            }
        </script>
        <script>
                    //FUNCTION SHOW POPUP
            function showSuccessPopup(message) {
                
                alert("Success: " + message);

                // OR use HTML/CSS/JS code to create a nicer popup
            }

            // --- LOGIC SHOW POPUP FROM SESSION ---

            <%
            String successMessage = (String) session.getAttribute("popupMessage");

            if (successMessage != null) {
                // DELETE attribute FROM session
                session.removeAttribute("popupMessage");
            %>
            var message = "<%= successMessage%>";
            showSuccessPopup(message);
            <%
            }
            %>
        </script>

        <hr>
        <a href="${pageContext.request.contextPath}/receptionist">Back to Guest list</a>
    </body>
</html>