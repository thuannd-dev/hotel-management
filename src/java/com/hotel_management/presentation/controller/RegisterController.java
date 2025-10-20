package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.GuestService;
import com.hotel_management.application.service.StaffService;
import com.hotel_management.domain.dto.guest.GuestCreateModel;
import com.hotel_management.domain.entity.Guest;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.infrastructure.security.CsrfTokenUtil;
import com.hotel_management.infrastructure.worker.Mailer;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Register Controller for Guest Registration
 * @author thuannd.dev
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String CSRF_TOKEN_COOKIE_NAME = "CSRF-TOKEN";
    private static final String CSRF_TOKEN_PARAM_NAME = "csrfToken";

    private GuestService guestService;
    private StaffService staffService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        GuestDAO guestDAO = new GuestDAO(ds);
        StaffDAO staffDAO = new StaffDAO(ds);
        this.guestService = new GuestService(guestDAO);
        this.staffService = new StaffService(staffDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Generate CSRF token and set as cookie (double-submit pattern)
        String csrfToken = CsrfTokenUtil.generateToken();

        // Set cookie using Set-Cookie header for better control
        String cookiePath = request.getContextPath().isEmpty() ? "/" : request.getContextPath();
        String cookieValue = String.format(
            "%s=%s; Path=%s; Max-Age=%d; SameSite=Lax",
            CSRF_TOKEN_COOKIE_NAME,
            csrfToken,
            cookiePath,
            15 * 60  // 15 minutes
        );

        // Add Secure flag if HTTPS
        if (request.isSecure()) {
            cookieValue += "; Secure";
        }

        response.addHeader("Set-Cookie", cookieValue);

        // Also set as request attribute for immediate use in JSP if needed
        request.setAttribute("csrfToken", csrfToken);

        // Forward to register page
        request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set character encoding to UTF-8 for Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // === CSRF Token Validation (Double-Submit Cookie Pattern) ===
        String submittedToken = request.getParameter(CSRF_TOKEN_PARAM_NAME);
        String cookieToken = getCsrfTokenFromCookie(request);

        if (submittedToken == null || cookieToken == null || !cookieToken.equals(submittedToken)) {
            // CSRF validation failed - clear cookie and show error
            clearCsrfCookie(request, response);
            request.setAttribute(RequestAttribute.ERROR_MESSAGE,
                "Invalid CSRF token. Please refresh the page and try again.");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }
        // === End CSRF Validation ===

        // Parse form parameters into DTO
        GuestCreateModel createModel = parseRequestToModel(request);

        // Trim all fields
        createModel.trimFields();

        // Validate required fields
        if (!createModel.hasRequiredFields()) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Please fill all required fields");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Validate password match
        if (!createModel.passwordsMatch()) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Passwords do not match");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Validate password length
        if (!createModel.isPasswordValid(MIN_PASSWORD_LENGTH)) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE,
                "Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Check if username already exists in both Guest and Staff tables
        if (guestService.isUsernameExists(createModel.getUsername()) ||
            staffService.isUsernameExists(createModel.getUsername())) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Username already exists");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Check if email already exists (if provided)
        if (createModel.getEmail() != null && !createModel.getEmail().isEmpty() &&
            guestService.isEmailExists(createModel.getEmail())) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Email already exists");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Check if phone already exists (if provided)
        if (createModel.getPhone() != null && !createModel.getPhone().isEmpty() &&
            guestService.isPhoneExists(createModel.getPhone())) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Phone number already exists");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Check if ID number already exists
        if (guestService.isIdNumberExists(createModel.getIdNumber())) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "ID number already exists");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Validate age (must be at least 18 years old)
        if (createModel.getDateOfBirth() != null &&
            createModel.getDateOfBirth().isAfter(LocalDate.now().minusYears(MIN_AGE))) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE,
                "You must be at least " + MIN_AGE + " years old");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
            return;
        }

        // Convert DTO to entity and register guest
        Guest newGuest = createModel.toEntity();
        int guestId = guestService.registerGuest(newGuest);

        if (guestId > 0) {
            // Registration successful
            request.setAttribute("success", "Registration successful! Please check email and login with your credentials.");

            // Send welcome email to guest (async, non-blocking)
            if (createModel.getEmail() != null && !createModel.getEmail().trim().isEmpty()) {
                Mailer.sendWelcomeEmail(
                    createModel.getEmail(),
                    createModel.getFullName(),
                    createModel.getUsername()
                );
            }

            request.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);
        } else {
            // Registration failed
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Registration failed. Please try again.");
            request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
        }
    }

    /**
     * Parse HTTP request parameters to GuestCreateModel
     */
    private GuestCreateModel parseRequestToModel(HttpServletRequest request) {
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String idNumber = request.getParameter("idNumber");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Parse date of birth
        LocalDate dateOfBirth = null;
        if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeParseException e) {
                // Keep dateOfBirth as null, will be handled in validation
            }
        }

        return new GuestCreateModel(
            fullName,
            phone,
            email,
            address,
            idNumber,
            dateOfBirth,
            username,
            password,
            confirmPassword
        );
    }

    /**
     * Get CSRF token value from cookie
     */
    private String getCsrfTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CSRF_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Clear CSRF token cookie
     */
    private void clearCsrfCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(CSRF_TOKEN_COOKIE_NAME, null);
        cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        cookie.setHttpOnly(false);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);
    }
}
