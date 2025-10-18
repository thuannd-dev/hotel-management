package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.HousekeepingTask;
import com.hotel_management.domain.entity.enums.Priority;
import com.hotel_management.domain.entity.enums.TaskStatus;
import com.hotel_management.domain.entity.enums.TaskType;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author thuannd.dev
 */
public class HousekeepingTaskDAO extends BaseDAO<HousekeepingTask> {

    public HousekeepingTaskDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public HousekeepingTask mapRow(ResultSet rs) throws SQLException {
        return new HousekeepingTask(
                rs.getInt("TaskID"),
                rs.getInt("RoomID"),
                rs.getObject("StaffID") != null ? rs.getInt("StaffID") : null,
                TaskType.fromDbValue(rs.getString("TaskType")),
                TaskStatus.fromDbValue(rs.getString("Status")),
                rs.getObject("AssignedDate") != null ? rs.getDate("AssignedDate").toLocalDate() : null,
                rs.getObject("CompletedDate") != null ? rs.getDate("CompletedDate").toLocalDate() : null,
                Priority.fromDbValue(rs.getString("Priority"))
        );
    }

    /**
     * Creates a new housekeeping task
     * @param roomId Room ID
     * @param taskType Type of task (Regular, Deep, Post-Checkout)
     * @param priority Priority level
     * @return Generated task ID, or 0 if failed
     */
    public int createTask(int roomId, TaskType taskType, Priority priority) {
        String sql = "INSERT INTO HOUSEKEEPING_TASK (RoomID, TaskType, Status, Priority) " +
                     "VALUES (?, ?, ?, ?)";
        return insertAndReturnId(sql,
                roomId,
                taskType.getDbValue(),
                TaskStatus.PENDING.getDbValue(),
                priority.getDbValue()
        );
    }

    /**
     * Updates task status
     */
    public int updateTaskStatus(int taskId, TaskStatus status) {
        String sql = "UPDATE HOUSEKEEPING_TASK SET Status = ? WHERE TaskID = ?";
        return update(sql, status.getDbValue(), taskId);
    }

    /**
     * Completes a task
     */
    public int completeTask(int staffId, int roomId) {
        Date now = Date.valueOf(LocalDate.now());
        String sql = "UPDATE HOUSEKEEPING_TASK SET StaffID = ?, Status = ?, CompletedDate = ? WHERE RoomID = ? AND Status = ?";
        return update(sql, staffId, TaskStatus.COMPLETED.getDbValue(), now, roomId, TaskStatus.PENDING.getDbValue());
    }
}
