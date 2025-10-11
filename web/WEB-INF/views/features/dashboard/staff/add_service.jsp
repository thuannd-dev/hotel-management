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
        <label for="serviceId">Service:</label>
        <select name="serviceId" id="serviceId" required>
            <option value="">-- Select Service --</option>
            <c:forEach var="s" items="${services}">
              <option value="${s.serviceId}">${s.serviceName} - ${s.price} $</option>
            </c:forEach>
        </select>
        <br><br>
        <label for="quantity">Quantity:</label>
        <input type="number" name="quantity" id="quantity" min="1" value="1" required />
        <br><br>
      <button type="submit">Add Service</button>
    </form>

    <hr>
    <a href="${pageContext.request.contextPath}/service-staff">← Back to Booking List</a>
  </body>
</html>
