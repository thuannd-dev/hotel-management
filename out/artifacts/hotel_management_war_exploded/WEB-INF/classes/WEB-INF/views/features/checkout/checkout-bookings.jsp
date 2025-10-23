<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Select Booking</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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

        .checkout-container {
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
            display: grid;
            grid-template-columns: 1fr auto;
            gap: 20px;
            align-items: center;
        }

        .booking-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
        }

        .booking-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
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
            font-size: 12px;
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

        .btn-checkout {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 12px 24px;
            background: #cc8c18;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.3s ease;
            white-space: nowrap;
            border: none;
            cursor: pointer;
        }

        .btn-checkout:hover {
            background: #b57a15;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
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

        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            background: #d4edda;
            color: #155724;
        }

        @media (max-width: 768px) {
            .checkout-container {
                padding: 15px;
                margin: 20px auto;
            }

            .page-header h1 {
                font-size: 24px;
            }

            .booking-card {
                grid-template-columns: 1fr;
            }

            .booking-details {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <jsp:include page="../../layout/header.jsp"/>

    <div class="checkout-container">
        <a href="${pageContext.request.contextPath}/guest/my-booking" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to My Bookings
        </a>

        <div class="page-header">
            <h1><i class="fas fa-cash-register"></i> Checkout - Select Booking</h1>
            <p>Select a booking to proceed with checkout payment</p>
        </div>

        <c:choose>
            <c:when test="${empty bookings}">
                <div class="no-bookings">
                    <i class="fas fa-bed"></i>
                    <h3>No Bookings Available for Checkout</h3>
                    <p>You don't have any active checked-in bookings at the moment.</p>
                    <a href="${pageContext.request.contextPath}/guest/my-booking" class="btn-checkout">
                        <i class="fas fa-list"></i> View All Bookings
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="bookings-grid">
                    <c:forEach var="booking" items="${bookings}">
                        <div class="booking-card">
                            <div class="booking-details">
                                <div class="detail-item">
                                    <i class="detail-icon fas fa-hashtag"></i>
                                    <div class="detail-content">
                                        <h4>Booking ID</h4>
                                        <p>#${booking.bookingId}</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-door-open"></i>
                                    <div class="detail-content">
                                        <h4>Room</h4>
                                        <p>${booking.roomNumber} - ${booking.roomType}</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-calendar-alt"></i>
                                    <div class="detail-content">
                                        <h4>Check-in</h4>
                                        <p>${booking.checkInDate.dayOfMonth}/${booking.checkInDate.monthValue}/${booking.checkInDate.year}</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-calendar-check"></i>
                                    <div class="detail-content">
                                        <h4>Check-out</h4>
                                        <p>${booking.checkOutDate.dayOfMonth}/${booking.checkOutDate.monthValue}/${booking.checkOutDate.year}</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-users"></i>
                                    <div class="detail-content">
                                        <h4>Guests</h4>
                                        <p>${booking.totalGuests} Guest(s)</p>
                                    </div>
                                </div>

                                <div class="detail-item">
                                    <i class="detail-icon fas fa-info-circle"></i>
                                    <div class="detail-content">
                                        <h4>Status</h4>
                                        <p><span class="status-badge">${booking.status}</span></p>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <a href="${pageContext.request.contextPath}/guest/checkout?action=summary&bookingId=${booking.bookingId}"
                                   class="btn-checkout">
                                    <i class="fas fa-credit-card"></i> Proceed to Checkout
                                </a>
                            </div>
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

