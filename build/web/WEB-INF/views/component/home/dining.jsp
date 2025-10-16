<%--
    Document   : dining
    Created on : Oct 5, 2025, 12:04:10 AM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<section id="dining" class="section-padding">
    <div class="container">
        <div class="row g-5 align-items-center">
            <div class="col-lg-6 scroll-animate">
                <h2 class="section-title display-4 mb-4">Exquisite Dining,<br>Unforgettable Nights</h2>
                <p class="text-muted lead mb-4">Indulge your senses at The Misauka Grill, where our award-winning chefs craft culinary masterpieces from the freshest locally-sourced ingredients. Enjoy a sophisticated ambiance, an extensive wine list, and impeccable service, making every dinner a special occasion.</p>
                
                <ul class="dining-info-list">
                    <li>Cuisine:<span>International & Local Fusion</span></li>
                    <li>Opening Hours:<span>6:00 PM - 10:00 PM Daily</span></li>
                </ul>

                <a href="#" class="btn btn-warning btn-lg mt-4">View Menu & Reserve</a>
            </div>
            <div class="col-lg-6 scroll-animate" style="animation-delay: 0.2s;">
                <div class="dining-image">
                    <img src="${pageContext.request.contextPath}/public/img/dining.jpg" alt="Restaurant interior" class="img-fluid rounded-3 shadow">
                </div>
            </div>
        </div>
    </div>
</section>