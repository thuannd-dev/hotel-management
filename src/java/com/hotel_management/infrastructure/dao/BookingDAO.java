package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.PaymentStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class BookingDAO extends BaseDAO<Booking> {

    public BookingDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Booking mapRow(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("BookingID"),
                rs.getInt("GuestID"),
                rs.getInt("RoomID"),
                rs.getDate("CheckInDate").toLocalDate(),
                rs.getDate("CheckOutDate").toLocalDate(),
                rs.getDate("BookingDate").toLocalDate(),
                BookingStatus.fromDbValue(rs.getString("Status")),
                rs.getInt("TotalGuests"),
                rs.getString("SpecialRequests") != null ? rs.getString("SpecialRequests") : "",
                PaymentStatus.fromDbValue(rs.getString("PaymentStatus")),
                rs.getDate("CancellationDate") != null ? rs.getDate("CancellationDate").toLocalDate() : null,
                rs.getString("CancellationReason") != null ? rs.getString("CancellationReason") : ""
        );
    }

    
    public List<Booking> findAll() {
        String sql = "SELECT B.BookingID, B.GuestID, B.RoomID, "
                + "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status, "
                + "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate, "
                + "B.CancellationReason FROM BOOKING B";
        return query(sql);
    }

    public Optional<Booking> findById(int id) {
        String sql = "SELECT B.BookingID, B.GuestID, B.RoomID, "
                + "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status, "
                + "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate, "
                + "B.CancellationReason FROM BOOKING B "
                + "WHERE B.BookingID = ?";
        List<Booking> bookings = query(sql, id);
        return bookings.stream().findFirst();
    }

    public List<Booking> findByStatus(BookingStatus status) {
        String sql = "SELECT B.BookingID, B.GuestID, B.RoomID, "
                + "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status, "
                + "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate, "
                + "B.CancellationReason FROM BOOKING B "
                + "WHERE B.Status = ?";
        return query(sql, status.getDbValue());
    }

    public int bookingCreate(Booking booking) {
        String sql = "INSERT INTO BOOKING ("
                + "GuestID, RoomID, CheckInDate, CheckOutDate, "
                + "Status, TotalGuests, SpecialRequests, PaymentStatus"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insertAndReturnId(sql,
                booking.getGuestId(),
                booking.getRoomId(),
                Date.valueOf(booking.getCheckInDate()),
                Date.valueOf(booking.getCheckOutDate()),
                booking.getStatus().getDbValue(),
                booking.getTotalGuests(),
                booking.getSpecialRequests(),
                booking.getPaymentStatus().getDbValue());
    }

    public Optional<Booking> findUnpaidBookingByGuestId(int guestId) {
        String sql = "SELECT * FROM BOOKING\n"
                + "WHERE GuestID = ?\n"
                + "AND PaymentStatus IN ('Pending', 'Deposit Paid', 'Guaranteed');";
        List<Booking> result = query(sql, guestId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public int markAsPaid(int bookingId) {
        String sql = "UPDATE BOOKING SET PaymentStatus = 'PAID' WHERE BookingID = ?";
        return update(sql, bookingId);
    }
}
