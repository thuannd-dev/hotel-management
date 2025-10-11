package com.hotel_management.domain.dto.service;
import com.hotel_management.domain.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ServiceViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private  int serviceId;
    private  String serviceName;
    private String serviceType;
    private  double price;

    // Static factory method
    public static ServiceViewModel fromEntity(Service service) {
        return new ServiceViewModel(
            service.getServiceId(),
            service.getServiceName(),
            service.getServiceType(),
            service.getPrice()
        );
    }

    //If map from dto to entity name should be toEntity()
}
