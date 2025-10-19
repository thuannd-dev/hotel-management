package com.hotel_management.presentation.controller;
import com.hotel_management.application.service.GuestService;
import com.hotel_management.application.service.StaffService;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.Path;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
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
        request.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Username and password is required");
            request.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);
            return;
        }
        StaffViewModel staff = staffService.getStaffByUsernameAndPassword(username, password);
        GuestViewModel guest = guestService.getGuestByUsernameAndPassword(username, password);
        if(staff == null && guest == null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Incorrect username or password");
            request.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);
            return;
        }
        request.getSession().setAttribute(SessionAttribute.CURRENT_USER, staff == null ? guest : staff);
        //switch case role to redirect to different home page by role
        String role = staff == null ? "GUEST" : staff.getRole();
        String url = request.getContextPath();
        switch(role){
            case "GUEST":
                url = request.getContextPath();
                break;
            case "MANAGER":
                break;
            case "RECEPTIONIST":
                url = request.getContextPath() + Path.RECEPTIONIST_DASHBOARD_PATH;
                break;
            case "HOUSEKEEPING":
                url = request.getContextPath() + Path.HOUSEKEEPING_DASHBOARD_PATH;
                break;
            case "SERVICE_STAFF":
                url = request.getContextPath() + Path.SERVICE_STAFF_DASHBOARD_PATH;
                break;
            case "ADMIN":
                url = request.getContextPath() + Path.ADMIN_DASHBOARD_PATH;
        }
        response.sendRedirect(url);

    }
}
