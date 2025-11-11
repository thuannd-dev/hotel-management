package com.hotel_management.infrastructure.dao.service;

import com.hotel_management.domain.dto.manager.ServiceUsageViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * DAO for Most Used Services Report
 * @author Chauu
 */
public class ServiceUsageDAO extends BaseDAO<ServiceUsageViewModel> {

    public ServiceUsageDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public ServiceUsageViewModel mapRow(ResultSet rs) throws SQLException {
        return new ServiceUsageViewModel(
                rs.getInt("ServiceID"),
                rs.getString("ServiceName"),
                rs.getString("ServiceType"),
                rs.getInt("UsageCount"),
                rs.getInt("TotalQuantity"),
                rs.getBigDecimal("TotalRevenue")
        );
    }

    /**
     * Get most used services ordered by usage count
     */
    public List<ServiceUsageViewModel> getMostUsedServices() {
        String sql = 
                "SELECT " +
                "    S.ServiceID, " +
                "    S.ServiceName, " +
                "    S.ServiceType, " +
                "    COUNT(BS.Booking_Service_ID) AS UsageCount, " +
                "    SUM(BS.Quantity) AS TotalQuantity, " +
                "    SUM(BS.Quantity * S.Price) AS TotalRevenue " +
                "FROM SERVICE S " +
                "INNER JOIN BOOKING_SERVICE BS ON S.ServiceID = BS.ServiceID " +
                "GROUP BY S.ServiceID, S.ServiceName, S.ServiceType " +
                "ORDER BY UsageCount DESC, TotalRevenue DESC";
        
        return query(sql);
    }

    /**
     * Get most used services by date range
     */
    public List<ServiceUsageViewModel> getMostUsedServicesByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        String sql = 
                "SELECT " +
                "    S.ServiceID, " +
                "    S.ServiceName, " +
                "    S.ServiceType, " +
                "    COUNT(BS.Booking_Service_ID) AS UsageCount, " +
                "    SUM(BS.Quantity) AS TotalQuantity, " +
                "    SUM(BS.Quantity * S.Price) AS TotalRevenue " +
                "FROM SERVICE S " +
                "INNER JOIN BOOKING_SERVICE BS ON S.ServiceID = BS.ServiceID " +
                "WHERE BS.ServiceDate BETWEEN ? AND ? " +
                "GROUP BY S.ServiceID, S.ServiceName, S.ServiceType " +
                "ORDER BY UsageCount DESC, TotalRevenue DESC";
        
        return query(sql, startDate, endDate);
    }
}

