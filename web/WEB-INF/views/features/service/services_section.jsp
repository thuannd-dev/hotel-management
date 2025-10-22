<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<section class="offer mtop" id="services">
  <div class="container">
    <div class="heading">
      <h5>EXCLUSIVE OFFERS</h5>
      <h3>You can get an exclusive offer</h3>
    </div>

    <div class="content grid2 mtop">
      <div class="box flex">
        <div class="left">
          <img
            src="${pageContext.request.contextPath}/public/image/o1.jpg"
            alt=""
          />
        </div>
        <div class="right">
          <h4>Single Room</h4>
          <div class="rate flex">
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
          </div>
          <p>
            This room comfortably accommodates 1 guest with premium bedding and amenities.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
            officia deserunt mollit anim id est laborum.
          </p>
          <h5>From $50.0/night</h5>
          <button class="flex1" onclick="window.location.href='${pageContext.request.contextPath}/available-rooms?roomType=Single'">
            <span>Check Availability</span>
            <i class="fas fa-arrow-circle-right"></i>
          </button>
        </div>
      </div>
      <div class="box flex">
        <div class="left">
          <img
            src="${pageContext.request.contextPath}/public/image/o2.jpg"
            alt=""
          />
        </div>
        <div class="right">
          <h4>Double Room</h4>
          <div class="rate flex">
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
          </div>
          <p>
            Ideal for couples or friends. This spacious room comfortably accommodates 2 guests with premium bedding and amenities.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
          </p>
          <h5>From $80.0/night</h5>
          <button class="flex1" onclick="window.location.href='${pageContext.request.contextPath}/available-rooms?roomType=Double'">
            <span>Check Availability</span>
            <i class="fas fa-arrow-circle-right"></i>
          </button>
        </div>
      </div>
      <div class="box flex">
        <div class="left">
          <img
            src="${pageContext.request.contextPath}/public/image/o3.jpg"
            alt=""
          />
        </div>
        <div class="right">
          <h4>Deluxe Room</h4>
          <div class="rate flex">
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
          </div>
          <p>
            Luxurious comfort for couples. This premium room accommodates 2 guests with upgraded amenities and elegant furnishings.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
          </p>
          <h5>From $120.0/night</h5>
          <button class="flex1" onclick="window.location.href='${pageContext.request.contextPath}/available-rooms?roomType=Deluxe'">
            <span>Check Availability</span>
            <i class="fas fa-arrow-circle-right"></i>
          </button>
        </div>
      </div>
      <div class="box flex">
        <div class="left">
          <img
            src="${pageContext.request.contextPath}/public/image/o4.jpg"
            alt=""
          />
        </div>
        <div class="right">
          <h4>Suite Room</h4>
          <div class="rate flex">
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
            <i class="fas fa-star"></i>
          </div>
          <p>
            This expansive suite accommodates up to 4 guests with separate living area and premium amenities.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui
            Ultimate luxury for families or groups.
          <h5>From $150.0/night</h5>
          <button class="flex1" onclick="window.location.href='${pageContext.request.contextPath}/available-rooms?roomType=Suite'">
            <span>Check Availability</span>
            <i class="fas fa-arrow-circle-right"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</section>
