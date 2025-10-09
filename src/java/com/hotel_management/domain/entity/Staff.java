package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class Staff {
    private int staffId;
    private String fullName;
    private StaffRole role;
    private String username;
    private String password;
    private String phone;
    private String email;

    
}
