<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Additional Services - Hotel Management</title>
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

        .request-services-container {
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

        .services-form {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .services-form h3 {
            color: #333;
            margin-bottom: 20px;
            font-size: 20px;
        }

        .services-grid {
            display: grid;
            gap: 15px;
            margin-bottom: 25px;
        }

        .service-item {
            display: grid;
            grid-template-columns: 1fr auto 120px;
            align-items: center;
            gap: 15px;
            padding: 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .service-item:hover {
            border-color: #cc8c18;
            background: #fff9f0;
        }

        .service-info {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .service-name {
            font-size: 16px;
            font-weight: 600;
            color: #333;
        }

        .service-type {
            font-size: 13px;
            color: #666;
            background: #f0f0f0;
            padding: 3px 10px;
            border-radius: 12px;
            display: inline-block;
            width: fit-content;
        }

        .service-price {
            font-size: 18px;
            font-weight: 600;
            color: #cc8c18;
            white-space: nowrap;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .quantity-control label {
            font-size: 13px;
            color: #666;
            margin-right: 5px;
        }

        .quantity-input {
            width: 80px;
            padding: 8px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            text-align: center;
            transition: border-color 0.3s ease;
        }

        .quantity-input:focus {
            outline: none;
            border-color: #cc8c18;
        }

        .form-actions {
            display: flex;
            gap: 15px;
            justify-content: flex-end;
            padding-top: 20px;
            border-top: 2px solid #f0f0f0;
        }

        .btn {
            padding: 12px 30px;
            border-radius: 8px;
            font-weight: 500;
            font-size: 15px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary {
            background: #cc8c18;
            color: white;
        }

        .btn-primary:hover {
            background: #b57a15;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
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

        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .alert-warning {
            background: #fff3cd;
            color: #856404;
            border-left: 4px solid #ffc107;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border-left: 4px solid #dc3545;
        }

        .no-services {
            text-align: center;
            padding: 40px;
            color: #666;
        }

        .no-services i {
            font-size: 60px;
            color: #cc8c18;
            opacity: 0.3;
            margin-bottom: 15px;
        }

        @media (max-width: 768px) {
            .request-services-container {
                padding: 15px;
                margin: 20px auto;
            }

            .page-header h1 {
                font-size: 22px;
            }

            .service-item {
                grid-template-columns: 1fr;
                gap: 10px;
            }

            .service-price {
                text-align: left;
            }

            .form-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <jsp:include page="../../layout/header.jsp"/>

    <div class="request-services-container">
        <a href="${pageContext.request.contextPath}/my-booking" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to My Bookings
        </a>

        <div class="page-header">
            <h1><i class="fas fa-concierge-bell"></i> Request Additional Services</h1>
            <p>Select the services you'd like to add to your booking</p>
        </div>

        <!-- Display error messages -->
        <c:if test="${param.error == 'noservice'}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Please select at least one service
            </div>
        </c:if>
        <c:if test="${param.error == 'noquantity'}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Please specify quantity for at least one service
            </div>
        </c:if>

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

        <!-- Services Form -->
        <div class="services-form">
            <h3><i class="fas fa-list"></i> Available Services</h3>

            <c:choose>
                <c:when test="${empty services}">
                    <div class="no-services">
                        <i class="fas fa-concierge-bell"></i>
                        <p>No services are currently available</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/guest/submit-service-request" method="post">
                        <input type="hidden" name="bookingId" value="${booking.bookingId}">

                        <div class="services-grid">
                            <c:forEach var="service" items="${services}">
                                <div class="service-item">
                                    <div class="service-info">
                                        <div class="service-name">
                                            <i class="fas fa-concierge-bell"></i> ${service.serviceName}
                                        </div>
                                        <div class="service-type">${service.serviceType}</div>
                                    </div>
                                    <div class="service-price">
                                        $<fmt:formatNumber value="${service.price}" pattern="#,##0.00"/>
                                    </div>
                                    <div class="quantity-control">
                                        <label>Qty:</label>
                                        <input type="hidden" name="serviceId" value="${service.serviceId}">
                                        <input type="number"
                                               name="quantity"
                                               min="0"
                                               max="99"
                                               value="0"
                                               class="quantity-input"
                                               placeholder="0">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="form-actions">
                            <a href="${pageContext.request.contextPath}/my-booking" class="btn btn-secondary">
                                <i class="fas fa-times"></i> Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-check"></i> Submit Request
                            </button>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Include footer -->
    <jsp:include page="../../layout/footer.jsp"/>

    <script>
        // Auto-highlight service items when quantity is changed
        document.querySelectorAll('.quantity-input').forEach(input => {
            input.addEventListener('input', function() {
                const serviceItem = this.closest('.service-item');
                if (parseInt(this.value) > 0) {
                    serviceItem.style.borderColor = '#cc8c18';
                    serviceItem.style.background = '#fff9f0';
                } else {
                    serviceItem.style.borderColor = '#e0e0e0';
                    serviceItem.style.background = 'white';
                }
            });
        });
    </script>
</body>
</html>

