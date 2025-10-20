package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Guest;
import javax.sql.DataSource;
import java.sql.Date;
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
                rs.getString("Username"),
                rs.getString("Password"),
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

    public Optional<Guest> findByUsername(String userName) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE UserName = ?", userName);
        return guests.stream().findFirst();
    }

    public Optional<Guest> findByEmail(String email) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE Email = ?", email);
        return guests.stream().findFirst();
    }

    public Optional<Guest> findByPhone(String phone) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE Phone = ?", phone);
        return guests.stream().findFirst();
    }

    public Optional<Guest> findByIdNumber(String idNumber) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE IDNumber = ?", idNumber);
        return guests.stream().findFirst();
    }

    public int insert(Guest guest) {
        Date dateOfBirth = Date.valueOf(guest.getDateOfBirth());
        String sql = "INSERT INTO GUEST (FullName, Phone, Email, Address, IDNumber, DateOfBirth, Username, Password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insertAndReturnId(sql,
            guest.getFullName(),
            guest.getPhone(),
            guest.getEmail(),
            guest.getAddress(),
            guest.getIdNumber(),
            dateOfBirth,
            guest.getUsername(),
            guest.getPassword()
        );
    }
}
