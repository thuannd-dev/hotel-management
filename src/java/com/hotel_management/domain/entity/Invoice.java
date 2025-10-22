package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity for Invoice
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Invoice {
    private int invoiceId;
    private int bookingId;
    private LocalDate issueDate;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private BigDecimal roomCharges;
    private BigDecimal serviceCharges;
    private Integer taxConfigId;
    private BigDecimal taxAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
}

