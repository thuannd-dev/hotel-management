package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.BookingServiceReportService;
import com.hotel_management.domain.dto.booking_service.BookingServiceCompletedReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceProvidedPeriodReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceRequestReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServicesProvidedReportViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.infrastructure.dao.booking_service.*;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "BookingServiceReportController", urlPatterns = {"/service-staff/statistics"})
public class BookingServiceReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingServiceReportService bookingServiceReportService;

    @Override
    public void init() {
        BookingServicesProvidedReportDAO bookingServicesProvidedReportDao;
        BookingServiceRequestReportDAO bookingServiceRequestReportDao;
        BookingServiceCompletedReportDAO bookingServiceCompletedReportDao;
        BookingServiceProvidedPeriodReportDAO bookingServiceProvidedPeriodReportDao;
        DataSource ds = DataSourceProvider.getDataSource();
        bookingServicesProvidedReportDao = new BookingServicesProvidedReportDAO(ds);
        bookingServiceRequestReportDao = new BookingServiceRequestReportDAO(ds);
        bookingServiceCompletedReportDao = new BookingServiceCompletedReportDAO(ds);
        bookingServiceProvidedPeriodReportDao = new BookingServiceProvidedPeriodReportDAO(ds);
        this.bookingServiceReportService = new BookingServiceReportService(
                bookingServicesProvidedReportDao,
                bookingServiceRequestReportDao,
                bookingServiceCompletedReportDao,
                bookingServiceProvidedPeriodReportDao
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            if (staff == null) {
                throw new IllegalArgumentException("Invalid user session");
            }

            String reportType = request.getParameter("reportType");
            if (reportType == null || reportType.trim().isEmpty()) {
                reportType = "servicesProvidedToday";
            }

            switch (reportType) {
                case "servicesProvidedToday":
                    List<BookingServicesProvidedReportViewModel> servicesProvidedToday =
                            bookingServiceReportService.getServicesProvidedTodayReportOfStaffId(staff.getStaffId());
                    request.setAttribute(RequestAttribute.SERVICES_PROVIDED_TODAY_REPORT, servicesProvidedToday);
                    break;

                case "servicesProvidedPeriod":
                    String startDateStr = request.getParameter("startDate");
                    String endDateStr = request.getParameter("endDate");

                    if (startDateStr != null && !startDateStr.trim().isEmpty() &&
                            endDateStr != null && !endDateStr.trim().isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

                            List<BookingServiceProvidedPeriodReportViewModel> servicesProvidedPeriod =
                                    bookingServiceReportService.getServicesProvidedPeriodReport(startDate, endDate);
                            request.setAttribute(RequestAttribute.SERVICES_PROVIDED_PERIOD_REPORT, servicesProvidedPeriod);
                        } catch (DateTimeParseException e) {
                            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd format.");
                        }
                    }

                    request.setAttribute(RequestAttribute.START_DATE, startDateStr);
                    request.setAttribute(RequestAttribute.END_DATE, endDateStr);
                    break;

                case "servicesRequest":
                    List<BookingServiceRequestReportViewModel> servicesRequest =
                            bookingServiceReportService.getServicesRequestReportOfStaffId(staff.getStaffId());
                    request.setAttribute(RequestAttribute.SERVICES_REQUEST_REPORT, servicesRequest);
                    break;

                case "servicesCompleted":
                    List<BookingServiceCompletedReportViewModel> servicesCompleted =
                            bookingServiceReportService.getServicesCompletedReport();
                    request.setAttribute(RequestAttribute.SERVICES_COMPLETED_REPORT, servicesCompleted);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid report type");
            }

            request.setAttribute(RequestAttribute.REPORT_TYPE, reportType);
            request.getRequestDispatcher(Page.SERVICE_REPORT_PAGE).forward(request, response);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
