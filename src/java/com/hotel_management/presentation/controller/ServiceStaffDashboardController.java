package com.hotel_management.presentation.controller;
import com.hotel_management.application.service.BookingService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
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
 * @author thuannd.dev
 */
@WebServlet(name = "ServiceStaffDashboardController", urlPatterns = {"/service-staff"})
public class ServiceStaffDashboardController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;


    @Override
    public void init() {
        BookingDAO bookingDAO;
        BookingDetailDAO bookingDetailDAO;
        DataSource ds = DataSourceProvider.getDataSource();
        bookingDAO = new BookingDAO(ds);
        bookingDetailDAO = new BookingDetailDAO(ds);
        this.bookingService = new BookingService(bookingDAO, bookingDetailDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchType = request.getParameter("searchType");
            String query = request.getParameter("searchValue");
            // if one of two fields is null -> throw error
            if (query != null && query.trim().isEmpty()) {
                query = null;
            }
            if ((searchType == null) != (query == null)) {
                throw new ServletException("Invalid search parameters");
            }
            List<BookingDetailViewModel> bookings = bookingService.findBookings(searchType, query);
            request.setAttribute(RequestAttribute.BOOKING_SEARCH_TYPE, searchType);
            request.setAttribute(RequestAttribute.BOOKING_SEARCH_VALUE, query);
            request.setAttribute(RequestAttribute.LIST_CHECK_IN_BOOKING_DETAILS, bookings);
            request.getRequestDispatcher(Page.SERVICE_STAFF_DASHBOARD_PAGE).forward(request, response);
        } catch (ServletException servletException) {
            //request.setAttribute(RequestAttribute.ERROR_MESSAGE, servletException.getMessage());
            //request.getRequestDispatcher(Page.SERVICE_STAFF_DASHBOARD_PAGE).forward(request, response);
            //Implement error handling later
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, servletException.getMessage());
        }

    }

}
