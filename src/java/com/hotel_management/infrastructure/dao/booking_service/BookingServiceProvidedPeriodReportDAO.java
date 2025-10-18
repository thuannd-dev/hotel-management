package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.dto.booking_service.BookingServiceProvidedPeriodReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingServiceProvidedPeriodReportDAO extends BaseDAO<BookingServiceProvidedPeriodReportViewModel> {

    public BookingServiceProvidedPeriodReportDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServiceProvidedPeriodReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new BookingServiceProvidedPeriodReportViewModel(
                rs.getString("ServiceName"),
                rs.getInt("TotalQuantity"),
                rs.getDouble("TotalRevenue"),
                rs.getString("Period")
        );
    }

    public List<BookingServiceProvidedPeriodReportViewModel> getReport(LocalDate startDate, LocalDate endDate) {
            Date sqlStart = Date.valueOf(startDate);
            Date sqlEnd = Date.valueOf(endDate);
        return query("SELECT \n" +
                "    s.ServiceName,\n" +
                "    SUM(bs.Quantity) AS TotalQuantity,\n" +
                "    SUM(bs.Quantity * s.Price) AS TotalRevenue,\n" +
                "    CONCAT(CONVERT(varchar(10), ?, 120), ' to ', CONVERT(varchar(10), ?, 120)) AS Period\n" +
                "FROM BOOKING_SERVICE bs\n" +
                "JOIN SERVICE s ON bs.ServiceID = s.ServiceID\n" +
                "WHERE bs.ServiceDate BETWEEN ? AND ?\n" +
                "  AND bs.Status = 'Completed'\n" +
                "GROUP BY s.ServiceName\n" +
                "ORDER BY TotalRevenue DESC", sqlStart, sqlEnd, sqlStart, sqlEnd);
    }
}
