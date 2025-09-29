<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css"/>

<section class="gallary top" id="gallary">
  <div class="gallery-carousel owl-carousel owl-theme">
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g1.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g2.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g3.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g4.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g5.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g1.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g2.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img src="${pageContext.request.contextPath}/public/image/g3.jpg" alt="" />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
  </div>
</section>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
<script>
  $(document).ready(function () {
    $(".gallery-carousel").owlCarousel({
      loop: true,
      margin: 0,
      nav: false,
      dots: false,
      autoplay: true,
      slideTransition: "linear",
      autoplayTimeout: 4000,
      autoplaySpeed: 4000,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        768: { items: 3 },
        1000: { items: 5 },
      },
    });
  });
</script>
