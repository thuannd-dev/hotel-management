<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <script
            src="${pageContext.request.contextPath}/public/js/login.js"
            defer
    ></script>
    <title>Sign in to Hotel Management</title>
    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/public/css/login.css"
    />
</head>
<body
        class="min-h-screen bg-gradient-to-br from-yellow-50 to-orange-100 flex items-center justify-center p-4"
        style="background: linear-gradient(135deg, #faf3e7 0%, #f5e8d1 100%)"
>
<div class="w-full max-w-md">
    <!-- Login Card -->
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
                            d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                    ></path>
                </svg>
            </div>
            <h2 class="text-2xl font-bold" style="color: #24416b">
                Welcome Back
            </h2>
            <p class="text-gray-600">Sign in to Hotel Management System</p>
        </div>

        <!-- Login Form -->
        <form class="space-y-5" action="login" method="POST">
            <!-- Username Field -->
            <div>
                <label
                        for="username"
                        class="block text-sm font-medium text-gray-700 mb-2"
                >
                    Username
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
                            type="text"
                            id="username"
                            name="username"
                            required
                            class="login-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                            placeholder="Enter your username"
                    />
                </div>
            </div>

            <!-- Password Field -->
            <div>
                <label
                        for="password"
                        class="block text-sm font-medium text-gray-700 mb-2"
                >
                    Password
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
                                    d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                            ></path>
                        </svg>
                    </div>
                    <input
                            type="password"
                            id="password"
                            name="password"
                            required
                            class="login-input w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg transition-colors duration-200 placeholder-gray-400"
                            placeholder="Enter your password"
                    />
                    <button
                            type="button"
                            class="absolute inset-y-0 right-0 pr-3 flex items-center"
                            onclick="togglePassword()"
                    >
                        <svg
                                id="eye-icon"
                                class="eye-icon h-5 w-5 text-gray-400 cursor-pointer transition-colors duration-200"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
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

            <!-- Remember Me & Forgot Password -->
            <div class="flex items-center justify-between">
                <div class="flex items-center">
                    <input
                            id="remember-me"
                            name="remember-me"
                            type="checkbox"
                            class="h-4 w-4 border-gray-300 rounded"
                            style="color: #cc8c18; accent-color: #cc8c18"
                    />
                    <label for="remember-me" class="ml-2 block text-sm text-gray-700">
                        Remember me
                    </label>
                </div>
                <div class="text-sm">
                    <a
                            href="#"
                            class="forgot-link font-medium transition-colors duration-200"
                            style="color: #cc8c18"
                    >
                        Forgot password?
                    </a>
                </div>
            </div>

            <!-- Login Button -->
            <button
                    type="submit"
                    class="login-button w-full text-white py-3 px-4 rounded-lg font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 transform hover:scale-[1.02] transition-all duration-200 shadow-lg"
                    style="
              background: linear-gradient(135deg, #cc8c18 0%, #b8780f 100%);
            "
            >
                Sign In
            </button>

            <!-- Error Message Placeholder -->
            <c:if test="${not empty error}">
                <div
                        id="error-message"
                        class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-2"
                >
                    <div class="flex items-center">
                        <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                    fill-rule="evenodd"
                                    d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                                    clip-rule="evenodd"
                            ></path>
                        </svg>
                            ${error}
                    </div>
                </div>
            </c:if>
            <c:if test="${empty error}">
                <div
                        id="error-message"
                        class="hidden bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-2"
                >
                    <div class="flex items-center">
                        <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                    fill-rule="evenodd"
                                    d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                                    clip-rule="evenodd"
                            ></path>
                        </svg>
                        Invalid username or password. Please try again.
                    </div>
                </div>
            </c:if>
        </form>

        <!-- Footer -->
        <div class="text-center pt-4 border-t border-gray-100">
            <p class="text-sm text-gray-600">
                Don't have an account?
                <a
                        href="#"
                        class="forgot-link font-medium transition-colors duration-200"
                        style="color: #cc8c18"
                >
                    Contact Administrator
                </a>
            </p>
        </div>
    </div>

    <!-- Additional Info -->
    <div class="mt-6 text-center">
        <p class="text-xs text-gray-500">
            © 2025 Hotel Management System. All rights reserved.
        </p>
    </div>
</div>
</body>
</html>