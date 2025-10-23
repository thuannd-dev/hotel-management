<%-- 
    Document: payment_invoice
    Theme: Coastal Elegance (Design Only)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Invoice - Payment</title>
        <style>
            /* Coastal Elegance Color Palette */
            :root {
                --color-deep-navy: #1B4965; /* Primary: Headers, Buttons */
                --color-aqua: #75C3B3;      /* Secondary: Accent, Border */
                --color-background: #F8F9FA; /* Off-White Background */
                --color-sand: #D4B483;      /* Highlight/Error */
                --color-white: #FFFFFF;
                --color-text-dark: #333333;
            }

            body {
                font-family: 'Arial', sans-serif;
                background-color: var(--color-background);
                color: var(--color-text-dark);
                margin: 0;
                padding: 40px 20px;
            }

            .invoice-container {
                max-width: 600px;
                margin: auto;
                background: var(--color-white);
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                border: 1px solid var(--color-aqua);
            }

            h2 {
                color: var(--color-deep-navy);
                text-align: center;
                border-bottom: 3px solid var(--color-aqua);
                padding-bottom: 10px;
                margin-bottom: 25px;
            }

            /* --- Thông tin Booking/Chi tiết --- */
            .details-section p {
                padding: 8px 0;
                margin: 0;
                border-bottom: 1px dotted #eee;
            }
            .details-section strong {
                color: var(--color-deep-navy);
                display: inline-block;
                min-width: 150px;
                font-weight: 600;
            }

            /* --- Form Thanh toán --- */
            form {
                margin-top: 30px;
                padding: 20px 0;
                border-top: 1px solid #ccc;
                display: flex;
                align-items: center;
                gap: 15px;
                justify-content: flex-end; /* Căn phải */
            }

            label {
                font-weight: bold;
                color: var(--color-text-dark);
            }

            select {
                padding: 10px;
                border: 1px solid var(--color-aqua);
                border-radius: 4px;
                min-width: 150px;
            }

            button[type="submit"] {
                background-color: var(--color-deep-navy);
                color: var(--color-white);
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s, color 0.3s;
                margin-left: 15px; /* Khoảng cách sau select */
            }

            button[type="submit"]:hover {
                background-color: var(--color-aqua);
                color: var(--color-deep-navy);
            }

            /* --- Thông báo Lỗi/Không tìm thấy --- */
            .error-message {
                text-align: center;
                padding: 15px;
                color: var(--color-deep-navy);
                background-color: var(--color-sand);
                border-radius: 5px;
                margin-top: 20px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="invoice-container">
            <h2>Payment Invoice</h2>

            <c:set var="booking" value="${requestScope.booking}" />
            <%--@elvariable id="booking" type="com.hotel_management.domain.dto.booking.BookingDetailViewModel"--%>

            <c:if test="${not empty booking}">
                <div class="details-section">
                    <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
                    <p><strong>Guest:</strong> ${booking.guestFullName}</p>
                    <p><strong>Room:</strong> ${booking.roomNumber}</p>
                    <p><strong>Status:</strong> ${booking.status}</p>
                    <p><strong>Payment Status:</strong> ${booking.paymentStatus}</p>
                    <p><strong>Check-in:</strong> ${booking.checkInDate}</p>
                    <p><strong>Check-out:</strong> ${booking.checkOutDate}</p>
                    <p><strong>Special Request:</strong> ${booking.specialRequests}</p>
                </div>

                <form action="${pageContext.request.contextPath}/receptionist-dashboard/create-payment" method="post">
                    <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                    <label for="paymentMethod">Payment Method:</label>
                    <select name="paymentMethod" id="paymentMethod">
                        <option value="CASH">Cash</option>
                        <option value="CREDIT_CARD">Credit Card</option>
                        <option value="BANK_TRANSFER">Bank Transfer</option>
                    </select>
                    <button type="submit">Confirm Payment</button>
                </form>
            </c:if>
            <c:if test="${not empty sessionScope.popupMessage}">
                <script>
                    document.querySelector('form').addEventListener('submit', function (e) {
                    e.preventDefault();
                            fetch(this.action, {
                            method: 'POST',
                                    body: new FormData(this)
                            })
                            .then(res => {
                            if (res.ok) {
                            Swal.fire({
                            icon: 'success',
                                    title: 'Payment Successful!',
                                    showConfirmButton: false,
                                    timer: 1500
                            });
                            } else {
                            Swal.fire({
                            icon: 'error',
                                    title: 'Payment Failed'
                            });
                            }
                            })
                            .catch(error => {
                            Swal.fire({
                            icon: 'error',
                                    title: 'Network Error',
                                    text: 'Unable to process payment. Please check your connection and try again.'
                            });
                            });
                </script>

                <c:remove var="popupMessage" scope="session"/>
            </c:if>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


            <c:if test="${empty booking}">
                <p class="error-message">No booking information found.</p>
            </c:if>
        </div>
    </body>
</html>