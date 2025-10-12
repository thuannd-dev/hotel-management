<%--
    Document   : testimonialsBlog
    Created on : Oct 4, 2025, 11:48:11 PM
    Author     : TR_NGHIA
--%>

<section id="testimonials" class="section-padding scroll-animate">
    <div class="container">
        <%-- ... (N?i dung ph?n Testimonials gi? nguyên) ... --%>
        <div class="row">
            <div class="col-12 text-center mb-5">
                <p class="text-warning text-uppercase small tracking-wider mb-3">Testimonials</p>
                <h2 class="section-title display-4">Client Feedback</h2>
            </div>
        </div>
        <div class="row g-4">
            <div class="col-lg-4 col-md-6 scroll-animate">
                <div class="card border-0 text-center h-100 testimonial-card p-4">
                    <img src="${pageContext.request.contextPath}/public/img/guest-1.jpg" alt="Rosalina D. William" class="rounded-circle mx-auto mb-3" style="width: 80px; height: 80px; object-fit: cover;">
                    <h5 class="card-title mb-2">Rosalina D. William</h5>
                    <p class="text-warning text-uppercase small mb-3">Businesswoman</p>
                    <p class="card-text text-muted fst-italic">"My business trip felt like a vacation thanks to Hotel Misauka. The service was impeccable, and the quiet, luxurious room was the perfect retreat."</p>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.2s;">
                 <div class="card border-0 text-center h-100 testimonial-card p-4">
                    <img src="${pageContext.request.contextPath}/public/img/guest-2.jpg" alt="Donald H. Hilixer" class="rounded-circle mx-auto mb-3" style="width: 80px; height: 80px; object-fit: cover;">
                    <h5 class="card-title mb-2">Donald H. Hilixer</h5>
                    <p class="text-warning text-uppercase small mb-3">Consultant</p>
                    <p class="card-text text-muted fst-italic">"An absolutely unforgettable experience. The staff went above and beyond to make our stay comfortable. The attention to detail is simply outstanding."</p>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.4s;">
                <div class="card border-0 text-center h-100 testimonial-card p-4">
                    <img src="${pageContext.request.contextPath}/public/img/guest-3.jpg" alt="Amelia Tremblay" class="rounded-circle mx-auto mb-3" style="width: 80px; height: 80px; object-fit: cover;">
                    <h5 class="card-title mb-2">Amelia Tremblay</h5>
                    <p class="text-warning text-uppercase small mb-3">Designer</p>
                    <p class="card-text text-muted fst-italic">"As a designer, I was captivated by the hotel's stunning architecture and elegant interiors. Waking up to the ocean view was pure bliss."</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="blog" class="section-padding bg-light scroll-animate">
    <div class="container">
       <%-- ... (N?i dung ph?n Blog gi? nguyên) ... --%>
        <div class="text-center mb-5">
            <h2 class="section-title display-4">News & Feeds</h2>
        </div>
        <div class="row g-4">
            <div class="col-lg-4 col-md-6 scroll-animate">
                <div class="card border-0 shadow-sm h-100 news-card card-hover">
                    <img src="${pageContext.request.contextPath}/public/img/new-1.jpg" class="card-img-top" alt="News 1" style="height: 250px; object-fit: cover;">
                    <div class="card-body p-4">
                        <h5 class="card-title mb-3">Discover the New Summer Menu</h5>
                        <p class="card-text text-muted small">This season, our head chef celebrates the vibrant flavors of summer with a newly crafted menu, designed to be a light, refreshing, and unforgettable journey for your taste buds.</p>
                        <a href="#" class="btn btn-link text-warning p-0">Read More</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.2s;">
                <div class="card border-0 shadow-sm h-100 news-card card-hover">
                    <img src="${pageContext.request.contextPath}/public/img/new-2.jpg" class="card-img-top" alt="News 2" style="height: 250px; object-fit: cover;">
                    <div class="card-body p-4">
                        <h5 class="card-title mb-3">"Wellness Retreat" Package</h5>
                         <p class="card-text text-muted small">Escape the hustle and reconnect with yourself with our exclusive Wellness Retreat package, offering rejuvenating spa treatments and guided morning yoga.</p>
                        <a href="#" class="btn btn-link text-warning p-0">Read More</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.4s;">
                <div class="card border-0 shadow-sm h-100 news-card card-hover">
                    <img src="${pageContext.request.contextPath}/public/img/new-3.jpg" class="card-img-top" alt="News 3" style="height: 250px; object-fit: cover;">
                    <div class="card-body p-4">
                        <h5 class="card-title mb-3">Exclusive Artisan Tours</h5>
                        <p class="card-text text-muted small">Go beyond the typical tourist path and immerse yourself in the local culture with our exclusive guided tours to traditional craft villages.</p>
                        <a href="#" class="btn btn-link text-warning p-0">Read More</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>