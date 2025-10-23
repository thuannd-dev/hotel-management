package com.hotel_management.domain.dto.tax;

import com.hotel_management.domain.entity.TaxConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Update model for TaxConfig
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxConfigUpdateModel {
    private int taxConfigId;
    private String taxName;
    private BigDecimal taxRate;
    private String description;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private boolean isActive;

    public TaxConfig toEntity(TaxConfig existing) {
        existing.setTaxConfigId(taxConfigId);
        existing.setTaxName(taxName);
        existing.setTaxRate(taxRate);
        existing.setDescription(description);
        existing.setEffectiveFrom(effectiveFrom);
        existing.setEffectiveTo(effectiveTo);
        existing.setActive(isActive);
        return existing;
    }
}


