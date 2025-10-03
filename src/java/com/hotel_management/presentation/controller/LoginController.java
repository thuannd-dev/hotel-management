package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.StaffService;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
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

    @Override
    public void init() throws ServletException {
        StaffDAO staffDAO;
        DataSource ds = DataSourceProvider.getDataSource();
        staffDAO = new StaffDAO(ds);
        this.staffService = new StaffService(staffDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/features/auth/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null) {
            request.setAttribute("error", "Username and password is required");
            request.getRequestDispatcher("/WEB-INF/views/features/auth/login.jsp").forward(request, response);
            return;
        }
        StaffViewModel staff = staffService.getStaffByUsernameAndPassword(username, password);
        if (staff != null) {
            request.getSession().setAttribute("staff", staff);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Incorrect username or password");
            request.getRequestDispatcher("/WEB-INF/views/features/auth/login.jsp").forward(request, response);
        }
    }
}
