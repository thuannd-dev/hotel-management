package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.manager.FrequentGuestViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * DAO for Top Frequent Guests Report
 * @author Chauu
 */
public class FrequentGuestDAO extends BaseDAO<FrequentGuestViewModel> {

    public FrequentGuestDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public FrequentGuestViewModel mapRow(ResultSet rs) throws SQLException {
        return new FrequentGuestViewModel(
                rs.getInt("GuestID"),
                rs.getString("FullName"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getInt("BookingCount"),
                rs.getInt("TotalNights")
        );
    }

    /**
     * Get top N frequent guests based on booking count
     */
    public List<FrequentGuestViewModel> getTopFrequentGuests(int topN) {
        String sql = 
                "SELECT TOP (?) " +
                "    G.GuestID, " +
                "    G.FullName, " +
                "    G.Phone, " +
                "    G.Email, " +
                "    COUNT(B.BookingID) AS BookingCount, " +
                "    SUM(DATEDIFF(DAY, B.CheckInDate, B.CheckOutDate)) AS TotalNights " +
                "FROM GUEST G " +
                "INNER JOIN BOOKING B ON G.GuestID = B.GuestID " +
                "WHERE B.Status IN ('Checked-in', 'Checked-out') " +
                "GROUP BY G.GuestID, G.FullName, G.Phone, G.Email " +
                "ORDER BY BookingCount DESC, TotalNights DESC";
        
        return query(sql, topN);
    }
}

