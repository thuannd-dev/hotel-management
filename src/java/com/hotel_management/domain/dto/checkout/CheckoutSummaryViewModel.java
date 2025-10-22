package com.hotel_management.domain.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ViewModel for checkout summary with all charges
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutSummaryViewModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookingId;
    private String roomNumber;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;

    private BigDecimal roomCharges;
    private BigDecimal serviceCharges;
    private BigDecimal subtotal;

    private String taxName;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;

    private BigDecimal discount;
    private BigDecimal finalAmount;
}

