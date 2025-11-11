package com.hotel_management.infrastructure.dao.payment;

import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.BaseDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * DAO for Payment
 * @author thuannd.dev
 */
public class PaymentDAO extends BaseDAO<Payment> {

    public PaymentDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Payment mapRow(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("PaymentID"),
                rs.getInt("BookingID"),
                rs.getDate("PaymentDate").toLocalDate(),
                rs.getBigDecimal("Amount"),
                PaymentMethod.fromDbValue(rs.getString("PaymentMethod")),
                PaymentTransactionStatus.fromDbValue(rs.getString("Status"))
        );
    }

    public int createPayment(Payment payment) {
        String sql = "INSERT INTO PAYMENT (" +
                "BookingID, PaymentDate, Amount, PaymentMethod, Status) " +
                "VALUES (?, ?, ?, ?, ?)";

        return insertAndReturnId(sql,
                payment.getBookingId(),
                java.sql.Date.valueOf(payment.getPaymentDate()),
                payment.getAmount(),
                payment.getPaymentMethod().getDbValue(),
                payment.getStatus().getDbValue()
        );
    }

    public List<Payment> findByBookingId(int bookingId) {
        String sql = "SELECT * FROM PAYMENT WHERE BookingID = ? ORDER BY PaymentDate DESC";
        return query(sql, bookingId);
    }

    public Optional<Payment> findById(int paymentId) {
        String sql = "SELECT * FROM PAYMENT WHERE PaymentID = ?";
        List<Payment> result = query(sql, paymentId);
        return result.stream().findFirst();
    }
}


