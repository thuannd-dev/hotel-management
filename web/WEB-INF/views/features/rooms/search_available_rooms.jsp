<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Available Rooms - Hotel Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .search-container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
        }

        .search-form {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .search-form h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            margin-bottom: 5px;
            font-weight: 600;
            color: #555;
        }

        .form-group input,
        .form-group select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }

        .form-group select {
            cursor: pointer;
            background-color: white;
        }

        .form-group select:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 2px rgba(0,123,255,0.1);
        }

        .search-btn {
            background: #007bff;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .search-btn:hover {
            background: #0056b3;
        }

        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }

        .results-section {
            margin-top: 30px;
        }

        .results-header {
            margin-bottom: 20px;
        }

        .results-header h3 {
            color: #333;
        }

        .rooms-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }

        .room-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 20px;
            transition: transform 0.3s ease;
        }

        .room-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }

        .room-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .room-number {
            font-size: 24px;
            font-weight: bold;
            color: #007bff;
        }

        .room-status {
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            background: #28a745;
            color: white;
        }

        .room-info {
            margin-bottom: 10px;
        }

        .room-info-item {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 10px;
            color: #666;
        }

        .room-info-item i {
            color: #007bff;
            width: 20px;
        }

        .room-price {
            font-size: 28px;
            font-weight: bold;
            color: #28a745;
            margin-top: 15px;
            text-align: right;
        }

        .room-price span {
            font-size: 14px;
            color: #666;
            font-weight: normal;
        }

        .no-results {
            text-align: center;
            padding: 40px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .no-results i {
            font-size: 48px;
            color: #ccc;
            margin-bottom: 20px;
        }

        .no-results p {
            color: #666;
            font-size: 18px;
        }

        .book-now-btn {
            width: 100%;
            background: #28a745;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            margin-top: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            transition: background 0.3s ease;
        }

        .book-now-btn:hover {
            background: #218838;
        }

        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.6);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 30px rgba(0,0,0,0.3);
            width: 90%;
            max-width: 600px;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .modal-header h2 {
            margin: 0;
            color: #333;
        }

        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.3s;
        }

        .close:hover,
        .close:focus {
            color: #000;
        }

        .booking-summary {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .booking-summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            color: #555;
        }

        .booking-summary-item strong {
            color: #333;
        }

        .booking-form .form-group {
            margin-bottom: 15px;
        }

        .booking-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #555;
        }

        .booking-form input,
        .booking-form select,
        .booking-form textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .booking-form textarea {
            resize: vertical;
            min-height: 80px;
        }

        .modal-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .modal-actions button {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .btn-cancel {
            background: #6c757d;
            color: white;
        }

        .btn-cancel:hover {
            background: #5a6268;
        }

        .btn-confirm {
            background: #28a745;
            color: white;
        }

        .btn-confirm:hover {
            background: #218838;
        }
    </style>
</head>
<body>
    <div class="search-container">
        <div class="search-form">
            <h2><i class="fas fa-search"></i> Search Available Rooms</h2>

            <c:if test="${param.bookingSuccess == 'true'}">
                <div class="success-message" style="background: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #c3e6cb;">
                    <i class="fas fa-check-circle"></i>
                    Booking created successfully! Your booking ID is: <strong>${param.bookingId}</strong>
                </div>
            </c:if>

            <c:if test="${not empty infoMessage}">
                <div class="info-message" style="background: #d1ecf1; color: #0c5460; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #bee5eb;">
                    <i class="fas fa-info-circle"></i> ${infoMessage}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/available-rooms" method="get">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="checkInDate">Check-in Date</label>
                        <input type="date" id="checkInDate" name="checkInDate"
                               value="${checkInDate}" required
                               min="${java.time.LocalDate.now()}">
                    </div>

                    <div class="form-group">
                        <label for="checkOutDate">Check-out Date</label>
                        <input type="date" id="checkOutDate" name="checkOutDate"
                               value="${checkOutDate}" required
                               min="${java.time.LocalDate.now()}">
                    </div>

                    <div class="form-group">
                        <label for="roomType"><i class="fas fa-bed"></i> Room Type</label>
                        <select id="roomType" name="roomType">
                            <option value="">All Room Types</option>
                            <option value="Single" ${roomType == 'Single' ? 'selected' : ''}>Single Room (1 guest)</option>
                            <option value="Double" ${roomType == 'Double' ? 'selected' : ''}>Double Room (2 guests)</option>
                            <option value="Deluxe" ${roomType == 'Deluxe' ? 'selected' : ''}>Deluxe Room (2 guests)</option>
                            <option value="Suite" ${roomType == 'Suite' ? 'selected' : ''}>Suite Room (4 guests)</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="adults">Adults</label>
                        <input type="number" id="adults" name="adults"
                               value="${adults != null ? adults : 1}"
                               min="0" max="10" required>
                    </div>

                    <div class="form-group">
                        <label for="children">Children</label>
                        <input type="number" id="children" name="children"
                               value="${children != null ? children : 0}"
                               min="0" max="10">
                    </div>
                </div>

                <button type="submit" class="search-btn">
                    <i class="fas fa-search"></i>
                    Search Available Rooms
                </button>
            </form>
        </div>

        <c:if test="${not empty rooms || not empty checkInDate}">
            <div class="results-section">
                <div class="results-header">
                    <h3>
                        <c:choose>
                            <c:when test="${not empty rooms}">
                                Found ${rooms.size()} Available
                                <c:if test="${not empty roomType}">
                                    ${roomType}
                                </c:if>
                                Room(s)
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${not empty roomType}">
                                        No ${roomType} rooms available for the selected dates
                                    </c:when>
                                    <c:otherwise>
                                        No rooms available for the selected dates
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </h3>
                </div>

                <c:choose>
                    <c:when test="${not empty rooms}">
                        <div class="rooms-grid">
                            <c:forEach var="room" items="${rooms}">
                                <div class="room-card">
                                    <div class="room-header">
                                        <div class="room-number">
                                            <i class="fas fa-door-open"></i> ${room.roomNumber}
                                        </div>
                                        <div class="room-status">${room.status}</div>
                                    </div>

                                    <div class="room-info">
                                        <div class="room-info-item">
                                            <i class="fas fa-bed"></i>
                                            <span>${room.typeName}</span>
                                        </div>

                                        <div class="room-info-item">
                                            <i class="fas fa-users"></i>
                                            <span>Capacity: ${room.capacity} guests</span>
                                        </div>
                                    </div>

                                    <div class="room-price">
                                        <fmt:formatNumber value="${room.pricePerNight}"
                                                         type="currency"
                                                         currencySymbol="$"
                                                         maxFractionDigits="2"/>
                                        <span>/night</span>
                                    </div>

                                    <button class="book-now-btn"
                                            onclick="bookRoom(${room.roomId}, '${room.roomNumber}', ${room.pricePerNight})">
                                        <i class="fas fa-calendar-check"></i>
                                        Book Now
                                    </button>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-results">
                            <i class="fas fa-bed"></i>
                            <p>No available rooms found for the selected dates and guest count.</p>
                            <p style="margin-top: 10px; color: #999; font-size: 14px;">
                                Try adjusting your search criteria or selecting different dates.
                            </p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <!-- Booking Modal -->
    <div id="bookingModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2><i class="fas fa-calendar-check"></i> Book Room</h2>
                <span class="close" onclick="closeBookingModal()">&times;</span>
            </div>

            <div class="booking-summary">
                <div class="booking-summary-item">
                    <span><i class="fas fa-door-open"></i> Room Number:</span>
                    <strong id="summaryRoomNumber">-</strong>
                </div>
                <div class="booking-summary-item">
                    <span><i class="fas fa-calendar-alt"></i> Check-in:</span>
                    <strong id="summaryCheckIn">-</strong>
                </div>
                <div class="booking-summary-item">
                    <span><i class="fas fa-calendar-alt"></i> Check-out:</span>
                    <strong id="summaryCheckOut">-</strong>
                </div>
                <div class="booking-summary-item">
                    <span><i class="fas fa-moon"></i> Number of Nights:</span>
                    <strong id="summaryNights">-</strong>
                </div>
                <div class="booking-summary-item">
                    <span><i class="fas fa-users"></i> Total Guests:</span>
                    <strong id="summaryGuests">-</strong>
                </div>
                <div class="booking-summary-item" style="font-size: 18px; color: #28a745; margin-top: 10px; padding-top: 10px; border-top: 2px solid #ddd;">
                    <span><strong>Total Price:</strong></span>
                    <strong id="summaryTotalPrice">$0.00</strong>
                </div>
            </div>

            <form id="bookingForm" class="booking-form" method="post" action="${pageContext.request.contextPath}/guest/create-booking">
                <input type="hidden" id="bookingRoomId" name="roomId" value="">
                <input type="hidden" id="bookingCheckInDate" name="checkInDate" value="${checkInDate}">
                <input type="hidden" id="bookingCheckOutDate" name="checkOutDate" value="${checkOutDate}">
                <input type="hidden" id="bookingTotalGuest" name="totalGuest" value="">

                <%-- Get guestId from session --%>
                <c:set var="currentUser" value="${sessionScope.currentUser}" />
                <c:set var="guestIdFromSession" value="${currentUser.guestId}" />

                <input type="hidden" id="guestId" name="guestId" value="${guestIdFromSession}">

                <c:if test="${not empty currentUser and not empty guestIdFromSession}">
                    <div class="form-group">
                        <label><i class="fas fa-user"></i> Guest Information</label>
                        <div style="background: #f8f9fa; padding: 10px; border-radius: 5px; color: #555;">
                            <strong>${currentUser.fullName}</strong><br>
                            <small>Guest ID: ${guestIdFromSession}</small>
                        </div>
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="specialRequests"><i class="fas fa-comment"></i> Special Requests</label>
                    <textarea id="specialRequests" name="specialRequests"
                              placeholder="Any special requirements or requests..."></textarea>
                </div>

                <div class="modal-actions">
                    <button type="button" class="btn-cancel" onclick="closeBookingModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="btn-confirm">
                        <i class="fas fa-check"></i> Confirm Booking
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Validate check-in date on page load and set minimum date
        window.addEventListener('DOMContentLoaded', function() {
            const today = new Date().toISOString().split('T')[0];
            const checkInInput = document.getElementById('checkInDate');
            const checkOutInput = document.getElementById('checkOutDate');

            // Set minimum dates to today
            checkInInput.min = today;
            checkOutInput.min = today;
        });

        // Ensure check-out date is always after check-in date
        document.getElementById('checkInDate').addEventListener('change', function() {
            const today = new Date();
            today.setHours(0, 0, 0, 0);

            const checkInDate = new Date(this.value);
            checkInDate.setHours(0, 0, 0, 0);

            // Validate check-in is not in the past
            if (checkInDate < today) {
                alert('Check-in date cannot be in the past. Please select today or a future date.');
                this.value = new Date().toISOString().split('T')[0];
                return;
            }

            const checkOutInput = document.getElementById('checkOutDate');
            const checkOutDate = new Date(checkOutInput.value);

            if (checkOutDate <= checkInDate) {
                const nextDay = new Date(checkInDate);
                nextDay.setDate(nextDay.getDate() + 1);
                checkOutInput.value = nextDay.toISOString().split('T')[0];
            }

            checkOutInput.min = this.value;
        });

        // Validate check-out date
        document.getElementById('checkOutDate').addEventListener('change', function() {
            const today = new Date();
            today.setHours(0, 0, 0, 0);

            const checkOutDate = new Date(this.value);
            checkOutDate.setHours(0, 0, 0, 0);

            const checkInDate = new Date(document.getElementById('checkInDate').value);
            checkInDate.setHours(0, 0, 0, 0);

            // Validate check-out is not in the past
            if (checkOutDate < today) {
                alert('Check-out date cannot be in the past.');
                this.value = new Date().toISOString().split('T')[0];
                return;
            }

            // Validate check-out is after check-in
            if (checkOutDate <= checkInDate) {
                alert('Check-out date must be after check-in date.');
                const nextDay = new Date(checkInDate);
                nextDay.setDate(nextDay.getDate() + 1);
                this.value = nextDay.toISOString().split('T')[0];
            }
        });

        // Booking Modal Functions
        function bookRoom(roomId, roomNumber, pricePerNight) {
            const checkInDate = document.getElementById('checkInDate').value;
            const checkOutDate = document.getElementById('checkOutDate').value;
            const adults = parseInt(document.getElementById('adults').value) || 0;
            const children = parseInt(document.getElementById('children').value) || 0;

            if (!checkInDate || !checkOutDate) {
                alert('Please select check-in and check-out dates first.');
                return;
            }

            // Calculate number of nights
            const checkIn = new Date(checkInDate);
            const checkOut = new Date(checkOutDate);
            const today = new Date();
            today.setHours(0, 0, 0, 0); // Reset time to midnight for accurate comparison

            checkIn.setHours(0, 0, 0, 0);
            checkOut.setHours(0, 0, 0, 0);

            // Validate check-in date is not in the past
            if (checkIn < today) {
                alert('Check-in date cannot be in the past. Please select today or a future date.');
                return;
            }

            // Validate check-out date is not in the past
            if (checkOut < today) {
                alert('Check-out date cannot be in the past. Please select today or a future date.');
                return;
            }

            const nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24));

            if (nights <= 0) {
                alert('Check-out date must be after check-in date.');
                return;
            }

            // Calculate total price
            const totalPrice = pricePerNight * nights;
            const totalGuests = adults + children;

            // Update modal with booking details
            document.getElementById('summaryRoomNumber').textContent = roomNumber;
            document.getElementById('summaryCheckIn').textContent = formatDate(checkInDate);
            document.getElementById('summaryCheckOut').textContent = formatDate(checkOutDate);
            document.getElementById('summaryNights').textContent = nights + (nights === 1 ? ' night' : ' nights');
            document.getElementById('summaryGuests').textContent = totalGuests + ' guest' + (totalGuests !== 1 ? 's' : '');
            document.getElementById('summaryTotalPrice').textContent = '$' + totalPrice.toFixed(2);

            // Set form hidden fields
            document.getElementById('bookingRoomId').value = roomId;
            document.getElementById('bookingCheckInDate').value = checkInDate;
            document.getElementById('bookingCheckOutDate').value = checkOutDate;
            document.getElementById('bookingTotalGuest').value = totalGuests;

            // Show modal
            document.getElementById('bookingModal').style.display = 'block';
        }

        function closeBookingModal() {
            document.getElementById('bookingModal').style.display = 'none';
            document.getElementById('bookingForm').reset();
        }

        function formatDate(dateString) {
            const date = new Date(dateString);
            const options = { year: 'numeric', month: 'short', day: 'numeric' };
            return date.toLocaleDateString('en-US', options);
        }

        // Close modal when clicking outside of it
        window.onclick = function(event) {
            const modal = document.getElementById('bookingModal');
            if (event.target === modal) {
                closeBookingModal();
            }
        }

        // Handle form submission
        document.getElementById('bookingForm').addEventListener('submit', function(e) {
            const guestId = document.getElementById('guestId').value;

            const guestIdNum = parseInt(guestId, 10);
            if (!guestId || isNaN(guestIdNum) || guestIdNum <= 0) {
                e.preventDefault();
                // Redirect to login page
                window.location.href = '${pageContext.request.contextPath}/login';
                return false;
            }
        });
    </script>
</body>
</html>

