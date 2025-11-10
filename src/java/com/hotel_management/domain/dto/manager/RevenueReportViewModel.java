/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.domain.dto.manager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
/**
 *
 * @author Chauu
 */
public class RevenueReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate date;
    private BigDecimal totalRevenue;
    private int invoiceCount;
}
