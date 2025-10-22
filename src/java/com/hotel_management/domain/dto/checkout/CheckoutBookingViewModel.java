package com.hotel_management.domain.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ViewModel for bookings available for checkout
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutBookingViewModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookingId;
    private String roomNumber;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalGuests;
    private String guestName;
    private String status;
}

