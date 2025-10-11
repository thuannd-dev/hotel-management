package com.hotel_management.application.service;

import com.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import com.hotel_management.domain.dto.booking_service.BookingServiceViewModel;
import com.hotel_management.domain.entity.BookingService;
import com.hotel_management.domain.entity.enums.BookingServiceStatus;
import com.hotel_management.infrastructure.dao.BookingServiceDAO;

/**
 *
 * @author thuannd.dev
 */
public class BookingServiceUsageService {

    private final BookingServiceDAO bookingServiceDao;

    public BookingServiceUsageService(BookingServiceDAO bookingServiceDao) {
        this.bookingServiceDao = bookingServiceDao;
    }

    public BookingServiceViewModel getBookingServiceById(int id) {
        BookingService bookingService = bookingServiceDao.findById(id).orElse(null);
        return bookingService != null ? BookingServiceViewModel.fromEntity(bookingService) : null;
    }

    public BookingServiceViewModel createBookingService(BookingServiceCreateModel bookingServiceCreateModel) {
        int newId = bookingServiceDao.insertBookingService(
                BookingServiceCreateModel.toEntity(bookingServiceCreateModel), BookingServiceStatus.REQUESTED
        );
        if (newId > 0) {
            return getBookingServiceById(newId);
        } else {
            // Handle insert failure: return null or throw an exception as appropriate
            return null;
        }
    }

}
