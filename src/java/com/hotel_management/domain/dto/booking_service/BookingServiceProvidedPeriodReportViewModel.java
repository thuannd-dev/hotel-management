package com.hotel_management.domain.dto.booking_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingServiceProvidedPeriodReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serviceName;
    private int totalQuantity;
    private double totalRevenue;
    private String period;
}