package com.hotel_management.presentation.controller.receptionist;

import com.hotel_management.application.service.CheckoutService;
import com.hotel_management.domain.dto.checkout.CheckoutSummaryViewModel;
import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.Invoice;
import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.InvoiceStatus;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.booking.BookingDAO;
import com.hotel_management.infrastructure.dao.booking.BookingDetailDAO;
import com.hotel_management.infrastructure.dao.booking.CheckoutBookingDAO;
import com.hotel_management.infrastructure.dao.booking.CheckoutDAO;
import com.hotel_management.infrastructure.dao.guest.GuestDAO;
import com.hotel_management.infrastructure.dao.invoice.InvoiceDAO;
import com.hotel_management.infrastructure.dao.payment.PaymentDAO;
import com.hotel_management.infrastructure.dao.TaxConfigDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet(name = "CreatePaymentController", urlPatterns = {"/receptionist/create-payment"})
public class CreatePaymentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PaymentDAO paymentDao;
    private BookingDAO bookingDao;
    private InvoiceDAO invoiceDao;
    private GuestDAO guestDao;
    private CheckoutDAO checkoutDao;
    private CheckoutService checkoutService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        BookingDAO bookingDAO = new BookingDAO(ds);
        CheckoutBookingDAO checkoutBookingDAO = new CheckoutBookingDAO(ds);
        CheckoutDAO checkoutDAO = new CheckoutDAO(ds);
        InvoiceDAO invoiceDAO = new InvoiceDAO(ds);
        PaymentDAO paymentDAO = new PaymentDAO(ds);
        TaxConfigDAO taxConfigDAO = new TaxConfigDAO(ds);
        BookingDetailDAO bookingDetailDAO = new BookingDetailDAO(ds);
        GuestDAO guestDAO = new GuestDAO(ds);

        // Gán vào biến instance
        this.bookingDao = bookingDAO;
        this.invoiceDao = invoiceDAO;
        this.paymentDao = paymentDAO;
        this.guestDao = guestDAO;
        this.checkoutDao = checkoutDAO;

        this.checkoutService = new CheckoutService(
                bookingDAO, checkoutBookingDAO, checkoutDAO,
                invoiceDAO, paymentDAO, taxConfigDAO, bookingDetailDAO
        );
    }

    // Show invoice.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr == null || bookingIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Missing booking ID.");
            forwardError(request, response);
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            // get booking
            Optional<Booking> bookingOpt = bookingDao.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                request.setAttribute("errorMessage", "Booking not found!");
                forwardError(request, response);
                return;
            }
            Booking booking = bookingOpt.get();

            // Only allow viewing invoice for CHECK_IN and CHECK_OUT bookings
            BookingStatus currentStatus = booking.getStatus();
            if (currentStatus != BookingStatus.CHECK_IN &&
                currentStatus != BookingStatus.CHECK_OUT) {
                request.setAttribute("errorMessage",
                    "Invoice is only available for checked-in or checked-out bookings. Please check-in the guest first.");
                forwardError(request, response);
                return;
            }

            // Check if invoice already exists
            Optional<Invoice> invoiceOpt = invoiceDao.findByBookingId(bookingId);
            Invoice invoice;

            if (invoiceOpt.isPresent()) {
                invoice = invoiceOpt.get();
            } else {
                // Create invoice for CHECK_IN or CHECK_OUT bookings
                CheckoutSummaryViewModel summary = checkoutService.calculateCheckoutSummary(bookingId);

                // Create new invoice
                Invoice newInvoice = new Invoice();
                newInvoice.setBookingId(bookingId);
                newInvoice.setIssueDate(LocalDate.now());
                newInvoice.setStatus(InvoiceStatus.UNPAID);
                newInvoice.setRoomCharges(summary.getRoomCharges());
                newInvoice.setServiceCharges(summary.getServiceCharges());
                newInvoice.setTaxAmount(summary.getTaxAmount());
                newInvoice.setDiscount(summary.getDiscount());
                newInvoice.setTotalAmount(summary.getSubtotal().add(summary.getTaxAmount()));
                newInvoice.setFinalAmount(summary.getFinalAmount());

                int invoiceId = invoiceDao.createInvoice(newInvoice);
                Optional<Invoice> newInvoiceOpt = invoiceDao.findById(invoiceId);
                invoice = newInvoiceOpt.orElse(newInvoice);
            }

            request.setAttribute("booking", booking);
            request.setAttribute("invoice", invoice);

            request.getRequestDispatcher(Page.CREATE_PAYMENT_PAGE)
                    .forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid booking ID format.");
            forwardError(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("errorMessage", "Server error: " + e.getMessage());
            forwardError(request, response);
        }
    }

    // Do submit payment
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionAttribute.CURRENT_USER) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int guestId = Integer.parseInt(request.getParameter("guestId"));
            PaymentMethod method = PaymentMethod.valueOf(request.getParameter("paymentMethod"));

            Optional<Invoice> invoiceOpt = invoiceDao.findByBookingId(bookingId);
            if (!invoiceOpt.isPresent()) {
                request.setAttribute("errorMessage", "Invoice not found!");
                forwardError(request, response);
                return;
            }

            Invoice invoice = invoiceOpt.get();

            // if invoice do not have finalAmount -> recalculate by CheckoutService
            if (invoice.getFinalAmount() == null || invoice.getFinalAmount().compareTo(BigDecimal.ZERO) == 0) {
                CheckoutSummaryViewModel summary = checkoutService.calculateCheckoutSummary(bookingId);
                BigDecimal newFinalAmount = summary.getFinalAmount();
                invoice.setFinalAmount(newFinalAmount);
            }

            // If invoiceStatus = Paid
            if (invoice.getStatus() == InvoiceStatus.PAID) {
                request.setAttribute("errorMessage", "Invoice already paid!");
                forwardError(request, response);
                return;
            }

            // method = online, redirect to external page
            if (method == PaymentMethod.ONLINE) {
                // save sesion and redirect to payment-page
                response.sendRedirect(request.getContextPath()
                        + "/receptionist/payment-page?bookingId=" + bookingId + "&guestId=" + guestId);
                return;
            }

            // other payment method as Cash, Credit Card, Debit Card
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(invoice.getFinalAmount());
            payment.setPaymentMethod(method);
            payment.setPaymentDate(LocalDate.now());
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            System.out.println("[CreatePaymentController] Creating payment for bookingId: " + bookingId);
            System.out.println("[CreatePaymentController] Invoice ID: " + invoice.getInvoiceId());
            System.out.println("[CreatePaymentController] Invoice current status: " + invoice.getStatus());
            System.out.println("[CreatePaymentController] Payment amount: " + invoice.getFinalAmount());
            System.out.println("[CreatePaymentController] Payment method: " + method);

            int paymentId = paymentDao.createPayment(payment);
            if (paymentId > 0) {
                System.out.println("[CreatePaymentController] Payment created successfully with ID: " + paymentId);

                // Update invoice status to PAID
                int invoiceUpdateResult = invoiceDao.updateStatus(invoice.getInvoiceId(), InvoiceStatus.PAID);
                System.out.println("[CreatePaymentController] Invoice status update result: " + invoiceUpdateResult);

                if (invoiceUpdateResult > 0) {
                    System.out.println("[CreatePaymentController] Invoice status updated to PAID successfully");
                } else {
                    System.err.println("[CreatePaymentController] Failed to update invoice status!");
                }

                // Update booking payment status
                int bookingUpdateResult = bookingDao.markAsPaid(bookingId);
                System.out.println("[CreatePaymentController] Booking markAsPaid result: " + bookingUpdateResult);

                session.setAttribute("successMessage", "Payment Success!");
                response.sendRedirect(request.getContextPath()
                        + "/receptionist-dashboard/detail-booking?guestId=" + guestId + "&bookingId=" + bookingId);
                return;
            }

            System.err.println("[CreatePaymentController] Failed to create payment!");
            request.setAttribute("errorMessage", "Payment failed.");
            forwardError(request, response);

        } catch (NumberFormatException e) {
            System.err.println("[CreatePaymentController] NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input format: " + e.getMessage());
            forwardError(request, response);
        } catch (IllegalArgumentException e) {
            System.err.println("[CreatePaymentController] IllegalArgumentException: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid payment method: " + e.getMessage());
            forwardError(request, response);
        } catch (ServletException e) {
            System.err.println("[CreatePaymentController] ServletException: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error: " + e.getMessage());
            forwardError(request, response);
        } catch (IOException e) {
            System.err.println("[CreatePaymentController] IOException: " + e.getMessage());
            e.printStackTrace();
            throw e; // Rethrow IOException
        } catch (Exception e) {
            System.err.println("[CreatePaymentController] Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            forwardError(request, response);
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
    }
}
