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
            :root {
                --color-deep-navy: #1B4965;
                --color-aqua: #75C3B3;
                --color-background: #F8F9FA;
                --color-sand: #D4B483;
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

            form {
                margin-top: 30px;
                padding: 20px 0;
                border-top: 1px solid #ccc;
                display: flex;
                align-items: center;
                gap: 15px;
                justify-content: flex-end;
            }

            form label {
                font-weight: 600;
                color: var(--color-deep-navy);
            }

            select {
                padding: 10px;
                border: 2px solid var(--color-aqua);
                border-radius: 4px;
                min-width: 200px;
                font-size: 14px;
                cursor: pointer;
                transition: all 0.3s;
                background-color: white;
            }

            select:hover {
                border-color: var(--color-deep-navy);
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            select:focus {
                outline: none;
                border-color: var(--color-deep-navy);
                box-shadow: 0 0 8px rgba(27, 73, 101, 0.3);
            }

            select option {
                padding: 10px;
            }

            select option:first-child {
                color: #999;
            }

            select:valid:not([value=""]) {
                border-color: #28a745;
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
            }

            button[type="submit"]:hover:not(:disabled) {
                background-color: var(--color-aqua);
                color: var(--color-deep-navy);
            }

            button[type="submit"]:disabled {
                background-color: #ccc;
                cursor: not-allowed;
                opacity: 0.6;
            }

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
            <c:if test="${not empty booking}">
                <div class="details-section">
                    <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
                    <p><strong>Guest:</strong> ${booking.guestId}</p>
                    <p><strong>Room:</strong> ${booking.roomId}</p>
                    <p><strong>Status:</strong> ${booking.status}</p>
                    <p><strong>Payment Status:</strong> ${booking.paymentStatus}</p>
                    <p><strong>Check-in:</strong> ${booking.checkInDate}</p>
                    <p><strong>Check-out:</strong> ${booking.checkOutDate}</p>
                    <p><strong>Special Request:</strong> ${booking.specialRequests}</p>
                </div>

                <c:set var="invoice" value="${requestScope.invoice}" />
                <c:if test="${not empty invoice}">
                    <h3 style="color: var(--color-deep-navy); margin-top: 25px;">Invoice Details</h3>
                    <div class="details-section">
                        <p><strong>Invoice ID:</strong> ${invoice.invoiceId}</p>
                        <p><strong>Issue Date:</strong> ${invoice.issueDate}</p>
                        <p><strong>Room Charges:</strong> $${invoice.roomCharges}</p>
                        <p><strong>Service Charges:</strong> $${invoice.serviceCharges}</p>
                        <p><strong>Tax:</strong> $${invoice.taxAmount}</p>
                        <p><strong>Discount:</strong> $${invoice.discount}</p>
                        <p><strong>Total Amount:</strong> <b>$${invoice.finalAmount}</b></p>
                        <p><strong>Status:</strong> ${invoice.status}</p>
                    </div>
                </c:if>

                <c:if test="${not empty invoiceNotFound}">
                    <p class="error-message">No invoice found for this booking.</p>
                </c:if>

                <c:if test="${not empty invoice and invoice.status.name() ne 'PAID'}">
                    <form id="paymentForm" method="post" action="${pageContext.request.contextPath}/receptionist/create-payment" onsubmit="return handlePaymentSubmit(event)">
                        <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                        <input type="hidden" name="guestId" value="${booking.guestId}" />

                        <label for="paymentMethod">Payment Method:</label>
                        <select id="paymentMethod" name="paymentMethod" required>
                            <option value="">-- Select Payment Method --</option>
                            <option value="CASH">Cash</option>
                            <option value="CREDIT_CARD">Credit Card</option>
                            <option value="DEBIT_CARD">Debit Card</option>
                            <option value="ONLINE">Online (QR)</option>
                        </select>

                        <button type="submit" id="submitPaymentBtn">Confirm Payment</button>
                    </form>
                </c:if>

                <c:if test="${not empty invoice and invoice.status.name() eq 'PAID'}">
                    <p style="text-align: center; color: var(--color-aqua); font-weight: bold; margin-top: 20px; font-size: 16px;">
                        ✓ This invoice has already been paid.
                    </p>
                </c:if>

                <%-- Booking Status Change Buttons --%>
                <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #ccc;">
                    <h3 style="color: var(--color-deep-navy); margin-bottom: 15px;">Booking Status Management</h3>


                    <c:if test="${booking.status eq 'CHECK_IN'}">
                        <form method="post" action="${pageContext.request.contextPath}/receptionist/update-booking-status" style="border-top: none; padding: 0; margin: 0;">
                            <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                            <input type="hidden" name="action" value="checkout" />
                            <button type="submit" style="background-color: #dc3545; width: auto;">
                                ✓ Check-out Guest
                            </button>
                        </form>
                    </c:if>

                    <c:if test="${booking.status eq 'CHECK_OUT'}">
                        <p style="color: var(--color-aqua); font-weight: bold; font-size: 16px;">
                            ✓ Guest has already checked out.
                        </p>
                    </c:if>

                    <c:if test="${booking.status eq 'CANCELED'}">
                        <p style="color: var(--color-danger); font-weight: bold; font-size: 16px;">
                            ✗ This booking has been canceled.
                        </p>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${empty booking}">
                <p class="error-message">No booking information found.</p>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            // Add change event listener to payment method select
            document.addEventListener('DOMContentLoaded', function() {
                const paymentMethodSelect = document.getElementById('paymentMethod');
                const submitBtn = document.getElementById('submitPaymentBtn');

                if (paymentMethodSelect) {
                    console.log('Payment method select found:', paymentMethodSelect);

                    // Enable/disable submit button based on selection
                    paymentMethodSelect.addEventListener('change', function() {
                        console.log('Payment method selected:', this.value);
                        if (this.value) {
                            submitBtn.style.opacity = '1';
                            submitBtn.style.cursor = 'pointer';
                        }
                    });
                } else {
                    console.error('Payment method select not found!');
                }
            });

            function handlePaymentSubmit(event) {
                console.log('Form submission triggered');
                const paymentMethod = document.getElementById('paymentMethod').value;
                const submitBtn = document.getElementById('submitPaymentBtn');

                console.log('Selected payment method:', paymentMethod);

                if (!paymentMethod || paymentMethod === '') {
                    event.preventDefault();
                    console.warn('No payment method selected');
                    Swal.fire({
                        icon: 'warning',
                        title: 'Payment Method Required',
                        text: 'Please select a payment method before confirming payment.',
                        confirmButtonColor: '#1B4965'
                    });
                    return false;
                }

                console.log('Payment method valid, processing...');

                // Disable button to prevent double submission
                submitBtn.disabled = true;
                submitBtn.textContent = 'Processing...';

                // Show loading alert
                Swal.fire({
                    title: 'Processing Payment',
                    text: 'Please wait...',
                    allowOutsideClick: false,
                    allowEscapeKey: false,
                    didOpen: () => {
                        Swal.showLoading();
                    }
                });

                return true;
            }
        </script>
        <c:if test="${not empty sessionScope.popupMessage}">
            <script>
                Swal.fire({
                    icon: 'info',
                    title: '${sessionScope.popupMessage}',
                    showConfirmButton: false,
                    timer: 1500
                });
            </script>
            <c:remove var="popupMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.successMessage}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: '${sessionScope.successMessage}',
                    showConfirmButton: false,
                    timer: 1500
                });
            </script>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: '${sessionScope.errorMessage}',
                    confirmButtonColor: '#1B4965'
                });
            </script>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
    </body>
</html>
