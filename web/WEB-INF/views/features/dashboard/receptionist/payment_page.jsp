<%-- 
    Document   : payment_page
    Created on : Oct 24, 2025, 2:59:31 AM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Payment Gateway</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px;
            }

            .payment-container {
                background: #fff;
                border-radius: 20px;
                padding: 50px;
                box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                max-width: 500px;
                width: 100%;
                text-align: center;
                animation: slideIn 0.5s ease-out;
            }

            @keyframes slideIn {
                from {
                    opacity: 0;
                    transform: translateY(-30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .payment-header {
                margin-bottom: 30px;
            }

            .payment-header i {
                font-size: 50px;
                color: #667eea;
                margin-bottom: 15px;
            }

            h2 {
                color: #1B4965;
                font-size: 28px;
                margin-bottom: 10px;
                font-weight: 600;
            }

            .payment-subtitle {
                color: #666;
                font-size: 14px;
                margin-bottom: 20px;
            }

            .booking-info {
                background: #f8f9fa;
                border-radius: 10px;
                padding: 20px;
                margin: 25px 0;
                border-left: 4px solid #667eea;
            }

            .booking-info p {
                color: #333;
                font-size: 16px;
                margin: 8px 0;
            }

            .booking-info strong {
                color: #1B4965;
                font-weight: 600;
            }

            .qr-container {
                margin: 30px 0;
                padding: 20px;
                background: #f8f9fa;
                border-radius: 15px;
            }

            .qr-label {
                color: #666;
                font-size: 14px;
                margin-bottom: 15px;
                font-weight: 500;
            }

            img.qr {
                width: 220px;
                height: 220px;
                margin: 10px 0;
                border: 4px solid #fff;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            }

            .payment-method-badge {
                display: inline-block;
                background: #667eea;
                color: white;
                padding: 8px 20px;
                border-radius: 20px;
                font-size: 14px;
                font-weight: 600;
                margin: 15px 0;
            }

            button {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: #fff;
                padding: 15px 40px;
                border: none;
                border-radius: 30px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                margin-top: 20px;
                transition: all 0.3s ease;
                box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
                width: 100%;
            }

            button:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
            }

            button:active {
                transform: translateY(0);
            }

            button:disabled {
                background: #ccc;
                cursor: not-allowed;
                box-shadow: none;
            }

            .security-note {
                margin-top: 25px;
                padding: 15px;
                background: #e3f2fd;
                border-radius: 10px;
                font-size: 13px;
                color: #0277bd;
            }

            .security-note i {
                margin-right: 8px;
            }

            .loading {
                display: none;
                margin-top: 15px;
            }

            .loading.active {
                display: block;
            }

            .spinner {
                border: 3px solid #f3f3f3;
                border-top: 3px solid #667eea;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                animation: spin 1s linear infinite;
                margin: 10px auto;
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        </style>
    </head>
    <body>
        <div class="payment-container">
            <div class="payment-header">
                <i class="fas fa-qrcode"></i>
                <h2>Online Payment</h2>
                <p class="payment-subtitle">Scan QR code to complete your payment</p>
            </div>

            <div class="payment-method-badge">
                <i class="fas fa-mobile-alt"></i> QR Code Payment
            </div>

            <div class="booking-info">
                <p><i class="fas fa-receipt"></i> <strong>Booking ID:</strong> ${bookingId}</p>
                <p><i class="fas fa-user"></i> <strong>Guest ID:</strong> ${guestId}</p>
            </div>

            <div class="qr-container">
                <p class="qr-label"><i class="fas fa-camera"></i> Scan with your banking app</p>
                <img src="https://api.qrserver.com/v1/create-qr-code/?size=220x220&data=ONLINE-PAYMENT-BOOKING-${bookingId}-AMOUNT-FINAL" alt="Payment QR Code" class="qr" />
            </div>

            <form id="paymentForm" method="post" action="${pageContext.request.contextPath}/receptionist/payment-page" onsubmit="return handleSubmit(event)">
                <input type="hidden" name="bookingId" value="${bookingId}"/>
                <input type="hidden" name="guestId" value="${guestId}"/>
                <button type="submit" id="confirmBtn">
                    <i class="fas fa-check-circle"></i> Confirm Payment
                </button>
            </form>

            <div class="loading" id="loading">
                <div class="spinner"></div>
                <p style="color: #666; margin-top: 10px;">Processing payment...</p>
            </div>

            <div class="security-note">
                <i class="fas fa-shield-alt"></i>
                <strong>Secure Payment:</strong> This transaction is encrypted and secure
            </div>
        </div>

        <script>
            function handleSubmit(event) {
                const btn = document.getElementById('confirmBtn');
                const loading = document.getElementById('loading');

                // Disable button and show loading
                btn.disabled = true;
                btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
                loading.classList.add('active');

                return true;
            }
        </script>
    </body>
</html>
