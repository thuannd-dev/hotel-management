package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.manager.RoomOccupancyViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * DAO for Room Occupancy Report
 * @author Chauu
 */
public class RoomOccupancyDAO extends BaseDAO<RoomOccupancyViewModel> {

    public RoomOccupancyDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public RoomOccupancyViewModel mapRow(ResultSet rs) throws SQLException {
        return new RoomOccupancyViewModel(
                rs.getInt("Month"),
                rs.getInt("Year"),
                rs.getInt("TotalRooms"),
                rs.getInt("TotalDaysInMonth"),
                rs.getInt("OccupiedRoomDays"),
                rs.getDouble("OccupancyRate")
        );
    }

    /**
     * Get room occupancy rate for a specific year
     */
    public List<RoomOccupancyViewModel> getRoomOccupancyByYear(int year) {
        String sql = 
                "WITH MonthlyData AS ( " +
                "    SELECT " +
                "        MONTH(CheckInDate) AS Month, " +
                "        YEAR(CheckInDate) AS Year, " +
                "        (SELECT COUNT(*) FROM ROOM) AS TotalRooms, " +
                "        DAY(EOMONTH(DATEFROMPARTS(?, MONTH(CheckInDate), 1))) AS TotalDaysInMonth, " +
                "        SUM(DATEDIFF(DAY,  " +
                "            CASE WHEN CheckInDate < DATEFROMPARTS(?, MONTH(CheckInDate), 1)  " +
                "                THEN DATEFROMPARTS(?, MONTH(CheckInDate), 1) ELSE CheckInDate END, " +
                "            CASE WHEN CheckOutDate > EOMONTH(DATEFROMPARTS(?, MONTH(CheckInDate), 1))  " +
                "                THEN DATEADD(DAY, 1, EOMONTH(DATEFROMPARTS(?, MONTH(CheckInDate), 1))) ELSE CheckOutDate END " +
                "        )) AS OccupiedRoomDays " +
                "    FROM BOOKING " +
                "    WHERE YEAR(CheckInDate) = ? " +
                "        AND Status IN ('Checked-in', 'Checked-out') " +
                "        AND ( " +
                "            (YEAR(CheckInDate) = ? AND YEAR(CheckOutDate) = ?) " +
                "            OR (YEAR(CheckInDate) = ? AND YEAR(CheckOutDate) > ?) " +
                "        ) " +
                "    GROUP BY MONTH(CheckInDate), YEAR(CheckInDate) " +
                ") " +
                "SELECT " +
                "    Month, " +
                "    Year, " +
                "    TotalRooms, " +
                "    TotalDaysInMonth, " +
                "    OccupiedRoomDays, " +
                "    CAST((OccupiedRoomDays * 100.0 / (TotalRooms * TotalDaysInMonth)) AS DECIMAL(5,2)) AS OccupancyRate " +
                "FROM MonthlyData " +
                "ORDER BY Month";
        
        return query(sql, year, year, year, year, year, year, year, year, year, year);
    }

    /**
     * Get room occupancy rate for a specific month
     */
    public RoomOccupancyViewModel getRoomOccupancyByMonth(int year, int month) {
        String sql = 
                "WITH MonthlyData AS ( " +
                "    SELECT " +
                "        ? AS Month, " +
                "        ? AS Year, " +
                "        (SELECT COUNT(*) FROM ROOM) AS TotalRooms, " +
                "        DAY(EOMONTH(DATEFROMPARTS(?, ?, 1))) AS TotalDaysInMonth, " +
                "        SUM(DATEDIFF(DAY,  " +
                "            CASE WHEN CheckInDate < DATEFROMPARTS(?, ?, 1)  " +
                "                THEN DATEFROMPARTS(?, ?, 1) ELSE CheckInDate END, " +
                "            CASE WHEN CheckOutDate > EOMONTH(DATEFROMPARTS(?, ?, 1))  " +
                "                THEN DATEADD(DAY, 1, EOMONTH(DATEFROMPARTS(?, ?, 1))) ELSE CheckOutDate END " +
                "        )) AS OccupiedRoomDays " +
                "    FROM BOOKING " +
                "    WHERE Status IN ('Checked-in', 'Checked-out') " +
                "        AND ( " +
                "            (CheckInDate <= EOMONTH(DATEFROMPARTS(?, ?, 1)) AND CheckOutDate >= DATEFROMPARTS(?, ?, 1)) " +
                "        ) " +
                ") " +
                "SELECT " +
                "    Month, " +
                "    Year, " +
                "    TotalRooms, " +
                "    TotalDaysInMonth, " +
                "    ISNULL(OccupiedRoomDays, 0) AS OccupiedRoomDays, " +
                "    CAST((ISNULL(OccupiedRoomDays, 0) * 100.0 / (TotalRooms * TotalDaysInMonth)) AS DECIMAL(5,2)) AS OccupancyRate " +
                "FROM MonthlyData";
        
        List<RoomOccupancyViewModel> results = query(sql, 
                month, year, year, month, year, month, year, month, year, month, year, month, year, month, year, month);
        
        return results.isEmpty() ? null : results.get(0);
    }
}

