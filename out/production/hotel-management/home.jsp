<%--
    Document   : home
    Created on : Oct 4, 2025, 7:38:31 PM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%-- Header  --%>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>

<%-- Navbar  --%>
<%@ include file="/WEB-INF/views/features/auth/navbar.jsp" %>

<%-- Login  --%>
<%@ include file="/WEB-INF/views/features/auth/login.jsp" %>

<%-- Register  --%>
<%@ include file="/WEB-INF/views/features/auth/register.jsp" %>

<main>
    <%-- Include Hero  --%>
    <%@ include file="/WEB-INF/views/component/home/hero.jsp" %>
    
    <%-- Include Booking Form --%>
    <%@ include file="/WEB-INF/views/features/booking/booking_form.jsp" %>
    
    <%-- Include Featured Rooms --%>
    <%@ include file="/WEB-INF/views/component/home/about_core_feature.jsp" %>


    <%-- Include Featured Rooms --%>
    <%@ include file="/WEB-INF/views/features/booking/featured_room.jsp" %>

    <%-- Include dinning --%>
    <%@ include file="/WEB-INF/views/component/home/dining.jsp" %>
    
    <%-- Include All Other Home Sections (About, Stats, Testimonials...) --%>
    <%@ include file="/WEB-INF/views/component/home/testimonials_blog.jsp" %>
    
    <%-- Include Home Map --%>
    <%@ include file="/WEB-INF/views/component/home/map.jsp" %>
</main>

<%-- Include Footer --%>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
