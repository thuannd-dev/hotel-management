<!--Header of welome page-->
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<section class="head">
  <div class="container flex1">
    <div class="address">
      <i class="fas fa-map-marker-alt"></i>
      <span>205 Fida Walinton, Tongo Street Front, USA</span>
    </div>
    <div class="logo">
      <img
        src="${pageContext.request.contextPath}/public/image/logo.png"
        alt=""
      />
    </div>
    <div class="auth-buttons">
      <a href="${pageContext.request.contextPath}/login" id="loginBtn" class="auth-btn">Sign in</a>
      <a href="${pageContext.request.contextPath}/register" id="registerBtn" class="auth-btn">Sign up</a>
    </div>
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
        <li><a href="#shop">shop</a></li>
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
</script>
