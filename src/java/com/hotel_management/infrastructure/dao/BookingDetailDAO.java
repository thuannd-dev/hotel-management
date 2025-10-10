package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.booking.BookingDetailViewModel;
import com.hotel_management.domain.entity.enums.BookingStatus;
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
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, G.FullName, G.Phone,\n" +
                "G.IDNumber, B.RoomID, R.RoomNumber, B.CheckInDate,\n" +
                "B.CheckOutDate, B.BookingDate, B.Status, B.TotalGuests,\n" +
                "B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "LEFT JOIN ROOM R ON B.RoomID = R.RoomID\n" +
                "LEFT JOIN GUEST G ON B.GuestID = G.GuestID");
    }

    public Optional<BookingDetailViewModel> findById(int id) {
        List<BookingDetailViewModel> bookings = query("SELECT\n" +
                "B.BookingID, B.GuestID, G.FullName, G.Phone,\n" +
                "G.IDNumber, B.RoomID, R.RoomNumber, B.CheckInDate,\n" +
                "B.CheckOutDate, B.BookingDate, B.Status, B.TotalGuests,\n" +
                "B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "LEFT JOIN ROOM R ON B.RoomID = R.RoomID\n" +
                "LEFT JOIN GUEST G ON B.GuestID = G.GuestID\n" +
                "WHERE B.BookingID = ?", id);
        return bookings.stream().findFirst();
    }

    public List<BookingDetailViewModel> findByStatus(BookingStatus status) {
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, G.FullName, G.Phone,\n" +
                "G.IDNumber, B.RoomID, R.RoomNumber, B.CheckInDate,\n" +
                "B.CheckOutDate, B.BookingDate, B.Status, B.TotalGuests,\n" +
                "B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "LEFT JOIN ROOM R ON B.RoomID = R.RoomID\n" +
                "LEFT JOIN GUEST G ON B.GuestID = G.GuestID\n" +
                "WHERE B.Status = ?", status.getDbValue());
    }
}