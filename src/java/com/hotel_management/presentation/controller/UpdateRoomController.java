/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.RoomService;
import com.hotel_management.domain.entity.enums.RoomStatus;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.RoomDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.Path;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "UpdateRoomController", urlPatterns = {"/housekeeping/room/status"})
public class UpdateRoomController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RoomService roomService;

    @Override
    public void init() {
        RoomDAO roomDao;
        RoomDetailDAO roomDetailDao;
        DataSource ds = DataSourceProvider.getDataSource();
        roomDao = new RoomDAO(ds);
        roomDetailDao = new RoomDetailDAO(ds);
        this.roomService = new RoomService(roomDao, roomDetailDao);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String status = request.getParameter("status");
            RoomStatus roomStatus = RoomStatus.fromDbValue(status);
            Boolean isSuccess = roomService.updateRoomStatus(roomId, roomStatus);
            if (!isSuccess) {
                throw new IOException("Failed to update room status");
            }
            response.sendRedirect(request.getContextPath() + Path.HOUSEKEEPING_DASHBOARD_PATH + "?msg=updated");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid roomId");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
