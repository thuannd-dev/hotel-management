package com.hotel_management.domain.dto.guest;

import com.hotel_management.domain.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO for creating a new guest
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuestCreateModel {
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private String confirmPassword;

    /**
     * Convert DTO to Guest entity
     * @return Guest entity with plain password (will be hashed in service layer)
     */
    public Guest toEntity() {
        return new Guest(
            0, // ID will be auto-generated
            this.fullName,
            this.username,
            this.password, // Plain password - will be hashed in service
            this.phone,
            this.email,
            this.address,
            this.idNumber,
            this.dateOfBirth
        );
    }

    /**
     * Validate required fields
     */
    public boolean hasRequiredFields() {
        return fullName != null && !fullName.trim().isEmpty() &&
               idNumber != null && !idNumber.trim().isEmpty() &&
               username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty() &&
               confirmPassword != null && !confirmPassword.trim().isEmpty();
    }

    /**
     * Check if passwords match
     */
    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

    /**
     * Check if password meets minimum length requirement
     */
    public boolean isPasswordValid(int minLength) {
        return password != null && password.length() >= minLength;
    }

    /**
     * Trim all string fields
     */
    public void trimFields() {
        if (fullName != null) fullName = fullName.trim();
        if (phone != null) phone = phone.trim();
        if (email != null) email = email.trim();
        if (address != null) address = address.trim();
        if (idNumber != null) idNumber = idNumber.trim();
        if (username != null) username = username.trim();
    }
}

