package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.application.service.BookingServiceUsageService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceDAO;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceUsageDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Controller for guest to view requested services
 * @author thuannd.dev
 */
@WebServlet(name = "GuestViewServicesController", urlPatterns = {"/guest/view-services"})
public class GuestViewServicesController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookingService bookingService;
    private BookingServiceUsageService bookingServiceUsageService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        BookingDAO bookingDao = new BookingDAO(ds);
        BookingDetailDAO bookingDetailDao = new BookingDetailDAO(ds);
        BookingServiceDAO bookingServiceDao = new BookingServiceDAO(ds);
        BookingServiceUsageDetailDAO bookingServiceUsageDetailDao = new BookingServiceUsageDetailDAO(ds);

        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
        this.bookingServiceUsageService = new BookingServiceUsageService(bookingServiceDao, bookingServiceUsageDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object currentUser = session.getAttribute(SessionAttribute.CURRENT_USER);

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!(currentUser instanceof GuestViewModel)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "This page is for guests only");
            return;
        }

        GuestViewModel guest = (GuestViewModel) currentUser;
        int guestId = guest.getGuestId();

        // Get booking ID from request
        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Booking ID is required");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdParam);

        // Get booking details and verify it belongs to this guest
        List<BookingDetailViewModel> allBookings = bookingService.getBookingsByGuestId(guestId);
        BookingDetailViewModel booking = allBookings.stream()
                .filter(b -> b.getBookingId() == bookingId)
                .findFirst()
                .orElse(null);

        if (booking == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have access to this booking");
            return;
        }

        // Get all requested services for this booking
        List<BookingServiceUsageDetailViewModel> requestedServices =
                bookingServiceUsageService.getByBookingId(bookingId);

        // Set attributes for the view
        request.setAttribute("booking", booking);
        request.setAttribute("requestedServices", requestedServices);

        // Forward to the view
        request.getRequestDispatcher("/WEB-INF/views/features/booking/view-services.jsp")
                .forward(request, response);
    }
}

