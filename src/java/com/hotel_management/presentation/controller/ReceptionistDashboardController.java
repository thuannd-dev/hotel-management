package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.application.service.GuestService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
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
    private BookingService bookingService;
    private GuestService guestService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        GuestDAO guestDAO = new GuestDAO(ds);
        BookingDAO bookingDAO = new BookingDAO(ds);
        BookingDetailDAO bookingDetailDAO = new BookingDetailDAO(ds);

        this.guestService = new GuestService(guestDAO);
        this.bookingService = new BookingService(bookingDAO, bookingDetailDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<GuestViewModel> guests = guestService.getAllGuests();
        request.setAttribute(RequestAttribute.GUESTS, guests);
        request.getRequestDispatcher(Page.RECEPTIONIST_DASHBOARD_PAGE).forward(request, response);

    }

}
