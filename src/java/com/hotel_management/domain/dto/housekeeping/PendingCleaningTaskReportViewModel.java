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
public class PendingCleaningTaskReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private String roomStatus;
    private String priority;
    private String taskType;
    private String assignedStaff;
    private String taskStatus;
    private LocalDate assignedDate;
}
