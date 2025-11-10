/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Invoice;
import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.InvoiceStatus;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.InvoiceDAO;
import com.hotel_management.infrastructure.dao.PaymentDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PaymentPageController
 * --------------------------------------------------------- This controller
 * simulates a payment gateway page. When a user clicks "Pay Now" from the
 * invoice, it displays a fake payment page with a QR or button. Once confirmed,
 * it updates the booking status to "Paid".
 */
@WebServlet(name = "PaymentPageController", urlPatterns = {"/receptionist/payment-page"})
public class PaymentPageController extends HttpServlet {

    private BookingDAO bookingDao;
    private PaymentDAO paymentDao;
    private GuestDAO guestDao;
    private InvoiceDAO invoiceDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceProvider.getDataSource();
        this.bookingDao = new BookingDAO(ds);
        this.paymentDao = new PaymentDAO(ds);
        this.guestDao = new GuestDAO(ds);
        this.invoiceDao = new InvoiceDAO(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdParam = request.getParameter("bookingId");
        String guestIdParam = request.getParameter("guestId");
        if (bookingIdParam == null || guestIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        request.setAttribute("bookingId", bookingIdParam);
        request.setAttribute("guestId", guestIdParam);
        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/receptionist/payment_page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int guestId = Integer.parseInt(request.getParameter("guestId"));

            System.out.println("[PaymentPageController] Processing online payment for bookingId: " + bookingId);

            // Get invoice by booking ID
            Optional<Invoice> invoiceOpt = invoiceDao.findByBookingId(bookingId);
            if (!invoiceOpt.isPresent()) {
                System.err.println("[PaymentPageController] Invoice not found for bookingId: " + bookingId);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invoice not found.");
                return;
            }

            Invoice invoice = invoiceOpt.get();
            BigDecimal totalAmount = invoice.getFinalAmount();

            if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                // Fallback to calculating total amount
                totalAmount = bookingDao.calculateTotalAmount(bookingId);
            }

            System.out.println("[PaymentPageController] Invoice ID: " + invoice.getInvoiceId());
            System.out.println("[PaymentPageController] Total amount: " + totalAmount);

            // Create Payment record with ONLINE method
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setPaymentDate(LocalDate.now());
            payment.setAmount(totalAmount);
            payment.setPaymentMethod(PaymentMethod.ONLINE);  // Changed from CASH to ONLINE
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            int paymentId = paymentDao.createPayment(payment);
            System.out.println("[PaymentPageController] Payment created with ID: " + paymentId);

            if (paymentId > 0) {
                // Update invoice status to PAID
                int invoiceUpdateResult = invoiceDao.updateStatus(invoice.getInvoiceId(), InvoiceStatus.PAID);
                System.out.println("[PaymentPageController] Invoice status update result: " + invoiceUpdateResult);

                if (invoiceUpdateResult > 0) {
                    System.out.println("[PaymentPageController] Invoice status updated to PAID successfully");
                } else {
                    System.err.println("[PaymentPageController] Failed to update invoice status!");
                }

                // Mark booking payment status as Paid
                int bookingUpdateResult = bookingDao.markAsPaid(bookingId);
                System.out.println("[PaymentPageController] Booking markAsPaid result: " + bookingUpdateResult);

                if (bookingUpdateResult <= 0) {
                    System.err.println("[PaymentPageController] Failed to update booking payment status");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update payment status.");
                    return;
                }

                // Show success popup message
                request.getSession().setAttribute("successMessage", "Online payment completed successfully!");
                System.out.println("[PaymentPageController] Payment completed successfully");

                // Redirect back to Booking Detail page
                response.sendRedirect(request.getContextPath()
                        + "/receptionist-dashboard/detail-booking?guestId=" + guestId + "&bookingId=" + bookingId);
            } else {
                System.err.println("[PaymentPageController] Failed to create payment record");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create payment.");
            }

        } catch (NumberFormatException e) {
            System.err.println("[PaymentPageController] NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid booking or guest ID format.");
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[PaymentPageController] Exception: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
        }
    }
}
