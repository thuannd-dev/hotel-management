/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.presentation.dto.staff;
import com.hotel_management.domain.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author DELL
 */
@AllArgsConstructor
@Getter
public class StaffViewModel {
    private final int staffid;
    private final String fullname;
    private final String role;
    private final String username;
    private final String phone;
    private final String email;

    
    // Static factory method
    public static StaffViewModel fromEntity(Staff staff) {
        return new StaffViewModel(
                staff.getStaffId(),
                staff.getFullName(),
                staff.getRole(),
                staff.getUsername(),
                staff.getPhone(),
                staff.getEmail()
        );
    }
    
    //If map from dto to entity name should be toEntity()
}
