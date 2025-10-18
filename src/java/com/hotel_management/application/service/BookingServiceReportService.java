package com.hotel_management.application.service;

import com.hotel_management.domain.dto.booking_service.BookingServiceCompletedReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceProvidedPeriodReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceRequestReportViewModel;
import com.hotel_management.domain.dto.booking_service.BookingServicesProvidedReportViewModel;
import com.hotel_management.infrastructure.dao.booking_service.*;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author thuannd.dev
 */
public class BookingServiceReportService {
    private final BookingServicesProvidedReportDAO bookingServicesProvidedReportDao;
    private final BookingServiceRequestReportDAO bookingServiceRequestReportDao;
    private final BookingServiceCompletedReportDAO bookingServiceCompletedReportDao;
    private final BookingServiceProvidedPeriodReportDAO bookingServiceProvidedPeriodReportDao;

    public BookingServiceReportService(BookingServicesProvidedReportDAO bookingServicesProvidedReportDao,
                                       BookingServiceRequestReportDAO bookingServiceRequestReportDao,
                                       BookingServiceCompletedReportDAO bookingServiceCompletedReportDao,
                                       BookingServiceProvidedPeriodReportDAO bookingServiceProvidedPeriodReportDao) {
        this.bookingServiceCompletedReportDao = bookingServiceCompletedReportDao;
        this.bookingServiceRequestReportDao = bookingServiceRequestReportDao;
        this.bookingServiceProvidedPeriodReportDao = bookingServiceProvidedPeriodReportDao;
        this.bookingServicesProvidedReportDao = bookingServicesProvidedReportDao;
    }

    public List<BookingServicesProvidedReportViewModel> getServicesProvidedTodayReportOfStaffId(int staffId) {
        return bookingServicesProvidedReportDao.getToDayReport(staffId);
    }

    public List<BookingServiceProvidedPeriodReportViewModel> getServicesProvidedPeriodReport(LocalDate startDate, LocalDate endDate) {
        return bookingServiceProvidedPeriodReportDao.getReport(startDate, endDate);
    }

    public List<BookingServiceRequestReportViewModel> getServicesRequestReportOfStaffId(int staffId) {
        return bookingServiceRequestReportDao.getReport(staffId);
    }

    public List<BookingServiceCompletedReportViewModel> getServicesCompletedReport() {
        return bookingServiceCompletedReportDao.getReport();
    }



}
