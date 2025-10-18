/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.RoomService;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import com.hotel_management.domain.entity.enums.RoomStatus;
import com.hotel_management.infrastructure.dao.HousekeepingTaskDAO;
import com.hotel_management.infrastructure.dao.MaintenanceIssueDAO;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.RoomDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Path;
import com.hotel_management.presentation.constants.SessionAttribute;
import org.apache.catalina.Session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "UpdateRoomController", urlPatterns = {"/housekeeping/room/status"})
public class UpdateRoomController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RoomService roomService;

    @Override
    public void init() {
        DataSource ds = DataSourceProvider.getDataSource();
        RoomDAO roomDao = new RoomDAO(ds);
        RoomDetailDAO roomDetailDao = new RoomDetailDAO(ds);
        HousekeepingTaskDAO housekeepingTaskDao = new HousekeepingTaskDAO(ds);
        MaintenanceIssueDAO maintenanceIssueDao = new MaintenanceIssueDAO(ds);
        this.roomService = new RoomService(roomDao, roomDetailDao, housekeepingTaskDao, maintenanceIssueDao);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String status = request.getParameter("status");
            RoomStatus roomStatus = RoomStatus.fromDbValue(status);
            Boolean isSuccess = roomService.updateRoomStatus(staff.getStaffId(), roomId, roomStatus);
            if (!isSuccess) {
                throw new IOException("Failed to update room status");
            }
            response.sendRedirect(request.getContextPath() + Path.HOUSEKEEPING_DASHBOARD_PATH + "?msg=updated");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid roomId");
        } catch (IllegalStateException e) {
            // Handle invalid status transition
            response.sendRedirect(request.getContextPath() + Path.HOUSEKEEPING_DASHBOARD_PATH + "?msg=invalid_transition&error=" + e.getMessage());
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
