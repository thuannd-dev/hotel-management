package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingServiceUsageService;
import com.hotel_management.domain.dto.booking_service.BookingServiceViewModel;
import com.hotel_management.infrastructure.dao.BookingServiceDAO;
import com.hotel_management.infrastructure.dao.BookingServiceUsageDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "UpdateBookingServiceController", urlPatterns = {"/service-staff/booking-services/status"})
public class UpdateBookingServiceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingServiceUsageService bookingServiceUsageService;

    @Override
    public void init() {
        BookingServiceDAO bookingServiceDao;
        BookingServiceUsageDetailDAO bookingServiceUsageDetailDao;
        DataSource ds = DataSourceProvider.getDataSource();
        bookingServiceDao = new BookingServiceDAO(ds);
        bookingServiceUsageDetailDao = new BookingServiceUsageDetailDAO(ds);
        this.bookingServiceUsageService = new BookingServiceUsageService(bookingServiceDao, bookingServiceUsageDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookingServiceId = Integer.parseInt(request.getParameter("id"));
            BookingServiceViewModel bookingServiceViewModel = bookingServiceUsageService.getBookingServiceById(bookingServiceId);
            if (bookingServiceViewModel == null) {
                throw new IllegalArgumentException("Booking service does not exist");
            }
            Boolean isSuccess = bookingServiceUsageService.updateBookingServiceStatusToCompleted(bookingServiceId);
            if (Boolean.FALSE.equals(isSuccess)) {
                throw new Exception("Failed to update booking service status");
            }
            response.sendRedirect(request.getContextPath() + "/service-staff/service-usage-detail?bookingId=" + bookingServiceViewModel.getBookingId());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking service ID");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

}
