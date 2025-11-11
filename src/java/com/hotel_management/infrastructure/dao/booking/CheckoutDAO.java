package com.hotel_management.infrastructure.dao.booking;

import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for checkout calculations
 * @author thuannd.dev
 */
public class CheckoutDAO extends BaseDAO<Object> {

    public CheckoutDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Object mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("mapRow is not supported in CheckoutDAO"); // Not used for this DAO
    }

    /**
     * Calculate room charges for a booking
     */
    public BigDecimal calculateRoomCharges(int bookingId) {
        String sql = "SELECT " +
                "DATEDIFF(DAY, B.CheckInDate, B.CheckOutDate) * RT.PricePerNight AS RoomCharges " +
                "FROM BOOKING B " +
                "INNER JOIN ROOM R ON B.RoomID = R.RoomID " +
                "INNER JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID " +
                "WHERE B.BookingID = ?";

        try (java.sql.Connection conn = getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("RoomCharges");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculate service charges for completed services in a booking
     */
    public BigDecimal calculateServiceCharges(int bookingId) {
        String sql = "SELECT ISNULL(SUM(BS.Quantity * S.Price), 0) AS ServiceCharges " +
                "FROM BOOKING_SERVICE BS " +
                "INNER JOIN SERVICE S ON BS.ServiceID = S.ServiceID " +
                "WHERE BS.BookingID = ? AND BS.Status = 'Completed'";

        try (java.sql.Connection conn = getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("ServiceCharges");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
