/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.domain.dto.guest;

import com.hotel_management.domain.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class GuestViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int guestId;
    private String fullName;
    private String username;
    private String phone;
    private String email;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;

    // Static factory method
    public static GuestViewModel fromEntity(Guest guest) {
        return new GuestViewModel(
                guest.getGuestId(),
                guest.getFullName(),
                guest.getUsername(),
                guest.getPhone(),
                guest.getEmail(),
                guest.getAddress(),
                guest.getIdNumber(),
                guest.getDateOfBirth()
        );
    }
}
