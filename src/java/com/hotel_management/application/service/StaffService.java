package com.hotel_management.application.service;

import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.domain.entity.Staff;
import com.hotel_management.domain.dto.staff.StaffViewModel;
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
        return staffDao.findAll().stream()
                .map(StaffViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public StaffViewModel getStaffById(int id) {
        Staff staff = staffDao.findById(id).orElse(null);
        return staff != null ? StaffViewModel.fromEntity(staff) : null;
//        return staffDao.findById(id)
//                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id " + id));
    }

    public StaffViewModel getStaffByUsernameAndPassword(String username, String password) {
        Staff staff = staffDao.findByUsername(username).orElse(null);
        return staff != null && BCrypt.checkpw(password, staff.getPassword()) ? StaffViewModel.fromEntity(staff) : null;
    }

    /**
     * Check if username already exists in staff table
     */
    public boolean isUsernameExists(String username) {
        return staffDao.findByUsername(username).isPresent();
    }

    /**
     * Check if username exists for another staff (used in update)
     */
    public boolean isUsernameExistsForOther(String username, int staffId) {
        return staffDao.findByUsername(username)
                .map(staff -> staff.getStaffId() != staffId)
                .orElse(false);
    }

    /**
     * Create a new staff member
     */
    public int createStaff(Staff staff) {
        return staffDao.insert(staff);
    }

    /**
     * Update an existing staff member
     */
    public boolean updateStaff(Staff staff) {
        return staffDao.update(staff) > 0;
    }

    /**
     * Update staff without changing password
     */
    public boolean updateStaffWithoutPassword(Staff staff) {
        return staffDao.updateWithoutPassword(staff) > 0;
    }

    /**
     * Delete a staff member by ID
     */
    public boolean deleteStaff(int staffId) {
        return staffDao.delete(staffId) > 0;
    }

}
