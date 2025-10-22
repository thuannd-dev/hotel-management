package com.hotel_management.domain.entity.enums;

import lombok.Getter;

/**
 * Enum for Invoice Status
 * @author thuannd.dev
 */
@Getter
public enum InvoiceStatus {
    UNPAID("Unpaid"),
    PAID("Paid"),
    CANCELED("Canceled");

    private final String dbValue;

    InvoiceStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static InvoiceStatus fromDbValue(String value) {
        for (InvoiceStatus status : values()) {
            if (status.dbValue.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown invoice status: " + value);
    }
}