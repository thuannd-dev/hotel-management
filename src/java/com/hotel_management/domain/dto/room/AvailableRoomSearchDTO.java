package com.hotel_management.domain.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableRoomSearchDTO {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;
    private int children;
}

