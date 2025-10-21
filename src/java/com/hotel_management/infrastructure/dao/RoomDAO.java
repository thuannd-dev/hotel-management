package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Room;
import com.hotel_management.domain.entity.enums.RoomStatus;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * Find available rooms based on check-in/check-out dates and guest capacity
     * A room is available if:
     * 1. Status is 'Available'
     * 2. Room capacity >= total guests (adults + children)
     * 3. No overlapping bookings for the date range (excluding Canceled bookings)
     */
    public List<Room> findAvailableRooms(Date checkInDate, Date checkOutDate, int totalGuests) {
        String sql = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID, R.Status " +
                     "FROM ROOM R " +
                     "INNER JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID " +
                     "WHERE R.Status = 'Available' " +
                     "AND RT.Capacity >= ? " +
                     "AND R.RoomID NOT IN ( " +
                     "    SELECT B.RoomID FROM BOOKING B " +
                     "    WHERE B.Status != 'Canceled' " +
                     "    AND ( " +
                     "        (B.CheckInDate <= ? AND B.CheckOutDate > ?) " +
                     "        OR (B.CheckInDate < ? AND B.CheckOutDate >= ?) " +
                     "        OR (B.CheckInDate >= ? AND B.CheckOutDate <= ?) " +
                     "    ) " +
                     ")";
        return query(sql, totalGuests, checkOutDate, checkInDate, checkOutDate, checkInDate, checkInDate, checkOutDate);
    }

}
