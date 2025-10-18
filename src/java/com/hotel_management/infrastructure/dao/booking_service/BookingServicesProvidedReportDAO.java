package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.dto.booking_service.BookingServicesProvidedReportViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookingServicesProvidedReportDAO extends BaseDAO<BookingServicesProvidedReportViewModel> {

    public BookingServicesProvidedReportDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServicesProvidedReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new BookingServicesProvidedReportViewModel(
                rs.getDate("ServiceDate") == null ? null : rs.getDate("ServiceDate").toLocalDate(),
                rs.getString("GuestName"),
                rs.getString("RoomNumber"),
                rs.getString("ServiceName"),
                rs.getInt("Quantity"),
                rs.getString("Status")
        );
    }

    public List<BookingServicesProvidedReportViewModel> getToDayReport(int staffId) {
        return query("SELECT \n" +
                "    bs.ServiceDate,\n" +
                "    g.FullName AS GuestName,\n" +
                "    r.RoomNumber,\n" +
                "    s.ServiceName,\n" +
                "    bs.Quantity,\n" +
                "    bs.Status\n" +
                "FROM BOOKING_SERVICE bs\n" +
                "JOIN BOOKING b ON bs.BookingID = b.BookingID\n" +
                "JOIN GUEST g ON b.GuestID = g.GuestID\n" +
                "JOIN ROOM r ON b.RoomID = r.RoomID\n" +
                "JOIN SERVICE s ON bs.ServiceID = s.ServiceID\n" +
                "WHERE CAST(bs.ServiceDate AS DATE) = CAST(GETDATE() AS DATE)\n" +
                "AND bs.AssignedStaffID = ?\n" +
                "ORDER BY bs.ServiceDate, g.FullName", staffId);
    }


}
