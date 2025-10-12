/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.application.service;

import edu.hotel_management.domain.entities.Staff;
import edu.hotel_management.infrastructure.persistence.dao.StaffDAO;
import edu.hotel_management.presentation.dto.staff.StaffPresentationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author TR_NGHIA
 */
public class StaffService {
    private final StaffDAO staffDao;

    public StaffService(StaffDAO staffDao) {
        this.staffDao = staffDao;
    }

    public List<StaffPresentationModel> getAllStaffs() {
        List<Staff> staffEntities = staffDao.findAll();
        List<StaffPresentationModel> staffPresentationModel = new ArrayList<>();

        for (Staff entity : staffEntities) {
            StaffPresentationModel viewModel = StaffPresentationModel.fromEntity(entity);
            staffPresentationModel.add(viewModel);
        }
        
        return staffPresentationModel;
    }

    public StaffPresentationModel getStaffById(int id) {
        Optional<Staff> optionalStaff = staffDao.findById(id);

        if (optionalStaff.isPresent()) {
            Staff staffEntity = optionalStaff.get();
            return StaffPresentationModel.fromEntity(staffEntity);
        } else {
            return null;
        }
    }

    public StaffPresentationModel getStaffByUsernameAndPassword(String username, String password) {
        Optional<Staff> optionalStaff = staffDao.findByUsername(username);

        if (!optionalStaff.isPresent()) {
            return null;
        }

        Staff staffEntity = optionalStaff.get();
        String hashedPasswordFromDB = staffEntity.getPassword();
        boolean passwordMatches = BCrypt.checkpw(password, hashedPasswordFromDB);

        if (passwordMatches) {
            return StaffPresentationModel.fromEntity(staffEntity);
        } else {
            return null;
        }
    }
}

