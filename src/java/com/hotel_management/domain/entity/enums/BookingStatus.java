package com.hotel_management.domain.entity.enums;

import lombok.Getter;
/**
 *
 * @author thuannd.dev
 */
@Getter
public enum BookingStatus {
    RESERVED("Reserved"),
    CHECK_IN("Checked-in"),
    CHECK_OUT("Checked-out"),
    CANCELED("Canceled");

    private final String dbValue;

    BookingStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static BookingStatus fromDbValue(String value) {
        for (BookingStatus bookingStatus : values()) {
            if (bookingStatus.dbValue.equalsIgnoreCase(value)) {
                return bookingStatus;
            }
        }
        throw new IllegalArgumentException("Unknown booking status: " + value);
    }
}