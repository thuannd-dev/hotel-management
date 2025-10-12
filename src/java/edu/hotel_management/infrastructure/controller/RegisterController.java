package edu.hotel_management.infrastructure.controller;

import edu.hotel_management.application.service.GuestService;
import edu.hotel_management.domain.entities.Guest;
import edu.hotel_management.infrastructure.persistence.dao.GuestDAO;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.IConstant;
import edu.hotel_management.presentation.constants.RequestAttribute;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private GuestService guestService;

    @Override
    public void init() throws ServletException {
        GuestDAO guestDAO = new GuestDAO(DataSourceProvider.getDataSource());
        this.guestService = new GuestService(guestDAO);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = IConstant.PAGE_HOME;

        try {
            //========== LẤY DỮ LIỆU TỪ FORM ==========
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String idNumber = request.getParameter("idNumber");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String dobString = request.getParameter("dateOfBirth");

            System.out.println("Debug1 - Register: " + username + idNumber + " " + password + " " + email + " " + dobString);
            //========== KIỂM TRA & XỬ LÝ DỮ LIỆU ==========
            if (!password.equals(confirmPassword)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Passwords do not match.");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            Guest newGuest = new Guest();
            newGuest.setUsername(username);
            newGuest.setFullName(fullName);
            newGuest.setEmail(email);
            newGuest.setAddress(address);
            newGuest.setPhone(phone);
            newGuest.setIdNumber(idNumber);
            newGuest.setPassword(password);

            try {
                if (dobString != null && !dobString.isEmpty()) {
                    newGuest.setDateOfBirth(LocalDate.parse(dobString));
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for DateOfBirth: " + dobString);
            }

            System.out.println("Debug2 - Register: " + username + idNumber + " " + password + " " + email + " " + newGuest.getDateOfBirth());
            //========== GỌI SERVICE & XỬ LÝ KẾT QUẢ ==========
            String errorMessage = guestService.registerGuest(newGuest);

            if (errorMessage == null) {
                request.setAttribute(RequestAttribute.SUCCESS_REGISTER_MESSAGE, "Your account is created successfully.");
                response.sendRedirect(request.getContextPath() + IConstant.PAGE_HOME);
            } else {
                request.setAttribute(RequestAttribute.ERROR_REGISTER_MESSAGE, errorMessage);
                request.getRequestDispatcher(url).forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_REGISTER_MESSAGE, "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Guest Registration Controller";
    }
}
