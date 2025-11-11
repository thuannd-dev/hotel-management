package com.hotel_management.presentation.controller.guest;

import com.hotel_management.application.service.booking.BookingService;
import com.hotel_management.application.service.booking_service.BookingServiceUsageService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.booking.BookingDAO;
import com.hotel_management.infrastructure.dao.booking.BookingDetailDAO;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to handle guest service requests submission
 * @author thuannd.dev
 */
@WebServlet(name = "GuestSubmitServiceRequestController", urlPatterns = {"/guest/submit-service-request"})
public class GuestSubmitServiceRequestController extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        try {
            // Get booking ID
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            // Verify booking belongs to guest
            List<BookingDetailViewModel> allBookings = bookingService.getBookingsByGuestId(guestId);
            BookingDetailViewModel booking = allBookings.stream()
                    .filter(b -> b.getBookingId() == bookingId)
                    .findFirst()
                    .orElse(null);

            if (booking == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have access to this booking");
                return;
            }

            // Check if booking is checked out or cancelled
            if ("CHECK_OUT".equals(booking.getStatus()) || "CANCELLED".equals(booking.getStatus())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Cannot request services for a checked-out or cancelled booking");
                return;
            }

            // Get selected services and quantities
            String[] serviceIds = request.getParameterValues("serviceId");
            String[] quantities = request.getParameterValues("quantity");

            if (serviceIds == null || serviceIds.length == 0) {
                response.sendRedirect(request.getContextPath() + "/guest/request-services?bookingId=" + bookingId + "&error=noservice");
                return;
            }

            // Create booking service requests
            List<BookingServiceCreateModel> serviceRequests = new ArrayList<>();
            for (int i = 0; i < serviceIds.length; i++) {
                int serviceId = Integer.parseInt(serviceIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                if (quantity > 0) {
                    // assignedStaffId will be null for guest requests (to be assigned later by staff)
                    BookingServiceCreateModel model = new BookingServiceCreateModel(
                            bookingId,
                            serviceId,
                            quantity,
                            null  // No staff assigned yet
                    );
                    serviceRequests.add(model);
                }
            }

            if (serviceRequests.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/guest/request-services?bookingId=" + bookingId + "&error=noquantity");
                return;
            }

            // Save service requests
            bookingServiceUsageService.createBatchBookingService(serviceRequests);

            // Redirect to my bookings with success message
            response.sendRedirect(request.getContextPath() + "/guest/view-services?bookingId=" + bookingId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while processing your request: " + e.getMessage());
        }
    }
}

