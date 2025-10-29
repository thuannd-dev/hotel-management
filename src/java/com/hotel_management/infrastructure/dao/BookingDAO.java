package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.PaymentStatus;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
        String sql = "SELECT BookingID, GuestID, RoomID, CheckInDate, CheckOutDate, BookingDate, Status, TotalGuests, SpecialRequests, PaymentStatus, CancellationDate, CancellationReason "
                + "FROM BOOKING\n"
                + "WHERE GuestID = ?\n"
                + "AND PaymentStatus IN ('Pending', 'Deposit Paid', 'Guaranteed');";
        List<Booking> result = query(sql, guestId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public int markAsPaid(int bookingId) {
        String sql = "UPDATE BOOKING SET PaymentStatus = ? WHERE BookingID = ?";
        return update(sql, PaymentStatus.PAID.getDbValue(), bookingId);
    }

    public int updateBookingStatus(int bookingId, BookingStatus status) {
        String sql = "UPDATE BOOKING SET Status = ? WHERE BookingID = ?";
        return update(sql, status.getDbValue(), bookingId);
    }

    public int updateBookingStatusAndPaymentStatus(int bookingId, BookingStatus status, PaymentStatus paymentStatus) {
        String sql = "UPDATE BOOKING SET Status = ?, PaymentStatus = ? WHERE BookingID = ?";
        return update(sql, status.getDbValue(), paymentStatus.getDbValue(), bookingId);
    }

    // Calculate total price based on room price and number of days
    public BigDecimal calculateTotalAmount(int bookingId) {
        String sql = "SELECT "
                + "DATEDIFF(day, b.CheckInDate, b.CheckOutDate) * rt.PricePerNight AS TotalAmount "
                + "FROM BOOKING b "
                + "JOIN ROOM r ON b.RoomID = r.RoomID "
                + "JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID "
                + "WHERE b.BookingID = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("TotalAmount");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}