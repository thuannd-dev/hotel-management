<!--Footer of welcome page-->
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<footer>
  <div class="container top">
    <div class="subscribe" id="contact">
      <h2>Subscribe newsletter</h2>
      <p>
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
        officia deserunt mollit anim id est laborum.
      </p>
      <div class="input flex">
        <input type="email" placeholder="Your Email address" />
        <button class="flex1">
          <span>Subscribe</span>
          <i class="fas fa-arrow-circle-right"></i>
        </button>
      </div>
    </div>

    <div class="content grid top">
      <div class="box">
        <div class="logo">
          <img
            src="${pageContext.request.contextPath}/public/image/logo.png"
            alt=""
          />
        </div>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </p>
        <div class="social flex">
          <i class="fab fa-facebook-f"></i>
          <i class="fab fa-twitter"></i>
          <i class="fab fa-instagram"></i>
          <i class="fab fa-youtube"></i>
        </div>
      </div>

      <div class="box">
        <h2>Quick Links</h2>
        <ul>
          <li><i class="fas fa-angle-double-right"></i>Big Data</li>
          <li><i class="fas fa-angle-double-right"></i>Wellness</li>
          <li><i class="fas fa-angle-double-right"></i>Spa Gallery</li>
          <li><i class="fas fa-angle-double-right"></i>Reservation</li>
          <li><i class="fas fa-angle-double-right"></i>FAQ</li>
          <li><i class="fas fa-angle-double-right"></i>Contact</li>
        </ul>
      </div>

      <div class="box">
        <h2>Services</h2>
        <ul>
          <li><i class="fas fa-angle-double-right"></i>Restaurant</li>
          <li><i class="fas fa-angle-double-right"></i>Swimming Pool</li>
          <li><i class="fas fa-angle-double-right"></i>Wellness & Spa</li>
          <li><i class="fas fa-angle-double-right"></i>Conference Room</li>
          <li><i class="fas fa-angle-double-right"></i>Events</li>
          <li><i class="fas fa-angle-double-right"></i>Adult Room</li>
        </ul>
      </div>

      <div class="box">
        <h2>Contact Info</h2>
        <div class="icon flex">
          <div class="i">
            <i class="fas fa-map-marker-alt"></i>
          </div>
          <div class="text">
            <h3>Address</h3>
            <p>205 Fida Walinton, Tongo Street Front The USA</p>
          </div>
        </div>
        <div class="icon flex">
          <div class="i">
            <i class="fas fa-phone"></i>
          </div>
          <div class="text">
            <h3>Phone</h3>
            <p>+123 456 7898</p>
          </div>
        </div>
        <div class="icon flex">
          <div class="i">
            <i class="far fa-envelope"></i>
          </div>
          <div class="text">
            <h3>Email</h3>
            <p>hello@ecorik.com</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</footer>
