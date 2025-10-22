package com.hotel_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity for Tax Configuration
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxConfig {
    private int taxConfigId;
    private String taxName;
    private BigDecimal taxRate;
    private String description;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private int createdBy;
    private LocalDateTime createdDate;
    private boolean isActive;
}

