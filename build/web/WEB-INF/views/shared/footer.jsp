<%--
    Document   : footer
    Created on : Oct 5, 2025, 4:02:12 PM
    Author     : TR_NGHIA
--%>
<footer id="contact" class="bg-dark text-white py-5">
    <div class="container">
        <div class="row g-4">
            <div class="col-lg-3 col-md-6">
                <div class="d-flex align-items-center mb-4">
                    <div class="logo-icon bg-warning d-flex align-items-center justify-content-center rounded me-2"><span class="text-white fw-bold">H</span></div>
                    <div><div class="fw-semibold">HOTEL</div><div class="text-white small">MISAUKA</div></div>
                </div>
                <p class="text-white mb-4">Hotel Misauka is committed to delivering excellence in every service to make your stay truly complete.</p>
                <div class="d-flex gap-3">
                    <a href="#" class="text-white"><i class="bi bi-facebook"></i></a>
                    <a href="#" class="text-white"><i class="bi bi-twitter"></i></a>
                    <a href="#" class="text-white"><i class="bi bi-instagram"></i></a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <h5 class="mb-4">Services</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Restaurant & Bar</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Swimming Pool</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Wellness & Spa</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Restaurant</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Conference Room</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Cocktail Party House</a></li>
                </ul>
            </div>
            <div class="col-lg-3 col-md-6">
                <h5 class="mb-4">Quick Links</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Gaming Zone</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Marriage Party</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Party Planning</a></li>
                    <li class="mb-2"><a href="#" class="text-white text-decoration-none">Tour Consultancy</a></li>
                </ul>
            </div>
            <div class="col-lg-3 col-md-6">
                <h5 class="mb-4">Contact Us</h5>
                <div class="contact-info">
                    <div class="d-flex align-items-start mb-3"><i class="bi bi-geo-alt me-3 mt-1"></i><span class="text-white">275 Quadra Street Victoria Road, New York</span></div>
                    <div class="d-flex align-items-center mb-3"><i class="bi bi-telephone me-3"></i><span class="text-white">+250 962 712 101</span></div>
                    <div class="d-flex align-items-center mb-3"><i class="bi bi-envelope me-3"></i><span class="text-white">info@misauka.com</span></div>
                </div>
            </div>
        </div>
        <hr class="border-secondary my-4">
        <div class="text-center text-white"><p class="mb-0">Copyright TrNghia 2025</p></div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/main.js"></script>

<%
    Object showLoginModalFlag = request.getAttribute("SHOW_LOGIN_MODAL");
    if (Boolean.TRUE.equals(showLoginModalFlag)) {
%>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        var loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
        loginModal.show();
    });
</script>
<%
    }
%>

<%
    Object showRegisterModalFlag = request.getAttribute("SHOW_REGISTER_MODAL");
    if (Boolean.TRUE.equals(showRegisterModalFlag)) {
%>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        var registerModal = new bootstrap.Modal(document.getElementById('registerModal'));
        registerModal.show();
    });
</script>
<%
    }
%>

</body>
</html>