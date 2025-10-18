package com.hotel_management.domain.entity;

import com.hotel_management.domain.entity.enums.Priority;
import com.hotel_management.domain.entity.enums.TaskStatus;
import com.hotel_management.domain.entity.enums.TaskType;
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
public class HousekeepingTask {
    private int taskId;
    private int roomId;
    private Integer staffId; // Nullable
    private TaskType taskType;
    private TaskStatus status;
    private LocalDate assignedDate;
    private LocalDate completedDate; // Nullable
    private Priority priority;
}
