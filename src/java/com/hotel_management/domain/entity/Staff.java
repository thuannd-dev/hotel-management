/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author DELL
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
