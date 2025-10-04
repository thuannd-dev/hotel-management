<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<section class="customer top" id="testimonials">
  <div class="container">
    <div class="heading">
      <h5>TESTIMONIALS</h5>
      <h3>What customers say</h3>
    </div>

    <div class="testimonials-carousel owl-carousel owl-theme mtop">
      <div class="item">
        <div class="rate">
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
        </div>
        <h3>Excellent Swimming</h3>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <div class="admin flex">
          <div class="img">
            <img
              src="${pageContext.request.contextPath}/public/image/c1.jpg"
              alt=""
            />
          </div>
          <div class="text">
            <h3>Ayman Jensi</h3>
            <span>Manager</span>
          </div>
        </div>
      </div>
      <div class="item">
        <div class="rate">
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
        </div>
        <h3>Excellent Swimming</h3>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <div class="admin flex">
          <div class="img">
            <img
              src="${pageContext.request.contextPath}/public/image/c2.jpg"
              alt=""
            />
          </div>
          <div class="text">
            <h3>Ayman Jensi</h3>
            <span>Manager</span>
          </div>
        </div>
      </div>
      <div class="item">
        <div class="rate">
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
          <i class="fas fa-star"></i>
        </div>
        <h3>Excellent Swimming</h3>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <div class="admin flex">
          <div class="img">
            <img
              src="${pageContext.request.contextPath}/public/image/c3.jpg"
              alt=""
            />
          </div>
          <div class="text">
            <h3>Ayman Jensi</h3>
            <span>Manager</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<script>
  $(document).ready(function () {
    $(".testimonials-carousel").owlCarousel({
      loop: true,
      margin: 10,
      nav: false,
      dots: false,
      navText: [
        "<i class='far fa-long-arrow-alt-left'></i>",
        "<i class='far fa-long-arrow-alt-right'></i>",
      ],
      responsive: {
        0: { items: 1 },
        768: { items: 1 },
        1000: { items: 1 },
      },
    });
  });
</script>
