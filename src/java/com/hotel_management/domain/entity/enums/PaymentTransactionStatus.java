package com.hotel_management.domain.entity.enums;

import lombok.Getter;

/**
 * Enum for Payment Transaction Status (different from booking payment status)
 * @author thuannd.dev
 */
@Getter
public enum PaymentTransactionStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String dbValue;

    PaymentTransactionStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static PaymentTransactionStatus fromDbValue(String value) {
        for (PaymentTransactionStatus status : values()) {
            if (status.dbValue.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown payment transaction status: " + value);
    }
}

