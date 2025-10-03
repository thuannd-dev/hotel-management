package com.hotel_management.application.service;

import com.hotel_management.infrastructure.dao.StaffDAO;

import com.hotel_management.domain.entity.Staff;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
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
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Staff staff = staffDao.findStaffByUsername(username);
        return staff != null && BCrypt.checkpw(password, staff.getPassword()) ? StaffViewModel.fromEntity(staff) : null;
    }

}
