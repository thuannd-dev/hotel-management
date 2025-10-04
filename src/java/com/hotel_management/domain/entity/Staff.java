package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Staff {
    private int staffId;
    private String fullName;
    private StaffRole role;
    private String username;
    private String password;
    private String phone;
    private String email;
}
