<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
  <head>
    <title>Record Service Usage</title>
  </head>
  <body>
    <c:set var="booking" value="${requestScope['booking']}" />
    <%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
    <div>
        <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
        <p><strong>Guest Name:</strong> ${booking.guestFullName}</p>
        <p><strong>Room Number:</strong> ${booking.roomNumber}</p>
        <p><strong>Status:</strong> ${booking.status}</p>
    </div>

    <hr>

    <!-- Form add service -->
    <form action="add-service" method="post">
        <input type="hidden" name="bookingId" value="${booking.bookingId}" />
        <c:set var="services" value="${requestScope['services']}" />
        <%--@elvariable id="services" type="com.hotel_management.domain.dto.service.ServiceViewModel"--%>
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
                            <input type="checkbox" name="serviceId[]" value="${s.serviceId}" id="service_${s.serviceId}" onclick="toggleQuantity(this, '${s.serviceId}')" />
                        </td>
                        <td><label for="service_${s.serviceId}">${s.serviceName}</label></td>
                        <td>${s.price}</td>
                        <td>
                            <input type="number" name="quantity_${s.serviceId}" min="1" value="1" disabled required />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <button type="submit">Add Services</button>
    </form>
    <script>
      function toggleQuantity(checkbox, serviceId) {
        var quantityInput = checkbox.closest('tr').querySelector('input[type=number]');
        quantityInput.disabled = !checkbox.checked;
        if (!checkbox.checked) quantityInput.value = 1;
      }
    </script>

    <hr>
    <a href="${pageContext.request.contextPath}/service-staff">← Back to Booking List</a>
  </body>
</html>
