package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Invoice;
import com.hotel_management.domain.entity.enums.InvoiceStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * DAO for Invoice
 * @author thuannd.dev
 */
public class InvoiceDAO extends BaseDAO<Invoice> {

    public InvoiceDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Invoice mapRow(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt("InvoiceID"),
                rs.getInt("BookingID"),
                rs.getDate("IssueDate").toLocalDate(),
                rs.getBigDecimal("TotalAmount"),
                InvoiceStatus.fromDbValue(rs.getString("Status")),
                rs.getBigDecimal("RoomCharges"),
                rs.getBigDecimal("ServiceCharges"),
                rs.getObject("TaxConfigID") != null ? rs.getInt("TaxConfigID") : null,
                rs.getBigDecimal("TaxAmount"),
                rs.getBigDecimal("Discount"),
                rs.getBigDecimal("FinalAmount")
        );
    }

    public int createInvoice(Invoice invoice) {
        String sql = "INSERT INTO INVOICE (" +
                "BookingID, IssueDate, TotalAmount, Status, RoomCharges, ServiceCharges, " +
                "TaxConfigID, TaxAmount, Discount, FinalAmount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return insertAndReturnId(sql,
                invoice.getBookingId(),
                java.sql.Date.valueOf(invoice.getIssueDate()),
                invoice.getTotalAmount(),
                invoice.getStatus().getDbValue(),
                invoice.getRoomCharges(),
                invoice.getServiceCharges(),
                invoice.getTaxConfigId(),
                invoice.getTaxAmount(),
                invoice.getDiscount(),
                invoice.getFinalAmount()
        );
    }

    public Optional<Invoice> findByBookingId(int bookingId) {
        String sql = "SELECT * FROM INVOICE WHERE BookingID = ?";
        List<Invoice> result = query(sql, bookingId);
        return result.stream().findFirst();
    }

    public Optional<Invoice> findById(int invoiceId) {
        String sql = "SELECT * FROM INVOICE WHERE InvoiceID = ?";
        List<Invoice> result = query(sql, invoiceId);
        return result.stream().findFirst();
    }

    public int updateStatus(int invoiceId, InvoiceStatus status) {
        String sql = "UPDATE INVOICE SET Status = ? WHERE InvoiceID = ?";
        System.out.println("[InvoiceDAO] Updating invoice status - ID: " + invoiceId + ", New Status: " + status.getDbValue());
        int result = update(sql, status.getDbValue(), invoiceId);
        System.out.println("[InvoiceDAO] Update result: " + result + " row(s) affected");
        return result;
    }
}

