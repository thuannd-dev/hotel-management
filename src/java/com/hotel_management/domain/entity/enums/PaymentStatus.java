package com.hotel_management.domain.entity.enums;

import lombok.Getter;
/**
 *
 * @author thuannd.dev
 */
@Getter
public enum PaymentStatus {
    PENDING("Pending"),
    DEPOSIT_PAID("Deposit Paid"),
    GUARANTEED("Guaranteed"),
    PAID("Paid");

    private final String dbValue;

    PaymentStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static PaymentStatus fromDbValue(String value) {
        for (PaymentStatus paymentStatus : values()) {
            if (paymentStatus.dbValue.equalsIgnoreCase(value)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("Unknown payment status: " + value);
    }
}
