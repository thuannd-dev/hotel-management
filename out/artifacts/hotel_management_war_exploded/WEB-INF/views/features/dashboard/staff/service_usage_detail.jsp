<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Service Usage Detail</title>
</head>
<body>
    <c:set var="newBookingServiceUsageModels" value="${requestScope['listNewBookingServiceUsage']}" />
    <c:set var="oldBookingServiceUsageModels" value="${requestScope['listOldBookingServiceUsage']}" />
    <c:set var="booking" value="${requestScope['checkInBookingDetails']}" />
    <%--@elvariable id="booking" type="edu.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
    <h2>Booking Information</h2>
    <p>Booking ID: ${booking.bookingId}</p>
    <p>Guest: ${booking.guestFullName}</p>
    <p>Room: ${booking.roomNumber}</p>
    <p>Status: ${booking.status}</p>
    <p>Check-in: ${booking.checkInDate}</p>
    <p>Check-out: ${booking.checkOutDate}</p>

    <hr/>

    <h3>Service Usage</h3>
    <!-- New Booking Service Usage -->
    <c:if test="${not empty newBookingServiceUsageModels}">
        <h4>New Service Usage</h4>
        <table border="1" cellspacing="0" cellpadding="5">
            <thead>
            <tr>
                <th>#</th>
                <th>Service Name</th>
                <th>Type</th>
                <th>Service Price</th>
                <th>Quantity</th>
                <th>Date</th>
                <th>Status</th>
                <th>Request Time</th>
                <th>Completion Time</th>
                <th>SubPrice</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="newSubTotal" value="0"/>
            <%--@elvariable id="s" type="edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel"--%>
            <c:forEach var="s" items="${newBookingServiceUsageModels}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${s.serviceName}</td>
                    <td>${s.serviceType}</td>
                    <td>${s.servicePrice}</td>
                    <td>${s.quantity}</td>
                    <td>${s.serviceDate}</td>
                    <td>${s.bookingServiceStatus}</td>
                    <td><c:out value="${s.requestTime != null ? s.requestTime : '-'}"/></td>
                    <td><c:out value="${s.completionTime != null ? s.completionTime : '-'}"/></td>
                    <td>${s.subPrice}</td>
                    <td>
                        <c:choose>
                            <c:when test="${s.bookingServiceStatus != 'Completed'}">
                                <a href="booking-services/status?id=${s.bookingServiceId}">Mark Completed</a>
                            </c:when>
                            <c:otherwise>Completed</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="newSubTotal" value="${newSubTotal + s.subPrice}"/>
            </c:forEach>
            <tr>
                <td colspan="9" style="text-align:right;"><b>Total of new service usage</b></td>
                <td><b>${newSubTotal}</b></td>
            </tr>
            </tbody>
        </table>
        <br/>
    </c:if>

    <!-- Old Booking Service Usage -->
    <c:if test="${not empty oldBookingServiceUsageModels}">
        <h4>Services used previously</h4>
        <table border="1" cellspacing="0" cellpadding="5">
            <thead>
            <tr>
                <th>#</th>
                <th>Service Name</th>
                <th>Type</th>
                <th>Service Price</th>
                <th>Quantity</th>
                <th>Date</th>
                <th>Status</th>
                <th>Request Time</th>
                <th>Completion Time</th>
                <th>SubPrice</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="oldSubTotal" value="0"/>
            <%--@elvariable id="s" type="edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel"--%>
            <c:forEach var="s" items="${oldBookingServiceUsageModels}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${s.serviceName}</td>
                    <td>${s.serviceType}</td>
                    <td>${s.servicePrice}</td>
                    <td>${s.quantity}</td>
                    <td>${s.serviceDate}</td>
                    <td>${s.bookingServiceStatus}</td>
                    <td><c:out value="${s.requestTime != null ? s.requestTime : '-'}"/></td>
                    <td><c:out value="${s.completionTime != null ? s.completionTime : '-'}"/></td>
                    <td>${s.subPrice}</td>
                    <td>
                        <c:choose>
                            <c:when test="${s.bookingServiceStatus != 'Completed'}">
                                <a href="booking-services/status?id=${s.bookingServiceId}">Mark Completed</a>
                            </c:when>
                            <c:otherwise>Completed</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="oldSubTotal" value="${oldSubTotal + s.subPrice}"/>
            </c:forEach>
            <tr>
                <td colspan="9" style="text-align:right;"><b>Total of services used previously</b></td>
                <td><b>${oldSubTotal}</b></td>
            </tr>
            </tbody>
        </table>
        <br/>
    </c:if>

    <!-- Grand Total for all services -->
    <c:if test="${(not empty newBookingServiceUsageModels) or (not empty oldBookingServiceUsageModels)}">
        <c:set var="grandTotal" value="${(newSubTotal != null ? newSubTotal : 0) + (oldSubTotal != null ? oldSubTotal : 0)}"/>
        <div style="text-align:right; margin-top:10px;">
            <b>Grand Total: ${grandTotal}</b>
        </div>
    </c:if>

    <!-- If both are empty -->
    <c:if test="${empty newBookingServiceUsageModels and empty oldBookingServiceUsageModels}">
        <p>No services recorded yet.</p>
    </c:if>

    <a href="services?bookingId=${booking.bookingId}">Add More Service</a>
    <br/>
    <a href="${pageContext.request.contextPath}/service-staff">Back to Booking List</a>

</body>
</html>
