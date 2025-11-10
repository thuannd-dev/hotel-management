package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.GuestService;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.GuestDAO;
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

/**
 *
 * @author PC
 */
@WebServlet(name = "ReceptionistDashboardController", urlPatterns = {"/receptionist"})
public class ReceptionistDashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private GuestService guestService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        GuestDAO guestDAO = new GuestDAO(ds);
        this.guestService = new GuestService(guestDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchType = request.getParameter("searchType");
            String searchValue = request.getParameter("searchValue");

            // Validate and trim search value
            if (searchValue != null && searchValue.trim().isEmpty()) {
                searchValue = null;
            }

            // If one of two fields is null -> throw error
            if ((searchType == null) != (searchValue == null)) {
                throw new ServletException("Invalid search parameters");
            }

            List<GuestViewModel> guests = guestService.searchGuests(searchType, searchValue);
            request.setAttribute(RequestAttribute.GUESTS, guests);
            request.getRequestDispatcher(Page.RECEPTIONIST_DASHBOARD_PAGE).forward(request, response);
        } catch (ServletException servletException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, servletException.getMessage());
        }
    }

}
