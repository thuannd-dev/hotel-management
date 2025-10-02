package com.hotel_management.application.service;

import com.hotel_management.domain.entity.Guest;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.presentation.dto.guest.GuestViewModel;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class GuestService {
    private final GuestDAO guestDao;


    public GuestService(GuestDAO guestDao) {
        this.guestDao = guestDao;
    }

    // (method reference)
    public List<GuestViewModel> getAllGuests() {
        return guestDao.findAllGuests().stream()
                .map(GuestViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public GuestViewModel getGuestById(int id) {
        Guest guest = guestDao.findGuestById(id);
        return guest != null ? GuestViewModel.fromEntity(guest) : null;
    }
}
