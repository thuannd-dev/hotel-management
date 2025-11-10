/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.dto.manager.RevenueReportViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;


/**
 *
 * @author Chauu
 */
public class RevenueReportDAO extends BaseDAO<RevenueReportViewModel> {

    private static final String BASE_QUERY =
            "SELECT CAST(IssueDate AS DATE) AS ReportDate, " +
            "       SUM(FinalAmount) AS TotalRevenue, " +
            "       COUNT(*) AS InvoiceCount " +
            "FROM INVOICE ";

    public RevenueReportDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public RevenueReportViewModel mapRow(ResultSet rs) throws SQLException {
        return new RevenueReportViewModel(
                rs.getDate("ReportDate").toLocalDate(),
                rs.getBigDecimal("TotalRevenue"),
                rs.getInt("InvoiceCount")
        );
    }

    /**
     * daily
     */
    public List<RevenueReportViewModel> getReport(LocalDate startDate, LocalDate endDate) {
        String condition =
                "WHERE IssueDate BETWEEN ? AND ? " +
                "GROUP BY CAST(IssueDate AS DATE) " +
                "ORDER BY ReportDate";
        return query(BASE_QUERY + condition, startDate, endDate);
    }

    /**
     * Monthly
     */
    public List<RevenueReportViewModel> getMonthlyReport(int year, int month) {
        String condition =
                "WHERE YEAR(IssueDate) = ? AND MONTH(IssueDate) = ? " +
                "GROUP BY CAST(IssueDate AS DATE) " +
                "ORDER BY ReportDate";
        return query(BASE_QUERY + condition, year, month);
    }

    /**
     * yearly - aggregates by month and returns first day of each month
     */
    public List<RevenueReportViewModel> getYearlyReport(int year) {
        String sql =
                "SELECT DATEFROMPARTS(YEAR(IssueDate), MONTH(IssueDate), 1) AS ReportDate, " +
                "       SUM(FinalAmount) AS TotalRevenue, " +
                "       COUNT(*) AS InvoiceCount " +
                "FROM INVOICE " +
                "WHERE YEAR(IssueDate) = ? " +
                "GROUP BY YEAR(IssueDate), MONTH(IssueDate) " +
                "ORDER BY ReportDate";
        return query(sql, year);
    }
}