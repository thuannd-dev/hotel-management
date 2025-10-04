package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Guest;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author thuannd.dev
 */
public class GuestDAO extends BaseDAO<Guest>{

    public GuestDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Guest mapRow(ResultSet rs) throws SQLException {
        return new Guest(
                rs.getInt("GuestID"),
                rs.getString("FullName"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getString("Address"),
                rs.getString("IDNumber"),
                rs.getDate("DateOfBirth").toLocalDate()
        );
    }

    public List<Guest> findAll() {
        return query("SELECT * FROM GUEST");
    }

    public Optional<Guest> findById(int id) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE GuestID = ?", id);
        return guests.stream().findFirst();
    }
}
