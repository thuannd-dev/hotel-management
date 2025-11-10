/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.application.service;

import com.hotel_management.domain.dto.manager.CancellationStatViewModel;
import com.hotel_management.domain.dto.manager.FrequentGuestViewModel;
import com.hotel_management.domain.dto.manager.RevenueReportViewModel;
import com.hotel_management.domain.dto.manager.RoomOccupancyViewModel;
import com.hotel_management.domain.dto.manager.ServiceUsageViewModel;
import com.hotel_management.infrastructure.dao.CancellationStatDAO;
import com.hotel_management.infrastructure.dao.FrequentGuestDAO;
import com.hotel_management.infrastructure.dao.RevenueReportDAO;
import com.hotel_management.infrastructure.dao.RoomOccupancyDAO;
import com.hotel_management.infrastructure.dao.ServiceUsageDAO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Chauu
 */
public class ManagerReportService {

    private final RevenueReportDAO revenueReportDao;
    private final FrequentGuestDAO frequentGuestDao;
    private final ServiceUsageDAO serviceUsageDao;
    private final RoomOccupancyDAO roomOccupancyDao;
    private final CancellationStatDAO cancellationStatDao;

    public ManagerReportService(RevenueReportDAO revenueReportDao, 
                                FrequentGuestDAO frequentGuestDao,
                                ServiceUsageDAO serviceUsageDao,
                                RoomOccupancyDAO roomOccupancyDao,
                                CancellationStatDAO cancellationStatDao) {
        this.revenueReportDao = revenueReportDao;
        this.frequentGuestDao = frequentGuestDao;
        this.serviceUsageDao = serviceUsageDao;
        this.roomOccupancyDao = roomOccupancyDao;
        this.cancellationStatDao = cancellationStatDao;
    }

    /**
     * Báo cáo doanh thu theo khoảng thời gian (daily)
     */
    public List<RevenueReportViewModel> getRevenueReport(LocalDate startDate, LocalDate endDate) {
        return revenueReportDao.getReport(startDate, endDate);
    }

    /**
     * Báo cáo doanh thu theo tháng
     */
    public List<RevenueReportViewModel> getMonthlyRevenueReport(int year, int month) {
        return revenueReportDao.getMonthlyReport(year, month);
    }

    /**
     * Báo cáo doanh thu theo năm
     */
    public List<RevenueReportViewModel> getYearlyRevenueReport(int year) {
        return revenueReportDao.getYearlyReport(year);
    }

    /**
     * Top N khách hàng thường xuyên
     */
    public List<FrequentGuestViewModel> getTopFrequentGuests(int topN) {
        return frequentGuestDao.getTopFrequentGuests(topN);
    }

    /**
     * Dịch vụ được sử dụng nhiều nhất
     */
    public List<ServiceUsageViewModel> getMostUsedServices() {
        return serviceUsageDao.getMostUsedServices();
    }

    /**
     * Dịch vụ được sử dụng nhiều nhất theo khoảng thời gian
     */
    public List<ServiceUsageViewModel> getMostUsedServicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return serviceUsageDao.getMostUsedServicesByDateRange(startDate, endDate);
    }

    /**
     * Tỷ lệ lấp phòng theo năm
     */
    public List<RoomOccupancyViewModel> getRoomOccupancyByYear(int year) {
        return roomOccupancyDao.getRoomOccupancyByYear(year);
    }

    /**
     * Tỷ lệ lấp phòng theo tháng
     */
    public RoomOccupancyViewModel getRoomOccupancyByMonth(int year, int month) {
        return roomOccupancyDao.getRoomOccupancyByMonth(year, month);
    }

    /**
     * Thống kê hủy phòng theo khoảng thời gian
     */
    public List<CancellationStatViewModel> getCancellationStatsByDateRange(LocalDate startDate, LocalDate endDate) {
        return cancellationStatDao.getCancellationStatsByDateRange(startDate, endDate);
    }

    /**
     * Thống kê hủy phòng tổng thể
     */
    public CancellationStatViewModel getOverallCancellationStats() {
        return cancellationStatDao.getOverallCancellationStats();
    }

    /**
     * Thống kê hủy phòng theo tháng trong năm
     */
    public List<CancellationStatViewModel> getMonthlyCancellationStats(int year) {
        return cancellationStatDao.getMonthlyCancellationStats(year);
    }
}
