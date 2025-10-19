package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.PaymentStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
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
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B");
    }

    public Optional<Booking> findById(int id) {
        List<Booking> bookings = query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "WHERE B.BookingID = ?", id);
        return bookings.stream().findFirst();
    }

    public List<Booking> findByStatus(BookingStatus status) {
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "WHERE B.Status = ?", status.getDbValue());
    }

    public int bookingCreate(Booking booking) {
        String sql = "INSERT INTO BOOKING (\n"
                + "    GuestID,\n"
                + "    RoomID,\n"
                + "    CheckInDate,\n"
                + "    CheckOutDate,\n"
                + "    Status,\n"
                + "    TotalGuests,\n"
                + "    SpecialRequests,\n"
                + "    PaymentStatus\n"
                + ")\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insertAndReturnId(sql, booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(), booking.getStatus(), booking.getTotalGuest(),
                booking.getSpecialRequests(), booking.getPaymentStatus());
    }
}