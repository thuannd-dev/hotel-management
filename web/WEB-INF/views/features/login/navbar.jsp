<%--
    Document   : login
    Created on : Oct 5, 2025, 10:40:06 AM
    Author     : TR_NGHIA
--%>

<%@page import="edu.hotel_management.presentation.constants.IConstant"%>
<%@page import="edu.hotel_management.presentation.dto.guest.GuestPresentationModel"%>
<%@page import="edu.hotel_management.presentation.dto.staff.StaffPresentationModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
    <div class="container">
        <div class="d-flex align-items-center justify-content-between w-100">
            
            <div class="d-none d-lg-flex gap-4">
                <a class="nav-link" href="#about">About Us</a>
                <a class="nav-link" href="#rooms">Rooms</a>
                <a class="nav-link" href="#testimonials">Feedback</a>
                <a class="nav-link" href="#contact">Contact</a>
            </div>

            <a class="navbar-brand d-flex align-items-center" href="#about">
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
                    <span>+84 906 123 279</span>
                </a>

                <%-- =========== LOGIC SCRIPTLET HIỂN THỊ ĐĂNG NHẬP HOẶC TÊN USER =========== --%>
                <%
                    Object user = session.getAttribute("USER");

                    if (user != null) {
                        String fullName = "";
                        if (user instanceof StaffPresentationModel) {
                            fullName = ((StaffPresentationModel) user).getFullName();
                        } else if (user instanceof GuestPresentationModel) {
                            fullName = ((GuestPresentationModel) user).getFullName();
                        }
                %>
                        <div class="nav-item dropdown">
                            <a class="info-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-person-fill me-2"></i>
                                <%= fullName %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="<%= request.getContextPath() %>">My Profile</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<%= IConstant.ACTION_LOGOUT %>">Logout</a></li>
                            </ul>
                        </div>
                <%
                    } else {
                %>
                <a href="<%= IConstant.ACTION_LOGIN %>" class="info-link" data-bs-toggle="modal" data-bs-target="#loginModal">
                            <i class="bi bi-person-circle me-2"></i>
                            <span>Login</span>
                        </a>
                <%
                    }
                %>
                <%-- ============================ KẾT THÚC LOGIC SCRIPTLET ============================ --%>
            </div>
            
            <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse" data-bs-target="#mobileNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
</nav>