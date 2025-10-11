package com.hotel_management.domain.dto.booking_service;

import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.domain.entity.BookingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class BookingServiceViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bookingServiceId;
    private int bookingId;
    private int serviceId;
    private int quantity;
    private LocalDate serviceDate;
    private String status;
    private int assignedStaffId;
    private LocalDateTime requestTime;
    private LocalDateTime completionTime;

    // Static factory method
    public static BookingServiceViewModel fromEntity(BookingService bookingService) {
        return new BookingServiceViewModel(
            bookingService.getBookingServiceId(),
            bookingService.getBookingId(),
            bookingService.getServiceId(),
            bookingService.getQuantity(),
            bookingService.getServiceDate(),
            bookingService.getStatus().name(),
            bookingService.getAssignedStaffId(),
            bookingService.getRequestTime(),
            bookingService.getCompletionTime()
        );
    }
}
