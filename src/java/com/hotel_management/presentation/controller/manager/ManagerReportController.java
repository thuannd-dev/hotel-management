package com.hotel_management.presentation.controller.manager;

import com.hotel_management.application.service.ManagerReportService;
import com.hotel_management.domain.dto.manager.*;
import com.hotel_management.infrastructure.dao.*;
import com.hotel_management.infrastructure.dao.guest.FrequentGuestDAO;
import com.hotel_management.infrastructure.dao.invoice.RevenueReportDAO;
import com.hotel_management.infrastructure.dao.room.RoomOccupancyDAO;
import com.hotel_management.infrastructure.dao.service.ServiceUsageDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Controller for all Manager Reports
 * @author Chauu
 */
@WebServlet(name = "ManagerReportController", urlPatterns = {"/manager/report"})
public class ManagerReportController extends HttpServlet {

    private ManagerReportService managerReportService;

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceProvider.getDataSource();

        RevenueReportDAO revenueReportDao = new RevenueReportDAO(ds);
        FrequentGuestDAO frequentGuestDao = new FrequentGuestDAO(ds);
        ServiceUsageDAO serviceUsageDao = new ServiceUsageDAO(ds);
        RoomOccupancyDAO roomOccupancyDao = new RoomOccupancyDAO(ds);
        CancellationStatDAO cancellationStatDao = new CancellationStatDAO(ds);

        managerReportService = new ManagerReportService(
                revenueReportDao,
                frequentGuestDao,
                serviceUsageDao,
                roomOccupancyDao,
                cancellationStatDao
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reportType = request.getParameter("type");

        if (reportType == null || reportType.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/manager");
            return;
        }

        try {
            switch (reportType) {
                case "revenue":
                    handleRevenueReport(request, response);
                    break;
                case "frequent-guests":
                    handleFrequentGuestsReport(request, response);
                    break;
                case "service-usage":
                    handleServiceUsageReport(request, response);
                    break;
                case "room-occupancy":
                    handleRoomOccupancyReport(request, response);
                    break;
                case "cancellation":
                    handleCancellationReport(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/manager");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error generating report: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
        }
    }

    /**
     * Handle Revenue Report (Daily/Monthly/Yearly)
     */
    private void handleRevenueReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String period = request.getParameter("period");
        if (period == null) period = "daily";

        List<RevenueReportViewModel> reportData = null;

        switch (period) {
            case "daily":
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");

                LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty())
                        ? LocalDate.parse(startDateStr)
                        : LocalDate.now().minusDays(30);
                LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty())
                        ? LocalDate.parse(endDateStr)
                        : LocalDate.now();

                reportData = managerReportService.getRevenueReport(startDate, endDate);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                break;

            case "monthly":
                int monthYear = request.getParameter("year") != null
                        ? Integer.parseInt(request.getParameter("year"))
                        : LocalDate.now().getYear();
                int month = request.getParameter("month") != null
                        ? Integer.parseInt(request.getParameter("month"))
                        : LocalDate.now().getMonthValue();

                reportData = managerReportService.getMonthlyRevenueReport(monthYear, month);
                request.setAttribute("selectedYear", monthYear);
                request.setAttribute("selectedMonth", month);
                break;

            case "yearly":
                int year = request.getParameter("year") != null
                        ? Integer.parseInt(request.getParameter("year"))
                        : LocalDate.now().getYear();

                reportData = managerReportService.getYearlyRevenueReport(year);
                request.setAttribute("selectedYear", year);
                break;
        }

        request.setAttribute("reportData", reportData);
        request.setAttribute("period", period);
        request.setAttribute("reportType", "revenue");

        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/manager/revenue_report.jsp")
                .forward(request, response);
    }

    /**
     * Handle Top Frequent Guests Report
     */
    private void handleFrequentGuestsReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int topN = request.getParameter("topN") != null
                ? Integer.parseInt(request.getParameter("topN"))
                : 10;

        List<FrequentGuestViewModel> reportData = managerReportService.getTopFrequentGuests(topN);

        request.setAttribute("reportData", reportData);
        request.setAttribute("topN", topN);
        request.setAttribute("reportType", "frequent-guests");

        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/manager/frequent_guests_report.jsp")
                .forward(request, response);
    }

    /**
     * Handle Service Usage Report
     */
    private void handleServiceUsageReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String period = request.getParameter("period");
        List<ServiceUsageViewModel> reportData = null;

        if ("custom".equals(period)) {
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            if (startDateStr != null && endDateStr != null && !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                reportData = managerReportService.getMostUsedServicesByDateRange(startDate, endDate);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
            } else {
                reportData = managerReportService.getMostUsedServices();
            }
        } else {
            reportData = managerReportService.getMostUsedServices();
        }

        request.setAttribute("reportData", reportData);
        request.setAttribute("period", period);
        request.setAttribute("reportType", "service-usage");

        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/manager/service_usage_report.jsp")
                .forward(request, response);
    }

    /**
     * Handle Room Occupancy Report
     */
    private void handleRoomOccupancyReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String period = request.getParameter("period");
        if (period == null) period = "yearly";

        if ("yearly".equals(period)) {
            int year = request.getParameter("year") != null
                    ? Integer.parseInt(request.getParameter("year"))
                    : LocalDate.now().getYear();

            List<RoomOccupancyViewModel> reportData = managerReportService.getRoomOccupancyByYear(year);
            request.setAttribute("reportData", reportData);
            request.setAttribute("selectedYear", year);
        } else if ("monthly".equals(period)) {
            int year = request.getParameter("year") != null
                    ? Integer.parseInt(request.getParameter("year"))
                    : LocalDate.now().getYear();
            int month = request.getParameter("month") != null
                    ? Integer.parseInt(request.getParameter("month"))
                    : LocalDate.now().getMonthValue();

            RoomOccupancyViewModel reportData = managerReportService.getRoomOccupancyByMonth(year, month);
            request.setAttribute("reportData", reportData);
            request.setAttribute("selectedYear", year);
            request.setAttribute("selectedMonth", month);
        }

        request.setAttribute("period", period);
        request.setAttribute("reportType", "room-occupancy");

        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/manager/room_occupancy_report.jsp")
                .forward(request, response);
    }

    /**
     * Handle Cancellation Statistics Report
     */
    private void handleCancellationReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String period = request.getParameter("period");
        if (period == null) period = "overall";

        switch (period) {
            case "overall":
                CancellationStatViewModel overallStats = managerReportService.getOverallCancellationStats();
                request.setAttribute("overallStats", overallStats);
                break;

            case "monthly":
                int year = request.getParameter("year") != null
                        ? Integer.parseInt(request.getParameter("year"))
                        : LocalDate.now().getYear();

                List<CancellationStatViewModel> monthlyStats = managerReportService.getMonthlyCancellationStats(year);
                request.setAttribute("reportData", monthlyStats);
                request.setAttribute("selectedYear", year);
                break;

            case "custom":
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");

                if (startDateStr != null && endDateStr != null && !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                    LocalDate startDate = LocalDate.parse(startDateStr);
                    LocalDate endDate = LocalDate.parse(endDateStr);
                    List<CancellationStatViewModel> customStats = managerReportService.getCancellationStatsByDateRange(startDate, endDate);
                    request.setAttribute("reportData", customStats);
                    request.setAttribute("startDate", startDate);
                    request.setAttribute("endDate", endDate);
                }
                break;
        }

        request.setAttribute("period", period);
        request.setAttribute("reportType", "cancellation");

        request.getRequestDispatcher("/WEB-INF/views/features/dashboard/manager/cancellation_report.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

