<%--
    Document   : home
    Created on : Oct 4, 2025, 7:38:31 PM
    Author     : TR_NGHIA
--%>

<%-- Header  --%>
<%@ include file="./WEB-INF/views/shared/header.jsp" %>

<%-- Navbar  --%>
<%@ include file="./WEB-INF/views/features/login/navbar.jsp" %>

<%-- Login  --%>
<%@ include file="WEB-INF/views/features/login/login.jsp" %>

<%-- Register  --%>
<%@ include file="WEB-INF/views/features/login/register.jsp" %>

<main>
    <%-- Include Hero  --%>
    <%@ include file="./WEB-INF/views/component/home/hero.jsp" %>
    
    <%-- Include Booking Form --%>
    <%@ include file="./WEB-INF/views/features/booking/bookingForm.jsp" %>
    
    <%-- Include Featured Rooms --%>
    <%@ include file="./WEB-INF/views/component/home/aboutCoreFeatures.jsp" %>


    <%-- Include Featured Rooms --%>
    <%@ include file="./WEB-INF/views/features/booking/featuredRoom.jsp" %>

    <%-- Include dinning --%>
    <%@ include file="./WEB-INF/views/component/home/dining.jsp" %>
    
    <%-- Include All Other Home Sections (About, Stats, Testimonials...) --%>
    <%@ include file="./WEB-INF/views/component/home/testimonialsBlog.jsp" %>
    
    <%-- Include Home Map --%>
    <%@ include file="./WEB-INF/views/component/home/map.jsp" %>
</main>

<%-- Include Footer --%>
<%@ include file="./WEB-INF/views/shared/footer.jsp" %>
