package com.hotel_management.domain.entity.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String dbValue;

    TaskStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static TaskStatus fromDbValue(String value) {
        for (TaskStatus taskStatus : values()) {
            if (taskStatus.dbValue.equalsIgnoreCase(value)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Unknown task status: " + value);
    }
}
