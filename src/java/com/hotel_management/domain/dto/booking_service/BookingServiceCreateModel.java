package com.hotel_management.domain.dto.booking_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.hotel_management.domain.entity.BookingService;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class BookingServiceCreateModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bookingId;
    private int serviceId;
    private int quantity;
    private Integer assignedStaffId;  // Changed to Integer to allow null

    public static BookingService toEntity(BookingServiceCreateModel bookingServiceCreateModel) {
        BookingService bookingService = new BookingService();
        bookingService.setBookingId(bookingServiceCreateModel.getBookingId());
        bookingService.setServiceId(bookingServiceCreateModel.getServiceId());
        bookingService.setQuantity(bookingServiceCreateModel.getQuantity());
        bookingService.setAssignedStaffId(bookingServiceCreateModel.getAssignedStaffId());
        return bookingService;
    }

    public static List<BookingService> toListEntity(List<BookingServiceCreateModel> listBookingServiceCreateModel) {
        return listBookingServiceCreateModel.stream()
                .map(BookingServiceCreateModel::toEntity)
                .collect(Collectors.toList());
    }
}
