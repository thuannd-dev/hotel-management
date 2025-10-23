package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.ServiceManagementService;
import com.hotel_management.domain.dto.service.ServiceCreateModel;
import com.hotel_management.domain.dto.service.ServiceViewModel;
import com.hotel_management.domain.dto.service.ServiceUpdateModel;
import com.hotel_management.domain.entity.Service;
import com.hotel_management.infrastructure.dao.ServiceDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Service Management (Admin)
 * @author thuannd.dev
 */
@WebServlet(name = "ServiceManagementController", urlPatterns = {
        "/admin/service",
        "/admin/service/create",
        "/admin/service/edit",
        "/admin/service/delete"
})
public class ServiceManagementController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ServiceManagementService serviceManagementService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        ServiceDAO serviceDAO = new ServiceDAO(ds);
        this.serviceManagementService = new ServiceManagementService(serviceDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/service":
                    listServices(request, response);
                    break;
                case "/admin/service/create":
                    showCreateForm(request, response);
                    break;
                case "/admin/service/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/service/delete":
                    deleteService(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_SERVICE_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/service/create":
                    createService(request, response);
                    break;
                case "/admin/service/edit":
                    updateService(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(Page.ADMIN_SERVICE_MANAGEMENT_PAGE).forward(request, response);
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ServiceViewModel> services = serviceManagementService.getAllServices();
        request.setAttribute(RequestAttribute.SERVICES, services);

        String success = request.getParameter("success");
        String error = request.getParameter("error");
        if (success != null) {
            request.setAttribute(RequestAttribute.SUCCESS_MESSAGE, success);
        }
        if (error != null) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, error);
        }

        request.getRequestDispatcher(Page.ADMIN_SERVICE_MANAGEMENT_PAGE).forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
    }

    private void createService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String serviceName = request.getParameter("serviceName");
            String serviceType = request.getParameter("serviceType");
            String priceStr = request.getParameter("price");

            // Validation
            if (serviceName == null || serviceName.trim().isEmpty() ||
                    priceStr == null || priceStr.trim().isEmpty()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Service name and price are required");
                request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
                return;
            }

            double price = Double.parseDouble(priceStr);
            if (price < 0) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Price must be greater than or equal to 0");
                request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
                return;
            }

            // Check if service name already exists
            if (serviceManagementService.isServiceNameExists(serviceName, null)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Service name already exists");
                request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
                return;
            }

            ServiceCreateModel createModel = new ServiceCreateModel(serviceName, serviceType, price);
            Service service = createModel.toEntity();
            int serviceId = serviceManagementService.createService(service);

            if (serviceId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/service?success=Service created successfully");
            } else {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to create service");
                request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Invalid price format");
            request.getRequestDispatcher(Page.ADMIN_SERVICE_CREATE_PAGE).forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("id"));
            ServiceViewModel service = serviceManagementService.getServiceById(serviceId);

            if (service == null) {
                response.sendRedirect(request.getContextPath() + "/admin/service?error=Service not found");
                return;
            }

            request.setAttribute(RequestAttribute.SERVICE, service);
            request.getRequestDispatcher(Page.ADMIN_SERVICE_EDIT_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/service?error=Invalid service ID");
        }
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String serviceName = request.getParameter("serviceName");
            String serviceType = request.getParameter("serviceType");
            String priceStr = request.getParameter("price");

            // Validation
            if (serviceName == null || serviceName.trim().isEmpty() ||
                    priceStr == null || priceStr.trim().isEmpty()) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Service name and price are required");
                ServiceViewModel service = serviceManagementService.getServiceById(serviceId);
                request.setAttribute(RequestAttribute.SERVICE, service);
                request.getRequestDispatcher(Page.ADMIN_SERVICE_EDIT_PAGE).forward(request, response);
                return;
            }

            double price = Double.parseDouble(priceStr);
            if (price < 0) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Price must be greater than or equal to 0");
                ServiceViewModel service = serviceManagementService.getServiceById(serviceId);
                request.setAttribute(RequestAttribute.SERVICE, service);
                request.getRequestDispatcher(Page.ADMIN_SERVICE_EDIT_PAGE).forward(request, response);
                return;
            }

            // Check if service name exists for another service
            if (serviceManagementService.isServiceNameExists(serviceName, serviceId)) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Service name already exists for another service");
                ServiceViewModel service = serviceManagementService.getServiceById(serviceId);
                request.setAttribute(RequestAttribute.SERVICE, service);
                request.getRequestDispatcher(Page.ADMIN_SERVICE_EDIT_PAGE).forward(request, response);
                return;
            }

            ServiceUpdateModel updateModel = new ServiceUpdateModel(serviceId, serviceName, serviceType, price);
            Service service = updateModel.toEntity();
            int result = serviceManagementService.updateService(service);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/service?success=Service updated successfully");
            } else {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Failed to update service");
                ServiceViewModel viewModel = serviceManagementService.getServiceById(serviceId);
                request.setAttribute(RequestAttribute.SERVICE, viewModel);
                request.getRequestDispatcher(Page.ADMIN_SERVICE_EDIT_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, "Invalid number format");
            response.sendRedirect(request.getContextPath() + "/admin/service?error=Invalid number format");
        }
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("id"));
            int result = serviceManagementService.deleteService(serviceId);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/service?success=Service deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/service?error=Failed to delete service because it is being used");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/service?error=Invalid service ID");
        }
    }
}

