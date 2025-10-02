/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.presentation.dto.guest;

import com.hotel_management.domain.entity.Guest;
import com.hotel_management.domain.entity.Staff;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 *
 * @author DELL
 */
@AllArgsConstructor
@Getter
public class GuestViewModel {
    private int guestId;
    private String fullName;
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
                guest.getPhone(),
                guest.getEmail(),
                guest.getAddress(),
                guest.getIdNumber(),
                guest.getDateOfBirth()
        );
    }

    //If map from dto to entity name should be toEntity()
}
