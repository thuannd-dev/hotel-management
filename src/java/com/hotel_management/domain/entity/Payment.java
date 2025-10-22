package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.PaymentMethod;
import com.hotel_management.domain.entity.enums.PaymentTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity for Payment
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    private int paymentId;
    private int bookingId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentTransactionStatus status;
}


