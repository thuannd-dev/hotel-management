package com.hotel_management.infrastructure.dao.room;

import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.entity.enums.RoomStatus;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomDetailDAO extends BaseDAO<RoomDetailViewModel> {
    private final static String BASE_QUERY = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID,\n" +
            "R.Status, RT.TypeName, RT.Capacity, RT.PricePerNight\n" +
            "FROM ROOM R LEFT JOIN ROOM_TYPE RT\n" +
            "ON R.RoomTypeID = RT.RoomTypeID\n";
    public RoomDetailDAO(DataSource ds) { super(ds); }

    @Override
    public RoomDetailViewModel mapRow(ResultSet rs) throws SQLException {
        return new RoomDetailViewModel(
                rs.getInt("RoomID"),
                rs.getString("RoomNumber"),
                rs.getInt("RoomTypeID"),
                rs.getString("Status"),
                rs.getString("TypeName"),
                rs.getInt("Capacity"),
                rs.getDouble("PricePerNight")
        );
    }

    public List<RoomDetailViewModel> findAll() {
        return query(BASE_QUERY);
    }

    public Optional<RoomDetailViewModel> findById(int id) {
        String condition = "WHERE R.RoomID = ?";
        List<RoomDetailViewModel> rooms = query(BASE_QUERY + condition, id);
        return rooms.stream().findFirst();
    }

    public List<RoomDetailViewModel> findByStatus(RoomStatus status) {
        String condition = "WHERE R.Status = ?";
        return query(BASE_QUERY + condition, status.getDbValue());
    }

    /**
     * Find available room details based on check-in/check-out dates and guest capacity
     */
    public List<RoomDetailViewModel> findAvailableRoomDetails(Date checkInDate, Date checkOutDate, int totalGuests) {
        String sql = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID, R.Status, \n" +
                "       RT.TypeName, RT.Capacity, RT.PricePerNight\n" +
                "FROM ROOM R\n" +
                "INNER JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID\n" +
                "WHERE RT.Capacity >= ?\n" +
                "  AND R.RoomID NOT IN (\n" +
                "      SELECT B.RoomID \n" +
                "      FROM BOOKING B\n" +
                "      WHERE B.Status != 'Canceled'\n" +
                "        AND (\n" +
                "            (B.CheckInDate <= ? AND B.CheckOutDate > ?) \n" +
                "            OR (B.CheckInDate < ? AND B.CheckOutDate >= ?)\n" +
                "            OR (B.CheckInDate >= ? AND B.CheckOutDate <= ?)\n" +
                "        )\n" +
                "  );";
        return query(sql, totalGuests, checkOutDate, checkInDate, checkOutDate, checkInDate, checkInDate, checkOutDate);
    }

}

