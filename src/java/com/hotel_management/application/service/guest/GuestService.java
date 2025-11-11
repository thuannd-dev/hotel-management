package com.hotel_management.application.service.guest;

import com.hotel_management.domain.entity.Guest;
import com.hotel_management.infrastructure.dao.guest.GuestDAO;
import com.hotel_management.domain.dto.guest.GuestViewModel;
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

    /**
     * Register a new guest with hashed password
     * @param guest Guest entity with plain password
     * @return Generated guest ID, or 0 if failed
     */
    public int registerGuest(Guest guest) {
        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(guest.getPassword(), BCrypt.gensalt());
        Guest guestWithHashedPassword = new Guest(
            0, // ID will be auto-generated
            guest.getFullName(),
            guest.getUsername(),
            hashedPassword,
            guest.getPhone(),
            guest.getEmail(),
            guest.getAddress(),
            guest.getIdNumber(),
            guest.getDateOfBirth()
        );
        return guestDao.insert(guestWithHashedPassword);
    }

    /**
     * Check if username already exists
     */
    public boolean isUsernameExists(String username) {
        return guestDao.findByUsername(username).isPresent();
    }

    /**
     * Check if email already exists
     */
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return guestDao.findByEmail(email).isPresent();
    }

    /**
     * Check if phone already exists
     */
    public boolean isPhoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return guestDao.findByPhone(phone).isPresent();
    }

    /**
     * Check if ID number already exists
     */
    public boolean isIdNumberExists(String idNumber) {
        return guestDao.findByIdNumber(idNumber).isPresent();
    }

    /**
     * Search guests by search type and value
     * @param searchType Type of search (guestName, guestPhone, guestIdNumber)
     * @param searchValue Value to search for
     * @return List of matching guests
     */
    public List<GuestViewModel> searchGuests(String searchType, String searchValue) {
        return guestDao.searchGuests(searchType, searchValue).stream()
                .map(GuestViewModel::fromEntity)
                .collect(Collectors.toList());
    }
}
