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

    public Boolean updateRoomStatus(int roomId, RoomStatus status) {
        return roomDao.updateRoomStatus(roomId, status) > 0;
    }
}
