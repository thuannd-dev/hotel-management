package com.hotel_management.presentation.constants;
/**
 *
 * @author thuannd.dev
 */
public final class Page {
    private Page() {}
    public static final String LOGIN_PAGE = "/WEB-INF/views/features/auth/login.jsp";
    public static final String HOME_PAGE = "home.jsp";
    public static final String ACCESS_DENIED_PAGE = "/WEB-INF/views/error/access_denied.jsp";
    public static final String SERVICE_STAFF_DASHBOARD_PAGE = "/WEB-INF/views/features/dashboard/staff/service_staff_dashboard.jsp";
    public static final String ADD_SERVICE_PAGE = "/WEB-INF/views/features/dashboard/staff/add_service.jsp";
    public static final String SERVICE_USAGE_DETAIL_PAGE = "/WEB-INF/views/features/dashboard/staff/service_usage_detail.jsp";
    public static final String SERVICE_REPORT_PAGE = "/WEB-INF/views/features/dashboard/staff/service_report.jsp";
    public static final String HOUSEKEEPING_DASHBOARD_PAGE = "/WEB-INF/views/features/dashboard/housekeeping/housekeeping_dashboard.jsp";
    public static final String HOUSEKEEPING_REPORT_PAGE = "/WEB-INF/views/features/dashboard/housekeeping/housekeeping_report.jsp";
    public static final String CREATE_BOOKING_PAGE = "/WEB-INF/views/features/dashboard/receptionist/create_booking.jsp";
    public static final String CREATE_PAYMENT_PAGE = "/WEB-INF/views/features/dashboard/receptionist/invoice.jsp";
    public static final String RECEPTIONIST_DASHBOARD_PAGE = "/WEB-INF/views/features/dashboard/receptionist/receptionist_dashboard.jsp";

}
