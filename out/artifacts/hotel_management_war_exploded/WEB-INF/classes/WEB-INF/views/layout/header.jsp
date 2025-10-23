<!--Header of welome page-->
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hotel_management.domain.dto.guest.GuestViewModel" %>
<%@ page import="com.hotel_management.domain.dto.staff.StaffViewModel" %>

<%
    // Get the current session if it exists (do not create a new one)
    HttpSession userSession = request.getSession(false);
    Object currentUser = null;
    String fullName = null;

    if (userSession != null) {
        currentUser = userSession.getAttribute("currentUser");
        if (currentUser instanceof GuestViewModel) {
            fullName = ((GuestViewModel) currentUser).getFullName();
        } else if (currentUser instanceof StaffViewModel) {
            fullName = ((StaffViewModel) currentUser).getFullName();
        }
    }
%>

<style>
  /* User menu dropdown styling */
  .user-menu {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    position: relative;
    z-index: 10;
  }

  .user-dropdown {
    position: relative;
  }

  .user-btn {
    background: #fff;
    color: #cc8c18;
    border: 2px solid #cc8c18;
    border-radius: 25px;
    padding: 8px 20px;
    font-size: 14px;
    font-weight: 500;
    display: flex;
    align-items: center;
    cursor: pointer;
    transition: all 0.3s ease;
    gap: 8px;
    font-family: "Poppins", sans-serif;
  }

  .user-btn:hover {
    background: #cc8c18;
    color: #fff;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(204, 140, 24, 0.3);
  }

  .user-icon {
    font-size: 20px;
  }

  .user-fullname {
    margin: 0 4px;
    font-weight: 600;
    font-size: 14px;
  }

  .caret-icon {
    font-size: 12px;
    transition: transform 0.3s ease;
  }

  .user-dropdown:hover .caret-icon {
    transform: rotate(180deg);
  }

  .dropdown-content {
    display: none;
    position: absolute;
    right: 0;
    top: 102%;
    background: #fff;
    min-width: 180px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
    border-radius: 12px;
    overflow: hidden;
    z-index: 100;
    opacity: 0;
    transform: translateY(-10px);
    transition: opacity 0.3s ease, transform 0.3s ease;
    border: 1px solid #f0f0f0;
  }

  .user-dropdown:hover .dropdown-content {
    display: flex;
    flex-direction: column;
    opacity: 1;
    transform: translateY(0);
  }

  .dropdown-content a {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 10px 16px;
    color: #333;
    font-size: 14px;
    background: none;
    text-decoration: none;
    transition: all 0.2s ease;
    border-bottom: 1px solid #f5f5f5;
    line-height: 1.5;
  }

  .dropdown-content a:last-child {
    border-bottom: none;
  }

  .dropdown-content a:hover {
    background: #faf3e7;
    color: #cc8c18;
    padding-left: 22px;
  }

  .profile-icon, .logout-icon {
    font-size: 15px;
    width: 18px;
    text-align: center;
  }

  .logout-icon {
    color: #d9534f;
  }

  .dropdown-content a:hover .logout-icon {
    color: #c9302c;
  }

  /* Mobile responsive styles */
  @media only screen and (max-width: 768px) {
    .user-menu {
      width: 100%;
      justify-content: center;
    }

    .user-btn {
      padding: 6px 16px;
      font-size: 13px;
      border-radius: 20px;
    }

    .user-fullname {
      font-size: 13px;
        /* Display full name, do not truncate */
    }

    .user-icon {
      font-size: 18px;
    }

    .caret-icon {
      font-size: 11px;
    }

    .dropdown-content {
      min-width: 160px;
      right: auto;
      left: 50%;
      transform: translateX(-50%) translateY(-10px);
    }

    .user-dropdown:hover .dropdown-content {
      transform: translateX(-50%) translateY(0);
    }

    .dropdown-content a {
      padding: 12px 16px;
      font-size: 13px;
    }

    .dropdown-content a:hover {
      padding-left: 20px;
    }

    .auth-buttons {
      width: 100%;
      justify-content: center;
      gap: 10px;
    }

    .auth-buttons .auth-btn {
      padding: 6px 14px !important;
      font-size: 13px !important;
      border-radius: 16px !important;
    }
  }

  @media only screen and (max-width: 512px) {
    .user-btn {
      padding: 5px 12px;
      font-size: 12px;
      gap: 6px;
    }

    .user-fullname {
      font-size: 12px;
      /* Hiển thị full tên, không cắt */
    }

    .user-icon {
      font-size: 16px;
    }

    .caret-icon {
      font-size: 10px;
    }

    .dropdown-content {
      min-width: 140px;
      border-radius: 10px;
    }

    .dropdown-content a {
      padding: 10px 14px;
      font-size: 12px;
      gap: 8px;
    }

    .dropdown-content a:hover {
      padding-left: 18px;
    }

    .profile-icon, .logout-icon {
      font-size: 14px;
      width: 18px;
    }

    .auth-buttons {
      gap: 8px;
      flex-wrap: wrap;
    }

    .auth-buttons .auth-btn {
      padding: 5px 12px !important;
      font-size: 12px !important;
      border-radius: 14px !important;
    }
  }

  /* Touch devices - click to open dropdown */
  @media (hover: none) and (pointer: coarse) {
    .user-dropdown {
      cursor: pointer;
    }

    .user-btn:active {
      background: #cc8c18;
      color: #fff;
    }

    .dropdown-content {
      display: none;
    }

    .user-dropdown.active .dropdown-content {
      display: flex;
      flex-direction: column;
      opacity: 1;
      transform: translateX(-50%) translateY(0);
    }

    .user-dropdown.active .caret-icon {
      transform: rotate(180deg);
    }
  }
</style>

<section class="head">
  <div class="container flex1">
    <div class="address">
      <i class="fas fa-map-marker-alt"></i>
      <span>7 Đ. D1, Long Thạnh Mỹ, Thủ Đức</span>
    </div>
    <div class="logo">
      <img
        src="${pageContext.request.contextPath}/public/image/logo.png"
        alt=""
      />
    </div>

    <% if (currentUser != null && fullName != null) { %>
        <div class="user-menu">
          <div class="user-dropdown">
            <button class="user-btn">
              <i class="user-icon fas fa-user-circle"></i>
              <span class="user-fullname"><%= fullName %></span>
              <i class="caret-icon fas fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
              <a href="${pageContext.request.contextPath}/guest/my-booking">
                <i class="profile-icon fas fa-user"></i> My Booking
              </a>
              <a href="${pageContext.request.contextPath}/guest/checkout">
                <i class="profile-icon fas fa-user"></i> Check Out
              </a>
              <a href="${pageContext.request.contextPath}/logout">
                <i class="logout-icon fas fa-sign-out-alt"></i> Logout
              </a>
            </div>
          </div>
        </div>
    <% } else { %>
        <div class="auth-buttons">
          <a href="${pageContext.request.contextPath}/login" id="loginBtn" class="auth-btn">Sign in</a>
          <a href="${pageContext.request.contextPath}/register" id="registerBtn" class="auth-btn">Sign up</a>
        </div>
    <% } %>
  </div>
</section>

<header class="header">
  <div class="container">
    <nav class="navbar flex1">
      <div class="sticky_logo logo">
        <img
          src="${pageContext.request.contextPath}/public/image/logo.png"
          alt=""
        />
      </div>

      <ul class="nav-menu">
        <li><a href="#home">Home</a></li>
        <li><a href="#about">about</a></li>
        <li><a href="#room">room</a></li>
        <li><a href="#services">services</a></li>
        <li><a href="#testimonials">testimonials</a></li>
        <li><a href="#gallary">gallary</a></li>
        <li><a href="#blog">blog</a></li>
        <li><a href="#contact">contact</a></li>
      </ul>
      <div class="hamburger">
        <span class="bar"></span>
        <span class="bar"></span>
        <span class="bar"></span>
      </div>

      <div class="head_contact">
        <i class="fas fa-phone-volume"></i>
        <span>+000 1234 5678</span>
      </div>
    </nav>
  </div>
</header>

<script>
  const hamburger = document.querySelector(".hamburger");
  const navMenu = document.querySelector(".nav-menu");

  hamburger.addEventListener("click", mobliemmenu);

  function mobliemmenu() {
    hamburger.classList.toggle("active");
    navMenu.classList.toggle("active");
  }

  window.addEventListener("scroll", function () {
    var header = document.querySelector("header");
    header.classList.toggle("sticky", window.scrollY > 0);
  });

  // Handle dropdown for touch devices
  document.addEventListener('DOMContentLoaded', function() {
    const userDropdown = document.querySelector('.user-dropdown');
    const userBtn = document.querySelector('.user-btn');

    if (userDropdown && userBtn) {
      // Check if touch device
      if ('ontouchstart' in window || navigator.maxTouchPoints > 0) {
        userBtn.addEventListener('click', function(e) {
          e.preventDefault();
          e.stopPropagation();
          userDropdown.classList.toggle('active');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', function(e) {
          if (!userDropdown.contains(e.target)) {
            userDropdown.classList.remove('active');
          }
        });
      }
    }
  });
</script>
