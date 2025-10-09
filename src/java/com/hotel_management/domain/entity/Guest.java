package com.hotel_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Guest {
    private int guestId;
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;
}
