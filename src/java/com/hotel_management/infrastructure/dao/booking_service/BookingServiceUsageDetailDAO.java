package com.hotel_management.infrastructure.dao.booking_service;

import com.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingServiceUsageDetailDAO extends BaseDAO<BookingServiceUsageDetailViewModel> {

    public BookingServiceUsageDetailDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServiceUsageDetailViewModel mapRow(ResultSet rs) throws SQLException {
        Timestamp rq = rs.getTimestamp("RequestTime");
        LocalDateTime requestTime = rq != null ? rq.toLocalDateTime() : null;
        Timestamp cmp = rs.getTimestamp("CompletionTime");
        LocalDateTime completionTime = cmp != null ? cmp.toLocalDateTime() : null;
        return new BookingServiceUsageDetailViewModel(
                rs.getInt("Booking_Service_ID"),
                rs.getInt("BookingID"),
                rs.getInt("ServiceID"),
                rs.getString("ServiceName"),
                rs.getString("ServiceType"),
                rs.getDate("ServiceDate").toLocalDate(),
                rs.getString("BookingServiceStatus"),
                rs.getInt("AssignedStaffID"),
                requestTime,
                completionTime,
                rs.getDouble("Price"),
                rs.getInt("Quantity"),
                rs.getDouble("SubPrice")
        );
    }

    public Optional<BookingServiceUsageDetailViewModel> findById(int bookingServiceId) {
        List<BookingServiceUsageDetailViewModel> bookings = query("SELECT\n" +
                "BS.Booking_Service_ID, BS.BookingID, \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, BS.ServiceDate, \n" +
                "BS.Status AS BookingServiceStatus, BS.AssignedStaffID,\n" +
                "BS.RequestTime, BS.CompletionTime, S.Price, BS.Quantity, \n" +
                "(S.Price * BS.Quantity) AS SubPrice\n" +
                "FROM BOOKING_SERVICE BS\n" +
                "JOIN SERVICE S ON BS.ServiceID = S.ServiceID\n" +
                "WHERE BS.Booking_Service_ID = ?", bookingServiceId);
        return bookings.stream().findFirst();
    }

    public List<BookingServiceUsageDetailViewModel> findByBookingId(int bookingId) {
        return query("SELECT\n" +
                "BS.Booking_Service_ID, BS.BookingID, \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, BS.ServiceDate, \n" +
                "BS.Status AS BookingServiceStatus, BS.AssignedStaffID,\n" +
                "BS.RequestTime, BS.CompletionTime, S.Price, BS.Quantity, \n" +
                "(S.Price * BS.Quantity) AS SubPrice\n" +
                "FROM BOOKING_SERVICE BS\n" +
                "JOIN SERVICE S ON BS.ServiceID = S.ServiceID\n" +
                "WHERE BS.BookingID = ?", bookingId);
    }

    public List<BookingServiceUsageDetailViewModel> findByIds(List<Integer> bookingServiceIds) {
        String placeholders = bookingServiceIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        return query("SELECT\n" +
                "BS.Booking_Service_ID, BS.BookingID, \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, BS.ServiceDate, \n" +
                "BS.Status AS BookingServiceStatus, BS.AssignedStaffID,\n" +
                "BS.RequestTime, BS.CompletionTime, S.Price, BS.Quantity, \n" +
                "(S.Price * BS.Quantity) AS SubPrice\n" +
                "FROM BOOKING_SERVICE BS\n" +
                "JOIN SERVICE S ON BS.ServiceID = S.ServiceID\n" +
                "WHERE BS.Booking_Service_ID IN (" + placeholders + ")", bookingServiceIds.toArray());
    }

    public List<BookingServiceUsageDetailViewModel> findByBookingIdExceptBookingServiceIds(int bookingId, List<Integer> bookingServiceExceptIds) {
        String placeholders = bookingServiceExceptIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        List<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.addAll(bookingServiceExceptIds);
        return query("SELECT\n" +
                "BS.Booking_Service_ID, BS.BookingID, \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, BS.ServiceDate, \n" +
                "BS.Status AS BookingServiceStatus, BS.AssignedStaffID,\n" +
                "BS.RequestTime, BS.CompletionTime, S.Price, BS.Quantity, \n" +
                "(S.Price * BS.Quantity) AS SubPrice\n" +
                "FROM BOOKING_SERVICE BS\n" +
                "JOIN SERVICE S ON BS.ServiceID = S.ServiceID\n" +
                "WHERE BS.BookingID = ? AND BS.Booking_Service_ID NOT IN (" + placeholders + ")", params.toArray());
    }
}