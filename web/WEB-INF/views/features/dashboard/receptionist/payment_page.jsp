<%-- 
    Document   : payment_page
    Created on : Oct 24, 2025, 2:59:31 AM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment Gateway</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f8f9fa;
                text-align: center;
                padding: 50px;
            }

            .payment-container {
                background: #fff;
                border-radius: 10px;
                padding: 40px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                display: inline-block;
            }

            h2 {
                color: #1B4965;
            }

            img.qr {
                width: 200px;
                height: 200px;
                margin: 20px 0;
            }

            button {
                background-color: #1B4965;
                color: #fff;
                padding: 12px 25px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
            }

            button:hover {
                background-color: #75C3B3;
                color: #1B4965;
            }
        </style>
    </head>
    <body>
        <div class="payment-container">
            <h2>Scan QR to Pay</h2>
            <p>Booking ID: <strong>${bookingId}</strong></p>
            <img src="https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=Booking-${bookingId}" alt="Fake QR Code" class="qr" />
            <form method="post" action="${pageContext.request.contextPath}/payment-page">
                <input type="hidden" name="bookingId" value="${bookingId}" />
                <button type="submit">Confirm Payment</button>
            </form>
        </div>
    </body>
</html>
