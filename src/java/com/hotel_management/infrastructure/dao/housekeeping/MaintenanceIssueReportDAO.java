package com.hotel_management.infrastructure.dao.housekeeping;

import com.hotel_management.domain.dto.housekeeping.MaintenanceIssueReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MaintenanceIssueReportDAO extends BaseDAO<MaintenanceIssueReportViewModel> {

    public MaintenanceIssueReportDAO(DataSource ds) { super(ds); }

    @Override
    public MaintenanceIssueReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new MaintenanceIssueReportViewModel(
            rs.getString("RoomNumber"),
            rs.getString("IssueDescription"),
            rs.getDate("ReportDate") == null ? null : rs.getDate("ReportDate").toLocalDate(),
            rs.getString("Status"),
            rs.getString("FixedBy")
        );
    }

    public List<MaintenanceIssueReportViewModel> getReport() {
        return query("SELECT \n" +
                "    R.RoomNumber,\n" +
                "    MI.IssueDescription,\n" +
                "    MI.ReportDate,\n" +
                "    MI.Status,\n" +
                "    ISNULL(SR.FullName, N'Not fixed yet') AS FixedBy\n" +
                "FROM MAINTENANCE_ISSUE MI\n" +
                "JOIN ROOM R ON MI.RoomID = R.RoomID\n" +
                "LEFT JOIN STAFF SR ON MI.ResolvedByStaffID = SR.StaffID\n" +
                "ORDER BY \n" +
                "    MI.Status, MI.ReportDate DESC");
    }

}
