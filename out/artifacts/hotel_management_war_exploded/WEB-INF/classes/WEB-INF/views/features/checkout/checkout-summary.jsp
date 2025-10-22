<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Summary</title>
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
            max-width: 900px;
            margin: 40px auto;
            padding: 20px;
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

        .summary-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .card-header {
            background: linear-gradient(135deg, #cc8c18 0%, #d4a535 100%);
            color: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;
            text-align: center;
        }

        .card-header h1 {
            font-size: 28px;
            margin-bottom: 5px;
        }

        .card-header p {
            font-size: 14px;
            opacity: 0.95;
        }

        .booking-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 25px;
        }

        .booking-info h3 {
            color: #333;
            font-size: 18px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .info-item {
            display: flex;
            flex-direction: column;
        }

        .info-item label {
            font-size: 12px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 5px;
        }

        .info-item span {
            font-size: 15px;
            color: #333;
            font-weight: 500;
        }

        .charges-section {
            margin-bottom: 25px;
        }

        .charges-section h3 {
            color: #333;
            font-size: 18px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .charge-item {
            display: flex;
            justify-content: space-between;
            padding: 12px 0;
            border-bottom: 1px solid #e9ecef;
        }

        .charge-item:last-child {
            border-bottom: none;
        }

        .charge-label {
            color: #666;
            font-size: 15px;
        }

        .charge-value {
            color: #333;
            font-size: 15px;
            font-weight: 500;
        }

        .subtotal-item {
            padding-top: 15px;
            border-top: 2px solid #dee2e6;
            margin-top: 10px;
        }

        .subtotal-item .charge-label,
        .subtotal-item .charge-value {
            font-weight: 600;
            font-size: 16px;
        }

        .total-item {
            background: linear-gradient(135deg, #cc8c18 0%, #d4a535 100%);
            color: white;
            padding: 15px 20px;
            border-radius: 8px;
            margin-top: 15px;
        }

        .total-item .charge-label,
        .total-item .charge-value {
            color: white;
            font-size: 20px;
            font-weight: 700;
        }

        .payment-section {
            margin-bottom: 25px;
        }

        .payment-section h3 {
            color: #333;
            font-size: 18px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .payment-methods {
            display: grid;
            gap: 12px;
        }

        .payment-method {
            display: flex;
            align-items: center;
            padding: 15px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .payment-method:hover {
            border-color: #cc8c18;
            background: #fff8f0;
        }

        .payment-method input[type="radio"] {
            width: 20px;
            height: 20px;
            margin-right: 12px;
            cursor: pointer;
            accent-color: #cc8c18;
        }

        .payment-method label {
            display: flex;
            align-items: center;
            gap: 10px;
            cursor: pointer;
            flex: 1;
            font-size: 15px;
            font-weight: 500;
            color: #333;
        }

        .payment-method i {
            font-size: 24px;
            color: #cc8c18;
            width: 30px;
        }

        .payment-method input[type="radio"]:checked + label {
            color: #cc8c18;
        }

        .payment-method:has(input[type="radio"]:checked) {
            border-color: #cc8c18;
            background: #fff8f0;
        }

        .action-buttons {
            display: flex;
            gap: 15px;
            margin-top: 25px;
        }

        .btn {
            flex: 1;
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            text-decoration: none;
        }

        .btn-primary {
            background: linear-gradient(135deg, #cc8c18 0%, #d4a535 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
        }

        .btn-primary:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #5a6268;
        }

        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        @media (max-width: 768px) {
            .checkout-container {
                padding: 15px;
                margin: 20px auto;
            }

            .summary-card {
                padding: 20px;
            }

            .card-header h1 {
                font-size: 22px;
            }

            .info-grid {
                grid-template-columns: 1fr;
            }

            .action-buttons {
                flex-direction: column;
            }

            .total-item .charge-label,
            .total-item .charge-value {
                font-size: 18px;
            }
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <jsp:include page="../../layout/header.jsp"/>

    <div class="checkout-container">
        <a href="${pageContext.request.contextPath}/guest/checkout" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to Booking Selection
        </a>

        <div class="summary-card">
            <div class="card-header">
                <h1><i class="fas fa-file-invoice-dollar"></i> Checkout Summary</h1>
                <p>Review your charges and complete payment</p>
            </div>

            <!-- Booking Information -->
            <div class="booking-info">
                <h3><i class="fas fa-info-circle"></i> Booking Information</h3>
                <div class="info-grid">
                    <div class="info-item">
                        <label>Booking ID</label>
                        <span>#${summary.bookingId}</span>
                    </div>
                    <div class="info-item">
                        <label>Guest Name</label>
                        <span>${summary.guestName}</span>
                    </div>
                    <div class="info-item">
                        <label>Room Number</label>
                        <span>${summary.roomNumber}</span>
                    </div>
                    <div class="info-item">
                        <label>Check-in Date</label>
                        <span>${summary.checkInDate.dayOfMonth}/${summary.checkInDate.monthValue}/${summary.checkInDate.year}</span>
                    </div>
                    <div class="info-item">
                        <label>Check-out Date</label>
                        <span>${summary.checkOutDate.dayOfMonth}/${summary.checkOutDate.monthValue}/${summary.checkOutDate.year}</span>
                    </div>
                    <div class="info-item">
                        <label>Number of Nights</label>
                        <span>${summary.numberOfNights} Night(s)</span>
                    </div>
                </div>
            </div>

            <!-- Charges Breakdown -->
            <div class="charges-section">
                <h3><i class="fas fa-receipt"></i> Charges Breakdown</h3>

                <div class="charge-item">
                    <span class="charge-label">Room Charges (${summary.numberOfNights} nights)</span>
                    <span class="charge-value">$<fmt:formatNumber value="${summary.roomCharges}" pattern="#,##0.00"/></span>
                </div>

                <div class="charge-item">
                    <span class="charge-label">Service Charges (Completed Services)</span>
                    <span class="charge-value">$<fmt:formatNumber value="${summary.serviceCharges}" pattern="#,##0.00"/></span>
                </div>

                <div class="charge-item subtotal-item">
                    <span class="charge-label">Subtotal</span>
                    <span class="charge-value">$<fmt:formatNumber value="${summary.subtotal}" pattern="#,##0.00"/></span>
                </div>

                <c:if test="${summary.taxAmount > 0}">
                    <div class="charge-item">
                        <span class="charge-label">${summary.taxName} (<fmt:formatNumber value="${summary.taxRate}" pattern="#,##0.00"/>%)</span>
                        <span class="charge-value">$<fmt:formatNumber value="${summary.taxAmount}" pattern="#,##0.00"/></span>
                    </div>
                </c:if>

                <c:if test="${summary.discount > 0}">
                    <div class="charge-item">
                        <span class="charge-label">Discount</span>
                        <span class="charge-value" style="color: #28a745;">-$<fmt:formatNumber value="${summary.discount}" pattern="#,##0.00"/></span>
                    </div>
                </c:if>

                <div class="charge-item total-item">
                    <span class="charge-label">Total Amount</span>
                    <span class="charge-value">$<fmt:formatNumber value="${summary.finalAmount}" pattern="#,##0.00"/></span>
                </div>
            </div>

            <!-- Payment Method Selection -->
            <form id="checkoutForm" method="post" action="${pageContext.request.contextPath}/guest/checkout">
                <input type="hidden" name="bookingId" value="${summary.bookingId}">

                <div class="payment-section">
                    <h3><i class="fas fa-credit-card"></i> Select Payment Method</h3>

                    <div class="payment-methods">
                        <div class="payment-method">
                            <input type="radio" id="cash" name="paymentMethod" value="Cash" required>
                            <label for="cash">
                                <i class="fas fa-money-bill-wave"></i>
                                <span>Cash</span>
                            </label>
                        </div>

                        <div class="payment-method">
                            <input type="radio" id="creditCard" name="paymentMethod" value="Credit Card" required>
                            <label for="creditCard">
                                <i class="fas fa-credit-card"></i>
                                <span>Credit Card</span>
                            </label>
                        </div>

                        <div class="payment-method">
                            <input type="radio" id="debitCard" name="paymentMethod" value="Debit Card" required>
                            <label for="debitCard">
                                <i class="fas fa-credit-card"></i>
                                <span>Debit Card</span>
                            </label>
                        </div>

                        <div class="payment-method">
                            <input type="radio" id="online" name="paymentMethod" value="Online" required>
                            <label for="online">
                                <i class="fas fa-globe"></i>
                                <span>Online Payment</span>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/guest/checkout" class="btn btn-secondary">
                        <i class="fas fa-times"></i> Cancel
                    </a>
                    <button type="submit" class="btn btn-primary" id="submitBtn">
                        <i class="fas fa-check-circle"></i> Complete Checkout
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Include footer -->
    <jsp:include page="../../layout/footer.jsp"/>

    <script>
        document.getElementById('checkoutForm').addEventListener('submit', function(e) {
            const submitBtn = document.getElementById('submitBtn');
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');

            if (!paymentMethod) {
                e.preventDefault();
                alert('Please select a payment method');
                return false;
            }

            // Disable submit button to prevent double submission
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
        });
    </script>
</body>
</html>

