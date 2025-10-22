package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.*;
import com.hotel_management.domain.dto.booking.BookingCreateModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.*;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.SessionAttribute;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Controller for guest self-service booking
 * @author thuannd.dev
 */
@WebServlet(name = "GuestBookingController", urlPatterns = {"/guest/create-booking"})
public class GuestBookingController extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set UTF-8 encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Object currentUser = session.getAttribute(SessionAttribute.CURRENT_USER);

            // Check if user is logged in as guest
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!(currentUser instanceof GuestViewModel)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "This endpoint is for guests only");
                return;
            }

            GuestViewModel guest = (GuestViewModel) currentUser;
            int guestId = guest.getGuestId();

            // Get booking parameters
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int totalGuest = Integer.parseInt(request.getParameter("totalGuest"));
            String specialRequests = request.getParameter("specialRequests");
            LocalDate checkInDate = LocalDate.parse(request.getParameter("checkInDate"));
            LocalDate checkOutDate = LocalDate.parse(request.getParameter("checkOutDate"));

            // Validate dates
            if (checkInDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Check-in date cannot be in the past");
            }

            if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)) {
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }

            // Create booking
            BookingCreateModel createModel = new BookingCreateModel(
                guestId,
                roomId,
                checkInDate,
                checkOutDate,
                totalGuest,
                specialRequests != null ? specialRequests : ""
            );

            int bookingId = bookingService.bookingCreate(createModel);

            if (bookingId == 0) {
                throw new IllegalArgumentException("Failed to create booking");
            }

            // Redirect to success page or booking confirmation
            response.sendRedirect(request.getContextPath() + "/available-rooms?bookingSuccess=true&bookingId=" + bookingId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }
}

