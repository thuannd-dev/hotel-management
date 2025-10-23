package com.hotel_management.domain.dto.service;

import com.hotel_management.domain.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create model for Service
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceCreateModel {
    private String serviceName;
    private String serviceType;
    private double price;

    public Service toEntity() {
        return new Service(0, serviceName, serviceType, price);
    }
}

