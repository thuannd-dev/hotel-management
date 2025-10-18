package com.hotel_management.infrastructure.dao.housekeeping;

import com.hotel_management.domain.dto.housekeeping.HousekeepingStaffPerformanceReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HousekeepingStaffPerformanceReportDAO extends BaseDAO<HousekeepingStaffPerformanceReportViewModel> {

    public HousekeepingStaffPerformanceReportDAO(DataSource ds) { super(ds); }

    @Override
    public HousekeepingStaffPerformanceReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new HousekeepingStaffPerformanceReportViewModel(
                rs.getString("StaffName"),
                rs.getInt("RoomsCleaned"),
                rs.getInt("DeepCleanings"),
                rs.getDate("Date") == null ? null : rs.getDate("Date").toLocalDate()
        );
    }

    public List<HousekeepingStaffPerformanceReportViewModel> getReport() {
        return query("SELECT \n" +
                "    S.FullName AS StaffName,\n" +
                "    COUNT(*) AS RoomsCleaned,\n" +
                "    SUM(CASE WHEN HT.TaskType = 'Deep' THEN 1 ELSE 0 END) AS DeepCleanings,\n" +
                "    CONVERT(DATE, HT.CompletedDate) AS [Date]\n" +
                "FROM HOUSEKEEPING_TASK HT\n" +
                "JOIN STAFF S ON HT.StaffID = S.StaffID\n" +
                "WHERE \n" +
                "    HT.Status = 'Completed'\n" +
                "    AND CONVERT(DATE, HT.CompletedDate) = CAST(GETDATE() AS DATE)\n" +
                "GROUP BY \n" +
                "    S.FullName, CONVERT(DATE, HT.CompletedDate)\n" +
                "ORDER BY \n" +
                "    RoomsCleaned DESC");
    }
}
