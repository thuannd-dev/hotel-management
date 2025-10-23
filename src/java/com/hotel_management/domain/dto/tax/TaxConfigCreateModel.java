package com.hotel_management.domain.dto.tax;

import com.hotel_management.domain.entity.TaxConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Create model for TaxConfig
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxConfigCreateModel {
    private String taxName;
    private BigDecimal taxRate;
    private String description;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private int createdBy;
    private boolean isActive;

    public TaxConfig toEntity() {
        TaxConfig taxConfig = new TaxConfig();
        taxConfig.setTaxName(taxName);
        taxConfig.setTaxRate(taxRate);
        taxConfig.setDescription(description);
        taxConfig.setEffectiveFrom(effectiveFrom);
        taxConfig.setEffectiveTo(effectiveTo);
        taxConfig.setCreatedBy(createdBy);
        taxConfig.setCreatedDate(LocalDateTime.now());
        taxConfig.setActive(isActive);
        return taxConfig;
    }
}

