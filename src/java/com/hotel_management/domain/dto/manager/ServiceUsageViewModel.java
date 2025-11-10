package com.hotel_management.domain.dto.manager;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Most Used Services Report
 * @author Chauu
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceUsageViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int serviceId;
    private String serviceName;
    private String serviceType;
    private int usageCount;
    private int totalQuantity;
    private BigDecimal totalRevenue;
}

