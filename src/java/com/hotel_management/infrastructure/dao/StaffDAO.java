package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Staff;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
                rs.getString("Role"),
                rs.getString("UserName"),
                rs.getString("PasswordHash"),
                rs.getString("Phone"),
                rs.getString("Email")
        );
    }

    public List<Staff> findAllStaffs() {
        return query("SELECT * FROM STAFF");
    }

    public Staff findStaffById(int id) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE StaffID = ?", id);
        return staffs.isEmpty() ? null : staffs.get(0);
    }

    public Staff findStaffByUsername(String userName) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE UserName = ?", userName);
        return staffs.isEmpty() ? null : staffs.get(0);
    }
}
