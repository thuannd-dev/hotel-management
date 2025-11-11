package com.hotel_management.application.service;

import com.hotel_management.domain.dto.checkout.CheckoutBookingViewModel;
import com.hotel_management.domain.dto.checkout.CheckoutSummaryViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.Invoice;
import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.TaxConfig;
import com.hotel_management.domain.entity.enums.*;
import com.hotel_management.infrastructure.dao.*;
import com.hotel_management.infrastructure.dao.booking.BookingDAO;
import com.hotel_management.infrastructure.dao.booking.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.booking.CheckoutBookingDAO;
import com.hotel_management.infrastructure.dao.booking.CheckoutDAO;
import com.hotel_management.infrastructure.dao.invoice.InvoiceDAO;
import com.hotel_management.infrastructure.dao.payment.PaymentDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service for handling checkout operations
 * @author thuannd.dev
 */
public class CheckoutService {

    private final BookingDAO bookingDAO;
    private final CheckoutBookingDAO checkoutBookingDAO;
    private final CheckoutDAO checkoutDAO;
    private final InvoiceDAO invoiceDAO;
    private final PaymentDAO paymentDAO;
    private final TaxConfigDAO taxConfigDAO;
    private final BookingDetailDAO bookingDetailDAO;

    public CheckoutService(BookingDAO bookingDAO, CheckoutBookingDAO checkoutBookingDAO,
                          CheckoutDAO checkoutDAO, InvoiceDAO invoiceDAO, PaymentDAO paymentDAO,
                          TaxConfigDAO taxConfigDAO, BookingDetailDAO bookingDetailDAO) {
        this.bookingDAO = bookingDAO;
        this.checkoutBookingDAO = checkoutBookingDAO;
        this.checkoutDAO = checkoutDAO;
        this.invoiceDAO = invoiceDAO;
        this.paymentDAO = paymentDAO;
        this.taxConfigDAO = taxConfigDAO;
        this.bookingDetailDAO = bookingDetailDAO;
    }

    /**
     * Get all checked-in bookings for a guest
     */
    public List<CheckoutBookingViewModel> getCheckedInBookingsForGuest(int guestId) {
        return checkoutBookingDAO.findCheckedInBookingsByGuestId(guestId);
    }

    /**
     * Get all checked-in bookings (for admin/staff)
     */
    public List<CheckoutBookingViewModel> getAllCheckedInBookings() {
        return checkoutBookingDAO.findAllCheckedInBookings();
    }

    /**
     * Calculate checkout summary for a booking
     */
    public CheckoutSummaryViewModel calculateCheckoutSummary(int bookingId) {
        // Get booking details
        Optional<Booking> bookingOpt = bookingDAO.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new IllegalArgumentException("Booking not found");
        }

        Booking booking = bookingOpt.get();

        // Verify booking is checked-in
        if (booking.getStatus() != BookingStatus.CHECK_IN) {
            throw new IllegalStateException("Booking must be in Checked-in status to checkout");
        }

        // Get booking details for guest name and room number
        Optional<com.hotel_management.domain.dto.booking.BookingDetailViewModel> bookingDetails = bookingDetailDAO.findById(bookingId);
        String guestName = bookingDetails.isPresent() ? bookingDetails.get().getGuestFullName() : "";
        String roomNumber = bookingDetails.isPresent() ? bookingDetails.get().getRoomNumber() : "";

        // Calculate charges
        BigDecimal roomCharges = checkoutDAO.calculateRoomCharges(bookingId);
        BigDecimal serviceCharges = checkoutDAO.calculateServiceCharges(bookingId);
        BigDecimal subtotal = roomCharges.add(serviceCharges);

        // Get active tax configuration
        Optional<TaxConfig> taxConfigOpt = taxConfigDAO.getActiveTaxConfig();
        String taxName = "";
        BigDecimal taxRate = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        Integer taxConfigId = null;

        if (taxConfigOpt.isPresent()) {
            TaxConfig taxConfig = taxConfigOpt.get();
            taxConfigId = taxConfig.getTaxConfigId();
            taxName = taxConfig.getTaxName();
            taxRate = taxConfig.getTaxRate();
            taxAmount = subtotal.multiply(taxRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }

        BigDecimal discount = BigDecimal.ZERO; // Can be extended later
        BigDecimal totalAmount = subtotal.add(taxAmount);
        BigDecimal finalAmount = totalAmount.subtract(discount);

        // Calculate number of nights
        int numberOfNights = (int) ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());

        return new CheckoutSummaryViewModel(
                bookingId,
                roomNumber,
                guestName,
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                numberOfNights,
                roomCharges,
                serviceCharges,
                subtotal,
                taxName,
                taxRate,
                taxAmount,
                discount,
                finalAmount
        );
    }

    /**
     * Process checkout payment
     * Creates invoice, payment record, and updates booking status
     */
    public boolean processCheckout(int bookingId, String paymentMethodStr) {
        try {
            // Get booking and verify status
            Optional<Booking> bookingOpt = bookingDAO.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                throw new IllegalArgumentException("Booking not found");
            }

            Booking booking = bookingOpt.get();
            if (booking.getStatus() != BookingStatus.CHECK_IN) {
                throw new IllegalStateException("Only checked-in bookings can be checked out");
            }

            // Check if invoice already exists
            Optional<Invoice> existingInvoice = invoiceDAO.findByBookingId(bookingId);
            if (existingInvoice.isPresent()) {
                throw new IllegalStateException("Invoice already exists for this booking");
            }

            // Calculate all charges
            BigDecimal roomCharges = checkoutDAO.calculateRoomCharges(bookingId);
            BigDecimal serviceCharges = checkoutDAO.calculateServiceCharges(bookingId);
            BigDecimal subtotal = roomCharges.add(serviceCharges);

            // Get tax configuration
            Optional<TaxConfig> taxConfigOpt = taxConfigDAO.getActiveTaxConfig();
            Integer taxConfigId = null;
            BigDecimal taxAmount = BigDecimal.ZERO;

            if (taxConfigOpt.isPresent()) {
                TaxConfig taxConfig = taxConfigOpt.get();
                taxConfigId = taxConfig.getTaxConfigId();
                taxAmount = subtotal.multiply(taxConfig.getTaxRate())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            }

            BigDecimal discount = BigDecimal.ZERO;
            BigDecimal totalAmount = subtotal.add(taxAmount);
            BigDecimal finalAmount = totalAmount.subtract(discount);

            // Create invoice
            Invoice invoice = new Invoice();
            invoice.setBookingId(bookingId);
            invoice.setIssueDate(LocalDate.now());
            invoice.setTotalAmount(totalAmount);
            invoice.setStatus(InvoiceStatus.PAID);
            invoice.setRoomCharges(roomCharges);
            invoice.setServiceCharges(serviceCharges);
            invoice.setTaxConfigId(taxConfigId);
            invoice.setTaxAmount(taxAmount);
            invoice.setDiscount(discount);
            invoice.setFinalAmount(finalAmount);

            int invoiceId = invoiceDAO.createInvoice(invoice);
            if (invoiceId <= 0) {
                throw new RuntimeException("Failed to create invoice");
            }

            // Create payment record
            PaymentMethod paymentMethod = PaymentMethod.fromDbValue(paymentMethodStr);
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setPaymentDate(LocalDate.now());
            payment.setAmount(finalAmount);
            payment.setPaymentMethod(paymentMethod);
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            int paymentId = paymentDAO.createPayment(payment);
            if (paymentId <= 0) {
                throw new RuntimeException("Failed to create payment record");
            }

            // Update booking status to Checked-out and payment status to Paid
            int updated = bookingDAO.updateBookingStatusAndPaymentStatus(
                    bookingId,
                    BookingStatus.CHECK_OUT,
                    PaymentStatus.PAID
            );

            if (updated <= 0) {
                throw new RuntimeException("Failed to update booking status");
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

