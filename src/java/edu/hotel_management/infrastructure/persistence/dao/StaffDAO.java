/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Staff;
import edu.hotel_management.domain.entities.enums.StaffRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author TR_NGHIA
 */

public class StaffDAO extends BaseDAO<Staff> {

    // ========= CONSTRUCTOR ĐỂ INJECT DATASOURCE =========
    public StaffDAO(DataSource ds) {
        super(ds);
    }

    // ========= TRIỂN KHAI ÁNH XẠ TỪ RESULTSET SANG OBJECT =========
    @Override
    public Staff mapRowtoObject(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getInt("StaffID"),
                rs.getString("FullName"),
                StaffRole.valueFromDb(rs.getString("Role")),
                rs.getString("UserName"),
                rs.getString("PasswordHash"),
                rs.getString("Phone"),
                rs.getString("Email")
        );
    }

    // ========= LẤY TẤT CẢ STAFF =========
    public List<Staff> findAll() {
        return query("SELECT * FROM STAFF");
    }

    // ========= TÌM STAFF THEO ID =========
    public Optional<Staff> findById(int id) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE StaffID = ?", id);
        return staffs.stream().findFirst();
    }

    // ========= TÌM STAFF THEO USERNAME =========
    public Optional<Staff> findByUsername(String userName) {
        List<Staff> staffs = query("SELECT * FROM STAFF WHERE UserName = ?", userName);
        return staffs.stream().findFirst();
    }
}






