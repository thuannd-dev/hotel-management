package edu.hotel_management.application.service;

import edu.hotel_management.domain.entities.Room;
import edu.hotel_management.infrastructure.persistence.dao.RoomDAO;
import edu.hotel_management.presentation.dto.room.RoomPresentationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author TR_NGHIA
 */
public class RoomService {
    private final RoomDAO roomDao;
    
    public RoomService(RoomDAO roomDao) {
        this.roomDao = roomDao;
    }
    
    // =========================================================================
    // SECTION: PHƯƠNG THỨC THÊM PHÒNG MỚI
    // =========================================================================
    public String createRoom(Room room) {
        // Kiểm tra số phòng đã tồn tại chưa
        if (roomDao.existsByRoomNumber(room.getRoomNumber())) {
            return "Room number already exists.";
        }
        
        // Validate status
        if (room.getStatus() == null || room.getStatus().isEmpty()) {
            room.setStatus("Available");
        }
        
        boolean isSuccess = roomDao.create(room);
        
        if (isSuccess) {
            return null; 
        } else {
            return "An error occurred while creating room.";
        }
    }
    
    // =========================================================================
    // SECTION: PHƯƠNG THỨC CẬP NHẬT PHÒNG
    // =========================================================================
    public String updateRoom(Room room) {
        Optional<Room> existingRoom = roomDao.findById(room.getRoomId());
        if (!existingRoom.isPresent()) {
            return "Room not found.";
        }
        
        Optional<Room> roomWithSameNumber = roomDao.findByRoomNumber(room.getRoomNumber());
        if (roomWithSameNumber.isPresent() && 
            roomWithSameNumber.get().getRoomId() != room.getRoomId()) {
            return "Room number already exists.";
        }
        
        boolean isSuccess = roomDao.updateRoom(room);
        
        if (isSuccess) {
            return null;
        } else {
            return "An error occurred while updating room.";
        }
    }
    
    // =========================================================================
    // SECTION: PHƯƠNG THỨC CẬP NHẬT TRẠNG THÁI PHÒNG
    // =========================================================================
    public String updateRoomStatus(int roomId, String status) {
        if (!isValidStatus(status)) {
            return "Invalid room status. Valid statuses: Available, Occupied, Dirty, Maintenance";
        }
        
        boolean isSuccess = roomDao.updateStatus(roomId, status);
        
        if (isSuccess) {
            return null;
        } else {
            return "An error occurred while updating room status.";
        }
    }
    
    // =========================================================================
    // SECTION: PHƯƠNG THỨC XÓA PHÒNG
    // =========================================================================
    public String deleteRoom(int roomId) {
        Optional<Room> room = roomDao.findById(roomId);
        if (!room.isPresent()) {
            return "Room not found.";
        }
        
        boolean isSuccess = roomDao.delete(roomId);
        
        if (isSuccess) {
            return null;
        } else {
            return "An error occurred while deleting room.";
        }
    }
    
    // =========================================================================
    // CÁC PHƯƠNG THỨC QUERY - TRẢ VỀ PRESENTATION MODEL
    // =========================================================================
    
    public List<RoomPresentationModel> getAllRooms() {
        return convertToViewModels(roomDao.findAll());
    }
    
    public RoomPresentationModel getRoomById(int id) {
        Optional<Room> optionalRoom = roomDao.findById(id);
        return optionalRoom.map(RoomPresentationModel::fromEntity).orElse(null);
    }
    
    public RoomPresentationModel getRoomByRoomNumber(String roomNumber) {
        Optional<Room> optionalRoom = roomDao.findByRoomNumber(roomNumber);
        return optionalRoom.map(RoomPresentationModel::fromEntity).orElse(null);
    }
    
    public List<RoomPresentationModel> getRoomsByRoomTypeId(int roomTypeId) {
        return convertToViewModels(roomDao.findByRoomTypeId(roomTypeId));
    }
    
    public List<RoomPresentationModel> getRoomsByStatus(String status) {
        return convertToViewModels(roomDao.findByStatus(status));
    }
    
    public List<RoomPresentationModel> getAvailableRooms() {
        return convertToViewModels(roomDao.findAllAvailable());
    }
    
    public List<RoomPresentationModel> getAvailableRoomsByType(int roomTypeId) {
        return convertToViewModels(roomDao.findAvailableRoomsByType(roomTypeId));
    }
    
    public List<RoomPresentationModel> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return convertToViewModels(roomDao.findByPriceRange(minPrice, maxPrice));
    }
    
    public List<RoomPresentationModel> getRoomsByMinCapacity(int minCapacity) {
        return convertToViewModels(roomDao.findByMinCapacity(minCapacity));
    }
    
    public List<RoomPresentationModel> getAvailableRoomsByCapacity(int minCapacity) {
        return convertToViewModels(roomDao.findAvailableByCapacity(minCapacity));
    }
    
    public List<RoomPresentationModel> getRoomsByTypeName(String typeName) {
        return convertToViewModels(roomDao.findByTypeName(typeName));
    }
    
    
    // =========================================================================
    // HELPER METHODS
    // =========================================================================
    
    private List<RoomPresentationModel> convertToViewModels(List<Room> rooms) {
        List<RoomPresentationModel> viewModels = new ArrayList<>();
        for (Room room : rooms) {
            viewModels.add(RoomPresentationModel.fromEntity(room));
        }
        return viewModels;
    }
    
    private boolean isValidStatus(String status) {
        return status != null && 
               (status.equals("Available") || 
                status.equals("Occupied") || 
                status.equals("Dirty") || 
                status.equals("Maintenance"));
    }
    
    public int countRoomsByStatus(String status) {
        return roomDao.countByStatus(status);
    }
    
    public int countAllRooms() {
        return roomDao.countAll();
    }
}