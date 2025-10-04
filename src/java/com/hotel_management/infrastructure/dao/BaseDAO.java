package com.hotel_management.infrastructure.dao;

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
 * @author thuannd.dev
 * @param <T>
 */
public abstract class BaseDAO<T> {

    private final DataSource ds;

    protected BaseDAO(DataSource ds) {
        this.ds = Objects.requireNonNull(ds, "DataSource must not be null");
    }

    protected Connection getConnection() throws SQLException {
        return ds.getConnection(); // lấy từ pool
    }

    protected void close(AutoCloseable c) {
        if (c != null) try { c.close(); } catch (Exception ignored) {}//just ignore
    }

    // mapping must implement
    public abstract T mapRow(ResultSet rs) throws SQLException;

    public List<T> query(String sql, Object... params) {
        List<T> list = new ArrayList<>();
        try ( Connection conn = getConnection();  
              PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(String sql, Object... params) {
        try ( Connection conn = getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
