package com.hotel_management.presentation.controller;
import com.hotel_management.application.service.GuestService;
import com.hotel_management.application.service.StaffService;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Path;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;
import com.hotel_management.presentation.dto.guest.GuestViewModel;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffService staffService;
    private GuestService guestService;

    @Override
    public void init() {
        StaffDAO staffDAO;
        GuestDAO guestDAO;
        DataSource ds = DataSourceProvider.getDataSource();
        staffDAO = new StaffDAO(ds);
        guestDAO = new GuestDAO(ds);
        this.staffService = new StaffService(staffDAO);
        this.guestService = new GuestService(guestDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Username and password is required");
            request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);
            return;
        }
        StaffViewModel staff = staffService.getStaffByUsernameAndPassword(username, password);
        GuestViewModel guest = guestService.getGuestByUsernameAndPassword(username, password);
        if(staff == null && guest == null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Incorrect username or password");
            request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);
            return;
        }
        request.getSession().setAttribute(SessionAttribute.CURRENT_USER, staff == null ? guest : staff);
        //switch case role to redirect to different home page by role
        response.sendRedirect(request.getContextPath());

    }
}
