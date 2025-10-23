package com.hotel_management.application.service;

import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.domain.entity.Service;
import com.hotel_management.infrastructure.dao.ServiceDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for Service Management (Admin)
 * @author thuannd.dev
 */
public class ServiceManagementService {
    private final ServiceDAO serviceDAO;

    public ServiceManagementService(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public List<ServiceViewModel> getAllServices() {
        return serviceDAO.findAll().stream()
                .map(ServiceViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public ServiceViewModel getServiceById(int id) {
        Optional<Service> service = serviceDAO.findById(id);
        return service.map(ServiceViewModel::fromEntity).orElse(null);
    }

    public int createService(Service service) {
        return serviceDAO.insert(service);
    }

    public int updateService(Service service) {
        return serviceDAO.update(service);
    }

    public int deleteService(int id) {
        return serviceDAO.delete(id);
    }

    public boolean isServiceNameExists(String serviceName, Integer excludeId) {
        List<Service> services = serviceDAO.findAll();
        return services.stream()
                .anyMatch(s -> s.getServiceName().equalsIgnoreCase(serviceName)
                        && (excludeId == null || s.getServiceId() != excludeId));
    }
}

