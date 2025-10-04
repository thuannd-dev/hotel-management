package com.hotel_management.domain.entity.enums;
import lombok.Getter;
/**
 *
 * @author thuannd.dev
 */
@Getter
public enum StaffRole {
    RECEPTIONIST("Receptionist"),
    MANAGER("Manager"),
    HOUSEKEEPING("Housekeeping"),
    SERVICE_STAFF("ServiceStaff"),
    ADMIN("Admin");

    private final String dbValue;

    StaffRole(String dbValue) {
        this.dbValue = dbValue;
    }

    public static StaffRole fromDbValue(String value) {
        for (StaffRole role : values()) {
            if (role.dbValue.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
