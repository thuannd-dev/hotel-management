package com.hotel_management.infrastructure.dao.housekeeping;

import com.hotel_management.domain.dto.housekeeping.PendingCleaningTaskReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PendingCleaningTaskReportDAO extends BaseDAO<PendingCleaningTaskReportViewModel>{

    public PendingCleaningTaskReportDAO(DataSource ds) { super(ds); }

    @Override
    public PendingCleaningTaskReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new PendingCleaningTaskReportViewModel(
                rs.getString("RoomNumber"),
                rs.getString("RoomStatus"),
                rs.getString("Priority"),
                rs.getString("TaskType"),
                rs.getString("AssignedStaff"),
                rs.getString("TaskStatus"),
                rs.getDate("AssignedDate") == null ? null : rs.getDate("AssignedDate").toLocalDate()
        );
    }

    public List<PendingCleaningTaskReportViewModel> getReport() {
        return query("SELECT \n" +
                "    R.RoomNumber,\n" +
                "    R.Status AS RoomStatus,\n" +
                "    HT.Priority,\n" +
                "    HT.TaskType,\n" +
                "    ISNULL(S.FullName, N'Not assigned yet') AS AssignedStaff,\n" +
                "    HT.Status AS TaskStatus,\n" +
                "    HT.AssignedDate\n" +
                "FROM HOUSEKEEPING_TASK HT\n" +
                "JOIN ROOM R ON HT.RoomID = R.RoomID\n" +
                "LEFT JOIN STAFF S ON HT.StaffID = S.StaffID\n" +
                "WHERE \n" +
                "    HT.Status IN ('Pending', 'In Progress')\n" +
                "ORDER BY \n" +
                "    CASE HT.Priority \n" +
                "        WHEN 'Urgent' THEN 1\n" +
                "        WHEN 'High' THEN 2\n" +
                "        WHEN 'Medium' THEN 3\n" +
                "        WHEN 'Low' THEN 4\n" +
                "    END,\n" +
                "    R.RoomNumber");
    }


}
