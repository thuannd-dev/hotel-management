package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.dto.booking_service.BookingServiceCompletedReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookingServiceCompletedReportDAO extends BaseDAO<BookingServiceCompletedReportViewModel> {
    public BookingServiceCompletedReportDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServiceCompletedReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new BookingServiceCompletedReportViewModel(
            rs.getString("StaffName"),
            rs.getString("ServiceName"),
            rs.getInt("TotalCompleted"),
            rs.getDate("Date") == null ? null : rs.getDate("Date").toLocalDate()
        );
    }

    public List<BookingServiceCompletedReportViewModel> getReport() {
        return query("SELECT \n" +
                "    st.FullName AS StaffName,\n" +
                "    s.ServiceName,\n" +
                "    COUNT(*) AS TotalCompleted,\n" +
                "    CAST(bs.CompletionTime AS DATE) AS [Date]\n" +
                "FROM BOOKING_SERVICE bs\n" +
                "JOIN SERVICE s ON bs.ServiceID = s.ServiceID\n" +
                "JOIN STAFF st ON bs.AssignedStaffID = st.StaffID\n" +
                "WHERE bs.Status = 'Completed'\n" +
                "GROUP BY st.FullName, s.ServiceName, CAST(bs.CompletionTime AS DATE)\n" +
                "ORDER BY [Date] DESC, st.FullName, s.ServiceName");
    }
}
