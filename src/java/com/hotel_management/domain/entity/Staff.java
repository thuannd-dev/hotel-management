package com.hotel_management.domain.entity;

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
    private String role;
    private String username;
    private String password;
    private String phone;
    private String email;
}
