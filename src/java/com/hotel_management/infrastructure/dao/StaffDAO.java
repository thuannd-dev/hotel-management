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
                rs.getString("Username"),
                rs.getString("Password"),
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
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE Username = ?", userName);
        return staffs.stream().findFirst();
    }

    public int insert(Staff staff) {
        String sql = "INSERT INTO STAFF (FullName, Role, Username, Password, Phone, Email) VALUES (?, ?, ?, ?, ?, ?)";
        return insertAndReturnId(sql,
            staff.getFullName(),
            staff.getRole().getDbValue(),
            staff.getUsername(),
            staff.getPassword(),
            staff.getPhone(),
            staff.getEmail()
        );
    }

    public int update(Staff staff) {
        String sql = "UPDATE STAFF SET FullName = ?, Role = ?, Username = ?, Password = ?, Phone = ?, Email = ? WHERE StaffID = ?";
        return update(sql,
            staff.getFullName(),
            staff.getRole().getDbValue(),
            staff.getUsername(),
            staff.getPassword(),
            staff.getPhone(),
            staff.getEmail(),
            staff.getStaffId()
        );
    }

    public int updateWithoutPassword(Staff staff) {
        String sql = "UPDATE STAFF SET FullName = ?, Role = ?, Username = ?, Phone = ?, Email = ? WHERE StaffID = ?";
        return update(sql,
            staff.getFullName(),
            staff.getRole().getDbValue(),
            staff.getUsername(),
            staff.getPhone(),
            staff.getEmail(),
            staff.getStaffId()
        );
    }

    public int delete(int staffId) {
        String sql = "DELETE FROM STAFF WHERE StaffID = ?";
        return update(sql, staffId);
    }
}
