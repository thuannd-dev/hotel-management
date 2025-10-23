package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.RoomService;
import com.hotel_management.domain.dto.room.RoomDetailViewModel;
import com.hotel_management.infrastructure.dao.HousekeepingTaskDAO;
import com.hotel_management.infrastructure.dao.MaintenanceIssueDAO;
import com.hotel_management.infrastructure.dao.RoomDAO;
import com.hotel_management.infrastructure.dao.RoomDetailDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.constants.RequestAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for searching available rooms based on check-in/check-out dates and guest count
 * @author thuannd.dev
 */
@WebServlet(name = "SearchAvailableRoomsController", urlPatterns = {"/available-rooms"})
public class SearchAvailableRoomsController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get search parameters
            String checkInDateStr = request.getParameter("checkInDate");
            String checkOutDateStr = request.getParameter("checkOutDate");
            String adultsStr = request.getParameter("adults");
            String childrenStr = request.getParameter("children");
            String roomType = request.getParameter("roomType");

            List<RoomDetailViewModel> availableRooms = new ArrayList<>();
            String errorMessage = null;
            String infoMessage = null;

            // If room type is provided but other fields are empty, show informational message
            if (roomType != null && !roomType.isEmpty() &&
                (checkInDateStr == null || checkInDateStr.isEmpty()) &&
                (checkOutDateStr == null || checkOutDateStr.isEmpty()) &&
                (adultsStr == null || adultsStr.isEmpty())) {
                infoMessage = "You have selected " + roomType + " room. Please enter your booking details below to check availability.";
                request.setAttribute("infoMessage", infoMessage);
            }

            // Set roomType for form retention if provided
            if (roomType != null && !roomType.isEmpty()) {
                request.setAttribute("roomType", roomType);
            }

            // Only search if all parameters are provided
            if (checkInDateStr != null && !checkInDateStr.isEmpty() &&
                checkOutDateStr != null && !checkOutDateStr.isEmpty() &&
                adultsStr != null && !adultsStr.isEmpty()) {

                try {
                    LocalDate checkInDate = LocalDate.parse(checkInDateStr);
                    LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);
                    int adults = Integer.parseInt(adultsStr);
                    int children = childrenStr != null && !childrenStr.isEmpty() ? Integer.parseInt(childrenStr) : 0;

                    // Search for available rooms
                    availableRooms = roomService.searchAvailableRooms(checkInDate, checkOutDate, adults, children);

                    // Filter by room type if specified
                    if (roomType != null && !roomType.isEmpty()) {
                        availableRooms = availableRooms.stream()
                                .filter(room -> roomType.equalsIgnoreCase(room.getTypeName()))
                                .collect(java.util.stream.Collectors.toList());
                    }

                    // Set search parameters back to the request for form retention
                    request.setAttribute("checkInDate", checkInDateStr);
                    request.setAttribute("checkOutDate", checkOutDateStr);
                    request.setAttribute("adults", adults);
                    request.setAttribute("children", children);

                } catch (IllegalArgumentException e) {
                    errorMessage = e.getMessage();
                } catch (Exception e) {
                    errorMessage = "An error occurred while searching for rooms: " + e.getMessage();
                }
            }

            // Set attributes for JSP
            request.setAttribute(RequestAttribute.ROOMS, availableRooms);
            if (errorMessage != null) {
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, errorMessage);
            }

            // Forward to the search page
            request.getRequestDispatcher("/WEB-INF/views/features/rooms/search_available_rooms.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                             "Error processing room search: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

