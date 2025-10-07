package com.hotel_management.application.service;

import com.hotel_management.domain.entity.Guest;
import com.hotel_management.domain.entity.Staff;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.presentation.dto.guest.GuestViewModel;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class GuestService {
    private final GuestDAO guestDao;


    public GuestService(GuestDAO guestDao) {
        this.guestDao = guestDao;
    }

    // (method reference)
    public List<GuestViewModel> getAllGuests() {
        return guestDao.findAll().stream()
                .map(GuestViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public GuestViewModel getGuestById(int id) {
        Guest guest = guestDao.findById(id).orElse(null);
        return guest != null ? GuestViewModel.fromEntity(guest) : null;
    }

    public GuestViewModel getGuestByUsernameAndPassword(String username, String password) {
        Guest guest = guestDao.findByUsername(username).orElse(null);
        return guest != null && BCrypt.checkpw(password, guest.getPassword()) ? GuestViewModel.fromEntity(guest) : null;
    }
}
