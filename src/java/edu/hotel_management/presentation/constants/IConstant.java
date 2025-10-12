package edu.hotel_management.presentation.constants;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author TR_NGHIA
 */
public interface IConstant {

    //========== CÁC HẰNG SỐ CHUNG ==========
    public static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //========== SERVLET & ACTIONS (Dùng cho Front Controller) ==========
    public static final String ACTION_HOME = "HomeController";
    public static final String ACTION_LOGIN = "LoginController";
    public static final String ACTION_LOGOUT = "LogoutController";
    public static final String ACTION_REGISTER = "RegisterController";

    public static final String ACTION_VIEW_PROFILE = "ViewProfileController";
    public static final String ACTION_VIEW_ROOMS = "ViewRoomsController";
    public static final String ACTION_VIEW_ROOM_DETAIL = "ViewRoomDetailController";

    public static final String ACTION_ROOM_BOOKING = "RoomBookingController"; 
    public static final String ACTION_VIEW_BOOKING = "ViewBookingController";
    public static final String ACTION_BOOKING_DETAIL = "BookingDetailController";

    //========== CÁC LAYOUT CHÍNH ==========
    public static final String PAGE_HOME = "/home.jsp";
    public static final String PAGE_GUEST = ""; 
    public static final String PAGE_ADMIN = "";
    public static final String PAGE_RECEPTIONIST = "";
    public static final String PAGE_MANAGER = "";

    //========== CÁC TRANG CHUC NANG CHÍNH ==========
    public static final String PAGE_LOGIN = "/WEB-INF/views/features/login/login.jsp";
    public static final String PAGE_SIGN_UP = "/WEB-INF/views/features/login/sign-up.jsp";
    public static final String PAGE_ROOM_BOOKING = "/WEB-INF/views/features/booking/viewRoomsBooking.jsp";

}
