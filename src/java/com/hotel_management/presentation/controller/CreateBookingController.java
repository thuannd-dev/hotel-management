package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.*;
import com.hotel_management.domain.dto.booking.BookingCreateModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.infrastructure.dao.*;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceDAO;
import com.hotel_management.infrastructure.dao.booking_service.BookingServiceUsageDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "CreateBookingController", urlPatterns = {"/receptionist-dashboard/create-booking"})
public class CreateBookingController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ServiceEntityService serviceEntityService;
    private BookingServiceUsageService bookingServiceUsageService;
    private BookingService bookingService;
    private RoomService roomService;
    private GuestService guestService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();

        ServiceDAO serviceDao = new ServiceDAO(ds);
        BookingDAO bookingDao = new BookingDAO(ds);
        BookingDetailDAO bookingDetailDao = new BookingDetailDAO(ds);
        BookingServiceDAO bookingServiceDao = new BookingServiceDAO(ds);
        BookingServiceUsageDetailDAO bookingServiceUsageDetailDao = new BookingServiceUsageDetailDAO(ds);
        GuestDAO guestDao = new GuestDAO(ds);
        RoomDAO roomDao = new RoomDAO(ds);
        RoomDetailDAO roomDetailDao = new RoomDetailDAO(ds);
        HousekeepingTaskDAO housekeepingTaskDao = new HousekeepingTaskDAO(ds);
        MaintenanceIssueDAO maintenanceIssueDao = new MaintenanceIssueDAO(ds);
        this.serviceEntityService = new ServiceEntityService(serviceDao);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
        this.bookingServiceUsageService = new BookingServiceUsageService(bookingServiceDao, bookingServiceUsageDetailDao);
        this.guestService = new GuestService(guestDao);
        this.roomService = new RoomService(roomDao, roomDetailDao, housekeepingTaskDao, maintenanceIssueDao);
    }

    // ✅ Hiển thị form đặt phòng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int guestId = Integer.parseInt(request.getParameter("guestId")); // NOTE 1: Lấy Guest ID từ URL
            GuestViewModel guests = guestService.getGuestById(guestId);
            List<ServiceViewModel> services = serviceEntityService.getAllServices();
            List<RoomDetailViewModel> rooms = roomService.getAllRoomDetails();
            request.setAttribute(RequestAttribute.GUESTS, guests);
            request.setAttribute(RequestAttribute.ROOMS, rooms);
            request.setAttribute(RequestAttribute.SERVICES, services);
            request.getRequestDispatcher(Page.CREATE_BOOKING_PAGE).forward(request, response);

            if (guests == null) {
                throw new IllegalArgumentException("Guest ID " + guestId + " not found.");//invalid guest
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading booking form: " + e.getMessage());
        }
    }

    // ✅ Xử lý tạo booking + thêm dịch vụ đi kèm
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            if (staff == null) {
                throw new IllegalArgumentException("Invalid user");
            }

            int guestId = Integer.parseInt(request.getParameter("guestId"));
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int totalGuest = Integer.parseInt(request.getParameter("totalGuest"));
            String specialRequests = request.getParameter("specialRequests");
            LocalDate checkInDate = LocalDate.parse(request.getParameter("checkInDate"));
            LocalDate checkOutDate = LocalDate.parse(request.getParameter("checkOutDate"));

            // ✅ Tao va luu booking
            BookingCreateModel creatModel
                    = new BookingCreateModel(guestId, roomId, checkInDate, checkOutDate, totalGuest, specialRequests);// staffID);
            int bookingId = bookingService.bookingCreate(creatModel);
            if (bookingId == 0) {
                throw new IllegalArgumentException("Failed to create booking");
            }

            // ✅ Thêm dịch vụ đi kèm nếu có
            String[] serviceIds = request.getParameterValues("serviceId[]");
            if (serviceIds != null && serviceIds.length > 0) {
                List<BookingServiceCreateModel> serviceCreateModels = new ArrayList<>();

                for (String serviceIdStr : serviceIds) {
                    int serviceId = Integer.parseInt(serviceIdStr);
                    String quantityStr = request.getParameter("quantity_" + serviceId);
                    int quantity = quantityStr != null ? Integer.parseInt(quantityStr) : 1;

                    serviceCreateModels.add(new BookingServiceCreateModel(
                            bookingId,
                            serviceId,
                            quantity,
                            staff.getStaffId()
                    ));
                }

                bookingServiceUsageService.createBatchBookingService(serviceCreateModels);
            }

            // ✅ Chuyển hướng về danh sách booking
            response.sendRedirect("booking-list?success=true");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input (guestId, roomId, or serviceId)");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }
}
