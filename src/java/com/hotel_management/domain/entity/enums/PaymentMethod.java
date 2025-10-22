package com.hotel_management.domain.entity.enums;

import lombok.Getter;

/**
 * Enum for Payment Method
 * @author thuannd.dev
 */
@Getter
public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    ONLINE("Online");

    private final String dbValue;

    PaymentMethod(String dbValue) {
        this.dbValue = dbValue;
    }

    public static PaymentMethod fromDbValue(String value) {
        for (PaymentMethod method : values()) {
            if (method.dbValue.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + value);
    }
}

