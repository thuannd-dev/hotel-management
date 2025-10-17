package com.hotel_management.domain.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomDetailViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int roomId;
    private String roomNumber;
    private int roomTypeId;
    private String status;
    private String typeName;
    private int capacity;
    private double pricePerNight;
}