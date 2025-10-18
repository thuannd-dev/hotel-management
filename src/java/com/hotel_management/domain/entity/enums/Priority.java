package com.hotel_management.domain.entity.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String dbValue;

    Priority(String dbValue) {
        this.dbValue = dbValue;
    }

    public static Priority fromDbValue(String value) {
        for (Priority priority : values()) {
            if (priority.dbValue.equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority: " + value);
    }
}
