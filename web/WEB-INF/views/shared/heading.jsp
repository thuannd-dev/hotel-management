<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<section class="wrapper">
  <div class="container">
    <div class="heading-carousel owl-carousel owl-theme">
      <div class="item">
        <div class="heading">
          <h5>FACILITIES</h5>
          <h2>Giving entirely awesome</h2>
        </div>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <p>
          Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
          dolore eu fugiat nulla pariatur.
        </p>
      </div>
      <div class="item">
        <div class="heading">
          <h5>FACILITIES</h5>
          <h2>Giving entirely awesome</h2>
        </div>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <p>
          Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
          dolore eu fugiat nulla pariatur.
        </p>
      </div>
    </div>
  </div>
</section>

<script>
  $(document).ready(function () {
    $(".heading-carousel").owlCarousel({
      loop: true,
      margin: 10,
      nav: true,
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
