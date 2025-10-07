<%@ page import="com.hotel_management.presentation.constants.SessionAttribute" %>
<%@ page import="com.hotel_management.presentation.dto.staff.StaffViewModel" %>
<%@ page import="com.hotel_management.presentation.dto.guest.GuestViewModel" %><%--
    Document   : navbar
    Created on : Oct 5, 2025, 12:55:10 PM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
    <div class="container">
        <div class="d-flex align-items-center justify-content-between w-100">

            <div class="d-none d-lg-flex gap-4">
                <a class="nav-link" href="home#about">About Us</a>
                <a class="nav-link" href="rooms">Rooms</a>
                <a class="nav-link" href="home#testimonials">Feedback</a>
                <a class="nav-link" href="home#contact">Contact</a>
            </div>

            <a class="navbar-brand d-flex align-items-center" href="home">
                <div class="logo-icon bg-warning d-flex align-items-center justify-content-center rounded me-2">
                    <span class="text-white fw-bold">H</span>
                </div>
                <div>
                    <div class="fw-semibold" style="font-size: 0.9rem; line-height: 1;">HOTEL</div>
                    <div class="text-muted" style="font-size: 0.7rem; line-height: 1;">MISUKA</div>
                </div>
            </a>

            <div class="d-none d-lg-flex align-items-center gap-4">
                <a href="tel:+250962712101" class="info-link">
                    <i class="bi bi-telephone me-2"></i>
                    <span>+250 962 712 101</span>
                </a>
                <%
                    HttpSession session = request.getSession(false);
                    StaffViewModel staff = null;
                    GuestViewModel guest = null;
                    // Lấy đối tượng từ session
                    if(session != null) {
                        Object currentUser = session.getAttribute("currentUser");
                        if(currentUser instanceof StaffViewModel) {
                            staff = (StaffViewModel) currentUser;
                        }else if (currentUser instanceof GuestViewModel) {
                            guest = (GuestViewModel) currentUser;
                        }
                    }
                    if (staff != null || guest != null) {
                %>
                <div class="nav-item dropdown">
                    <a class="info-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill me-2"></i>
                        <%= staff != null ? staff.getFullName() : guest.getFullName() %>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">My Profile</a></li>
                        <li><a class="dropdown-item" href="#">Logout</a></li>
                    </ul>
                </div>
                <%
                } else {
                %>
                <a href="${pageContext.request.contextPath}/login" class="info-link">
                    <i class="bi bi-person-circle me-2"></i>
                    <span>Login</span>
                </a>
                <%
                    }
                %>
            </div>

            <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse" data-bs-target="#mobileNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
</nav>