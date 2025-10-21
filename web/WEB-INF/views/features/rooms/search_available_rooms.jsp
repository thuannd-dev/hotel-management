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

        .form-group input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
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
    </style>
</head>
<body>
    <div class="search-container">
        <div class="search-form">
            <h2><i class="fas fa-search"></i> Search Available Rooms</h2>

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
                                Found ${rooms.size()} Available Room(s)
                            </c:when>
                            <c:otherwise>
                                Search Results
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

    <script>
        // Ensure check-out date is always after check-in date
        document.getElementById('checkInDate').addEventListener('change', function() {
            const checkInDate = new Date(this.value);
            const checkOutInput = document.getElementById('checkOutDate');
            const checkOutDate = new Date(checkOutInput.value);

            if (checkOutDate <= checkInDate) {
                const nextDay = new Date(checkInDate);
                nextDay.setDate(nextDay.getDate() + 1);
                checkOutInput.value = nextDay.toISOString().split('T')[0];
            }

            checkOutInput.min = this.value;
        });
    </script>
</body>
</html>

