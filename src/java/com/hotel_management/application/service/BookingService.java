package com.hotel_management.application.service;

import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.domain.dto.booking.BookingViewModel;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class BookingService {
    private final BookingDAO bookingDao;
    private final BookingDetailDAO bookingDetailDao;

    public BookingService(BookingDAO bookingDao, BookingDetailDAO bookingDetailDao) {
        this.bookingDao = bookingDao;
        this.bookingDetailDao = bookingDetailDao;
    }

    // (method reference)
    public List<BookingViewModel> getAllBookings() {
        return bookingDao.findAll().stream()
                .map(BookingViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public BookingViewModel getBookingById(int id) {
        Booking booking = bookingDao.findById(id).orElse(null);
        return booking != null ? BookingViewModel.fromEntity(booking) : null;
    }

    public List<BookingDetailViewModel> getAllCheckInBookingDetails() {
        return bookingDetailDao.findByStatus(BookingStatus.CHECK_IN);
    }

    public BookingDetailViewModel getCheckInBookingDetailById(int id) {
        BookingDetailViewModel booking = bookingDetailDao.findById(id).orElse(null);
        return booking != null &&
                booking.getStatus().equalsIgnoreCase(BookingStatus.CHECK_IN.getDbValue())
                ? booking : null;
    }
}