package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Invoice;
import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.InvoiceStatus;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.InvoiceDAO;
import com.hotel_management.infrastructure.dao.PaymentDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet(name = "CreatePaymentController", urlPatterns = {"/receptionist/create-payment"})
public class CreatePaymentController extends HttpServlet {

    private PaymentDAO paymentDao;
    private BookingDAO bookingDao;
    private InvoiceDAO invoiceDao;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        this.paymentDao = new PaymentDAO(ds);
        this.bookingDao = new BookingDAO(ds);
        this.invoiceDao = new InvoiceDAO(ds);
    }

    // show payment_page.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr == null) {
            request.setAttribute("errorMessage", "Missing booking ID.");
            forwardError(request, response);
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);

        Optional<Invoice> invoiceOpt = invoiceDao.findByBookingId(bookingId);
        if (!invoiceOpt.isPresent()) {
            request.setAttribute("errorMessage", "Invoice not found!");
            forwardError(request, response);
            return;
        }

// Không cần Method ở bước này
        request.setAttribute("invoice", invoiceOpt.get());

        request.getRequestDispatcher(Page.CREATE_PAYMENT_PAGE)
                .forward(request, response);
    }

    // submit payment
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
            PaymentMethod method = PaymentMethod.valueOf(request.getParameter("paymentMethod"));
            int guestId = Integer.parseInt(request.getParameter("guestId")); // redirect to booking detail

            Optional<Invoice> invoiceOpt = invoiceDao.findByBookingId(bookingId);
            if (!invoiceOpt.isPresent()) {
                request.setAttribute("errorMessage", "Invoice not found!");
                forwardError(request, response);
                return;
            }

            Invoice invoice = invoiceOpt.get();

            if (invoice.getStatus() == InvoiceStatus.PAID) {
                request.setAttribute("errorMessage", "Invoice already paid!");
                forwardError(request, response);
                return;
            }

            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(invoice.getFinalAmount());
            payment.setPaymentMethod(method);
            payment.setPaymentDate(LocalDate.now());
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            if (paymentDao.createPayment(payment) > 0) {

                invoiceDao.updateStatus(invoice.getInvoiceId(), InvoiceStatus.PAID);
                bookingDao.markAsPaid(bookingId);

                session.setAttribute("successMessage", "Payment Success!");

                response.sendRedirect(request.getContextPath()
                        + "/receptionist-dashboard/detail-booking?guestId="
                        + guestId + "&bookingId=" + bookingId);
                return;
            }

            request.setAttribute("errorMessage", "Payment failed.");
            forwardError(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error: " + e.getMessage());
            forwardError(request, response);
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                .forward(request, response);
    }
}
