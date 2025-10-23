package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.StaffService;
import com.hotel_management.domain.dto.staff.StaffCreateModel;
import com.hotel_management.domain.dto.staff.StaffUpdateModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.domain.entity.Staff;
import com.hotel_management.domain.entity.enums.StaffRole;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin/staff", "/admin/staff/create", "/admin/staff/edit", "/admin/staff/delete"})
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StaffService staffService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        StaffDAO staffDao = new StaffDAO(ds);
        this.staffService = new StaffService(staffDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/staff":
                    listStaff(request, response);
                    break;
                case "/admin/staff/create":
                    showCreateForm(request, response);
                    break;
                case "/admin/staff/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/staff/delete":
                    deleteStaff(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_STAFF_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/staff/create":
                    createStaff(request, response);
                    break;
                case "/admin/staff/edit":
                    updateStaff(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_STAFF_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<StaffViewModel> staffs = staffService.getAllStaffs();
        request.setAttribute(RequestAttribute.STAFFS, staffs);
        request.getRequestDispatcher(Page.ADMIN_STAFF_MANAGEMENT_PAGE).forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("roles", StaffRole.values());
        request.getRequestDispatcher(Page.ADMIN_STAFF_CREATE_PAGE).forward(request, response);
    }

    private void createStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        // Validation
        if (fullName == null || fullName.trim().isEmpty() ||
            role == null || role.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "All required fields must be filled");
            request.setAttribute("roles", StaffRole.values());
            request.getRequestDispatcher(Page.ADMIN_STAFF_CREATE_PAGE).forward(request, response);
            return;
        }

        // Check if username already exists
        if (staffService.isUsernameExists(username)) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Username already exists");
            request.setAttribute("roles", StaffRole.values());
            request.getRequestDispatcher(Page.ADMIN_STAFF_CREATE_PAGE).forward(request, response);
            return;
        }

        // Hash password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        // Create staff
        StaffCreateModel createModel = new StaffCreateModel(fullName, role, username, hashedPassword, phone, email);
        Staff staff = createModel.toEntity();

        int staffId = staffService.createStaff(staff);

        if (staffId > 0) {
            response.sendRedirect(request.getContextPath() + "/admin/staff?success=Staff created successfully");
        } else {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to create staff");
            request.setAttribute("roles", StaffRole.values());
            request.getRequestDispatcher(Page.ADMIN_STAFF_CREATE_PAGE).forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int staffId = Integer.parseInt(request.getParameter("id"));
            StaffViewModel staff = staffService.getStaffById(staffId);

            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/admin/staff?error=Staff not found");
                return;
            }

            request.setAttribute(RequestAttribute.STAFF, staff);
            request.setAttribute("roles", StaffRole.values());
            request.getRequestDispatcher(Page.ADMIN_STAFF_EDIT_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/staff?error=Invalid staff ID");
        }
    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            String fullName = request.getParameter("fullName");
            String role = request.getParameter("role");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            // Validation
            if (fullName == null || fullName.trim().isEmpty() ||
                role == null || role.trim().isEmpty() ||
                username == null || username.trim().isEmpty()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "All required fields must be filled");
                StaffViewModel staff = staffService.getStaffById(staffId);
                request.setAttribute(RequestAttribute.STAFF, staff);
                request.setAttribute("roles", StaffRole.values());
                request.getRequestDispatcher(Page.ADMIN_STAFF_EDIT_PAGE).forward(request, response);
                return;
            }

            // Check if username exists for another staff
            if (staffService.isUsernameExistsForOther(username, staffId)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Username already exists for another staff");
                StaffViewModel staff = staffService.getStaffById(staffId);
                request.setAttribute(RequestAttribute.STAFF, staff);
                request.setAttribute("roles", StaffRole.values());
                request.getRequestDispatcher(Page.ADMIN_STAFF_EDIT_PAGE).forward(request, response);
                return;
            }

            boolean success;
            if (password != null && !password.trim().isEmpty()) {
                // Hash new password and update including password
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                StaffUpdateModel updateModel = new StaffUpdateModel(staffId, fullName, role, username, password, phone, email);
                Staff staff = updateModel.toEntity(hashedPassword);
                success = staffService.updateStaff(staff);
            } else {
                // Update without changing password
                Staff staff = new Staff(staffId, fullName, StaffRole.fromDbValue(role), username, "", phone, email);
                success = staffService.updateStaffWithoutPassword(staff);
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/staff?success=Staff updated successfully");
            } else {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to update staff");
                // Get current staff for validation
                StaffViewModel currentStaff = staffService.getStaffById(staffId);
                request.setAttribute(RequestAttribute.STAFF, currentStaff);
                request.setAttribute("roles", StaffRole.values());
                request.getRequestDispatcher(Page.ADMIN_STAFF_EDIT_PAGE).forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/staff?error=Invalid staff ID");
        }
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int staffId = Integer.parseInt(request.getParameter("id"));
            boolean success = staffService.deleteStaff(staffId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/staff?success=Staff deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/staff?error=Failed to delete staff");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/staff?error=Invalid staff ID");
        }
    }
}
