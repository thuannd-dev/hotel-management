package edu.hotel_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * \
 * @author TR_NGHIA
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    private int roomId;
    private String roomNumber;
    private int roomTypeId;
    private String status;
    private String typeName;
    private int capacity;
    private double pricePerNight;
}