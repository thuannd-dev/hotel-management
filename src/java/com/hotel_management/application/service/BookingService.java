package com.hotel_management.application.service;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.Guest;
import com.hotel_management.domain.entity.Staff;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.presentation.dto.booking.BookingViewModel;
import com.hotel_management.presentation.dto.guest.GuestViewModel;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class BookingService {
    private final BookingDAO bookingDao;


    public BookingService(BookingDAO bookingDao) {
        this.bookingDao = bookingDao;
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

    public List<BookingViewModel> getAllCheckInBookings() {
        return bookingDao.findByStatus(BookingStatus.CHECK_IN).stream()
                .map(BookingViewModel::fromEntity)
                .collect(Collectors.toList());
    }
}