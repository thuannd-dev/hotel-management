<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="timer about wrapper">
  <div class="background"></div>
  <div class="container">
    <div class="heading">
      <h5>LAST MINUTE!</h5>
      <h2><span>Incredible!</span> Are you coming today</h2>
    </div>
    <div id="time" class="flex1 mtop"></div>
  </div>
</section>

<script>
  $(document).ready(function () {
    $("#time").countdown("2025/12/31", function (event) {
      $(this).html(
        event.strftime(
          '<div class="clock"><span>%d</span> <p>Days</p></div> ' +
            '<div class="clock"><span>%H</span> <p>Hours</p></div> ' +
            '<div class="clock"><span>%M</span> <p>Minutes</p></div> ' +
            '<div class="clock"><span>%S</span> <p>Seconds</p></div> '
        )
      );
    });
  });
</script>
