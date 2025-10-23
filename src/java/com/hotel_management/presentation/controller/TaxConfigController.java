package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.TaxConfigService;
import com.hotel_management.domain.dto.tax.TaxConfigCreateModel;
import com.hotel_management.domain.dto.tax.TaxConfigUpdateModel;
import com.hotel_management.domain.dto.tax.TaxConfigViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.domain.entity.TaxConfig;
import com.hotel_management.infrastructure.dao.TaxConfigDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;
import com.hotel_management.presentation.constants.SessionAttribute;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Tax Configuration Management
 * @author thuannd.dev
 */
@WebServlet(name = "TaxConfigController", urlPatterns = {
        "/admin/tax",
        "/admin/tax/create",
        "/admin/tax/edit",
        "/admin/tax/delete",
        "/admin/tax/deactivate"
})
public class TaxConfigController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TaxConfigService taxConfigService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        TaxConfigDAO taxConfigDAO = new TaxConfigDAO(ds);
        this.taxConfigService = new TaxConfigService(taxConfigDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/tax":
                    listTaxConfigs(request, response);
                    break;
                case "/admin/tax/create":
                    showCreateForm(request, response);
                    break;
                case "/admin/tax/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/tax/delete":
                    deleteTaxConfig(request, response);
                    break;
                case "/admin/tax/deactivate":
                    deactivateTaxConfig(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_TAX_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/tax/create":
                    createTaxConfig(request, response);
                    break;
                case "/admin/tax/edit":
                    updateTaxConfig(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_TAX_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    private void listTaxConfigs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TaxConfigViewModel> taxConfigs = taxConfigService.getAllTaxConfigs();
        request.setAttribute(RequestAttribute.TAX_CONFIGS, taxConfigs);

        // Check for success/error messages
        String success = request.getParameter("success");
        String error = request.getParameter("error");
        if (success != null) {
            request.setAttribute(RequestAttribute.SUCCESS_MESSAGE, success);
        }
        if (error != null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, error);
        }

        request.getRequestDispatcher(Page.ADMIN_TAX_MANAGEMENT_PAGE).forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
    }

    private void createTaxConfig(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String taxName = request.getParameter("taxName");
            String taxRateStr = request.getParameter("taxRate");
            String description = request.getParameter("description");
            String effectiveFromStr = request.getParameter("effectiveFrom");
            String effectiveToStr = request.getParameter("effectiveTo");
            String isActiveStr = request.getParameter("isActive");

            // Validation
            if (taxName == null || taxName.trim().isEmpty() ||
                    taxRateStr == null || taxRateStr.trim().isEmpty() ||
                    effectiveFromStr == null || effectiveFromStr.trim().isEmpty()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Tax name, rate, and effective from date are required");
                request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
                return;
            }

            BigDecimal taxRate = new BigDecimal(taxRateStr);
            if (taxRate.compareTo(BigDecimal.ZERO) < 0 || taxRate.compareTo(new BigDecimal("100")) > 0) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Tax rate must be between 0 and 100");
                request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
                return;
            }

            LocalDate effectiveFrom = LocalDate.parse(effectiveFromStr);
            LocalDate effectiveTo = (effectiveToStr != null && !effectiveToStr.trim().isEmpty())
                    ? LocalDate.parse(effectiveToStr) : null;
            boolean isActive = "on".equals(isActiveStr) || "true".equals(isActiveStr);

            // Validate period
            if (!taxConfigService.validateTaxPeriod(effectiveFrom, effectiveTo, null)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE,
                        "Invalid: Effective from must be before effective to, and there cannot be overlapping active tax configurations");
                request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
                return;
            }

            // Get current staff from session
            HttpSession session = request.getSession();
            StaffViewModel currentStaff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            if (currentStaff == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Create tax config
            TaxConfigCreateModel createModel = new TaxConfigCreateModel(
                    taxName, taxRate, description, effectiveFrom, effectiveTo,
                    currentStaff.getStaffId(), isActive
            );
            TaxConfig taxConfig = createModel.toEntity();
            int taxConfigId = taxConfigService.createTaxConfig(taxConfig);

            if (taxConfigId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?success=Tax configuration created successfully");
            } else {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to create tax configuration");
                request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Invalid tax rate format");
            request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_TAX_CREATE_PAGE).forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int taxConfigId = Integer.parseInt(request.getParameter("id"));
            TaxConfigViewModel taxConfig = taxConfigService.getTaxConfigById(taxConfigId);

            if (taxConfig == null) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?error=Tax configuration not found");
                return;
            }

            request.setAttribute(RequestAttribute.TAX_CONFIG, taxConfig);
            request.getRequestDispatcher(Page.ADMIN_TAX_EDIT_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/tax?error=Invalid tax configuration ID");
        }
    }

    private void updateTaxConfig(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int taxConfigId = Integer.parseInt(request.getParameter("taxConfigId"));
            String taxName = request.getParameter("taxName");
            String taxRateStr = request.getParameter("taxRate");
            String description = request.getParameter("description");
            String effectiveFromStr = request.getParameter("effectiveFrom");
            String effectiveToStr = request.getParameter("effectiveTo");
            String isActiveStr = request.getParameter("isActive");

            // Validation
            if (taxName == null || taxName.trim().isEmpty() ||
                    taxRateStr == null || taxRateStr.trim().isEmpty() ||
                    effectiveFromStr == null || effectiveFromStr.trim().isEmpty()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Tax name, rate, and effective from date are required");
                TaxConfigViewModel taxConfig = taxConfigService.getTaxConfigById(taxConfigId);
                request.setAttribute(RequestAttribute.TAX_CONFIG, taxConfig);
                request.getRequestDispatcher(Page.ADMIN_TAX_EDIT_PAGE).forward(request, response);
                return;
            }

            BigDecimal taxRate = new BigDecimal(taxRateStr);
            if (taxRate.compareTo(BigDecimal.ZERO) < 0 || taxRate.compareTo(new BigDecimal("100")) > 0) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Tax rate must be between 0 and 100");
                TaxConfigViewModel taxConfig = taxConfigService.getTaxConfigById(taxConfigId);
                request.setAttribute(RequestAttribute.TAX_CONFIG, taxConfig);
                request.getRequestDispatcher(Page.ADMIN_TAX_EDIT_PAGE).forward(request, response);
                return;
            }

            LocalDate effectiveFrom = LocalDate.parse(effectiveFromStr);
            LocalDate effectiveTo = (effectiveToStr != null && !effectiveToStr.trim().isEmpty())
                    ? LocalDate.parse(effectiveToStr) : null;
            boolean isActive = "on".equals(isActiveStr) || "true".equals(isActiveStr);

            // Validate period
            if (!taxConfigService.validateTaxPeriod(effectiveFrom, effectiveTo, taxConfigId)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE,
                        "Invalid period: Effective from must be before effective to, and there cannot be overlapping active tax configurations");
                TaxConfigViewModel taxConfig = taxConfigService.getTaxConfigById(taxConfigId);
                request.setAttribute(RequestAttribute.TAX_CONFIG, taxConfig);
                request.getRequestDispatcher(Page.ADMIN_TAX_EDIT_PAGE).forward(request, response);
                return;
            }

            // Get existing tax config
            TaxConfigViewModel existingViewModel = taxConfigService.getTaxConfigById(taxConfigId);
            if (existingViewModel == null) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?error=Tax configuration not found");
                return;
            }

            // Update tax config
            TaxConfigUpdateModel updateModel = new TaxConfigUpdateModel(
                    taxConfigId, taxName, taxRate, description, effectiveFrom, effectiveTo, isActive
            );

            // Create a TaxConfig entity from existing to preserve createdBy and createdDate
            TaxConfig existingEntity = new TaxConfig();
            existingEntity.setCreatedBy(existingViewModel.getCreatedBy());
            existingEntity.setCreatedDate(existingViewModel.getCreatedDate());

            TaxConfig taxConfig = updateModel.toEntity(existingEntity);
            int result = taxConfigService.updateTaxConfig(taxConfig);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?success=Tax configuration updated successfully");
            } else {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to update tax configuration");
                TaxConfigViewModel viewModel = taxConfigService.getTaxConfigById(taxConfigId);
                request.setAttribute(RequestAttribute.TAX_CONFIG, viewModel);
                request.getRequestDispatcher(Page.ADMIN_TAX_EDIT_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Invalid number format");
            response.sendRedirect(request.getContextPath() + "/admin/tax?error=Invalid number format");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/tax?error=" + e.getMessage());
        }
    }

    private void deleteTaxConfig(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int taxConfigId = Integer.parseInt(request.getParameter("id"));
            int result = taxConfigService.deleteTaxConfig(taxConfigId);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?success=Tax configuration deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/tax?error=Failed to delete tax configuration");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/tax?error=Invalid tax configuration ID");
        }
    }

    private void deactivateTaxConfig(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int taxConfigId = Integer.parseInt(request.getParameter("id"));
            int result = taxConfigService.deactivateTaxConfig(taxConfigId);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/tax?success=Tax configuration deactivated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/tax?error=Failed to deactivate tax configuration");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/tax?error=Invalid tax configuration ID");
        }
    }
}

