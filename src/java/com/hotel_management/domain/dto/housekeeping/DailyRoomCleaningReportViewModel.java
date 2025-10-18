package com.hotel_management.domain.dto.housekeeping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.datatransfer.StringSelection;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DailyRoomCleaningReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate date;
    private String roomNumber;
    private String cleaningType;
    private String staffName;
    private String status;


}
