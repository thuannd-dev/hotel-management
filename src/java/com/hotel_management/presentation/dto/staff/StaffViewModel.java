/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.presentation.dto.staff;

import com.hotel_management.domain.entity.Staff;

/**
 *
 * @author DELL
 */
public class StaffViewModel {
    private final int staffid;
    private final String fullname;
    private final String role;
    private final String username;
    private final String phone;
    private final String email;

    public StaffViewModel(int staffid, String fullname, String role, String username, String phone, String email) {
        this.staffid = staffid;
        this.fullname = fullname;
        this.role = role;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }
    
    // Static factory method
    public static StaffViewModel fromEntity(Staff staff) {
        return new StaffViewModel(
                staff.getStaffid(),
                staff.getFullname(),
                staff.getRole(),
                staff.getUsername(),
                staff.getPhone(),
                staff.getEmail()
        );
    }
    
    //If map from dto to entity name should be toEntity()

    public int getStaffid() {
        return staffid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    
}
