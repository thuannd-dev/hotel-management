/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.presentation.dto.staff;

import edu.hotel_management.domain.entities.Staff;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author TR_NGHIA
 */

@AllArgsConstructor
@Getter
public class StaffPresentationModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int staffId;
    private final String fullName;
    private final String role;
    private final String username;
    private final String phone;
    private final String email;

    
    // Static factory method
    public static StaffPresentationModel fromEntity(Staff staff) {
        return new StaffPresentationModel(
                staff.getStaffId(),
                staff.getFullName(),
                staff.getRole().getValue(),
                staff.getUsername(),
                staff.getPhone(),
                staff.getEmail()
        );
    }
    
}