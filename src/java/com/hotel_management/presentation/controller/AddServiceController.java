/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.application.service.ServiceEntityService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking.BookingViewModel;
import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.ServiceDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AddServiceController", urlPatterns = {"/add-service"})
public class AddServiceController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ServiceEntityService serviceEntityService;
    private BookingService bookingService;


    @Override
    public void init() {
        ServiceDAO serviceDao;
        BookingDAO bookingDao;
        BookingDetailDAO bookingDetailDao;
        DataSource ds = DataSourceProvider.getDataSource();
        serviceDao = new ServiceDAO(ds);
        bookingDao = new BookingDAO(ds);
        bookingDetailDao = new BookingDetailDAO(ds);
        this.serviceEntityService = new ServiceEntityService(serviceDao);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            BookingDetailViewModel booking = bookingService.getCheckInBookingDetailById(bookingId);
            if (booking == null) {
                throw new IllegalArgumentException("Invalid bookingId");
            }
            List<ServiceViewModel> services = serviceEntityService.getAllServices();

            request.setAttribute(RequestAttribute.BOOKING, booking);
            request.setAttribute(RequestAttribute.SERVICES, services);

            request.getRequestDispatcher("/WEB-INF/views/features/dashboard/staff/add_service.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bookingId");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
//            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
//            int quantity = Integer.parseInt(request.getParameter("quantity"));
//
//            bookingServiceUsageService.addServiceToBooking(bookingId, serviceId, quantity);
//
//            response.sendRedirect("record-service?success=true");
//        } catch (NumberFormatException e) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: bookingId, serviceId, or quantity");
//        }
//    }

}
