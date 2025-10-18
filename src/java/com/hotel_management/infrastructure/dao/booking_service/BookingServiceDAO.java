package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.entity.BookingService;
import com.hotel_management.domain.entity.enums.BookingServiceStatus;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingServiceDAO extends BaseDAO<BookingService> {

    public BookingServiceDAO(DataSource ds) { super(ds); }

    @Override
    public BookingService mapRow(ResultSet rs) throws SQLException {
        Timestamp rq = rs.getTimestamp("RequestTime");
        LocalDateTime requestTime = rq != null ? rq.toLocalDateTime() : null;
        Timestamp cmp = rs.getTimestamp("CompletionTime");
        LocalDateTime completionTime = cmp != null ? cmp.toLocalDateTime() : null;
        return new BookingService(
                rs.getInt("Booking_Service_ID"),
                rs.getInt("BookingID"),
                rs.getInt("ServiceID"),
                rs.getInt("Quantity"),
                rs.getDate("ServiceDate").toLocalDate(),
                BookingServiceStatus.fromDbValue(rs.getString("Status")),
                rs.getInt("AssignedStaffID"),
                requestTime,
                completionTime
        );
    }

    public List<BookingService> findAll() {
        return query("SELECT \n" +
                "B.Booking_Service_ID, B.BookingID, B.ServiceID,\n" +
                "B.Quantity, B.ServiceDate, B.Status, B.AssignedStaffID,\n" +
                "B.RequestTime, B.CompletionTime\n" +
                "FROM BOOKING_SERVICE B");
    }

    public Optional<BookingService> findById(int id) {
        return query("SELECT \n" +
                "B.Booking_Service_ID, B.BookingID, B.ServiceID,\n" +
                "B.Quantity, B.ServiceDate, B.Status, B.AssignedStaffID,\n" +
                "B.RequestTime, B.CompletionTime\n" +
                "FROM BOOKING_SERVICE B\n" +
                "WHERE B.Booking_Service_ID = ?", id)
                .stream().findFirst();
    }

    public int insertBookingService(BookingService b, BookingServiceStatus status) {
        String sql = "INSERT INTO BOOKING_SERVICE\n" +
                "(BookingID, ServiceID, Quantity, Status, AssignedStaffID)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        return insertAndReturnId(
                sql, b.getBookingId(), b.getServiceId(),
                b.getQuantity(), status.getDbValue(),
                b.getAssignedStaffId()
        );
    }

    public List<Integer> insertBatchBookingService(List<BookingService> lb, BookingServiceStatus status) {
        String sql = "INSERT INTO BOOKING_SERVICE\n" +
                "(BookingID, ServiceID, Quantity, Status, AssignedStaffID)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        List<Object[]> batchParams = new ArrayList<>();
        for (BookingService b : lb) {
            batchParams.add(new Object[]{b.getBookingId(), b.getServiceId(), b.getQuantity(), status.getDbValue(), b.getAssignedStaffId()});
        }
        return insertBatchAndReturnIds(sql, batchParams);
    }

    public int updateBookingServiceStatus(int bookingServiceId, BookingServiceStatus status) {
        String sql = "UPDATE BOOKING_SERVICE SET Status = ?, CompletionTime = ? WHERE Booking_Service_ID = ?";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return update(sql, status.getDbValue(), now, bookingServiceId);
    }

}
