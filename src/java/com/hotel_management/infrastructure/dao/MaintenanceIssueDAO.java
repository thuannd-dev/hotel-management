package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.MaintenanceIssue;
import com.hotel_management.domain.entity.enums.IssueStatus;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author thuannd.dev
 */
public class MaintenanceIssueDAO extends BaseDAO<MaintenanceIssue> {

    public MaintenanceIssueDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public MaintenanceIssue mapRow(ResultSet rs) throws SQLException {
        return new MaintenanceIssue(
                rs.getInt("IssueID"),
                rs.getInt("RoomID"),
                rs.getInt("ReportedByStaffID"),
                rs.getString("IssueDescription"),
                rs.getObject("ReportDate") != null ? rs.getDate("ReportDate").toLocalDate() : null,
                IssueStatus.fromDbValue(rs.getString("Status")),
                rs.getObject("ResolvedByStaffID") != null ? rs.getInt("ResolvedByStaffID") : null,
                rs.getObject("ResolutionDate") != null ? rs.getDate("ResolutionDate").toLocalDate() : null
        );
    }

    /**
     * Creates a new maintenance issue
     * @param roomId Room ID
     * @param reportedByStaffId Staff ID who reported the issue
     * @param description Issue description
     * @return Generated issue ID, or 0 if failed
     */
    public int createIssue(int roomId, int reportedByStaffId, String description) {
        String sql = "INSERT INTO MAINTENANCE_ISSUE (RoomID, ReportedByStaffID, IssueDescription, Status) " +
                     "VALUES (?, ?, ?, ?)";
        return insertAndReturnId(sql,
                roomId,
                reportedByStaffId,
                description,
                IssueStatus.REPORTED.getDbValue()
        );
    }

    /**
     * Updates issue status
     */
    public int updateIssueStatus(int issueId, IssueStatus status) {
        String sql = "UPDATE MAINTENANCE_ISSUE SET Status = ? WHERE IssueID = ?";
        return update(sql, status.getDbValue(), issueId);
    }

    /**
     * Resolves an issue
     */
    public int resolveIssue(int issueId, int resolvedByStaffId) {
        Date now = Date.valueOf(LocalDate.now());
        String sql = "UPDATE MAINTENANCE_ISSUE SET Status = ?, ResolvedByStaffID = ?, ResolutionDate = ? WHERE IssueID = ?";
        return update(sql, IssueStatus.RESOLVED.getDbValue(), resolvedByStaffId, now, issueId);
    }

    /**
     * Finds the most recent active (non-resolved) maintenance issue for a room
     * @param roomId Room ID
     * @return MaintenanceIssue if found, null otherwise
     */
    public MaintenanceIssue findActiveIssueByRoomId(int roomId) {
        String sql = "SELECT TOP 1 * FROM MAINTENANCE_ISSUE " +
                     "WHERE RoomID = ? AND Status != ? " +
                     "ORDER BY ReportDate DESC";
        List<MaintenanceIssue> results = query(sql, roomId, IssueStatus.RESOLVED.getDbValue());
        return results.isEmpty() ? null : results.get(0);
    }
}
