package com.hotel_management.presentation.constants;
/**
 *
 * @author thuannd.dev
 */
public final class RequestAttribute {
    private RequestAttribute() {}
    public static final String ERROR_MESSAGE = "error";
    public static final String CHECK_IN_BOOKING_DETAILS = "checkInBookingDetails";
    public static final String BOOKING = "booking";
    public static final String SERVICES = "services";
    public static final String ROOMS = "rooms";
    public static final String LIST_CHECK_IN_BOOKING_DETAILS = "listCheckInBookingDetails";
    public static final String BOOKING_SEARCH_TYPE = "searchType";
    public static final String BOOKING_SEARCH_VALUE = "searchValue";
    public static final String LIST_NEW_BOOKING_SERVICE_USAGE = "listNewBookingServiceUsage";
    public static final String LIST_OLD_BOOKING_SERVICE_USAGE = "listOldBookingServiceUsage";
    public static final String REPORT_TYPE = "reportType";
    public static final String SERVICES_PROVIDED_TODAY_REPORT = "servicesProvidedTodayReport";
    public static final String SERVICES_PROVIDED_PERIOD_REPORT = "servicesProvidedPeriodReport";
    public static final String SERVICES_REQUEST_REPORT = "servicesRequestReport";
    public static final String SERVICES_COMPLETED_REPORT = "servicesCompletedReport";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    // Housekeeping Report Attributes
    public static final String DAILY_ROOM_CLEANING_REPORT = "dailyRoomCleaningReport";
    public static final String STAFF_PERFORMANCE_REPORT = "staffPerformanceReport";
    public static final String MAINTENANCE_ISSUE_REPORT = "maintenanceIssueReport";
    public static final String PENDING_CLEANING_TASK_REPORT = "pendingCleaningTaskReport";
    public static final String ROOM_STATUS_REPORT = "roomStatusReport";
    //GUEST
    public static final String GUESTS = "guests";
}
