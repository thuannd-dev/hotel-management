<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>Access Denied</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
    <div class="bg-white p-8 rounded-lg shadow-lg flex flex-col items-center max-w-md w-full">
        <svg class="w-16 h-16 text-red-500 mb-4" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v2m0 4h.01M21 12A9 9 0 1 1 3 12a9 9 0 0 1 18 0z"></path>
        </svg>
        <h1 class="text-3xl font-bold text-gray-800 mb-2">Access Denied</h1>
        <p class="text-gray-600 mb-6 text-center">You do not have permission to access this page.<br>Please contact the administrator if you believe this is a mistake.</p>
        <a href="${pageContext.request.contextPath}/" class="px-6 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition">Back to Home</a>
    </div>
</body>
</html>
