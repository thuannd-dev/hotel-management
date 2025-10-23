<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Service</title>
        <style>
            :root {
                --color-primary: #1B4965;
                --color-secondary: #75C3B3;
                --color-background: #F8F9FA;
                --color-text: #333333;
                --color-danger: #D34E51;
            }
            body {
                font-family: Arial, sans-serif;
                background-color: var(--color-background);
                color: var(--color-text);
                margin: 25px;
            }
            h2 {
                color: var(--color-primary);
                border-bottom: 3px solid var(--color-secondary);
                padding-bottom: 10px;
            }
            .form-container {
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                max-width: 600px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: var(--color-primary);
            }
            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }
            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s;
                margin-right: 10px;
            }
            .btn-primary {
                background-color: var(--color-primary);
                color: white;
            }
            .btn-primary:hover {
                background-color: #0E2938;
            }
            .btn-secondary {
                background-color: #6c757d;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
            }
            .alert {
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
            }
            .alert-error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }
            .required {
                color: var(--color-danger);
            }
            .help-text {
                font-size: 12px;
                color: #6c757d;
                margin-top: 5px;
            }
        </style>
    </head>
    <body>
        <h2>Edit Service</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>

        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/admin/service/edit">
                <input type="hidden" name="serviceId" value="${service.serviceId}">

                <div class="form-group">
                    <label for="serviceName">Service Name <span class="required">*</span></label>
                    <input type="text" id="serviceName" name="serviceName" required
                           value="${service.serviceName}"
                           placeholder="e.g., Room Cleaning, Laundry">
                </div>

                <div class="form-group">
                    <label for="serviceType">Service Type</label>
                    <input type="text" id="serviceType" name="serviceType"
                           value="${service.serviceType}"
                           placeholder="e.g., Housekeeping, Food & Beverage">
                    <div class="help-text">Optional: Category or type of service</div>
                </div>

                <div class="form-group">
                    <label for="price">Price (VND) <span class="required">*</span></label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required
                           value="${service.price}"
                           placeholder="e.g., 50000">
                    <div class="help-text">Price must be greater than or equal to 0</div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Update Service</button>
                    <a href="${pageContext.request.contextPath}/admin/service" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>


