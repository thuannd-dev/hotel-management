package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.dto.booking_service.BookingServiceRequestReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class BookingServiceRequestReportDAO extends BaseDAO<BookingServiceRequestReportViewModel> {

    public BookingServiceRequestReportDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServiceRequestReportViewModel mapRow(ResultSet rs) throws SQLException {
        Timestamp rq = rs.getTimestamp("RequestTime");
        LocalDateTime requestTime = rq != null ? rq.toLocalDateTime() : null;
        return new BookingServiceRequestReportViewModel(
                requestTime,
                rs.getString("GuestName"),
                rs.getString("RoomNumber"),
                rs.getString("ServiceName"),
                rs.getInt("Quantity"),
                rs.getString("AssignedStaff")
        );
    }

    public List<BookingServiceRequestReportViewModel> getReport(int staffId) {
        return query("SELECT \n" +
                "    bs.RequestTime,\n" +
                "    g.FullName AS GuestName,\n" +
                "    r.RoomNumber,\n" +
                "    s.ServiceName,\n" +
                "    bs.Quantity,\n" +
                "    st.FullName AS AssignedStaff\n" +
                "FROM BOOKING_SERVICE bs\n" +
                "JOIN BOOKING b ON bs.BookingID = b.BookingID\n" +
                "JOIN GUEST g ON b.GuestID = g.GuestID\n" +
                "JOIN ROOM r ON b.RoomID = r.RoomID\n" +
                "JOIN SERVICE s ON bs.ServiceID = s.ServiceID\n" +
                "LEFT JOIN STAFF st ON bs.AssignedStaffID = st.StaffID\n" +
                "WHERE bs.Status IN ('Requested', 'In Progress') AND st.StaffID = ?\n" +
                "ORDER BY bs.RequestTime ASC", staffId);
    }


}
