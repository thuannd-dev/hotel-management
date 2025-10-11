package com.hotel_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class Service {
    private  int serviceId;
    private  String serviceName;
    private String serviceType;
    private  double price;
}
