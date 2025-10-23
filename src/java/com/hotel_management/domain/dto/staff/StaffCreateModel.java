package com.hotel_management.domain.dto.staff;

import com.hotel_management.domain.entity.Staff;
import com.hotel_management.domain.entity.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
@Setter
public class StaffCreateModel {
    private String fullName;
    private String role;
    private String username;
    private String password;
    private String phone;
    private String email;

    public Staff toEntity(String hashedPassword) {
        return new Staff(
                0, // ID will be auto-generated
                fullName,
                StaffRole.fromDbValue(role),
                username,
                hashedPassword,
                phone,
                email
        );
    }
}