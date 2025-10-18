package com.hotel_management.application.service;

import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.entity.MaintenanceIssue;
import com.hotel_management.domain.entity.enums.Priority;
import com.hotel_management.domain.entity.enums.RoomStatus;
import com.hotel_management.domain.entity.enums.TaskType;
import com.hotel_management.infrastructure.dao.HousekeepingTaskDAO;
import com.hotel_management.infrastructure.dao.MaintenanceIssueDAO;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.RoomDetailDAO;

import java.util.List;

/**
 *
 * @author thuannd.dev
 */
public class RoomService {
    private final RoomDetailDAO roomDetailDao;
    private final RoomDAO roomDao;
    private final HousekeepingTaskDAO housekeepingTaskDao;
    private final MaintenanceIssueDAO maintenanceIssueDao;

    public RoomService(RoomDAO roomDao, RoomDetailDAO roomDetailDao,
                       HousekeepingTaskDAO housekeepingTaskDao,
                       MaintenanceIssueDAO maintenanceIssueDao) {
        this.roomDetailDao = roomDetailDao;
        this.roomDao = roomDao;
        this.housekeepingTaskDao = housekeepingTaskDao;
        this.maintenanceIssueDao = maintenanceIssueDao;
    }

    public List<RoomDetailViewModel> getAllRoomDetails() {
        return roomDetailDao.findAll();
    }
    public RoomDetailViewModel getRoomDetailById(int id) {
        return roomDetailDao.findById(id).orElse(null);
    }

    public List<RoomDetailViewModel> getRoomDetailsByStatus(RoomStatus status) {
        return roomDetailDao.findByStatus(status);
    }

    public Boolean updateRoomStatus(int staffId, int roomId, RoomStatus newStatus) {
        // Get current room detail to check current status
        RoomDetailViewModel currentRoom = roomDetailDao.findById(roomId).orElse(null);
        if (currentRoom == null) {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }

        // Get current status
        RoomStatus currentStatus = RoomStatus.fromDbValue(currentRoom.getStatus());

        // Validate transition
        if (!RoomStatus.isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException(
                String.format("Invalid status transition from %s to %s",
                    currentStatus.getDbValue(), newStatus.getDbValue())
            );
        }

        // Update room status
        boolean isUpdated = roomDao.updateRoomStatus(roomId, newStatus) > 0;

        if (isUpdated) {
            // Create tracking records based on status change
            createTrackingRecords(staffId, roomId, currentStatus, newStatus);
        }

        return isUpdated;
    }

    /**
     * Creates tracking records (tasks/issues) based on room status transitions
     * Business rules:
     * - Available -> Dirty: Create regular cleaning task (Medium priority)
     * - Available -> Maintenance: Create maintenance issue
     * - Dirty -> Available: update housekeeping task as completed
     * - Dirty -> Maintenance: Create maintenance issue
     * - Maintenance -> Available: Resolve active maintenance issue and update housekeeping task as completed
     */
    private void createTrackingRecords(int staffId, int roomId, RoomStatus fromStatus, RoomStatus toStatus) {

        // Available -> Dirty: Regular cleaning needed
        if (fromStatus == RoomStatus.AVAILABLE && toStatus == RoomStatus.DIRTY) {
            housekeepingTaskDao.createTask(roomId, TaskType.REGULAR, Priority.MEDIUM);
        }

        // Available -> Maintenance: Maintenance issue reported
        else if (fromStatus == RoomStatus.AVAILABLE && toStatus == RoomStatus.MAINTENANCE) {
            maintenanceIssueDao.createIssue(roomId, staffId,
                    "Room marked for maintenance from available status");
        }

        // Dirty -> Available: Post-checkout cleaning completed
        else if (fromStatus == RoomStatus.DIRTY && toStatus == RoomStatus.AVAILABLE) {
            housekeepingTaskDao.completeTask(staffId, roomId);
        }

        // Dirty -> Maintenance: maintenance needed
        else if (fromStatus == RoomStatus.DIRTY && toStatus == RoomStatus.MAINTENANCE) {
            maintenanceIssueDao.createIssue(roomId, staffId,
                    "Room requires maintenance after being dirty");
        }

        // Maintenance -> Available: Resolve the active maintenance issue
        else if (fromStatus == RoomStatus.MAINTENANCE && toStatus == RoomStatus.AVAILABLE) {
            MaintenanceIssue activeIssue = maintenanceIssueDao.findActiveIssueByRoomId(roomId);
            if (activeIssue != null) {
                maintenanceIssueDao.resolveIssue(activeIssue.getIssueId(), staffId);
                housekeepingTaskDao.completeTask(staffId, roomId);
            }
        }

        // Occupied status transitions are blocked by validation, so no cases here
    }
}
