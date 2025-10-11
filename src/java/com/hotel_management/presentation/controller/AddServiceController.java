/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingService;
import com.hotel_management.application.service.BookingServiceUsageService;
import com.hotel_management.application.service.ServiceEntityService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.BookingServiceDAO;
import com.hotel_management.infrastructure.dao.ServiceDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "AddServiceController", urlPatterns = {"/service-staff/add-service"})
public class AddServiceController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ServiceEntityService serviceEntityService;
    private BookingService bookingService;
    private BookingServiceUsageService bookingServiceUsageService;


    @Override
    public void init() {
        ServiceDAO serviceDao;
        BookingDAO bookingDao;
        BookingDetailDAO bookingDetailDao;
        BookingServiceDAO bookingServiceDao;
        DataSource ds = DataSourceProvider.getDataSource();
        serviceDao = new ServiceDAO(ds);
        bookingDao = new BookingDAO(ds);
        bookingDetailDao = new BookingDetailDAO(ds);
        bookingServiceDao = new BookingServiceDAO(ds);
        this.serviceEntityService = new ServiceEntityService(serviceDao);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
        this.bookingServiceUsageService = new BookingServiceUsageService(bookingServiceDao);
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
            request.getRequestDispatcher(Page.ADD_SERVICE_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bookingId");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            if (staff == null) {
                throw new IllegalArgumentException("Invalid user");
            }
            bookingServiceUsageService.createBookingService(
                    new BookingServiceCreateModel(
                            bookingId,
                            serviceId,
                            quantity,
                            staff.getStaffId()
                    )
            );

            response.sendRedirect("record-service?success=true");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: bookingId, serviceId, or quantity");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
