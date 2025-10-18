package com.hotel_management.domain.dto.booking_service;

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
public class BookingServiceCompletedReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String staffName;
    private String serviceName;
    private int totalCompleted;
    private LocalDate date;

}
