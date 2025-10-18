package com.hotel_management.domain.dto.housekeeping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomStatusReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private String roomType;
    private String status;
    private LocalDate lastCleanedDate;
    private LocalDate nextCheckIn;
}
