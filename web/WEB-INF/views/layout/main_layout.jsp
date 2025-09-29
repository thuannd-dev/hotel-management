<!--Main layout is a welcome page-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <title>Hotel Booking Website</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link
      rel="stylesheet"
      href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
      integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/public/css/style.css"
    />

    <script
      src="https://code.jquery.com/jquery-1.12.4.min.js"
      integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
      crossorigin="anonymous"></script>

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css"
    />
  </head>

  <body>
    <!-- Include Header -->
    <jsp:include page="header.jsp" />

    <!-- Main Content -->
    <main>
      <jsp:include page="${contentPage}" />
    </main>

    <!-- Include Footer -->
    <jsp:include page="footer.jsp" />

    <!-- Common Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/js/jquery.countdown.min.js" charset="utf-8"></script>
  </body>
</html>
