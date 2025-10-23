package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.CheckoutService;
import com.hotel_management.domain.dto.checkout.CheckoutBookingViewModel;
import com.hotel_management.domain.dto.checkout.CheckoutSummaryViewModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.*;
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
 * Controller for handling checkout operations
 * @author thuannd.dev
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/guest/checkout"})
public class CheckoutController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CheckoutService checkoutService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        BookingDAO bookingDAO = new BookingDAO(ds);
        CheckoutBookingDAO checkoutBookingDAO = new CheckoutBookingDAO(ds);
        CheckoutDAO checkoutDAO = new CheckoutDAO(ds);
        InvoiceDAO invoiceDAO = new InvoiceDAO(ds);
        PaymentDAO paymentDAO = new PaymentDAO(ds);
        TaxConfigDAO taxConfigDAO = new TaxConfigDAO(ds);
        BookingDetailDAO bookingDetailDAO = new BookingDetailDAO(ds);

        this.checkoutService = new CheckoutService(
            bookingDAO, checkoutBookingDAO, checkoutDAO,
            invoiceDAO, paymentDAO, taxConfigDAO, bookingDetailDAO
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set UTF-8 encoding
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object currentUser = session.getAttribute(SessionAttribute.CURRENT_USER);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Verify user is a guest
        if (!(currentUser instanceof GuestViewModel)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "This page is for guests only");
            return;
        }

        GuestViewModel guest = (GuestViewModel) currentUser;
        String action = request.getParameter("action");

        if ("summary".equals(action)) {
            // Show checkout summary for a specific booking
            showCheckoutSummary(request, response, guest);
        } else {
            // Show list of bookings available for checkout
            showCheckoutBookings(request, response, guest);
        }
    }

    private void showCheckoutBookings(HttpServletRequest request, HttpServletResponse response,
                                     GuestViewModel guest) throws ServletException, IOException {
        try {
            // Get all checked-in bookings for this guest
            List<CheckoutBookingViewModel> bookings =
                checkoutService.getCheckedInBookingsForGuest(guest.getGuestId());

            request.setAttribute("bookings", bookings);
            request.setAttribute("guestName", guest.getFullName());

            request.getRequestDispatcher("/WEB-INF/views/features/checkout/checkout-bookings.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading checkout bookings: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                    .forward(request, response);
        }
    }

    private void showCheckoutSummary(HttpServletRequest request, HttpServletResponse response,
                                    GuestViewModel guest) throws ServletException, IOException {
        try {
            String bookingIdStr = request.getParameter("bookingId");
            if (bookingIdStr == null || bookingIdStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/guest/checkout");
                return;
            }

            int bookingId = Integer.parseInt(bookingIdStr);

            // Calculate checkout summary
            CheckoutSummaryViewModel summary = checkoutService.calculateCheckoutSummary(bookingId);

            request.setAttribute("summary", summary);
            request.setAttribute("guestName", guest.getFullName());

            request.getRequestDispatcher("/WEB-INF/views/features/checkout/checkout-summary.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID");
        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error calculating checkout: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set UTF-8 encoding
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object currentUser = session.getAttribute(SessionAttribute.CURRENT_USER);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Verify user is a guest
        if (!(currentUser instanceof GuestViewModel)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "This page is for guests only");
            return;
        }

        try {
            String bookingIdStr = request.getParameter("bookingId");
            String paymentMethod = request.getParameter("paymentMethod");

            if (bookingIdStr == null || paymentMethod == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                return;
            }

            int bookingId = Integer.parseInt(bookingIdStr);

            // Process checkout
            boolean success = checkoutService.processCheckout(bookingId, paymentMethod);

            if (success) {
                // Redirect to success page
                session.setAttribute("successMessage", "Checkout completed successfully!");
                response.sendRedirect(request.getContextPath() + "/guest/my-booking");
            } else {
                request.setAttribute("errorMessage", "Failed to process checkout. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                        .forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing checkout: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                    .forward(request, response);
        }
    }
}

