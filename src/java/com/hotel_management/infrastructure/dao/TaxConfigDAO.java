package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.TaxConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * DAO for TaxConfig
 * @author thuannd.dev
 */
public class TaxConfigDAO extends BaseDAO<TaxConfig> {

    public TaxConfigDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public TaxConfig mapRow(ResultSet rs) throws SQLException {
        java.sql.Date effectiveTo = rs.getDate("EffectiveTo");
        return new TaxConfig(
                rs.getInt("TaxConfigID"),
                rs.getString("TaxName"),
                rs.getBigDecimal("TaxRate"),
                rs.getString("Description"),
                rs.getDate("EffectiveFrom").toLocalDate(),
                effectiveTo != null ? effectiveTo.toLocalDate() : null,
                rs.getInt("CreatedBy"),
                rs.getTimestamp("CreatedDate").toLocalDateTime(),
                rs.getBoolean("IsActive")
        );
    }

    public Optional<TaxConfig> getActiveTaxConfig() {
        String sql = "SELECT TOP 1 * FROM TAX_CONFIG " +
                "WHERE IsActive = 1 " +
                "AND EffectiveFrom <= GETDATE() " +
                "AND (EffectiveTo IS NULL OR EffectiveTo > GETDATE()) " +
                "ORDER BY EffectiveFrom DESC";
        List<TaxConfig> result = query(sql);
        return result.stream().findFirst();
    }

    public Optional<TaxConfig> findById(int taxConfigId) {
        String sql = "SELECT * FROM TAX_CONFIG WHERE TaxConfigID = ?";
        List<TaxConfig> result = query(sql, taxConfigId);
        return result.stream().findFirst();
    }

    public List<TaxConfig> findAll() {
        String sql = "SELECT * FROM TAX_CONFIG ORDER BY CreatedDate DESC";
        return query(sql);
    }

    public int insert(TaxConfig taxConfig) {
        Date effectiveTo = taxConfig.getEffectiveTo() != null ? Date.valueOf(taxConfig.getEffectiveTo()) : null;
        Date effectiveFrom = Date.valueOf(taxConfig.getEffectiveFrom());
        String sql = "INSERT INTO TAX_CONFIG (TaxName, TaxRate, Description, EffectiveFrom, EffectiveTo, CreatedBy, IsActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return insertAndReturnId(sql,
                taxConfig.getTaxName(),
                taxConfig.getTaxRate(),
                taxConfig.getDescription(),
                effectiveFrom,
                effectiveTo,
                taxConfig.getCreatedBy(),
                taxConfig.isActive()
        );
    }

    public int update(TaxConfig taxConfig) {
        Date effectiveTo = taxConfig.getEffectiveTo() != null ? Date.valueOf(taxConfig.getEffectiveTo()) : null;
        Date effectiveFrom = Date.valueOf(taxConfig.getEffectiveFrom());
        String sql = "UPDATE TAX_CONFIG SET TaxName = ?, TaxRate = ?, Description = ?, " +
                "EffectiveFrom = ?, EffectiveTo = ?, IsActive = ? WHERE TaxConfigID = ?";
        return update(sql,
                taxConfig.getTaxName(),
                taxConfig.getTaxRate(),
                taxConfig.getDescription(),
                effectiveFrom,
                effectiveTo,
                taxConfig.isActive(),
                taxConfig.getTaxConfigId()
        );
    }

    public int delete(int taxConfigId) {
        String sql = "DELETE FROM TAX_CONFIG WHERE TaxConfigID = ?";
        return update(sql, taxConfigId);
    }

    public int deactivate(int taxConfigId) {
        String sql = "UPDATE TAX_CONFIG SET IsActive = 0 WHERE TaxConfigID = ?";
        return update(sql, taxConfigId);
    }

    public boolean existsActiveTaxInPeriod(LocalDate effectiveFrom, LocalDate effectiveTo, Integer excludeId) {
        String sql = "SELECT COUNT(*) AS cnt " +
                "FROM TAX_CONFIG " +
                "WHERE IsActive = 1 " +
                "AND TaxConfigID != ? " +
                "AND EffectiveFrom <= ? " +
                "AND (EffectiveTo IS NULL OR EffectiveTo >= ?)";

        int exclude = excludeId != null ? excludeId : -1;

        // If effectiveTo is null, treat as infinite (9999-12-31)
        LocalDate toCheck = effectiveTo != null ? effectiveTo : LocalDate.of(9999, 12, 31);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, exclude);                           // TaxConfigID != ?
            ps.setDate(2, java.sql.Date.valueOf(toCheck));   // existing.EffectiveFrom <= new.to
            ps.setDate(3, java.sql.Date.valueOf(effectiveFrom)); // existing.EffectiveTo >= new.from (or null)

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}