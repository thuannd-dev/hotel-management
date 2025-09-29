/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.presentation.dto.staff;

import com.hotel_management.domain.entity.Staff;
import java.util.List;

/**
 *
 * @author DELL
 */
public class StaffViewModel {
    private int staffid;
    private String fullname;
    private String role;
    private String username;
    private String phone;
    private String email;

    public StaffViewModel(int staffid, String fullname, String role, String username, String phone, String email) {
        this.staffid = staffid;
        this.fullname = fullname;
        this.role = role;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public int getStaffid() {
        return staffid;
    }

    public void setStaffid(int staffid) {
        this.staffid = staffid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
