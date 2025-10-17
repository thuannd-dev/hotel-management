package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.entity.Room;
import com.hotel_management.domain.entity.enums.BookingServiceStatus;
import com.hotel_management.domain.entity.enums.RoomStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RoomDAO extends BaseDAO<Room> {

    public RoomDAO(DataSource ds) { super(ds); }

    @Override
    public Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("RoomID"),
                rs.getString("RoomNumber"),
                rs.getInt("RoomTypeID"),
                RoomStatus.fromDbValue(rs.getString("Status"))
        );
    }

    public int updateRoomStatus(int roomId, RoomStatus status) {
        String sql = "UPDATE ROOM SET Status = ? WHERE RoomID = ?";
        return update(sql, status.getDbValue(), roomId);
    }

}
