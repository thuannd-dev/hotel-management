<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Tax Configuration</title>
        <style>
            :root {
                --color-primary: #1B4965;
                --color-secondary: #75C3B3;
                --color-background: #F8F9FA;
                --color-text: #333333;
                --color-accent-sand: #D4B483;
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
            input[type="date"],
            textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }
            input[type="checkbox"] {
                margin-right: 5px;
            }
            .checkbox-label {
                font-weight: normal;
                display: inline;
            }
            textarea {
                resize: vertical;
                min-height: 80px;
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
        <h2>Edit Tax Configuration</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>

        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/admin/tax/edit">
                <input type="hidden" name="taxConfigId" value="${taxConfig.taxConfigId}">

                <div class="form-group">
                    <label for="taxName">Tax Name <span class="required">*</span></label>
                    <input type="text" id="taxName" name="taxName" required
                           value="${taxConfig.taxName}"
                           placeholder="e.g., Value Added Tax (VAT)">
                </div>

                <div class="form-group">
                    <label for="taxRate">Tax Rate (%) <span class="required">*</span></label>
                    <input type="number" id="taxRate" name="taxRate" step="0.01" min="0" max="100" required
                           value="${taxConfig.taxRate}"
                           placeholder="e.g., 10.00">
                    <div class="help-text">Enter rate as percentage (0-100)</div>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description"
                              placeholder="Optional description of this tax configuration">${taxConfig.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="effectiveFrom">Effective From <span class="required">*</span></label>
                    <input type="date" id="effectiveFrom" name="effectiveFrom" required
                           value="${taxConfig.effectiveFrom}">
                    <div class="help-text">Date when this tax rate becomes effective</div>
                </div>

                <div class="form-group">
                    <label for="effectiveTo">Effective To</label>
                    <input type="date" id="effectiveTo" name="effectiveTo"
                           value="${taxConfig.effectiveTo != null ? taxConfig.effectiveTo : ''}">
                    <div class="help-text">Leave empty if this tax has no end date (ongoing)</div>
                </div>

                <div class="form-group">
                    <input type="checkbox" id="isActive" name="isActive" ${taxConfig.active ? 'checked' : ''}>
                    <label for="isActive" class="checkbox-label">Active</label>
                    <div class="help-text">Only active tax configurations can be used for calculations</div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Update Tax Configuration</button>
                    <a href="${pageContext.request.contextPath}/admin/tax" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>

