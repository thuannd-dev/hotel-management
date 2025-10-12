package edu.hotel_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 *
 * @author TR_NGHIA
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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