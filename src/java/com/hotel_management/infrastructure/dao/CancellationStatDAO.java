package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.manager.CancellationStatViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * DAO for Cancellation Statistics Report
 * @author Chauu
 */
public class CancellationStatDAO extends BaseDAO<CancellationStatViewModel> {

    public CancellationStatDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public CancellationStatViewModel mapRow(ResultSet rs) throws SQLException {
        return new CancellationStatViewModel(
                rs.getDate("CancellationDate") != null ? rs.getDate("CancellationDate").toLocalDate() : null,
                rs.getInt("CancellationCount"),
                rs.getString("TopReason"),
                rs.getInt("TotalBookings"),
                rs.getDouble("CancellationRate")
        );
    }

    /**
     * Get cancellation statistics by date range
     */
    public List<CancellationStatViewModel> getCancellationStatsByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        String sql = 
                "WITH CancellationData AS ( " +
                "    SELECT " +
                "        CAST(CancellationDate AS DATE) AS CancellationDate, " +
                "        COUNT(*) AS CancellationCount, " +
                "        ( " +
                "            SELECT TOP 1 CancellationReason " +
                "            FROM BOOKING B2 " +
                "            WHERE CAST(B2.CancellationDate AS DATE) = CAST(B.CancellationDate AS DATE) " +
                "                AND B2.CancellationReason IS NOT NULL " +
                "            GROUP BY CancellationReason " +
                "            ORDER BY COUNT(*) DESC " +
                "        ) AS TopReason " +
                "    FROM BOOKING B " +
                "    WHERE Status = 'Canceled' " +
                "        AND CancellationDate BETWEEN ? AND ? " +
                "    GROUP BY CAST(CancellationDate AS DATE) " +
                "), " +
                "TotalBookingsData AS ( " +
                "    SELECT " +
                "        CAST(BookingDate AS DATE) AS BookingDate, " +
                "        COUNT(*) AS TotalBookings " +
                "    FROM BOOKING " +
                "    WHERE BookingDate BETWEEN ? AND ? " +
                "    GROUP BY CAST(BookingDate AS DATE) " +
                ") " +
                "SELECT " +
                "    CD.CancellationDate, " +
                "    CD.CancellationCount, " +
                "    CD.TopReason, " +
                "    ISNULL(TBD.TotalBookings, CD.CancellationCount) AS TotalBookings, " +
                "    CAST((CD.CancellationCount * 100.0 / ISNULL(TBD.TotalBookings, CD.CancellationCount)) AS DECIMAL(5,2)) AS CancellationRate " +
                "FROM CancellationData CD " +
                "LEFT JOIN TotalBookingsData TBD ON CD.CancellationDate = TBD.BookingDate " +
                "ORDER BY CD.CancellationDate DESC";
        
        return query(sql, startDate, endDate, startDate, endDate);
    }

    /**
     * Get overall cancellation statistics
     */
    public CancellationStatViewModel getOverallCancellationStats() {
        String sql = 
                "WITH CancellationData AS ( " +
                "    SELECT " +
                "        COUNT(*) AS CancellationCount, " +
                "        ( " +
                "            SELECT TOP 1 CancellationReason " +
                "            FROM BOOKING " +
                "            WHERE Status = 'Canceled' AND CancellationReason IS NOT NULL " +
                "            GROUP BY CancellationReason " +
                "            ORDER BY COUNT(*) DESC " +
                "        ) AS TopReason " +
                "    FROM BOOKING " +
                "    WHERE Status = 'Canceled' " +
                "), " +
                "TotalBookingsData AS ( " +
                "    SELECT COUNT(*) AS TotalBookings " +
                "    FROM BOOKING " +
                ") " +
                "SELECT " +
                "    NULL AS CancellationDate, " +
                "    CD.CancellationCount, " +
                "    CD.TopReason, " +
                "    TBD.TotalBookings, " +
                "    CAST((CD.CancellationCount * 100.0 / TBD.TotalBookings) AS DECIMAL(5,2)) AS CancellationRate " +
                "FROM CancellationData CD, TotalBookingsData TBD";
        
        List<CancellationStatViewModel> results = query(sql);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Get monthly cancellation statistics for a year
     */
    public List<CancellationStatViewModel> getMonthlyCancellationStats(int year) {
        String sql = 
                "WITH Months AS ( " +
                "    SELECT 1 AS Month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 " +
                "    UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 " +
                "    UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 " +
                "), " +
                "CancellationData AS ( " +
                "    SELECT " +
                "        MONTH(CancellationDate) AS Month, " +
                "        COUNT(*) AS CancellationCount, " +
                "        ( " +
                "            SELECT TOP 1 CancellationReason " +
                "            FROM BOOKING B2 " +
                "            WHERE YEAR(B2.CancellationDate) = ? " +
                "                AND MONTH(B2.CancellationDate) = MONTH(B.CancellationDate) " +
                "                AND B2.CancellationReason IS NOT NULL " +
                "            GROUP BY CancellationReason " +
                "            ORDER BY COUNT(*) DESC " +
                "        ) AS TopReason " +
                "    FROM BOOKING B " +
                "    WHERE Status = 'Canceled' " +
                "        AND YEAR(CancellationDate) = ? " +
                "    GROUP BY MONTH(CancellationDate) " +
                "), " +
                "TotalBookingsData AS ( " +
                "    SELECT " +
                "        MONTH(BookingDate) AS Month, " +
                "        COUNT(*) AS TotalBookings " +
                "    FROM BOOKING " +
                "    WHERE YEAR(BookingDate) = ? " +
                "    GROUP BY MONTH(BookingDate) " +
                ") " +
                "SELECT " +
                "    DATEFROMPARTS(?, M.Month, 1) AS CancellationDate, " +
                "    ISNULL(CD.CancellationCount, 0) AS CancellationCount, " +
                "    CD.TopReason, " +
                "    ISNULL(TBD.TotalBookings, 0) AS TotalBookings, " +
                "    CASE  " +
                "        WHEN ISNULL(TBD.TotalBookings, 0) = 0 THEN 0 " +
                "        ELSE CAST((ISNULL(CD.CancellationCount, 0) * 100.0 / TBD.TotalBookings) AS DECIMAL(5,2)) " +
                "    END AS CancellationRate " +
                "FROM Months M " +
                "LEFT JOIN CancellationData CD ON M.Month = CD.Month " +
                "LEFT JOIN TotalBookingsData TBD ON M.Month = TBD.Month " +
                "WHERE ISNULL(TBD.TotalBookings, 0) > 0 " +
                "ORDER BY M.Month";
        
        return query(sql, year, year, year, year);
    }
}



