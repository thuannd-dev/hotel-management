/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.hotel_management.infrastructure.controller;

import edu.hotel_management.application.service.RoomService;
import edu.hotel_management.infrastructure.persistence.dao.RoomDAO;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.IConstant;
import edu.hotel_management.presentation.dto.room.RoomPresentationModel;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 
 * @author TR_NGHIA
 */

@WebServlet(name = "RoomBookingController", urlPatterns = {IConstant.ACTION_ROOM_BOOKING})
public class RoomBookingController extends HttpServlet {
    
    private RoomService roomService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        DataSource ds = DataSourceProvider.getDataSource();
        RoomDAO roomDAO = new RoomDAO(ds);
        this.roomService = new RoomService(roomDAO);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // Lấy parameters từ form
        String arrivalDateStr = request.getParameter("arrivalDate");
        String departureDateStr = request.getParameter("departureDate");
        String adultsStr = request.getParameter("adults");
        String childrenStr = request.getParameter("children");
        String roomTypeStr = request.getParameter("roomType");
        
        // Parse parameters
        int adults = parseIntOrDefault(adultsStr, 2);
        int children = parseIntOrDefault(childrenStr, 0);
        int totalGuests = adults + children;
        
        // Tìm kiếm phòng
        List<RoomPresentationModel> rooms = searchAvailableRooms(roomTypeStr, totalGuests, 
                arrivalDateStr, departureDateStr);
        
        // Set attributes để hiển thị
        setRequestAttributes(request, rooms, arrivalDateStr, departureDateStr, 
                adults, children, totalGuests);
        
        // Forward đến trang booking
        RequestDispatcher rd = request.getRequestDispatcher(IConstant.PAGE_ROOM_BOOKING);
        rd.forward(request, response);
    }
    
    // ========= TÌM KIẾM PHÒNG PHÙ HỢP =========
    private List<RoomPresentationModel> searchAvailableRooms(String roomTypeStr, 
            int totalGuests, String arrivalDateStr, String departureDateStr) {
        
        List<RoomPresentationModel> availableRooms;
        
        // Tìm theo room type nếu có
        if (roomTypeStr != null && !roomTypeStr.trim().isEmpty() && !roomTypeStr.equals("")) {
            try {
                int roomTypeId = Integer.parseInt(roomTypeStr);
                availableRooms = roomService.getAvailableRoomsByType(roomTypeId);
            } catch (NumberFormatException e) {
                availableRooms = roomService.getAvailableRooms();
            }
        } else {
            availableRooms = roomService.getAvailableRooms();
        }
        
        List<RoomPresentationModel> suitableRooms = filterByCapacity(availableRooms, totalGuests);
       
        return suitableRooms;
    }
    
    // =========  LỌC PHÒNG THEO CAPACITY =========
    private List<RoomPresentationModel> filterByCapacity(List<RoomPresentationModel> rooms, 
            int totalGuests) {
        return rooms.stream()
                .filter(room -> room.getCapacity() >= totalGuests)
                .collect(Collectors.toList());
    }
    
    // ========= CHECK BOOKING OVERLAP (TODO) =========
    // TODO: Implement khi có BookingService
    /*
    private List<RoomPresentationModel> checkBookingOverlap(List<RoomPresentationModel> rooms,
            String arrivalDateStr, String departureDateStr) {
        
        if (arrivalDateStr == null || departureDateStr == null) {
            return rooms;
        }
        
        try {
            LocalDate arrivalDate = LocalDate.parse(arrivalDateStr);
            LocalDate departureDate = LocalDate.parse(departureDateStr);
            
            // Lọc bỏ các phòng đã được book trong khoảng thời gian này
            return rooms.stream()
                    .filter(room -> !bookingService.isRoomBooked(
                            room.getRoomNumber(), arrivalDate, departureDate))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return rooms;
        }
    }
    */
    
    // ========= BƯỚC 5: SET ATTRIBUTES ĐỂ HIỂN THỊ =========
    private void setRequestAttributes(HttpServletRequest request, 
            List<RoomPresentationModel> rooms,
            String arrivalDate, String departureDate, 
            int adults, int children, int totalGuests) {
        
        // Set danh sách phòng
        request.setAttribute("rooms", rooms);
        request.setAttribute("totalRooms", rooms.size());
        
        // Set search criteria để giữ lại trong form
        request.setAttribute("arrivalDate", arrivalDate);
        request.setAttribute("departureDate", departureDate);
        request.setAttribute("adults", adults);
        request.setAttribute("children", children);
        request.setAttribute("totalGuests", totalGuests);
        
        // Set message
        if (rooms.isEmpty()) {
            request.setAttribute("message", "No rooms available matching your criteria.");
            request.setAttribute("messageType", "warning");
        } else {
            request.setAttribute("message", "Found " + rooms.size() + " room(s) matching your criteria.");
            request.setAttribute("messageType", "success");
        }
    }
    
    // ========= PARSE INT WITH DEFAULT =========
    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Room Booking Controller";
    }
}