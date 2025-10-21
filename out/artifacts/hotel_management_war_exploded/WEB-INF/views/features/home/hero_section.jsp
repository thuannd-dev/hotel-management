<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<section class="home" id="home">
  <div class="container">
    <h1>Make Memories</h1>
    <p>Discover the place where you have fun & enjoy a lot</p>

    <form action="${pageContext.request.contextPath}/available-rooms" method="get">
      <div class="content grid">
        <div class="box">
          <span>ARRIVAL DATE </span> <br />
          <input type="date" name="checkInDate" required min="${java.time.LocalDate.now()}" />
        </div>
        <div class="box">
          <span>DEPARTURE DATE </span> <br />
          <input type="date" name="checkOutDate" required min="${java.time.LocalDate.now()}" />
        </div>
        <div class="box">
          <span>ADULTS</span> <br />
          <input type="number" name="adults" placeholder="01" min="0" max="10" value="1" required />
        </div>
        <div class="box">
          <span>CHILDREN </span> <br />
          <input type="number" name="children" placeholder="01" min="0" max="10" value="0" />
        </div>
        <div class="box">
          <button type="submit" class="flex1">
            <span>Check Availability</span>
            <i class="fas fa-arrow-circle-right"></i>
          </button>
        </div>
      </div>
    </form>
  </div>
</section>
