package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.GuestService;
import com.hotel_management.application.service.StaffService;
import com.hotel_management.domain.dto.guest.GuestCreateModel;
import com.hotel_management.domain.entity.Guest;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
        request.getRequestDispatcher(Page.REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set character encoding to UTF-8 for Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

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
}
