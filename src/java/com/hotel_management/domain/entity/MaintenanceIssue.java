package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceIssue {
    private int issueId;
    private int roomId;
    private int reportedByStaffId;
    private String issueDescription;
    private LocalDate reportDate;
    private IssueStatus status;
    private Integer resolvedByStaffId; // Nullable
    private LocalDate resolutionDate; // Nullable
}
