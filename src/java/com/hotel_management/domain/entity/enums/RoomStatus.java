package com.hotel_management.domain.entity.enums;

import lombok.Getter;

@Getter
public enum RoomStatus {
    AVAILABLE("Available"),
    OCCUPIED("Occupied"),
    DIRTY("Dirty"),
    MAINTENANCE("Maintenance");

    private final String dbValue;

    RoomStatus(String dbValue) { this.dbValue = dbValue; }

    public static RoomStatus fromDbValue(String value) {
        for (RoomStatus roomStatus : values()) {
            if (roomStatus.dbValue.equalsIgnoreCase(value)) {
                return roomStatus;
            }
        }
        throw new IllegalArgumentException("Unknown room status: " + value);
    }
}
