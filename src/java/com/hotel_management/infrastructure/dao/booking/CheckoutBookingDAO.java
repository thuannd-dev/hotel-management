package com.hotel_management.infrastructure.dao.booking;

import com.hotel_management.domain.dto.checkout.CheckoutBookingViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO for fetching checkout booking details
 * @author thuannd.dev
 */
public class CheckoutBookingDAO extends BaseDAO<CheckoutBookingViewModel> {

    public CheckoutBookingDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public CheckoutBookingViewModel mapRow(ResultSet rs) throws SQLException {
        return new CheckoutBookingViewModel(
                rs.getInt("BookingID"),
                rs.getString("RoomNumber"),
                rs.getString("TypeName"),
                rs.getDate("CheckInDate").toLocalDate(),
                rs.getDate("CheckOutDate").toLocalDate(),
                rs.getInt("TotalGuests"),
                rs.getString("FullName"),
                rs.getString("Status")
        );
    }

    /**
     * Get all checked-in bookings for a specific guest
     */
    public List<CheckoutBookingViewModel> findCheckedInBookingsByGuestId(int guestId) {
        String sql = "SELECT B.BookingID, R.RoomNumber, RT.TypeName, " +
                "B.CheckInDate, B.CheckOutDate, B.TotalGuests, " +
                "G.FullName, B.Status " +
                "FROM BOOKING B " +
                "INNER JOIN ROOM R ON B.RoomID = R.RoomID " +
                "INNER JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID " +
                "INNER JOIN GUEST G ON B.GuestID = G.GuestID " +
                "WHERE B.GuestID = ? AND B.Status = 'Checked-in' " +
                "ORDER BY B.CheckInDate DESC";

        return query(sql, guestId);
    }

    /**
     * Get all checked-in bookings (for staff/admin)
     */
    public List<CheckoutBookingViewModel> findAllCheckedInBookings() {
        String sql = "SELECT B.BookingID, R.RoomNumber, RT.TypeName, " +
                "B.CheckInDate, B.CheckOutDate, B.TotalGuests, " +
                "G.FullName, B.Status " +
                "FROM BOOKING B " +
                "INNER JOIN ROOM R ON B.RoomID = R.RoomID " +
                "INNER JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID " +
                "INNER JOIN GUEST G ON B.GuestID = G.GuestID " +
                "WHERE B.Status = 'Checked-in' " +
                "ORDER BY B.CheckInDate DESC";

        return query(sql);
    }
}

