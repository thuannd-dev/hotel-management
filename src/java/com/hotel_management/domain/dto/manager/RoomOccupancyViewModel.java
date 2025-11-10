package com.hotel_management.domain.dto.manager;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Room Occupancy Report
 * @author Chauu
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomOccupancyViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int month;
    private int year;
    private int totalRooms;
    private int totalDaysInMonth;
    private int occupiedRoomDays;
    private double occupancyRate;
}

