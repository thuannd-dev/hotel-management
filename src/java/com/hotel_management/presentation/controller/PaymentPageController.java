/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Payment;
import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import com.hotel_management.infrastructure.dao.BookingDAO;
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
@WebServlet(name = "PaymentPageController", urlPatterns = {"/payment-page"})
public class PaymentPageController extends HttpServlet {

    private BookingDAO bookingDao;
    private PaymentDAO paymentDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceProvider.getDataSource();
        this.bookingDao = new BookingDAO(ds);
        this.paymentDao = new PaymentDAO(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing bookingId parameter");
            return;
        }

        request.setAttribute("bookingId", bookingIdParam);
        request.getRequestDispatcher("/WEB-INF/views/payment_page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));

            // 1️⃣ Get the total amount to insert into Payment table
            BigDecimal totalAmount = bookingDao.calculateTotalAmount(bookingId);

            // 2️⃣ Mark payment status in booking as Paid
            int rows = bookingDao.markAsPaid(bookingId);

            if (rows <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update payment status.");
                return;
            }

            // 3️⃣ Create Payment record
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setPaymentDate(LocalDate.now());
            payment.setAmount(totalAmount);
            payment.setPaymentMethod(PaymentMethod.CASH);  // Or ONLINE if you add QR logic
            payment.setStatus(PaymentTransactionStatus.COMPLETED);

            PaymentDAO paymentDAO = new PaymentDAO(DataSourceProvider.getDataSource());
            paymentDAO.createPayment(payment);

            // 4️⃣ Show success popup message
            request.getSession().setAttribute("popupMessage", "Payment completed successfully!");

            // 5️⃣ Redirect back to Booking Detail page
            response.sendRedirect(request.getContextPath() + "/booking-detail?bookingId=" + bookingId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }
}