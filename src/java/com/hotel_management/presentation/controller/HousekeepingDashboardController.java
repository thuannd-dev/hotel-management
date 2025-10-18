package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.RoomService;
import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.domain.entity.enums.RoomStatus;
import com.hotel_management.infrastructure.dao.HousekeepingTaskDAO;
import com.hotel_management.infrastructure.dao.MaintenanceIssueDAO;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.RoomDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "HousekeepingDashboardController", urlPatterns = {"/housekeeping"})
public class HousekeepingDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RoomService roomService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        RoomDAO roomDAO = new RoomDAO(ds);
        RoomDetailDAO roomDetailDAO = new RoomDetailDAO(ds);
        HousekeepingTaskDAO housekeepingTaskDao = new HousekeepingTaskDAO(ds);
        MaintenanceIssueDAO maintenanceIssueDao = new MaintenanceIssueDAO(ds);
        this.roomService = new RoomService(roomDAO, roomDetailDAO, housekeepingTaskDao, maintenanceIssueDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<RoomDetailViewModel> rooms;
            String status = request.getParameter("status");
            if (status != null && !status.trim().isEmpty()) {
                RoomStatus roomStatus = RoomStatus.fromDbValue(status);
                rooms = roomService.getRoomDetailsByStatus(roomStatus);
            } else {
                rooms = roomService.getAllRoomDetails();
            }
            request.setAttribute(RequestAttribute.ROOMS, rooms);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Page.HOUSEKEEPING_DASHBOARD_PAGE);
            dispatcher.forward(request, response);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
