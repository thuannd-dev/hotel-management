package com.hotel_management.domain.entity.enums;

import lombok.Getter;

@Getter
public enum IssueStatus {
    REPORTED("Reported"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved");

    private final String dbValue;

    IssueStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public static IssueStatus fromDbValue(String value) {
        for (IssueStatus issueStatus : values()) {
            if (issueStatus.dbValue.equalsIgnoreCase(value)) {
                return issueStatus;
            }
        }
        throw new IllegalArgumentException("Unknown issue status: " + value);
    }
}
