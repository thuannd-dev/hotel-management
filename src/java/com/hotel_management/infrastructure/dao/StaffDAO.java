package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Staff;
import com.hotel_management.domain.entity.enums.StaffRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
public class StaffDAO extends BaseDAO<Staff> {

    public StaffDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Staff mapRow(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getInt("StaffID"),
                rs.getString("FullName"),
                StaffRole.fromDbValue(rs.getString("Role")),
                rs.getString("UserName"),
                rs.getString("PasswordHash"),
                rs.getString("Phone"),
                rs.getString("Email")
        );
    }

    public List<Staff> findAll() {
        return query("SELECT * FROM STAFF");
    }

    public Optional<Staff> findById(int id) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE StaffID = ?", id);
        return staffs.stream().findFirst();
    }

    public Optional<Staff> findByUsername(String userName) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE UserName = ?", userName);
        return staffs.stream().findFirst();
    }
}
