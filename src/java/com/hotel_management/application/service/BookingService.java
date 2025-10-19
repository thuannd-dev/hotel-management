package com.hotel_management.application.service;

import com.hotel_management.domain.dto.booking.BookingCreateModel;
import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.dto.booking.BookingViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.BookingDetailDAO;

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

    // 🧾 Lấy tất cả booking (ViewModel)
    public List<BookingViewModel> getAllBookings() {
        return bookingDao.findAll().stream()
                .map(BookingViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Lấy booking theo ID
    public BookingViewModel getBookingById(int id) {
        Booking booking = bookingDao.findById(id).orElse(null);
        return booking != null ? BookingViewModel.fromEntity(booking) : null;
    }

    // 🏨 Lấy danh sách booking có trạng thái CHECK_IN
    public List<BookingDetailViewModel> getAllCheckInBookingDetails() {
        return bookingDetailDao.findByStatus(BookingStatus.CHECK_IN);
    }

    // 🔍 Lấy chi tiết booking CHECK_IN theo tên khách
    public List<BookingDetailViewModel> getCheckInBookingDetailsByGuestName(String name) {
        return bookingDetailDao.findByFullNameAndStatus(name, BookingStatus.CHECK_IN);
    }

    // 🔍 Lấy booking detail CHECK_IN theo ID (cũ — chỉ CHECK_IN)
    public BookingDetailViewModel getCheckInBookingDetailById(int id) {
        BookingDetailViewModel booking = bookingDetailDao.findById(id).orElse(null);
        return booking != null &&
                booking.getStatus().equalsIgnoreCase(BookingStatus.CHECK_IN.getDbValue())
                ? booking : null;
    }

    // ✅ MỚI — Lấy chi tiết booking theo ID (bất kỳ trạng thái)
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

    // 🔍 Tìm booking theo loại tìm kiếm
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

    // ➕ Tạo booking mới
    public int bookingCreate(BookingCreateModel model) {
        return bookingDao.bookingCreate(BookingCreateModel.toEntity(model));
    }
}
