package com.hotel_management.domain.dto.staff;
import com.hotel_management.domain.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;
/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class StaffViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int staffId;
    private final String fullName;
    private final String role;
    private final String username;
    private final String phone;
    private final String email;

    
    // Static factory method
    public static StaffViewModel fromEntity(Staff staff) {
        return new StaffViewModel(
                staff.getStaffId(),
                staff.getFullName(),
                staff.getRole().name(),
                staff.getUsername(),
                staff.getPhone(),
                staff.getEmail()
        );
    }
    
    //If map from dto to entity name should be toEntity()
}
