/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Staff;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author DELL
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

    public List<Staff> findAll() {
        return query("SELECT * FROM STAFF");
    }

}
