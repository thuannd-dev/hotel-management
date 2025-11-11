package com.hotel_management.infrastructure.dao.booking;

import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author thuannd.dev
 */
public class BookingDetailDAO extends BaseDAO<BookingDetailViewModel> {
    private static final String BASE_QUERY = "SELECT\n" +
            "B.BookingID, B.GuestID, G.FullName, G.Phone,\n" +
            "G.IDNumber, B.RoomID, R.RoomNumber, B.CheckInDate,\n" +
            "B.CheckOutDate, B.BookingDate, B.Status, B.TotalGuests,\n" +
            "B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
            "B.CancellationReason FROM BOOKING B\n" +
            "LEFT JOIN ROOM R ON B.RoomID = R.RoomID\n" +
            "LEFT JOIN GUEST G ON B.GuestID = G.GuestID\n";
    public BookingDetailDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public BookingDetailViewModel mapRow(ResultSet rs) throws SQLException {
        return new BookingDetailViewModel(
                rs.getInt("BookingID"),
                rs.getInt("GuestID"),
                rs.getString("FullName"),
                rs.getString("Phone"),
                rs.getString("IDNumber"),
                rs.getInt("RoomID"),
                rs.getString("RoomNumber"),
                rs.getDate("CheckInDate").toLocalDate(),
                rs.getDate("CheckOutDate").toLocalDate(),
                rs.getDate("BookingDate").toLocalDate(),
                rs.getString("Status"),
                rs.getInt("TotalGuests"),
                rs.getString("SpecialRequests") != null ? rs.getString("SpecialRequests") : "",
                rs.getString("PaymentStatus"),
                rs.getDate("CancellationDate") != null ? rs.getDate("CancellationDate").toLocalDate() : null,
                rs.getString("CancellationReason") != null ? rs.getString("CancellationReason") : ""
        );
    }

    public List<BookingDetailViewModel> findAll() {
        return query(BASE_QUERY);
    }

    public Optional<BookingDetailViewModel> findById(int id) {
        String condition = "WHERE B.BookingID = ?";
        List<BookingDetailViewModel> bookings = query(BASE_QUERY + condition, id);
        return bookings.stream().findFirst();
    }

    public List<BookingDetailViewModel> findByStatus(BookingStatus status) {
        String condition = "WHERE B.Status = ?";
        return query(BASE_QUERY + condition, status.getDbValue());
    }

    public List<BookingDetailViewModel> findByFullNameAndStatus(String fullName, BookingStatus status) {
        String condition = "WHERE G.FullName COLLATE Latin1_General_CI_AI LIKE ? AND B.Status = ?";
        return query(BASE_QUERY + condition, "%" + fullName + "%", status.getDbValue());
    }

    public Optional<BookingDetailViewModel> findByRoomNumberAndStatus(String roomNumber, BookingStatus status) {
        String condition = "WHERE R.RoomNumber = ? AND B.Status = ?";
        List<BookingDetailViewModel> bookings = query(BASE_QUERY + condition, roomNumber, status.getDbValue());
        return bookings.stream().findFirst();
    }

    public Optional<BookingDetailViewModel> findByGuestPhoneAndStatus(String phone, BookingStatus status) {
        String condition = "WHERE G.Phone = ? AND B.Status = ?";
        List<BookingDetailViewModel> bookings = query(BASE_QUERY + condition, phone, status.getDbValue());
        return bookings.stream().findFirst();
    }

    public Optional<BookingDetailViewModel> findByGuestIdNumberAndStatus(String idNumber, BookingStatus status) {
        String condition = "WHERE G.IDNumber = ? AND B.Status = ?";
        List<BookingDetailViewModel> bookings = query(BASE_QUERY + condition, idNumber, status.getDbValue());
        return bookings.stream().findFirst();
    }

    public List<BookingDetailViewModel> findByGuestId(int guestId) {
        String condition = "WHERE B.GuestID = ? ORDER BY B.BookingDate DESC";
        return query(BASE_QUERY + condition, guestId);
    }
}