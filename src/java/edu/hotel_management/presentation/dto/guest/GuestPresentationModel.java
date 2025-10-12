/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.presentation.dto.guest;

import edu.hotel_management.domain.entities.Guest;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author TR_NGHIA
 */

@AllArgsConstructor
@Getter
public class GuestPresentationModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int guestId;
    private String fullName;
    private String username;
    private String phone;
    private String email;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;

    // Static factory method => Che dau pwd
    public static GuestPresentationModel fromEntity(Guest guest) {
        return new GuestPresentationModel(
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
