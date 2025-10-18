package com.hotel_management.application.service;

import com.hotel_management.domain.dto.housekeeping.*;
import com.hotel_management.infrastructure.dao.housekeeping.*;

import java.util.List;

/**
 *
 * @author thuannd.dev
 */
public class HousekeepingReportService {
    private final DailyRoomCleaningReportDAO dailyRoomCleaningReportDao;
    private final HousekeepingStaffPerformanceReportDAO housekeepingStaffPerformanceReportDao;
    private final MaintenanceIssueReportDAO maintenanceIssueReportDao;
    private final PendingCleaningTaskReportDAO pendingCleaningTaskReportDao;
    private final RoomStatusReportDAO roomStatusReportDao;

    public HousekeepingReportService(DailyRoomCleaningReportDAO dailyRoomCleaningReportDao,
            HousekeepingStaffPerformanceReportDAO housekeepingStaffPerformanceReportDao,
            MaintenanceIssueReportDAO maintenanceIssueReportDao,
            PendingCleaningTaskReportDAO pendingCleaningTaskReportDao,
            RoomStatusReportDAO roomStatusReportDao) {
        this.dailyRoomCleaningReportDao = dailyRoomCleaningReportDao;
        this.housekeepingStaffPerformanceReportDao = housekeepingStaffPerformanceReportDao;
        this.maintenanceIssueReportDao = maintenanceIssueReportDao;
        this.pendingCleaningTaskReportDao = pendingCleaningTaskReportDao;
        this.roomStatusReportDao = roomStatusReportDao;
    }

    public List<DailyRoomCleaningReportViewModel> getDailyRoomCleaningReport() {
        return dailyRoomCleaningReportDao.getReport();
    }

    public List<HousekeepingStaffPerformanceReportViewModel> getHousekeepingStaffPerformanceReport() {
        return housekeepingStaffPerformanceReportDao.getReport();
    }

    public List<MaintenanceIssueReportViewModel> getMaintenanceIssueReport() {
        return maintenanceIssueReportDao.getReport();
    }

    public List<PendingCleaningTaskReportViewModel> getPendingCleaningTaskReport() {
        return pendingCleaningTaskReportDao.getReport();
    }

    public List<RoomStatusReportViewModel> getRoomStatusReport() {
        return roomStatusReportDao.getReport();
    }
}
