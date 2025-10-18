package com.hotel_management.domain.entity.enums;

import lombok.Getter;

@Getter
public enum TaskType {
    REGULAR("Regular"),
    DEEP("Deep"),
    POST_CHECKOUT("Post-Checkout");

    private final String dbValue;

    TaskType(String dbValue) {
        this.dbValue = dbValue;
    }

    public static TaskType fromDbValue(String value) {
        for (TaskType taskType : values()) {
            if (taskType.dbValue.equalsIgnoreCase(value)) {
                return taskType;
            }
        }
        throw new IllegalArgumentException("Unknown task type: " + value);
    }
}
