<%--
    Document   : featuredRoom
    Created on : Oct 4, 2025, 11:55:00 PM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<section id="rooms" class="section-padding bg-light">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-5 scroll-animate">
            <div>
                <h2  class="section-title">Featured Rooms</h2>
                <p class="text-muted">Discover rooms designed for your comfort and delight.</p>
            </div>
            <a href="rooms" class="btn btn-outline-dark">View All</a>
        </div>

        <div class="row g-4 gy-5">
            <div class="col-lg-4 col-md-6 scroll-animate">
                <div class="card card-hover h-100 room-card">
                    <img src="${pageContext.request.contextPath}/public/img/room-1.jpg" class="card-img-top room-image" alt="Deluxe Ocean View">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Deluxe Ocean View</h5>
                        <p class="card-text text-muted small">Wake up to panoramic ocean views from your private balcony. This room combines elegant furnishings with premium amenities for an unforgettable seaside escape.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-water"></i> Stunning Ocean View</li>
                            <li><i class="bi bi-aspect-ratio"></i> Luxurious King Bed</li>
                            <li><i class="bi bi-bounding-box"></i> Private Balcony</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$250 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=1" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.2s;">
                <div class="card card-hover h-100 room-card">
                    <img src="${pageContext.request.contextPath}/public/img/room-2.jpg" class="card-img-top room-image" alt="Garden Suite">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Garden Suite</h5>
                        <p class="card-text text-muted small">Immerse yourself in tranquility. This spacious suite opens onto a private terrace, surrounded by lush tropical foliage, blending indoor luxury with nature's calm.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-tree"></i> Serene Garden View</li>
                            <li><i class="bi bi-plus-square"></i> Spacious Suite Layout</li>
                            <li><i class="bi bi-vr"></i> Private Terrace</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$350 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=2" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.4s;">
                <div class="card card-hover h-100 room-card">
                    <img src="${pageContext.request.contextPath}/public/img/room-3.jpg" class="card-img-top room-image" alt="Marina Deluxe">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Marina Deluxe</h5>
                        <p class="card-text text-muted small">Perfect for those who appreciate modern elegance, this room offers stunning views of the vibrant marina, complemented by a sleek, contemporary design.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-anchor"></i> Exclusive Marina View</li>
                            <li><i class="bi bi-aspect-ratio-fill"></i> Comfortable Queen Bed</li>
                            <li><i class="bi bi-palette"></i> Modern Interior Design</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$280 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=3" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-4 col-md-6 scroll-animate">
                <div class="card card-hover h-100 room-card">
                    <%-- S?A L?I: D�ng l?i ?nh c? --%>
                    <img src="${pageContext.request.contextPath}/public/img/room-1.jpg" class="card-img-top room-image" alt="Executive Cityscape Suite">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Executive Cityscape Suite</h5>
                        <p class="card-text text-muted small">Ideal for the discerning business traveler, this suite offers panoramic city views, a dedicated workspace, and access to the executive lounge.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-building"></i> Cityscape View</li>
                            <li><i class="bi bi-pc-display"></i> Dedicated Workspace</li>
                            <li><i class="bi bi-cup-straw"></i> Lounge Access</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$400 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=4" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.2s;">
                <div class="card card-hover h-100 room-card">
                    <%-- S?A L?I: D�ng l?i ?nh c? --%>
                    <img src="${pageContext.request.contextPath}/public/img/room-2.jpg" class="card-img-top room-image" alt="Family Connecting Room">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Family Connecting Room</h5>
                        <p class="card-text text-muted small">Perfect for families, our connecting rooms offer both space and privacy, ensuring a comfortable stay for everyone.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-arrows-angle-expand"></i> Connecting Rooms</li>
                            <li><i class="bi bi-people-fill"></i> Sleeps 4+</li>
                            <li><i class="bi bi-door-open"></i> Two Bathrooms</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$450 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=5" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: 0.4s;">
                <div class="card card-hover h-100 room-card">
                    <%-- S?A L?I: D�ng l?i ?nh c? --%>
                    <img src="${pageContext.request.contextPath}/public/img/room-3.jpg" class="card-img-top room-image" alt="Penthouse Residence">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Penthouse Residence</h5>
                        <p class="card-text text-muted small">The pinnacle of luxury, this penthouse boasts unparalleled views, a private terrace with a jacuzzi, and personalized butler service.</p>
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-gem"></i> Rooftop Terrace</li>
                            <li><i class="bi bi-droplet-half"></i> Private Jacuzzi</li>
                            <li><i class="bi bi-person-check"></i> Butler Service</li>
                        </ul>
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0">$950 <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            <a href="room-detail?id=6" class="btn btn-warning">View Detail</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>