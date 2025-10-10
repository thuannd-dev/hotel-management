package com.hotel_management.domain.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingDetailViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bookingId;
    private int guestId;
    private String guestFullName;
    private String guestPhone;
    private String guestIdNumber;
    private int roomId;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private String status;
    private int totalGuest;
    private String specialRequests;
    private String paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;
}