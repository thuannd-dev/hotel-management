<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Import Owl Carousel CSS (bạn cần thêm 2 file này vào web/public/css/) -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/owl.carousel.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/owl.theme.default.min.css">
<!-- Import style.css đúng đường dẫn -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">
<!-- Import jQuery (nếu chưa có) -->
<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<!-- Import Owl Carousel JS -->
<script src="${pageContext.request.contextPath}/public/js/owl.carousel.min.js"></script>

<section class="gallary top" id="gallary">
  <div class="owl-carousel owl-theme">
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g1.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g2.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g3.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g4.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g5.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g1.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g2.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
    <div class="item">
      <img
        src="${pageContext.request.contextPath}/public/image/g3.jpg"
        alt=""
      />
      <div class="overlay">
        <i class="fab fa-instagram"></i>
      </div>
    </div>
  </div>
</section>

<script>
  $(document).ready(function () {
    $(".owl-carousel").owlCarousel({
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