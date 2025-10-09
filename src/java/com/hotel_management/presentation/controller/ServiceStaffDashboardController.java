/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;
import com.hotel_management.application.service.BookingService;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.dto.booking.BookingViewModel;
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
        DataSource ds = DataSourceProvider.getDataSource();
        bookingDAO = new BookingDAO(ds);
        this.bookingService = new BookingService(bookingDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String guestName = request.getParameter("guestName");
//        String roomNumber = request.getParameter("roomNumber");

        List<BookingViewModel> bookings = bookingService.getAllCheckInBookings();

        request.setAttribute(RequestAttribute.CHECK_IN_BOOKINGS, bookings);
        request.getRequestDispatcher(Page.SERVICE_STAFF_DASHBOARD_PAGE).forward(request, response);

    }

}
