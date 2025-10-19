package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CreatePaymentController", urlPatterns = {"/receptionist-dashboard/create-payment"})
public class CreatePaymentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookingService bookingService;
    private BookingDAO bookingDao;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        this.bookingDao = new BookingDAO(ds);
        BookingDetailDAO bookingDetailDao = new BookingDetailDAO(ds);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String guestIdParam = request.getParameter("guestId");
        if (guestIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing guestId parameter");
            return;
        }

        int guestId = Integer.parseInt(guestIdParam);

        // FIND UNPAID  BOOKING
        Booking unpaidBooking = bookingDao.findUnpaidBookingByGuestId(guestId).orElse(null);
        if (unpaidBooking == null) {
            request.setAttribute("error", "No unpaid booking found for this guest.");
            request.getRequestDispatcher(Page.CREATE_PAYMENT_PAGE).forward(request, response);
            return;
        }

       
        BookingDetailViewModel bookingDetail = bookingService.getBookingDetailById(unpaidBooking.getBookingId());
        if (bookingDetail == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking detail not found.");
            return;
        }

        request.setAttribute("booking", bookingDetail);
        request.getRequestDispatcher(Page.CREATE_PAYMENT_PAGE).forward(request, response);
    }

    // ✅ Cập nhật trạng thái thanh toán
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int rows = bookingDao.markAsPaid(bookingId);
            if (rows > 0) {
                // ADD MESS TO SESSION ---
                request.getSession().setAttribute("popupMessage", "Payment successfully!!!");

                // GO TO DASHBOARD                
                response.sendRedirect(request.getContextPath() + "/receptionist");

            } else {
                
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment update failed or booking not found");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }
}
