package edu.hotel_management.domain.entities;

import edu.hotel_management.domain.entities.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author TR_NGHIA
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Staff {
    private int staffId;
    private String fullName;
    private StaffRole role;
    private String username;
    private String password;
    private String phone;
    private String email;
}