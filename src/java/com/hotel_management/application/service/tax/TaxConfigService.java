package com.hotel_management.application.service.tax;

import com.hotel_management.domain.dto.tax.TaxConfigViewModel;
import com.hotel_management.domain.entity.TaxConfig;
import com.hotel_management.infrastructure.dao.TaxConfigDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for TaxConfig
 * @author thuannd.dev
 */
public class TaxConfigService {
    private final TaxConfigDAO taxConfigDAO;

    public TaxConfigService(TaxConfigDAO taxConfigDAO) {
        this.taxConfigDAO = taxConfigDAO;
    }

    public List<TaxConfigViewModel> getAllTaxConfigs() {
        return taxConfigDAO.findAll().stream()
                .map(TaxConfigViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public TaxConfigViewModel getTaxConfigById(int id) {
        Optional<TaxConfig> taxConfig = taxConfigDAO.findById(id);
        return taxConfig.map(TaxConfigViewModel::fromEntity).orElse(null);
    }

    public Optional<TaxConfig> getActiveTaxConfig() {
        return taxConfigDAO.getActiveTaxConfig();
    }

    public int createTaxConfig(TaxConfig taxConfig) {
        return taxConfigDAO.insert(taxConfig);
    }

    public int updateTaxConfig(TaxConfig taxConfig) {
        return taxConfigDAO.update(taxConfig);
    }

    public int deleteTaxConfig(int id) {
        return taxConfigDAO.delete(id);
    }

    public int deactivateTaxConfig(int id) {
        return taxConfigDAO.deactivate(id);
    }

    public boolean validateTaxPeriod(LocalDate effectiveFrom, LocalDate effectiveTo, Integer excludeId) {
        // Check if effectiveFrom is before effectiveTo
        if (effectiveTo != null && effectiveFrom.isAfter(effectiveTo)) {
            return false;
        }

        // Check if there's already an active tax in this period
        // Only one active tax can exist at any given time where EffectiveTo is null or overlapping
        return !taxConfigDAO.existsActiveTaxInPeriod(effectiveFrom, effectiveTo, excludeId);
    }
}

