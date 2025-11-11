package com.hotel_management.presentation.controller.service_staff;

import com.hotel_management.application.service.booking.BookingService;
import com.hotel_management.application.service.booking_service.BookingServiceUsageService;
import com.hotel_management.application.service.service.ServiceEntityService;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;
import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.infrastructure.dao.booking.BookingDAO;
import com.hotel_management.infrastructure.dao.booking.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceDAO;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceUsageDetailDAO;
import com.hotel_management.infrastructure.dao.service.ServiceDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "AddServiceController", urlPatterns = {"/service-staff/services"})
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
        BookingServiceUsageDetailDAO bookingServiceUsageDetailDao;
        DataSource ds = DataSourceProvider.getDataSource();
        serviceDao = new ServiceDAO(ds);
        bookingDao = new BookingDAO(ds);
        bookingDetailDao = new BookingDetailDAO(ds);
        bookingServiceDao = new BookingServiceDAO(ds);
        bookingServiceUsageDetailDao = new BookingServiceUsageDetailDAO(ds);
        this.serviceEntityService = new ServiceEntityService(serviceDao);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
        this.bookingServiceUsageService = new BookingServiceUsageService(bookingServiceDao, bookingServiceUsageDetailDao);
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

            request.setAttribute(RequestAttribute.CHECK_IN_BOOKING_DETAILS, booking);
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
            String[] serviceIds = request.getParameterValues("serviceId[]");
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            if (staff == null) {
                throw new IllegalArgumentException("Invalid user");
            }
            if (serviceIds == null || serviceIds.length == 0) {
                throw new IllegalArgumentException("No service selected");
            }
            List<BookingServiceCreateModel> createModels = new ArrayList<>();
            for (String serviceIdStr : serviceIds) {
                int serviceId = Integer.parseInt(serviceIdStr);
                String quantityStr = request.getParameter("quantity_" + serviceId);
                int quantity = 1;
                if (quantityStr != null) {
                    quantity = Integer.parseInt(quantityStr);
                }
                createModels.add(new BookingServiceCreateModel(
                        bookingId,
                        serviceId,
                        quantity,
                        staff.getStaffId()
                ));
            }
            List<BookingServiceUsageDetailViewModel> newBookingServiceUsageModels =  bookingServiceUsageService.createBatchBookingService(createModels);
            if (newBookingServiceUsageModels == null || newBookingServiceUsageModels.isEmpty()) {
                throw new IllegalArgumentException("Failed to create booking service");
            }
            List<BookingServiceUsageDetailViewModel> oldBookingServiceUsageModels =  bookingServiceUsageService.getByBookingIdExceptBookingServiceIds(bookingId, newBookingServiceUsageModels);
            session.setAttribute(SessionAttribute.LIST_NEW_BOOKING_SERVICE_USAGE, newBookingServiceUsageModels);
            session.setAttribute(SessionAttribute.LIST_OLD_BOOKING_SERVICE_USAGE, oldBookingServiceUsageModels);
            response.sendRedirect("service-usage-detail?bookingId=" + bookingId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: bookingId, serviceId, or quantity");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
