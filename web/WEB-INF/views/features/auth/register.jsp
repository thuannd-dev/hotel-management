<%--
    Document   : register
    Created on : Oct 5, 2025, 1:00:56 PM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content shadow-lg">
            
            <div class="modal-header">
                <h4 class="modal-title w-100 text-center" id="registerModalLabel">Create Account</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <p class="text-muted text-center small mb-4">Join us and start your journey with Hotel Misauka.</p>

                <form action="register" method="POST">

                    <div class="row g-3">
                        <div class="col-12">
                            <label for="signup-fullname" class="form-label">Full Name </label>
                            <input type="text" class="form-control" id="signup-fullname" name="txtfullName" required placeholder="e.g., 0123456789">
                        </div>
                        <div class="col-md-6">
                            <label for="signup-idnumber" class="form-label">ID Number</label>
                            <input type="text" class="form-control" id="signup-idnumber" name="idNumber" required placeholder="This will be your username">
                        </div>
                        <div class="col-md-6">
                            <label for="signup-dob" class="form-label">Date of Birth</label>
                            <input type="date" class="form-control" id="signup-dob" name="dateOfBirth" required>
                        </div>
                        <div class="col-md-7">
                            <label for="signup-email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="signup-email" name="email" required placeholder="example@email.com">
                        </div>
                        <div class="col-md-5">
                            <label for="signup-phone" class="form-label">Phone</label>
                            <input type="tel" class="form-control" id="signup-phone" name="phone" required placeholder="Your phone number">
                        </div>
                        <div class="col-12">
                            <label for="signup-address" class="form-label">Address</label>
                            <textarea class="form-control" id="signup-address" name="address" rows="2" required placeholder="Your current address"></textarea>
                        </div>
                        <div class="col-md-6">
                            <label for="signup-password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="signup-password" name="password" required placeholder="Create a password">
                        </div>
                        <div class="col-md-6">
                            <label for="signup-confirm-password" class="form-label">Confirm Password</label>
                            <input type="password" class="form-control" id="signup-confirm-password" name="confirmPassword" required placeholder="Confirm your password">
                        </div>
                    </div>
                    <div class="d-grid mt-4">
                        <button type="submit" class="btn btn-warning btn-lg">Sign Up</button>
                    </div>
                </form>
            </div>

            <div class="modal-footer justify-content-center">
                <p class="small text-muted">Already have an account? 
                    <a href="#" class="text-warning fw-bold text-decoration-none" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#loginModal">
                        Login
                    </a>
                </p>
            </div>

        </div>
    </div>
</div>