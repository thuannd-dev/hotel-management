package com.hotel_management.domain.dto.manager;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Cancellation Statistics Report
 * @author Chauu
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancellationStatViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalDate cancellationDate;
    private int cancellationCount;
    private String topReason;
    private int totalBookings;
    private double cancellationRate;
}



