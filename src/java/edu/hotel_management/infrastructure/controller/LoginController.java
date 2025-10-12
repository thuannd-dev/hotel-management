package edu.hotel_management.infrastructure.controller;

import edu.hotel_management.application.service.GuestService;
import edu.hotel_management.application.service.StaffService;
import edu.hotel_management.infrastructure.persistence.dao.GuestDAO;
import edu.hotel_management.infrastructure.persistence.dao.StaffDAO;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.IConstant;
import edu.hotel_management.presentation.constants.RequestAttribute;
import edu.hotel_management.presentation.constants.SessionAttribute;
import edu.hotel_management.presentation.dto.guest.GuestPresentationModel;
import edu.hotel_management.presentation.dto.staff.StaffPresentationModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author TR_NGHIA
 */

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StaffService staffService;
    private GuestService guestService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        StaffDAO staffDAO = new StaffDAO(ds);
        GuestDAO guestDAO = new GuestDAO(ds);
        staffService = new StaffService(staffDAO);
        guestService = new GuestService(guestDAO);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            Object currentUser = staffService.getStaffByUsernameAndPassword(username, password);

            if (currentUser == null) {
                currentUser = guestService.getGuestByUsernameAndPassword(username, password);
            }

            if (currentUser != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionAttribute.USER, currentUser);

                String userRole = "";
                if (currentUser instanceof StaffPresentationModel) {
                    userRole = ((StaffPresentationModel) currentUser).getRole();
                } else if (currentUser instanceof GuestPresentationModel) {
                    userRole = "GUEST";
                }

                String redirectUrl = IConstant.PAGE_HOME;
                switch (userRole.toUpperCase()) {
                    case "ADMIN":
                        redirectUrl = "/admin/dashboard"; 
                        break;
                    case "RECEPTIONIST":
                        redirectUrl = "/receptionist/bookings"; 
                        break;
                    case "GUEST":
                        redirectUrl = IConstant.PAGE_HOME;
                        break;
                }
                response.sendRedirect(request.getContextPath() + redirectUrl);

            } else {
                request.setAttribute(RequestAttribute.ERROR_LOGIN_MESSAGE, "Incorrect username or password.");
                request.getRequestDispatcher(IConstant.PAGE_HOME).forward(request, response);
            }
        } else {
            request.setAttribute(RequestAttribute.ERROR_LOGIN_MESSAGE, "Username and password are required.");
            request.getRequestDispatcher(IConstant.PAGE_HOME).forward(request, response);
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
        return "Handles user login for both staff and guests.";
    }
}