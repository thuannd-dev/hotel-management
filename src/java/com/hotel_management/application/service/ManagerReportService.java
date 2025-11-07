/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.application.service;

import com.hotel_management.domain.dto.manager.RevenueReportViewModel;
import com.hotel_management.infrastructure.dao.BookingDAO;
import com.hotel_management.infrastructure.dao.GuestDAO;
import com.hotel_management.infrastructure.dao.InvoiceDAO;
import com.hotel_management.infrastructure.dao.RevenueReportDAO;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.ServiceDAO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Chauu
 */
public class ManagerReportService {

    private final RevenueReportDAO revenueReportDao;

    public ManagerReportService(RevenueReportDAO revenueReportDao) {
        this.revenueReportDao = revenueReportDao;
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
}
