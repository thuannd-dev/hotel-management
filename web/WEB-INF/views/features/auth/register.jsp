<%-- Document : register Created on : Oct 20, 2025 Author : thuannd.dev --%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <title>Register - Hotel Management</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/public/css/login.css"
    />
    <style>
        .register-container {
            max-width: 800px;
        }
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1.25rem;
        }
        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body
    class="min-h-screen bg-gradient-to-br from-yellow-50 to-orange-100 flex items-center justify-center p-4"
    style="background: linear-gradient(135deg, #faf3e7 0%, #f5e8d1 100%)"
>
    <div class="w-full register-container">
        <!-- Register Card -->
        <div class="bg-white rounded-2xl shadow-xl p-8 space-y-6">
            <!-- Header -->
            <div class="text-center space-y-2">
                <div
                    class="mx-auto w-16 h-16 rounded-full flex items-center justify-center mb-4"
                    style="background: #cc8c18"
                >
                    <svg
                        class="w-8 h-8 text-white"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                    >
                        <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"
                        ></path>
                    </svg>
                </div>
                <h2 class="text-2xl font-bold" style="color: #24416b">
                    Create Account
                </h2>
                <p class="text-gray-600">Register as a guest</p>
            </div>

            <!-- Register Form -->
            <form class="space-y-5" action="${pageContext.request.contextPath}/register" method="POST" id="registerForm">
                <!-- CSRF Token Hidden Field - populate from server-side attribute as fallback -->
                <input type="hidden" id="csrfToken" name="csrfToken" value="${csrfToken}" />

                <div class="form-grid">
                    <!-- Full Name Field -->
                    <div>
                        <label
                            for="fullName"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Full Name <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="text"
                                id="fullName"
                                name="fullName"
                                required
                                maxlength="100"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Enter your full name"
                            />
                        </div>
                    </div>

                    <!-- Phone Field -->
                    <div>
                        <label
                            for="phone"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Phone Number
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="tel"
                                id="phone"
                                name="phone"
                                maxlength="20"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Enter phone number"
                            />
                        </div>
                    </div>

                    <!-- Email Field -->
                    <div>
                        <label
                            for="email"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Email
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="email"
                                id="email"
                                name="email"
                                maxlength="100"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Enter email address"
                            />
                        </div>
                    </div>

                    <!-- ID Number Field -->
                    <div>
                        <label
                            for="idNumber"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            ID Number <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="text"
                                id="idNumber"
                                name="idNumber"
                                required
                                maxlength="50"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Enter ID/Passport number"
                            />
                        </div>
                    </div>

                    <!-- Date of Birth Field -->
                    <div>
                        <label
                            for="dateOfBirth"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Date of Birth
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="date"
                                id="dateOfBirth"
                                name="dateOfBirth"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200"
                            />
                        </div>
                    </div>

                    <!-- Username Field -->
                    <div>
                        <label
                            for="username"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Username <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="text"
                                id="username"
                                name="username"
                                required
                                maxlength="50"
                                class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Choose a username"
                            />
                        </div>
                    </div>
                </div>

                <!-- Address Field (Full Width) -->
                <div>
                    <label
                        for="address"
                        class="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Address
                    </label>
                    <div class="relative">
                        <div
                            class="absolute top-3 left-0 pl-3 flex items-start pointer-events-none"
                        >
                            <svg
                                class="h-5 w-5 text-gray-400"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                                ></path>
                                <path
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                                ></path>
                            </svg>
                        </div>
                        <textarea
                            required
                            id="address"
                            name="address"
                            rows="2"
                            maxlength="200"
                            class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400 resize-none"
                            placeholder="Enter your address"
                        ></textarea>
                    </div>
                </div>

                <div class="form-grid">
                    <!-- Password Field -->
                    <div>
                        <label
                            for="password"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Password <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                    aria-hidden="true"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="password"
                                id="password"
                                name="password"
                                required
                                maxlength="255"
                                class="login-input w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Create a password"
                            />
                            <button
                                type="button"
                                class="absolute inset-y-0 right-0 pr-3 flex items-center"
                                onclick="togglePassword('password', 'eye-icon-1', 'toggle-password-btn-1')"
                                id="toggle-password-btn-1"
                                aria-label="Show password"
                                aria-pressed="false"
                            >
                                <svg
                                    id="eye-icon-1"
                                    class="eye-icon h-5 w-5 text-gray-400 cursor-pointer transition-colors duration-200"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                    aria-hidden="true"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                                    ></path>
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                                    ></path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- Confirm Password Field -->
                    <div>
                        <label
                            for="confirmPassword"
                            class="block text-sm font-medium text-gray-700 mb-2"
                        >
                            Confirm Password <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div
                                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                            >
                                <svg
                                    class="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                    aria-hidden="true"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                                    ></path>
                                </svg>
                            </div>
                            <input
                                required
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                required
                                maxlength="255"
                                class="login-input w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                                placeholder="Confirm your password"
                            />
                            <button
                                type="button"
                                class="absolute inset-y-0 right-0 pr-3 flex items-center"
                                onclick="togglePassword('confirmPassword', 'eye-icon-2', 'toggle-password-btn-2')"
                                id="toggle-password-btn-2"
                                aria-label="Show password"
                                aria-pressed="false"
                            >
                                <svg
                                    id="eye-icon-2"
                                    class="eye-icon h-5 w-5 text-gray-400 cursor-pointer transition-colors duration-200"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                    aria-hidden="true"
                                >
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                                    ></path>
                                    <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                                    ></path>
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Error Message -->
                <div id="error-container" class="hidden">
                    <div
                        class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm"
                    >
                        <div class="flex items-center">
                            <svg class="w-4 h-4 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                <path
                                    fill-rule="evenodd"
                                    d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                                    clip-rule="evenodd"
                                ></path>
                            </svg>
                            <span id="error-text"></span>
                        </div>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div
                        class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm"
                    >
                        <div class="flex items-center">
                            <svg class="w-4 h-4 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                <path
                                    fill-rule="evenodd"
                                    d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                                    clip-rule="evenodd"
                                ></path>
                            </svg>
                            <span>${error}</span>
                        </div>
                    </div>
                </c:if>

                <!-- Success Message -->
                <c:if test="${not empty success}">
                    <div
                        class="bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-lg text-sm"
                    >
                        <div class="flex items-center">
                            <svg class="w-4 h-4 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                <path
                                    fill-rule="evenodd"
                                    d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                    clip-rule="evenodd"
                                ></path>
                            </svg>
                            <span>${success}</span>
                        </div>
                    </div>
                </c:if>

                <!-- Register Button -->
                <button
                    type="submit"
                    class="login-button w-full text-white py-3 px-4 rounded-lg font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 transform hover:scale-[1.02] transition-all duration-200 shadow-lg"
                    style="
                      background: linear-gradient(135deg, #cc8c18 0%, #b8780f 100%);
                    "
                >
                    Create Account
                </button>

                <!-- Login Link -->
                <div class="text-center text-sm">
                    <span class="text-gray-600">Already have an account?</span>
                    <a
                        href="${pageContext.request.contextPath}/login"
                        class="font-medium transition-colors duration-200 ml-1"
                        style="color: #cc8c18"
                    >
                        Sign in here
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Helper function to read cookie value by name
        function getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
            return null;
        }

        // Populate CSRF token from a cookie on page load
        (function populateCsrfToken() {
            const token = getCookie('CSRF-TOKEN');
            const csrfInput = document.getElementById('csrfToken');
            if (csrfInput && token) {
                csrfInput.value = token;
            }
        })();

        function togglePassword(inputId, iconId, btnId) {
            const passwordInput = document.getElementById(inputId);
            const eyeIcon = document.getElementById(iconId);
            const toggleBtn = document.getElementById(btnId);
            const isPassword = passwordInput.type === 'password';

            passwordInput.type = isPassword ? 'text' : 'password';
            toggleBtn.setAttribute('aria-pressed', !isPassword);

            if (isPassword) {
                eyeIcon.innerHTML = `
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21"></path>
                `;
            } else {
                eyeIcon.innerHTML = `
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
                `;
            }
        }

        function showError(message) {
            const errorContainer = document.getElementById('error-container');
            const errorText = document.getElementById('error-text');
            errorText.textContent = message;
            errorContainer.classList.remove('hidden');

            // Scroll to an error message
            errorContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }

        function hideError() {
            const errorContainer = document.getElementById('error-container');
            errorContainer.classList.add('hidden');
        }

        // Form validation
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            hideError(); // Clear previous errors

            // Validate CSRF token exists (client-side check)
            const csrfToken = document.getElementById('csrfToken').value;
            if (!csrfToken) {
                e.preventDefault();
                showError('Security token missing. Please refresh the page and try again.');
                return false;
            }

            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password !== confirmPassword) {
                e.preventDefault();
                showError('Passwords do not match!');
                return false;
            }

            // Additional client-side validation
            if (password.length < 6) {
                e.preventDefault();
                showError('Password must be at least 6 characters long!');
                return false;
            }
        });

        // Add input focus effects
        document.querySelectorAll('.login-input').forEach(input => {
            input.addEventListener('focus', function() {
                this.classList.add('input-focused');
                hideError(); // Hide error when the user starts typing
            });
            input.addEventListener('blur', function() {
                this.classList.remove('input-focused');
            });
        });
    </script>
</body>
</html>
