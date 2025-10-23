<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings - Hotel Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: "Poppins", sans-serif;
            background: #f5f5f5;
            min-height: 100vh;
        }

        .my-booking-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 20px;
        }

        .page-header {
            background: linear-gradient(135deg, #cc8c18 0%, #d4a535 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
            box-shadow: 0 4px 15px rgba(204, 140, 24, 0.3);
        }

        .page-header h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .page-header p {
            font-size: 16px;
            opacity: 0.95;
        }

        .bookings-grid {
            display: grid;
            gap: 20px;
        }

        .booking-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border-left: 5px solid #cc8c18;
        }

        .booking-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
        }

        .booking-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .booking-id {
            font-size: 18px;
            font-weight: 600;
            color: #333;
        }

        .booking-status {
            padding: 6px 16px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 500;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .status-pending {
            background: #fff3cd;
            color: #856404;
        }

        .status-confirmed {
            background: #d1ecf1;
            color: #0c5460;
        }

        .status-check-in, .status-check_in {
            background: #d4edda;
            color: #155724;
        }

        .status-check-out, .status-check_out {
            background: #cce5ff;
            color: #004085;
        }

        .status-cancelled {
            background: #f8d7da;
            color: #721c24;
        }

        .booking-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .detail-item {
            display: flex;
            align-items: flex-start;
            gap: 12px;
        }

        .detail-icon {
            color: #cc8c18;
            font-size: 20px;
            margin-top: 2px;
            min-width: 20px;
        }

        .detail-content h4 {
            font-size: 13px;
            color: #888;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .detail-content p {
            font-size: 15px;
            color: #333;
            font-weight: 500;
        }

        .no-bookings {
            background: white;
            border-radius: 12px;
            padding: 60px 30px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .no-bookings i {
            font-size: 20px;
            color: #cc8c18;
            opacity: 0.3;
        }

        .no-bookings h3 {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }

        .no-bookings p {
            font-size: 16px;
            color: #666;
            margin-bottom: 25px;
        }

        .btn-book-now {
            display: inline-block;
            padding: 12px 30px;
            background: #cc8c18;
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-book-now:hover {
            background: #b57a15;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: #cc8c18;
            text-decoration: none;
            font-weight: 500;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .back-link:hover {
            color: #b57a15;
            gap: 12px;
        }

        .payment-status {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: 500;
            margin-top: 5px;
        }

        .payment-pending {
            background: #fff3cd;
            color: #856404;
        }

        .payment-paid {
            background: #d4edda;
            color: #155724;
        }

        .payment-refunded {
            background: #f8d7da;
            color: #721c24;
        }

        .booking-actions {
            margin-top: 20px;
            padding-top: 15px;
            border-top: 2px solid #f0f0f0;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .btn-request-service {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: #cc8c18;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
        }

        .btn-request-service:hover {
            background: #b57a15;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
        }

        .btn-view-services {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        .btn-view-services:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }

        @media (max-width: 768px) {
            .my-booking-container {
                padding: 15px;
                margin: 20px auto;
            }

            .page-header h1 {
                font-size: 24px;
            }

            .page-header p {
                font-size: 14px;
                color: white;
            }

            .booking-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }

            .booking-details {
                grid-template-columns: 1fr;
                gap: 15px;
            }

            .booking-card {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <jsp:include page="../../layout/header.jsp"/>

    <div class="my-booking-container">
        <a href="${pageContext.request.contextPath}/" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to Home
        </a>

        <div class="page-header">
            <h1><i class="fas fa-calendar-check"></i> My Bookings</h1>
            <p>Welcome back, ${guestName}! Here are all your bookings.</p>
        </div>

        <c:choose>
            <c:when test="${empty bookings}">
                <div class="no-bookings">
                    <i class="fas fa-calendar-times"></i>
                    <h3>No Bookings Yet</h3>
                    <p>You haven't made any bookings. Start exploring our available rooms!</p>
                    <a href="${pageContext.request.contextPath}#services" class="btn-book-now">
                        <i class="fas fa-search"></i> Browse Rooms
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="bookings-grid">
                    <%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>
                    <c:forEach var="booking" items="${bookings}">
                        <div class="booking-card">
                            <div class="booking-header">
                                <div class="booking-id">
                                    <i class="fas fa-hashtag"></i> Booking #${booking.bookingId}
                                </div>
                                <span class="booking-status status-${booking.status.toLowerCase().replace('_', '-')}">
                                    ${booking.status}
                                </span>
                            </div>

                            <div class="booking-details">
                                <div class="detail-item">
                                    <i class="detail-icon fas fa-door-open"></i>
                                    <div class="detail-content">
                                        <h4>Room Number</h4>
                                        <p>${booking.roomNumber}</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-calendar-alt"></i>
                                    <div class="detail-content">
                                        <h4>Check-in Date</h4>
                                        <p>
                                            <c:set var="checkIn" value="${booking.checkInDate}" />
                                            ${checkIn.dayOfMonth}/${checkIn.monthValue}/${checkIn.year}
                                        </p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-calendar-alt"></i>
                                    <div class="detail-content">
                                        <h4>Check-out Date</h4>
                                        <p>
                                            <c:set var="checkOut" value="${booking.checkOutDate}" />
                                            ${checkOut.dayOfMonth}/${checkOut.monthValue}/${checkOut.year}
                                        </p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-calendar-check"></i>
                                    <div class="detail-content">
                                        <h4>Booking Date</h4>
                                        <p>
                                            <c:set var="bookingDate" value="${booking.bookingDate}" />
                                            ${bookingDate.dayOfMonth}/${bookingDate.monthValue}/${bookingDate.year}
                                        </p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-users"></i>
                                    <div class="detail-content">
                                        <h4>Total Guests</h4>
                                        <p>${booking.totalGuest} Guest(s)</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-credit-card"></i>
                                    <div class="detail-content">
                                        <h4>Payment Status</h4>
                                        <p>
                                            <span class="payment-status payment-${booking.paymentStatus.toLowerCase()}">
                                                ${booking.paymentStatus}
                                            </span>
                                        </p>
                                    </div>
                                </div>

                                <c:if test="${not empty booking.specialRequests}">
                                    <div class="detail-item" style="grid-column: 1 / -1;">
                                        <i class="detail-icon fas fa-comment-alt"></i>
                                        <div class="detail-content">
                                            <h4>Special Requests</h4>
                                            <p>${booking.specialRequests}</p>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${booking.status == 'CANCELLED' and not empty booking.cancellationReason}">
                                    <div class="detail-item" style="grid-column: 1 / -1;">
                                        <i class="detail-icon fas fa-ban"></i>
                                        <div class="detail-content">
                                            <h4>Cancellation Reason</h4>
                                            <p>${booking.cancellationReason}</p>
                                            <c:if test="${not empty booking.cancellationDate}">
                                                <c:set var="cancelDate" value="${booking.cancellationDate}" />
                                                <small style="color: #888;">Cancelled on: ${cancelDate.dayOfMonth}/${cancelDate.monthValue}/${cancelDate.year}</small>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                        <%-- Action buttons - Only show for bookings that haven't checked out --%>
                            <c:if test="${booking.status != 'Checked-out' && booking.status != 'Canceled'}">
                                <div class="booking-actions">
                                    <a href="${pageContext.request.contextPath}/guest/request-services?bookingId=${booking.bookingId}"
                                       class="btn-request-service">
                                        <i class="fas fa-plus-circle"></i> Request Additional Services
                                    </a>
                                    <a href="${pageContext.request.contextPath}/guest/view-services?bookingId=${booking.bookingId}"
                                       class="btn-view-services">
                                        <i class="fas fa-list"></i> View Requested Services
                                    </a>

                                    <%-- Show checkout button only for Checked-in bookings --%>
                                    <c:if test="${booking.status == 'Checked-in'}">
                                        <a href="${pageContext.request.contextPath}/guest/checkout?action=summary&bookingId=${booking.bookingId}"
                                           class="btn-request-service" style="background: #28a745;">
                                            <i class="fas fa-cash-register"></i> Checkout & Pay
                                        </a>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Include footer -->
    <jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

