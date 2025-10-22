package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
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
 * Controller for guest to view their bookings
 * @author thuannd.dev
 */
@WebServlet(name = "MyBookingController", urlPatterns = {"/my-booking"})
public class MyBookingController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookingService bookingService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        BookingDAO bookingDao = new BookingDAO(ds);
        BookingDetailDAO bookingDetailDao = new BookingDetailDAO(ds);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set UTF-8 encoding for request and response
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object currentUser = session.getAttribute(SessionAttribute.CURRENT_USER);

        // Check if user is logged in as guest
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

        // Get all bookings for this guest
        List<BookingDetailViewModel> bookings = bookingService.getBookingsByGuestId(guestId);

        // Set attributes for the view
        request.setAttribute("bookings", bookings);
        request.setAttribute("guestName", guest.getFullName());

        // Forward to the view
        request.getRequestDispatcher("/WEB-INF/views/features/booking/my-booking.jsp")
                .forward(request, response);
    }
}

