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

    /**
     * Validates if a status transition is allowed based on business rules:
     * - Available → Dirty, Maintenance
     * - Occupied → no transitions allowed
     * - Maintenance → Available
     * - Dirty → Available, Maintenance
     *
     * @param from Current status
     * @param to Target status
     * @return true if transition is valid, false otherwise
     */
    public static boolean isValidTransition(RoomStatus from, RoomStatus to) {
        if (from == to) {
            return true; // No change is always valid
        }

        switch (from) {
            case AVAILABLE:
                return to == DIRTY || to == MAINTENANCE;
            case OCCUPIED:
                return false; // No transitions allowed from occupied
            case MAINTENANCE:
                return to == AVAILABLE;
            case DIRTY:
                return to == AVAILABLE || to == MAINTENANCE;
            default:
                return false;
        }
    }
}
