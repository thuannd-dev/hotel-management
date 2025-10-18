package com.hotel_management.infrastructure.dao.housekeeping;

import com.hotel_management.domain.dto.housekeeping.RoomStatusReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoomStatusReportDAO extends BaseDAO<RoomStatusReportViewModel> {

    public RoomStatusReportDAO(DataSource ds) { super(ds); }

    @Override
    public RoomStatusReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new RoomStatusReportViewModel(
                rs.getString("RoomNumber"),
                rs.getString("RoomType"),
                rs.getString("Status"),
                rs.getDate("LastCleanedDate") == null ? null : rs.getDate("LastCleanedDate").toLocalDate(),
                rs.getDate("NextCheckIn") == null ? null : rs.getDate("NextCheckIn").toLocalDate()
        );
    }

    public List<RoomStatusReportViewModel> getReport() {
        return query("SELECT \n" +
                "    R.RoomNumber,\n" +
                "    RT.TypeName AS RoomType,\n" +
                "    R.Status,\n" +
                "    MAX(HT.CompletedDate) AS LastCleanedDate,\n" +
                "    MIN(B.CheckInDate) AS NextCheckIn\n" +
                "FROM ROOM R\n" +
                "JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID\n" +
                "LEFT JOIN HOUSEKEEPING_TASK HT ON R.RoomID = HT.RoomID\n" +
                "LEFT JOIN BOOKING B \n" +
                "    ON R.RoomID = B.RoomID \n" +
                "    AND B.Status IN ('Reserved', 'Checked-in')\n" +
                "    AND B.CheckInDate >= CAST(GETDATE() AS DATE)\n" +
                "GROUP BY \n" +
                "    R.RoomNumber, RT.TypeName, R.Status\n" +
                "ORDER BY \n" +
                "    R.RoomNumber");
    }

}
