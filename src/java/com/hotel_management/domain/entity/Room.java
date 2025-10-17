package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Room {
    private int roomId;
    private String roomNumber;
    private int roomTypeId;
    private RoomStatus status;
}
