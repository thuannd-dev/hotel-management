package com.hotel_management.infrastructure.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

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

    public int insertAndReturnId(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return id of row inserted
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ---------------------------
    // INSERT BATCH (return list of ids)
    // ---------------------------
    public List<Integer> insertBatchAndReturnIds(String sql, List<Object[]> batchParams) {
        List<Integer> generatedIds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // start transaction

            ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);

            for (Object[] params : batchParams) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                // execute each insert
                ps.executeUpdate();
                // get id of row inserted
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedIds.add(rs.getInt(1));
                    }
                }
                ps.clearParameters(); // clear parameters for next insert
            }
            conn.commit(); // commit transaction
            return generatedIds;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // rollback all changes/transactions
                    System.err.println("Batch insert rolled back due to error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return Collections.emptyList();// return an empty list if failed
        } finally {
            if (ps != null) close(ps);
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }

}

