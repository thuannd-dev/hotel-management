package com.hotel_management.domain.dto.booking_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingServiceRequestReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime requestTime;
    private String guestName;
    private String roomNumber;
    private String serviceName;
    private int quantity;
    private String assignedStaffName;

}
