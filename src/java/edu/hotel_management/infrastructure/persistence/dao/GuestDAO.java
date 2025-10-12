package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Guest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import java.sql.Date; 

/**
 *
 * @author TR_NGHIA
 */

public class GuestDAO extends BaseDAO<Guest> {

    // ========= CONSTRUCTOR ĐỂ INJECT DATASOURCE =========
    public GuestDAO(DataSource ds) {
        super(ds);
    }

    // ========= TRIỂN KHAI ÁNH XẠ TỪ RESULTSET SANG OBJECT =========
    @Override
    public Guest mapRowtoObject(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setGuestId(rs.getInt("GuestID"));
        guest.setFullName(rs.getString("FullName"));
        guest.setUsername(rs.getString("UserName"));
        guest.setPassword(rs.getString("Password"));
        guest.setPhone(rs.getString("Phone"));
        guest.setEmail(rs.getString("Email"));
        guest.setAddress(rs.getString("Address"));
        guest.setIdNumber(rs.getString("IDNumber"));
        
        java.sql.Date dob = rs.getDate("DateOfBirth");
        if (dob != null) {
            guest.setDateOfBirth(dob.toLocalDate());
        }
        
        return guest;
    }

    // ========= LẤY TẤT CẢ GUEST =========
    public List<Guest> findAll() {
        return query("SELECT * FROM GUEST");
    }

    // ========= TÌM GUEST THEO ID =========
    public Optional<Guest> findById(int id) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE GuestID = ?", id);
        return guests.stream().findFirst();
    }

    // ========= TÌM GUEST THEO USERNAME =========
    public Optional<Guest> findByUsername(String userName) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE UserName = ?", userName);
        return guests.stream().findFirst();
    }
    
    // ========= TÌM GUEST THEO IDNUMBER =========
    public Optional<Guest> findByIdNumber(String IdNumber) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE IdNumber = ?", IdNumber);
        return guests.stream().findFirst();
    }

    // ========= TÌM GUEST THEO EMAIL =========
    public Optional<Guest> findByEmail(String email) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE Email = ?", email);
        return guests.stream().findFirst();
    }
    
    // ========= TÌM GUEST THEO PHONE =========
    public Optional<Guest> findByPhone(String phone) {
        List<Guest> guests = query("SELECT * FROM GUEST WHERE Phone = ?", phone);
        return guests.stream().findFirst();
    }

    // ========= THÊM MỘT GUEST MỚI =========
    public boolean create(Guest guest) {
        String sql = "INSERT INTO GUEST (FullName, Phone, Email, Password, Address, IdNumber, DateOfBirth, Username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Date dobSQL = (guest.getDateOfBirth() != null) ? Date.valueOf(guest.getDateOfBirth()) : null;
        
        int result = update(sql,
                guest.getFullName(),
                guest.getPhone(),
                guest.getEmail(),
                guest.getPassword(),
                guest.getAddress(),
                guest.getIdNumber(),
                dobSQL,
                guest.getUsername()
        );
        
        return result > 0;
    }
}