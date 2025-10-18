package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.HousekeepingReportService;
import com.hotel_management.domain.dto.housekeeping.*;
import com.hotel_management.infrastructure.dao.housekeeping.*;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "HousekeepingReportController", urlPatterns = {"/housekeeping/reports"})
public class HousekeepingReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HousekeepingReportService housekeepingReportService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        DailyRoomCleaningReportDAO dailyRoomCleaningReportDao = new DailyRoomCleaningReportDAO(ds);
        HousekeepingStaffPerformanceReportDAO housekeepingStaffPerformanceReportDao = new HousekeepingStaffPerformanceReportDAO(ds);
        MaintenanceIssueReportDAO maintenanceIssueReportDao = new MaintenanceIssueReportDAO(ds);
        PendingCleaningTaskReportDAO pendingCleaningTaskReportDao = new PendingCleaningTaskReportDAO(ds);
        RoomStatusReportDAO roomStatusReportDao = new RoomStatusReportDAO(ds);

        this.housekeepingReportService = new HousekeepingReportService(
                dailyRoomCleaningReportDao,
                housekeepingStaffPerformanceReportDao,
                maintenanceIssueReportDao,
                pendingCleaningTaskReportDao,
                roomStatusReportDao
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String reportType = request.getParameter("reportType");
            if (reportType == null || reportType.trim().isEmpty()) {
                reportType = "dailyRoomCleaning";
            }

            switch (reportType) {
                case "dailyRoomCleaning":
                    List<DailyRoomCleaningReportViewModel> dailyRoomCleaningReport =
                            housekeepingReportService.getDailyRoomCleaningReport();
                    request.setAttribute(RequestAttribute.DAILY_ROOM_CLEANING_REPORT, dailyRoomCleaningReport);
                    break;

                case "staffPerformance":
                    List<HousekeepingStaffPerformanceReportViewModel> staffPerformanceReport =
                            housekeepingReportService.getHousekeepingStaffPerformanceReport();
                    request.setAttribute(RequestAttribute.STAFF_PERFORMANCE_REPORT, staffPerformanceReport);
                    break;

                case "maintenanceIssue":
                    List<MaintenanceIssueReportViewModel> maintenanceIssueReport =
                            housekeepingReportService.getMaintenanceIssueReport();
                    request.setAttribute(RequestAttribute.MAINTENANCE_ISSUE_REPORT, maintenanceIssueReport);
                    break;

                case "pendingCleaningTask":
                    List<PendingCleaningTaskReportViewModel> pendingCleaningTaskReport =
                            housekeepingReportService.getPendingCleaningTaskReport();
                    request.setAttribute(RequestAttribute.PENDING_CLEANING_TASK_REPORT, pendingCleaningTaskReport);
                    break;

                case "roomStatus":
                    List<RoomStatusReportViewModel> roomStatusReport =
                            housekeepingReportService.getRoomStatusReport();
                    request.setAttribute(RequestAttribute.ROOM_STATUS_REPORT, roomStatusReport);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid report type");
            }

            request.setAttribute(RequestAttribute.REPORT_TYPE, reportType);
            request.getRequestDispatcher(Page.HOUSEKEEPING_REPORT_PAGE).forward(request, response);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}

