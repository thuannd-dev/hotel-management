package edu.hotel_management.application.service;

import edu.hotel_management.domain.entities.Guest;
import edu.hotel_management.infrastructure.persistence.dao.GuestDAO;
import edu.hotel_management.presentation.dto.guest.GuestPresentationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author TR_NGHIA
 */
public class GuestService {

    private final GuestDAO guestDao;

    public GuestService(GuestDAO guestDao) {
        this.guestDao = guestDao;
    }

    // =========================================================================
    // SECTION: PHƯƠNG THỨC ĐĂNG KÝ TÀI KHOẢN GUEST
    // =========================================================================
    // Mục đích: Xử lý logic nghiệp vụ cho việc đăng ký tài khoản mới.
    // Kiểm tra thông tin trùng lặp và gọi DAO để lưu vào CSDL.
    // =========================================================================

    public String registerGuest(Guest guest) {
        // Kiểm tra sự tồn tại của các trường UNIQUE
        if (guestDao.findByUsername(guest.getUsername()).isPresent()) {
            return "Username already exists.";
        }
        if (guest.getEmail() != null && !guest.getEmail().isEmpty() && guestDao.findByEmail(guest.getEmail()).isPresent()) {
            return "Email is already registered.";
        }
        if (guest.getPhone() != null && !guest.getPhone().isEmpty() && guestDao.findByPhone(guest.getPhone()).isPresent()) {
            return "Phone number is already registered.";
        }

        if (guest.getIdNumber()!= null && !guest.getIdNumber().isEmpty() && guestDao.findByIdNumber(guest.getIdNumber()).isPresent()) {
            return "Phone number is already registered.";
        }
        
        boolean isSuccess = guestDao.create(guest);
        
        if (isSuccess) {
            return null; 
        } else {
            return "An error occurred during registration.";
        }
    }
    
    // =========================================================================
    // CÁC PHƯƠNG THỨC HIỆN CÓ
    // =========================================================================

    public List<GuestPresentationModel> getAllGuests() {
        List<Guest> guestEntities = guestDao.findAll();
        List<GuestPresentationModel> guestViewModels = new ArrayList<>();

        for (Guest entity : guestEntities) {
            GuestPresentationModel viewModel = GuestPresentationModel.fromEntity(entity);
            guestViewModels.add(viewModel);
        }

        return guestViewModels;
    }

    public GuestPresentationModel getGuestById(int id) {
        Optional<Guest> optionalGuest = guestDao.findById(id);

        if (optionalGuest.isPresent()) {
            Guest guestEntity = optionalGuest.get();
            return GuestPresentationModel.fromEntity(guestEntity);
        } else {
            return null;
        }
    }

    public GuestPresentationModel getGuestByUsernameAndPassword(String username, String password) {
        Optional<Guest> optionalGuest = guestDao.findByUsername(username);

        if (!optionalGuest.isPresent()) {
            return null;
        }

        Guest guestEntity = optionalGuest.get();
        String passwordFromDB = guestEntity.getPassword();
        
        if (password.equals(passwordFromDB)) {
            return GuestPresentationModel.fromEntity(guestEntity);
        } else {
            return null;
        }
    }
}