<%-- 
    Document: receptionist_dashboard_guest_list
    Created on: Oct 19, 2025
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receptionist Dashboard</title>
        <style>
            /* Coastal Elegance Color Palette */
            :root {
                --color-primary: #1B4965; /* Deep Navy - Headers, Buttons */
                --color-secondary: #75C3B3; /* Teal/Aqua - Table Header/Accent */
                --color-background: #F8F9FA; /* Off-White */
                --color-text: #333333;
                --color-accent-sand: #D4B483; /* Sand/Gold */
                --color-danger: #D34E51;
            }
            body {
                font-family: Arial, sans-serif;
                background-color: var(--color-background);
                color: var(--color-text);
                margin: 25px;
            }
            h2 {
                color: var(--color-primary);
                border-bottom: 3px solid var(--color-secondary);
                padding-bottom: 10px;
            }
            /* --- Search Form --- */
            .search-bar {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
                padding: 15px;
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .search-bar input[type="text"] {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                flex-grow: 1;
            }
            .search-bar button {
                background-color: var(--color-primary);
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .search-bar button:hover {
                background-color: #0E2938;
            }
            /* --- Data Table --- */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
                background-color: white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            th, td {
                border: 1px solid #e0e0e0;
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: var(--color-secondary);
                color: white;
                font-weight: bold;
            }
            tr:nth-child(even) {
                background-color: #f7f7f7;
            }
            /* --- Action Button --- */
            .action-link {
                background-color: var(--color-accent-sand);
                color: var(--color-primary);
                text-decoration: none;
                padding: 3px 10px;
                border-radius: 4px;
                font-size: 14px;
                font-weight: bold;
                transition: opacity 0.3s;
            }
            .action-link:hover {
                opacity: 0.8;
            }
            .no-data {
                text-align: center;
                color: var(--color-danger);
                margin-top: 20px;
                font-size: 1.1em;
            }
        </style>
    </head>
    <body>
        <h2>Guest List</h2>

        <form action="find-check-in-booking" method="get" class="search-bar">
            <input type="text" name="guestName" placeholder="Search by Guest Name" value="${param.guestName}">
            <input type="text" name="roomNumber" placeholder="Search by Room Number" value="${param.roomNumber}">
            <button type="submit">Search</button>
        </form>

        <c:set var="guests" value="${requestScope['guests']}" />

        <c:if test="${not empty guests}">
            <table>
                <thead>
                    <tr>
                        <th>Guest ID</th>
                        <th>Full Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>ID Number</th>
                        <th>Date of Birth</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Mapping based on your original columns and headers --%>
                    <c:forEach var="g" items="${guests}">
                        <tr>
                            <td>${g.guestId}</td>
                            <td>${g.fullName}</td>
                            <td>${g.phone}</td>
                            <td>${g.email}</td>
                            <td>${g.address}</td>
                            <td>${g.idNumber}</td>
                            <td>${g.dateOfBirth}</td>
                            <td>
                                <a href="receptionist-dashboard/create-booking?guestId=${g.guestId}" class="action-link">Create booking</a>
                                <a href="receptionist-dashboard/create-payment?guestId=${g.guestId}" class="action-link">Get Payment</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty guests}">
            <p class="no-data">No guests were found.</p>
        </c:if>
        <script>
            // --- LOGIC HIỂN THỊ POPUP TỪ SESSION (Tự động tắt) ---

            // Hàm hiển thị popup tùy chỉnh
            function showSuccessPopup(message) {
                var popupElement = document.getElementById('successPopup');

                if (popupElement) {
                    // Đặt nội dung
                    popupElement.textContent = "Success: " + message;

                    // Hiển thị popup bằng cách thêm class 'show' (dùng opacity transition)
                    popupElement.style.display = 'block';
                    setTimeout(() => {
                        popupElement.classList.add('show');
                    }, 10); // Đảm bảo display:block có hiệu lực trước khi thêm class show

                    // Tự động ẩn popup sau 5000 mili giây (5 giây)
                    setTimeout(function () {
                        popupElement.classList.remove('show');
                        // Ẩn hoàn toàn sau khi transition opacity kết thúc
                        setTimeout(() => {
                            popupElement.style.display = 'none';
                        }, 500); // 500ms là thời gian transition mặc định
                    }, 5000); // <-- Thời gian hiển thị: 5 giây
                } else {
                    // Dự phòng nếu không tìm thấy phần tử
                    alert("Success: " + message);
                }
            }

            // --- KIỂM TRA SESSION VÀ KÍCH HOẠT POPUP ---

            <%
            // Lấy thông báo từ Session. Key: "popupMessage"
            String successMessage = (String) session.getAttribute("popupMessage");

            if (successMessage != null) {
                // Xóa attribute khỏi session
                session.removeAttribute("popupMessage");
            %>
            var message = "<%= successMessage%>";
            showSuccessPopup(message);
            <%
            }
            %>
        </script>
    </body>
</html>