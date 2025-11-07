/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.PaymentDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;

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

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceProvider.getDataSource();
        this.bookingDao = new BookingDAO(ds);
        this.paymentDao = new PaymentDAO(ds);
        this.guestDao = new GuestDAO(ds);
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

            // Get the total amount to insert into Payment table
            BigDecimal totalAmount = bookingDao.calculateTotalAmount(bookingId);

            // Mark payment status in booking as Paid
            int rows = bookingDao.markAsPaid(bookingId);

            if (rows <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update payment status.");
                return;
            }

            // Create Payment record
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setPaymentDate(LocalDate.now());
            payment.setAmount(totalAmount);
            payment.setPaymentMethod(PaymentMethod.CASH);  // Or ONLINE if you add QR logic
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            PaymentDAO paymentDAO = new PaymentDAO(DataSourceProvider.getDataSource());
            paymentDAO.createPayment(payment);

            // Show success popup message
            request.getSession().setAttribute("popupMessage", "Payment completed successfully!");

            // Redirect back to Booking Detail of that guest id page
            int guestId = Integer.parseInt(request.getParameter("guestId"));
            response.sendRedirect(request.getContextPath()
                    + "/receptionist-dashboard/detail-booking?guestId=" + guestId);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid booking or guest ID format.");
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
        }
    }
}
