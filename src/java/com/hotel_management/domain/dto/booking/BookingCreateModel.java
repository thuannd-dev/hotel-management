package com.hotel_management.domain.dto.booking;

import com.hotel_management.domain.entity.Booking;
import com.hotel_management.domain.entity.enums.BookingStatus;
import com.hotel_management.domain.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class BookingCreateModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalGuest;
    private String specialRequests;
    

    public static Booking toEntity(BookingCreateModel model) {
        Booking booking = new Booking();
        booking.setGuestId(model.getGuestId());
        booking.setRoomId(model.getRoomId());
        booking.setCheckInDate(model.getCheckInDate());
        booking.setCheckOutDate(model.getCheckOutDate());
        booking.setBookingDate(LocalDate.now()); 
        booking.setTotalGuests(model.getTotalGuest());
        booking.setSpecialRequests(model.getSpecialRequests());
        booking.setStatus(BookingStatus.RESERVED); 
        booking.setPaymentStatus(PaymentStatus.PENDING); 
        booking.setCancellationDate(null);
        booking.setCancellationReason(null);
        return booking;
    }

    public static List<Booking> toListEntity(List<BookingCreateModel> models) {
        return models.stream()
                .map(BookingCreateModel::toEntity)
                .collect(Collectors.toList());
    }
}
