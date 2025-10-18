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
public class MaintenanceIssueReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private String issueDescription;
    private LocalDate reportDate;
    private String status;
    private String fixedBy;
}
