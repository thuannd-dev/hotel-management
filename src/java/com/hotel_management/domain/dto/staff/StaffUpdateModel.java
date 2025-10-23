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
public class StaffUpdateModel {
    private int staffId;
    private String fullName;
    private String role;
    private String username;
    private String password; // Optional - only if changing password
    private String phone;
    private String email;

    public Staff toEntity(String hashedPassword) {
        return new Staff(
                staffId,
                fullName,
                StaffRole.fromDbValue(role),
                username,
                hashedPassword,
                phone,
                email
        );
    }
}


