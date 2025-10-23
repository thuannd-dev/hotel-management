<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Staff</title>
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
                background: white;
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
            label .required {
                color: var(--color-danger);
            }
            input[type="text"],
            input[type="password"],
            input[type="email"],
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
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
            .alert-error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
            }
            .form-actions {
                margin-top: 30px;
            }
            .help-text {
                font-size: 12px;
                color: #6c757d;
                margin-top: 5px;
            }
        </style>
    </head>
    <body>
        <h2>Edit Staff Member</h2>

        <div class="form-container">
            <c:if test="${not empty error}">
                <div class="alert-error">
                    ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/staff/edit" method="post">
                <input type="hidden" name="staffId" value="${staff.staffId}">

                <div class="form-group">
                    <label for="fullName">Full Name <span class="required">*</span></label>
                    <input type="text" id="fullName" name="fullName" required
                           value="${staff.fullName}" placeholder="Enter full name">
                </div>

                <div class="form-group">
                    <label for="role">Role <span class="required">*</span></label>
                    <select id="role" name="role" required>
                        <option value="">-- Select Role --</option>
                        <c:forEach var="roleItem" items="${roles}">
                            <option value="${roleItem.dbValue}"
                                    ${staff.role == roleItem.name() ? 'selected' : ''}>
                                ${roleItem.name()}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="username">Username <span class="required">*</span></label>
                    <input type="text" id="username" name="username" required
                           value="${staff.username}" placeholder="Enter username">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password"
                           placeholder="Leave blank to keep current password">
                    <div class="help-text">Only fill this if you want to change the password</div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone"
                           value="${staff.phone}" placeholder="Enter phone number">
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email"
                           value="${staff.email}" placeholder="Enter email address">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Staff</button>
                    <a href="${pageContext.request.contextPath}/admin/staff" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>

