<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Requested Services - Hotel Management</title>
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

        .view-services-container {
            max-width: 1000px;
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
            font-size: 28px;
            margin-bottom: 10px;
        }

        .page-header p {
            font-size: 15px;
            opacity: 0.95;
        }

        .booking-info {
            background: white;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-left: 5px solid #cc8c18;
        }

        .booking-info h3 {
            color: #333;
            margin-bottom: 15px;
            font-size: 18px;
        }

        .booking-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .detail-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .detail-item label {
            font-size: 12px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .detail-item span {
            font-size: 15px;
            color: #333;
            font-weight: 500;
        }

        .services-list {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .services-list h3 {
            color: #333;
            margin-bottom: 20px;
            font-size: 20px;
        }

        .service-card {
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }

        .service-card:hover {
            border-color: #cc8c18;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        }

        .service-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e0e0e0;
        }

        .service-name {
            font-size: 18px;
            font-weight: 600;
            color: #333;
        }

        .service-status {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .status-requested {
            background: #fff3cd;
            color: #856404;
        }

        .status-in-progress, .status-in_progress {
            background: #d1ecf1;
            color: #0c5460;
        }

        .status-completed {
            background: #d4edda;
            color: #155724;
        }

        .status-cancelled {
            background: #f8d7da;
            color: #721c24;
        }

        .service-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
        }

        .service-detail-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .service-detail-item label {
            font-size: 12px;
            color: #888;
            text-transform: uppercase;
        }

        .service-detail-item span {
            font-size: 15px;
            color: #333;
            font-weight: 500;
        }

        .service-price-highlight {
            color: #cc8c18;
            font-size: 18px;
            font-weight: 600;
        }

        .total-section {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 2px solid #e0e0e0;
            text-align: right;
        }

        .total-amount {
            font-size: 24px;
            font-weight: 700;
            color: #cc8c18;
        }

        .total-label {
            font-size: 16px;
            color: #666;
            margin-right: 10px;
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

        .no-services {
            text-align: center;
            padding: 60px 30px;
            color: #666;
        }

        .no-services i {
            font-size: 20px;
            color: #cc8c18;
            opacity: 0.3;
        }

        .no-services h3 {
            font-size: 22px;
            color: #333;
            margin-bottom: 10px;
        }

        .no-services p {
            font-size: 15px;
            margin-bottom: 25px;
        }

        .btn-request {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 12px 25px;
            background: #cc8c18;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-request:hover {
            background: #b57a15;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
        }

        @media (max-width: 768px) {
            .view-services-container {
                padding: 15px;
                margin: 20px auto;
            }

            .page-header h1 {
                font-size: 22px;
            }

            .service-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }

            .service-details {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <jsp:include page="../../layout/header.jsp"/>

    <div class="view-services-container">
        <a href="${pageContext.request.contextPath}/guest/my-booking" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to My Bookings
        </a>

        <div class="page-header">
            <h1><i class="fas fa-list-alt"></i> Requested Services</h1>
            <p>View all services you've requested for this booking</p>
        </div>

        <!-- Booking Information -->
        <div class="booking-info">
            <h3><i class="fas fa-info-circle"></i> Booking Information</h3>
            <div class="booking-details">
                <div class="detail-item">
                    <label>Booking ID</label>
                    <span>#${booking.bookingId}</span>
                </div>
                <div class="detail-item">
                    <label>Room Number</label>
                    <span>${booking.roomNumber}</span>
                </div>
                <div class="detail-item">
                    <label>Check-in Date</label>
                    <span>
                        <c:set var="checkIn" value="${booking.checkInDate}" />
                        ${checkIn.dayOfMonth}/${checkIn.monthValue}/${checkIn.year}
                    </span>
                </div>
                <div class="detail-item">
                    <label>Check-out Date</label>
                    <span>
                        <c:set var="checkOut" value="${booking.checkOutDate}" />
                        ${checkOut.dayOfMonth}/${checkOut.monthValue}/${checkOut.year}
                    </span>
                </div>
            </div>
        </div>

        <!-- Services List -->
        <div class="services-list">
            <h3><i class="fas fa-concierge-bell"></i> Your Requested Services</h3>

            <c:choose>
                <c:when test="${empty requestedServices}">
                    <div class="no-services">
                        <i class="fas fa-clipboard-list"></i>
                        <h3>No Services Requested Yet</h3>
                        <p>You haven't requested any additional services for this booking.</p>
                        <c:if test="${booking.status != 'CHECK_OUT' && booking.status != 'CANCELLED'}">
                            <a href="${pageContext.request.contextPath}/guest/request-services?bookingId=${booking.bookingId}"
                               class="btn-request">
                                <i class="fas fa-plus-circle"></i> Request Services Now
                            </a>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:set var="totalAmount" value="0" />
                    <c:forEach var="service" items="${requestedServices}">
                        <div class="service-card">
                            <div class="service-header">
                                <div class="service-name">
                                    <i class="fas fa-concierge-bell"></i> ${service.serviceName}
                                </div>
                                <span class="service-status status-${service.bookingServiceStatus.toLowerCase().replace('_', '-')}">
                                    ${service.bookingServiceStatus}
                                </span>
                            </div>

                            <div class="service-details">
                                <div class="service-detail-item">
                                    <label>Service Type</label>
                                    <span>${service.serviceType}</span>
                                </div>
                                <div class="service-detail-item">
                                    <label>Quantity</label>
                                    <span>${service.quantity}</span>
                                </div>
                                <div class="service-detail-item">
                                    <label>Unit Price</label>
                                    <span>$<fmt:formatNumber value="${service.servicePrice}" pattern="#,##0.00"/></span>
                                </div>
                                <div class="service-detail-item">
                                    <label>Subtotal</label>
                                    <span class="service-price-highlight">
                                        $<fmt:formatNumber value="${service.subPrice}" pattern="#,##0.00"/>
                                    </span>
                                </div>
                                <c:if test="${not empty service.requestTime}">
                                    <div class="service-detail-item">
                                        <label>Requested At</label>
                                        <span>
                                            <c:set var="reqTime" value="${service.requestTime}" />
                                            ${reqTime.dayOfMonth}/${reqTime.monthValue}/${reqTime.year}
                                            ${reqTime.hour}:${String.format('%02d', reqTime.minute)}
                                        </span>
                                    </div>
                                </c:if>
                                <c:if test="${not empty service.completionTime}">
                                    <div class="service-detail-item">
                                        <label>Completed At</label>
                                        <span>
                                            <c:set var="compTime" value="${service.completionTime}" />
                                            ${compTime.dayOfMonth}/${compTime.monthValue}/${compTime.year}
                                            ${compTime.hour}:${String.format('%02d', compTime.minute)}
                                        </span>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <c:set var="totalAmount" value="${totalAmount + service.subPrice}" />
                    </c:forEach>

                    <div class="total-section">
                        <span class="total-label">Total Amount:</span>
                        <span class="total-amount">
                            $<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/>
                        </span>
                    </div>

                    <c:if test="${booking.status != 'CHECK_OUT' && booking.status != 'CANCELLED'}">
                        <div style="margin-top: 20px; text-align: center;">
                            <a href="${pageContext.request.contextPath}/guest/request-services?bookingId=${booking.bookingId}"
                               class="btn-request">
                                <i class="fas fa-plus-circle"></i> Request More Services
                            </a>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Include footer -->
    <jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

