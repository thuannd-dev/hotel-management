package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Room;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 * 
 * @author TR_NGHIA
 */
public class RoomDAO extends BaseDAO<Room> {
    
    // Base SQL với JOIN
    private static final String BASE_SQL = 
        "SELECT r.RoomID, r.RoomNumber, r.RoomTypeID, r.Status, " +
        "       rt.TypeName, rt.Capacity, rt.PricePerNight " +
        "FROM ROOM r " +
        "INNER JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID";
    
    public RoomDAO(DataSource ds) {
        super(ds);
    }
    
    @Override
    public Room mapRowtoObject(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("RoomID"));
        room.setRoomNumber(rs.getString("RoomNumber"));
        room.setRoomTypeId(rs.getInt("RoomTypeID"));
        room.setStatus(rs.getString("Status"));
        room.setTypeName(rs.getString("TypeName"));
        room.setCapacity(rs.getInt("Capacity"));
        room.setPricePerNight(rs.getDouble("PricePerNight"));
        
        return room;
    }
    
    // ========= LẤY TẤT CẢ ROOM VỚI THÔNG TIN ROOM TYPE =========
    public List<Room> findAll() {
        return query(BASE_SQL + " ORDER BY r.RoomNumber");
    }
    
    // ========= TÌM ROOM THEO ID =========
    public Optional<Room> findById(int id) {
        String sql = BASE_SQL + " WHERE r.RoomID = ?";
        List<Room> rooms = query(sql, id);
        return rooms.stream().findFirst();
    }
    
    // ========= TÌM ROOM THEO ROOM NUMBER =========
    public Optional<Room> findByRoomNumber(String roomNumber) {
        String sql = BASE_SQL + " WHERE r.RoomNumber = ?";
        List<Room> rooms = query(sql, roomNumber);
        return rooms.stream().findFirst();
    }
    
    // ========= TÌM ROOM THEO ROOM TYPE ID =========
    public List<Room> findByRoomTypeId(int roomTypeId) {
        String sql = BASE_SQL + " WHERE r.RoomTypeID = ?";
        return query(sql, roomTypeId);
    }
    
    // ========= TÌM ROOM THEO STATUS =========
    public List<Room> findByStatus(String status) {
        String sql = BASE_SQL + " WHERE r.Status = ?";
        return query(sql, status);
    }
    
    // ========= TÌM ROOM AVAILABLE THEO ROOM TYPE =========
    public List<Room> findAvailableRoomsByType(int roomTypeId) {
        String sql = BASE_SQL + " WHERE r.RoomTypeID = ? AND r.Status = 'Available'";
        return query(sql, roomTypeId);
    }
    
    // ========= TÌM TẤT CẢ ROOM AVAILABLE =========
    public List<Room> findAllAvailable() {
        String sql = BASE_SQL + " WHERE r.Status = 'Available'";
        return query(sql);
    }
    
    // ========= TÌM ROOM THEO KHOẢNG GIÁ =========
    public List<Room> findByPriceRange(double minPrice, double maxPrice) {
        String sql = BASE_SQL + " WHERE rt.PricePerNight BETWEEN ? AND ?";
        return query(sql, minPrice, maxPrice);
    }
    
    // ========= TÌM ROOM THEO CAPACITY TỐI THIỂU =========
    public List<Room> findByMinCapacity(int minCapacity) {
        String sql = BASE_SQL + " WHERE rt.Capacity >= ?";
        return query(sql, minCapacity);
    }
    
    // ========= TÌM ROOM AVAILABLE THEO CAPACITY =========
    public List<Room> findAvailableByCapacity(int minCapacity) {
        String sql = BASE_SQL + 
                     " WHERE r.Status = 'Available' AND rt.Capacity >= ?";
        return query(sql, minCapacity);
    }
    
    // ========= TÌM ROOM THEO TYPE NAME =========
    public List<Room> findByTypeName(String typeName) {
        String sql = BASE_SQL + " WHERE rt.TypeName = ?";
        return query(sql, typeName);
    }
    
    // ========= THÊM MỘT ROOM MỚI =========
    public boolean create(Room room) {
        String sql = "INSERT INTO ROOM (RoomNumber, RoomTypeID, Status) VALUES (?, ?, ?)";
        int result = update(sql,
                room.getRoomNumber(),
                room.getRoomTypeId(),
                room.getStatus()
        );
        return result > 0;
    }
    
    // ========= CẬP NHẬT THÔNG TIN ROOM =========
    public boolean updateRoom(Room room) {
        String sql = "UPDATE ROOM SET RoomNumber = ?, RoomTypeID = ?, Status = ? WHERE RoomID = ?";
        int result = update(sql,
                room.getRoomNumber(),
                room.getRoomTypeId(),
                room.getStatus(),
                room.getRoomId()
        );
        return result > 0;
    }
    
    // ========= CẬP NHẬT STATUS CỦA ROOM =========
    public boolean updateStatus(int roomId, String status) {
        String sql = "UPDATE ROOM SET Status = ? WHERE RoomID = ?";
        int result = update(sql, status, roomId);
        return result > 0;
    }
    
    // ========= XÓA ROOM THEO ID =========
    public boolean delete(int roomId) {
        String sql = "DELETE FROM ROOM WHERE RoomID = ?";
        int result = update(sql, roomId);
        return result > 0;
    }
    
    // ========= KIỂM TRA ROOM NUMBER ĐÃ TỒN TẠI CHƯA =========
    public boolean existsByRoomNumber(String roomNumber) {
        String sql = "SELECT COUNT(*) as count FROM ROOM WHERE RoomNumber = ?";
        List<Room> rooms = query("SELECT * FROM ROOM WHERE RoomNumber = ?", roomNumber);
        return !rooms.isEmpty();
    }
    
    // ========= ĐẾM SỐ LƯỢNG ROOM THEO STATUS =========
    public int countByStatus(String status) {
        List<Room> rooms = query("SELECT * FROM ROOM WHERE Status = ?", status);
        return rooms.size();
    }
    
    // ========= ĐẾM TỔNG SỐ ROOM =========
    public int countAll() {
        List<Room> rooms = query("SELECT * FROM ROOM");
        return rooms.size();
    }
    
 
}
