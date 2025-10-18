package com.hotel_management.application.service;

import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.entity.enums.RoomStatus;
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

    public RoomService(RoomDAO roomDao, RoomDetailDAO roomDetailDao) {
        this.roomDetailDao = roomDetailDao;
        this.roomDao = roomDao;
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

    public Boolean updateRoomStatus(int roomId, RoomStatus newStatus) {
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

        return roomDao.updateRoomStatus(roomId, newStatus) > 0;
    }
}
