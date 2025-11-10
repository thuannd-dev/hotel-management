package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller to handle booking status changes
 * Reserved -> Checked-in
 * Checked-in -> Checked-out
 *
 * @author hotel-management
 */
@WebServlet(name = "UpdateBookingStatusController", urlPatterns = {"/receptionist/update-booking-status"})
public class UpdateBookingStatusController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDao;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        this.bookingDao = new BookingDAO(ds);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionAttribute.CURRENT_USER) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String bookingIdStr = request.getParameter("bookingId");
        String action = request.getParameter("action");

        if (bookingIdStr == null || bookingIdStr.isEmpty()) {
            session.setAttribute("popupMessage", "Missing booking ID!");
            response.sendRedirect(request.getContextPath() + "/receptionist");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            // Get current booking
            Optional<Booking> bookingOpt = bookingDao.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                session.setAttribute("popupMessage", "Booking not found!");
                response.sendRedirect(request.getContextPath() + "/receptionist");
                return;
            }

            Booking booking = bookingOpt.get();
            BookingStatus currentStatus = booking.getStatus();
            BookingStatus newStatus = null;

            // Determine new status based on action or current status
            if ("checkin".equals(action)) {
                if (currentStatus == BookingStatus.RESERVED) {
                    newStatus = BookingStatus.CHECK_IN;
                } else {
                    session.setAttribute("popupMessage", "Only Reserved bookings can be checked-in!");
                    response.sendRedirect(request.getContextPath() + "/receptionist");
                    return;
                }
            } else if ("checkout".equals(action)) {
                if (currentStatus == BookingStatus.CHECK_IN) {
                    newStatus = BookingStatus.CHECK_OUT;
                } else {
                    session.setAttribute("popupMessage", "Only Checked-in bookings can be checked-out!");
                    response.sendRedirect(request.getContextPath() + "/receptionist");
                    return;
                }
            } else {
                session.setAttribute("popupMessage", "Invalid action!");
                response.sendRedirect(request.getContextPath() + "/receptionist");
                return;
            }

            // Update booking status
            int updated = bookingDao.updateBookingStatus(bookingId, newStatus);

            if (updated > 0) {
                String statusMessage = newStatus == BookingStatus.CHECK_IN
                    ? "Booking has been checked-in successfully!"
                    : "Booking has been checked-out successfully!";
                session.setAttribute("popupMessage", statusMessage);
            } else {
                session.setAttribute("popupMessage", "Failed to update booking status!");
            }

            // Redirect back to receptionist dashboard
            response.sendRedirect(request.getContextPath() + "/receptionist");

        } catch (NumberFormatException e) {
            session.setAttribute("popupMessage", "Invalid booking ID format!");
            response.sendRedirect(request.getContextPath() + "/receptionist");
        } catch (Exception e) {
            session.setAttribute("popupMessage", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/receptionist");
        }
    }
}

