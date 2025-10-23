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
 * ViewModel for TaxConfig
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxConfigViewModel {
    private int taxConfigId;
    private String taxName;
    private BigDecimal taxRate;
    private String description;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private int createdBy;
    private String createdByName;
    private LocalDateTime createdDate;
    private boolean isActive;

    public static TaxConfigViewModel fromEntity(TaxConfig entity) {
        return new TaxConfigViewModel(
                entity.getTaxConfigId(),
                entity.getTaxName(),
                entity.getTaxRate(),
                entity.getDescription(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getCreatedBy(),
                null, // Will be populated from join
                entity.getCreatedDate(),
                entity.isActive()
        );
    }

    public String getStatusLabel() {
        if (!isActive) {
            return "Inactive";
        }
        LocalDate now = LocalDate.now();
        if (now.isBefore(effectiveFrom)) {
            return "Scheduled";
        } else if (effectiveTo == null || now.isBefore(effectiveTo) || now.isEqual(effectiveTo)) {
            return "Active";
        } else {
            return "Expired";
        }
    }

    public String getStatusBadgeClass() {
        String status = getStatusLabel();
        switch (status) {
            case "Active":
                return "badge-success";
            case "Scheduled":
                return "badge-info";
            case "Expired":
                return "badge-secondary";
            case "Inactive":
                return "badge-danger";
            default:
                return "badge-light";
        }
    }
}