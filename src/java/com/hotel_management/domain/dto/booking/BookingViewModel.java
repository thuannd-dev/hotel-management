package com.hotel_management.domain.dto.booking;

import com.hotel_management.domain.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bookingId;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private String status;
    private int totalGuest;
    private String specialRequests;
    private String paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;

    // Static factory method
    public static BookingViewModel fromEntity(Booking booking) {
        return new BookingViewModel(
                booking.getBookingId(),
                booking.getGuestId(),
                booking.getRoomId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getBookingDate(),
                booking.getStatus().name(),
                booking.getTotalGuests(),
                booking.getSpecialRequests(),
                booking.getPaymentStatus().name(),
                booking.getCancellationDate(),
                booking.getCancellationReason()
        );
    }

    //If map from dto to entity name should be toEntity()
}
