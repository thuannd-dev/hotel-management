<%--
    Document   : login
    Created on : Oct 5, 2025, 10:40:06 AM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow-lg">
            
            <div class="modal-header">
                <h4 class="modal-title w-100 text-center" id="loginModalLabel">Welcome Back</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <p class="text-muted text-center small mb-4">Sign in to continue to Hotel Misauka.</p>
                <%--constant is useless--%>
                <form action="login" method="POST">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="txtusername" required placeholder="Enter your username">
                    </div>
                    <div class="mb-4">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="txtpassword" required placeholder="Enter your password">
                    </div>
                    <div class="d-grid mt-4">
                        <button type="submit" class="btn btn-warning btn-lg">Login</button>
                    </div>
                </form>
                
            </div>

            <div class="modal-footer justify-content-center">
                <p class="small text-muted">Don't have an account? 
                    
                    <%-- Link n�y se d�ng popup Sign-Up --%>
                    <a href="#" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#registerModal" class="text-warning fw-bold text-decoration-none">
                        Sign up
                    </a>
                    
                </p>
            </div>

        </div>
    </div>
</div>