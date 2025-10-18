package com.hotel_management.infrastructure.dao.housekeeping;

import com.hotel_management.domain.dto.housekeeping.DailyRoomCleaningReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DailyRoomCleaningReportDAO extends BaseDAO<DailyRoomCleaningReportViewModel> {

    public DailyRoomCleaningReportDAO(DataSource ds) { super(ds); }

    @Override
    public DailyRoomCleaningReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new DailyRoomCleaningReportViewModel(
                rs.getDate("Date") == null ? null : rs.getDate("Date").toLocalDate(),
                rs.getString("RoomNumber"),
                rs.getString("CleaningType"),
                rs.getString("StaffName"),
                rs.getString("Status")
        );
    }

    public List<DailyRoomCleaningReportViewModel> getReport() {
        return query("SELECT \n" +
                "    CONVERT(DATE, HT.CompletedDate) AS [Date],\n" +
                "    R.RoomNumber,\n" +
                "    HT.TaskType AS CleaningType,\n" +
                "    S.FullName AS StaffName,\n" +
                "    HT.Status\n" +
                "FROM HOUSEKEEPING_TASK HT\n" +
                "JOIN ROOM R ON HT.RoomID = R.RoomID\n" +
                "LEFT JOIN STAFF S ON HT.StaffID = S.StaffID\n" +
                "WHERE \n" +
                "    HT.CompletedDate = CAST(GETDATE() AS DATE)\n" +
                "ORDER BY HT.CompletedDate DESC, R.RoomNumber");
    }

}
