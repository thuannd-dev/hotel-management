/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.application.service;

import com.hotel_management.infrastructure.dao.StaffDAO;

import com.hotel_management.domain.entity.Staff;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class StaffService {

    private final StaffDAO staffDao;

    public StaffService(StaffDAO staffDao) {
        this.staffDao = staffDao;
    }

    // (method reference)
    public List<StaffViewModel> getAllStaffs() {
        return staffDao.findAllStaffs().stream()
                .map(StaffViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public StaffViewModel getStaffById(int id) {
        Staff staff = staffDao.findStaffById(id);
        return staff != null ? StaffViewModel.fromEntity(staff) : null;
    }

    public StaffViewModel getStaffByUsernameAndPassword(String username, String password) {
        Staff staff = staffDao.findStaffByUsernameAndPassword(username, password);
        return staff != null ? StaffViewModel.fromEntity(staff) : null;
    }

}
