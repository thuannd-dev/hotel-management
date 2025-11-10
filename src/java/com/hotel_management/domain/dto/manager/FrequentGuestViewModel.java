package com.hotel_management.domain.dto.manager;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Top Frequent Guests Report
 * @author Chauu
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FrequentGuestViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int guestId;
    private String fullName;
    private String phone;
    private String email;
    private int bookingCount;
    private int totalNights;
}

