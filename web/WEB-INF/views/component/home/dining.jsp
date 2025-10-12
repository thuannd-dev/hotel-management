<%--
    Document   : dining
    Created on : Oct 12, 2025
    Author     : TR_NGHIA
--%>

<%-- DÒNG GÂY L?I ?Ã ???C XÓA B? --%>

<section class="py-5 section-padding" id="dinning">
    <div class="container">
        <div class="row align-items-center g-5">
            <div class="col-lg-6">
                <p class="text-warning text-uppercase small tracking-wider mb-2">Dining</p>
                <h2 class="display-4 section-title mb-4">Exquisite Dining,<br>Unforgettable Nights</h2>
                <p class="text-muted mb-4">
                    Indulge your senses at The Misauka Grill, where our award-winning chefs craft culinary masterpieces from the freshest 
                    locally-sourced ingredients. Enjoy a sophisticated ambiance, an extensive wine list, and impeccable service, making every 
                    dinner a special occasion.
                </p>
                <div class="mb-4">
                    <p class="mb-1"><strong>Cuisine:</strong> International & Local Fusion</p>
                    <p><strong>Opening Hours:</strong> 6:00 PM - 10:00 PM Daily</p>
                </div>
                
                <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#diningModal">
                    View Menu & Reserve
                </button>
            </div>
            <div class="col-lg-6">
                <img src="public/img/dining.jpg" alt="Exquisite Dining at The Misauka Grill" class="img-fluid rounded-3 shadow">
            </div>
        </div>
    </div>
</section>

<div class="modal fade" id="diningModal" tabindex="-1" aria-labelledby="diningModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header border-0 pb-0">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body px-4 pt-0">
                <div class="row g-4">
                    <div class="col-lg-5">
                        <img src="public/img/dining1.jpg" alt="The Misauka Grill" class="img-fluid rounded-3 shadow-sm mb-3">
                        
                        <div class="card border-0 bg-light">
                            <div class="card-body">
                                <h6 class="text-warning mb-3">Restaurant Information</h6>
                                <div class="mb-3">
                                    <div class="d-flex align-items-start mb-2">
                                        <i class="bi bi-clock text-warning me-2 mt-1"></i>
                                        <div>
                                            <strong class="d-block small">Opening Hours</strong>
                                            <span class="text-muted small">6:00 PM - 10:00 PM Daily</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <div class="d-flex align-items-start mb-2">
                                        <i class="bi bi-geo-alt text-warning me-2 mt-1"></i>
                                        <div>
                                            <strong class="d-block small">Location</strong>
                                            <span class="text-muted small">Lobby Level, The Misauka Hotel</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <div class="d-flex align-items-start mb-2">
                                        <i class="bi bi-award text-warning me-2 mt-1"></i>
                                        <div>
                                            <strong class="d-block small">Cuisine Type</strong>
                                            <span class="text-muted small">International & Local Fusion</span>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <div class="d-flex align-items-start">
                                        <i class="bi bi-people text-warning me-2 mt-1"></i>
                                        <div>
                                            <strong class="d-block small">Dress Code</strong>
                                            <span class="text-muted small">Smart Casual</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-7">
                        <h3 class="section-title mb-2">The Misauka Grill</h3>
                        <p class="text-muted mb-4">
                            Indulge your senses at The Misauka Grill, where our award-winning chefs craft culinary masterpieces from the freshest locally-sourced ingredients. Enjoy a sophisticated ambiance, an extensive wine list, and impeccable service, making every dinner a special occasion.
                        </p>

                        <div class="mb-4">
                            <h5 class="menu-section-title mb-3">Our Menu</h5>
                            
                            <h6 class="text-warning small text-uppercase mb-3">Appetizers</h6>
                            <div class="menu-item mb-3">
                                <div>
                                    <h6 class="mb-1">Scallop Carpaccio</h6>
                                    <p class="text-muted small mb-0">Thinly sliced fresh scallops with a passion fruit vinaigrette and chili salt.</p>
                                </div>
                                <span class="menu-price">$18</span>
                            </div>
                            <div class="menu-item mb-4">
                                <div>
                                    <h6 class="mb-1">Wagyu Beef Tataki</h6>
                                    <p class="text-muted small mb-0">Seared A5 Wagyu beef served with a truffle ponzu sauce.</p>
                                </div>
                                <span class="menu-price">$25</span>
                            </div>

                            <h6 class="text-warning small text-uppercase mb-3">Main Courses</h6>
                            <div class="menu-item mb-3">
                                <div>
                                    <h6 class="mb-1">Pan-Seared Sea Bass</h6>
                                    <p class="text-muted small mb-0">Locally caught sea bass on a bed of saffron risotto and asparagus.</p>
                                </div>
                                <span class="menu-price">$35</span>
                            </div>
                            <div class="menu-item mb-4">
                                <div>
                                    <h6 class="mb-1">Grilled Lamb Chops</h6>
                                    <p class="text-muted small mb-0">Served with mint sauce, roasted potatoes, and seasonal vegetables.</p>
                                </div>
                                <span class="menu-price">$42</span>
                            </div>

                            <h6 class="text-warning small text-uppercase mb-3">Desserts</h6>
                            <div class="menu-item">
                                <div>
                                    <h6 class="mb-1">Chocolate Lava Cake</h6>
                                    <p class="text-muted small mb-0">Warm dark chocolate cake with a molten center, served with vanilla ice cream.</p>
                                </div>
                                <span class="menu-price">$12</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer border-0 pt-0">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <a href="#dinning" class="btn btn-warning">Reserve a Table</a>
            </div>
        </div>
    </div>
</div>