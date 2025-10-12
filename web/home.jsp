<%--
    Document   : home
    Created on : Oct 4, 2025, 7:38:31 PM
    Author     : TR_NGHIA
--%>

<%-- Header  --%>
<%@ include file="./WEB-INF/views/shared/header.jsp" %>

<%-- Alert  --%>
<%@ include file="./WEB-INF/views/shared/alert.jsp" %>

<%-- Navbar  --%>
<%@ include file="./WEB-INF/views/features/login/navbar.jsp" %>

<%-- Login Modal (?n) --%>
<%@ include file="WEB-INF/views/features/login/login.jsp" %>

<%-- Register Modal (?n) --%>
<%@ include file="WEB-INF/views/features/login/register.jsp" %>

<main>
    <%-- Hero Section --%>
    <%@ include file="./WEB-INF/views/component/home/hero.jsp" %>
    
    <%-- Booking Form Section --%>
    <%@ include file="./WEB-INF/views/features/booking/bookingForm.jsp" %>
    
    <%-- About & Core Features Section --%>
    <%@ include file="./WEB-INF/views/component/home/aboutCoreFeatures.jsp" %>

    <%-- Featured Rooms Section --%>
    <%@ include file="./WEB-INF/views/features/booking/featuredRoom.jsp" %>

    <%-- Dining Section --%>
    <%@ include file="./WEB-INF/views/component/home/dining.jsp" %>
    
    <%-- Testimonials & Blog Section --%>
    <%@ include file="./WEB-INF/views/component/home/testimonialsBlog.jsp" %>
    
    <%-- Map Section --%>
    <%@ include file="./WEB-INF/views/component/home/map.jsp" %>
</main>

<%-- Footer --%>
<%@ include file="./WEB-INF/views/shared/footer.jsp" %>