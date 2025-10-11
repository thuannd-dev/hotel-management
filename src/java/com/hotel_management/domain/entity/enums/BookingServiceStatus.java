package com.hotel_management.domain.entity.enums;

import lombok.Getter;
/**
 *
 * @author thuannd.dev
 */
@Getter
public enum BookingServiceStatus {
    REQUESTED("Requested"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String dbValue;

    BookingServiceStatus(String dbValue) { this.dbValue = dbValue; }

    public static BookingServiceStatus fromDbValue(String value) {
        for (BookingServiceStatus bookingServiceStatus : values()) {
            if (bookingServiceStatus.dbValue.equalsIgnoreCase(value)) {
                return bookingServiceStatus;
            }
        }
        throw new IllegalArgumentException("Unknown booking service status: " + value);
    }
}
