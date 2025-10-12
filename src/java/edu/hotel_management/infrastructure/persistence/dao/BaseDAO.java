/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.infrastructure.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;

/**
 *
 * @author TR_NGHIA
 */

public abstract class BaseDAO<T> {
    private final DataSource dataSource;

    // ========= CONSTRUCTOR ĐỂ INJECT DATASOURCE =========
    protected BaseDAO(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "DataSource must not be null");
    }

    // ========= LẤY CONNECTION TỪ CONNECTION POOL =========
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // ========= PHƯƠNG THỨC TRỪU TƯỢNG: ÁNH XẠ ROW SANG OBJECT =========
    public abstract T mapRowtoObject(ResultSet resultSet) throws SQLException;

    // ========= THỰC THI CÂU LỆNH QUERY (SELECT) =========
    public List<T> query(String sql, Object... params) {
        List<T> results = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapRowtoObject(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return results;
    }

    // ========= THỰC THI CÂU LỆNH UPDATE (INSERT, UPDATE, DELETE) =========
    public int update(String sql, Object... params) {
        try ( Connection connection = getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error executing update", e);
        }
    }
}
