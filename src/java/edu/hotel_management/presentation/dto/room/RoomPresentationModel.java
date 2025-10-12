package edu.hotel_management.presentation.dto.room;

import edu.hotel_management.domain.entities.Room;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author TR_NGHIA
 */

@AllArgsConstructor
@Getter
public class RoomPresentationModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private String status;
    private String typeName;
    private int capacity;
    private double pricePerNight;
    
    // Static factory method để tạo từ entity
    public static RoomPresentationModel fromEntity(Room room) {
        return new RoomPresentationModel(
                room.getRoomNumber(),
                room.getStatus(),
                room.getTypeName(),
                room.getCapacity(),
                room.getPricePerNight()
        );
    }
    
    // ========= HELPER METHODS =========
    
    // Format giá tiền
    public String getFormattedPrice() {
        return String.format("$%.2f", pricePerNight);
    }
    
    // Hiển thị thông tin capacity
    public String getCapacityInfo() {
        return capacity + (capacity > 1 ? " guests" : " guest");
    }
    
    // Kiểm tra phòng có available không
    public boolean isAvailable() {
        return "Available".equals(status);
    }
    
    // Hiển thị thông tin đầy đủ của phòng
    public String getFullInfo() {
        return String.format("Room %s - %s (%s) - %s/night", 
                roomNumber, typeName, getCapacityInfo(), getFormattedPrice());
    }
}