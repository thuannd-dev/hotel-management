package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.BookingServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingService {
    private int bookingServiceId;
    private int bookingId;
    private int serviceId;
    private int quantity;
    private LocalDate serviceDate;
    private BookingServiceStatus status;
    private Integer assignedStaffId;  // Changed to Integer to allow null
    private LocalDateTime requestTime;
    private LocalDateTime completionTime;
}
