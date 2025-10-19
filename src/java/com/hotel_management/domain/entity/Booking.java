package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    private int bookingId;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private BookingStatus status;
    private int totalGuest;
    private String specialRequests;
    private PaymentStatus paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;
}
