package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.TaxConfig;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}