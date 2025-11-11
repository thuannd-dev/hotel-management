package com.hotel_management.application.service.booking;

import com.hotel_management.domain.dto.booking.BookingCreateModel;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking.BookingViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.booking.BookingDAO;
import com.hotel_management.infrastructure.dao.booking.BookingDetailDAO;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingService {
    private final BookingDAO bookingDao;
    private final BookingDetailDAO bookingDetailDao;

    public BookingService(BookingDAO bookingDao, BookingDetailDAO bookingDetailDao) {
        this.bookingDao = bookingDao;
        this.bookingDetailDao = bookingDetailDao;
    }

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

    public List<BookingDetailViewModel> getCheckInBookingDetailsByGuestName(String name) {
        return bookingDetailDao.findByFullNameAndStatus(name, BookingStatus.CHECK_IN);
    }

    public BookingDetailViewModel getCheckInBookingDetailById(int id) {
        BookingDetailViewModel booking = bookingDetailDao.findById(id).orElse(null);
        return booking != null &&
                booking.getStatus().equalsIgnoreCase(BookingStatus.CHECK_IN.getDbValue())
                ? booking : null;
    }

    public BookingDetailViewModel getBookingDetailById(int bookingId) {
        return bookingDetailDao.findById(bookingId).orElse(null);
    }

    public BookingDetailViewModel getCheckInBookingDetailByRoomNumber(String roomNumber) {
        return bookingDetailDao.findByRoomNumberAndStatus(roomNumber, BookingStatus.CHECK_IN).orElse(null);
    }

    public BookingDetailViewModel getCheckInBookingDetailByGuestPhone(String phone) {
        return bookingDetailDao.findByGuestPhoneAndStatus(phone, BookingStatus.CHECK_IN).orElse(null);
    }

    public BookingDetailViewModel getCheckInBookingDetailByGuestIdNumber(String idNumber) {
        return bookingDetailDao.findByGuestIdNumberAndStatus(idNumber, BookingStatus.CHECK_IN).orElse(null);
    }

    public List<BookingDetailViewModel> findBookings(String searchType, String query) throws ServletException {
        if (searchType == null && query == null) {
            return getAllCheckInBookingDetails();
        }
        switch (Objects.requireNonNull(searchType, "Search type cannot be null")) {
            case "guestName":
                return getCheckInBookingDetailsByGuestName(query);
            case "roomNumber":
                BookingDetailViewModel byRoom = getCheckInBookingDetailByRoomNumber(query);
                return byRoom == null ? Collections.emptyList() : Collections.singletonList(byRoom);
            case "guestPhone":
                BookingDetailViewModel byPhone = getCheckInBookingDetailByGuestPhone(query);
                return byPhone == null ? Collections.emptyList() : Collections.singletonList(byPhone);
            case "guestIdNumber":
                BookingDetailViewModel byId = getCheckInBookingDetailByGuestIdNumber(query);
                return byId == null ? Collections.emptyList() : Collections.singletonList(byId);
            default:
                throw new ServletException("Invalid search type");
        }
    }

    // CREATE NEW BOOKING
    public int bookingCreate(BookingCreateModel model) {
        return bookingDao.bookingCreate(BookingCreateModel.toEntity(model));
    }


    public List<BookingDetailViewModel> getBookingsByGuestId(int guestId) {
        return bookingDetailDao.findByGuestId(guestId);
    }
}
