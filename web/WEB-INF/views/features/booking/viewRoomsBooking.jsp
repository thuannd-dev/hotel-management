<%-- 
    Document   : viewRoomsBooking
    Created on : Oct 5, 2025, 8:24:45 PM
    Author     : TR_NGHIA
--%>

<%@page import="edu.hotel_management.presentation.constants.SessionAttribute"%>
<%@page import="javax.websocket.Session"%>
<%@page import="edu.hotel_management.presentation.constants.IConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="booking-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-11">
                <div class="card shadow-lg booking-card">
                    <div class="card-body p-4">

                        <form id="bookingForm" action="<%= IConstant.ACTION_ROOM_BOOKING %>" method="Post">
                            <div class="row g-3">
                                <div class="col-lg-4 col-md-6">
                                    <label for="arrivalDate" class="form-label text-muted small">Arrival Date</label>
                                    <input type="date" class="form-control" id="arrivalDate" name="arrivalDate">
                                    <div class="invalid-feedback">
                                        Please select an arrival date.
                                    </div>
                                </div>

                                <div class="col-lg-4 col-md-6">
                                    <label for="departureDate" class="form-label text-muted small">Departure Date</label>
                                    <input type="date" class="form-control" id="departureDate" name="departureDate">
                                    <div class="invalid-feedback">
                                        Please select a departure date.
                                    </div>
                                </div>

                                <div class="col-lg-4 col-md-12">
                                    <label for="roomType" class="form-label text-muted small">Room Type</label>
                                    <select class="form-select" id="roomType" name="roomType">
                                        <option selected value="">All Room Types</option>
                                        <option value="1">Deluxe Ocean Room</option>
                                        <option value="2">Master Garden Suite</option>
                                        <option value="3">Marina Deluxe Room</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please select a room type.
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="adults" class="form-label text-muted small">Adults</label>
                                    <input type="number" class="form-control" id="adults" name="adults" value="2" min="1">
                                    <div class="invalid-feedback">
                                        The number of adults must be at least 1.
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="children" class="form-label text-muted small">Children</label>
                                    <input type="number" class="form-control" id="children" name="children" value="0" min="0">
                                </div>

                                <div class="col-12">
                                    <%
                                    if (session.getAttribute(SessionAttribute.USER) == null) {
                                    %>
                                        <button type="button" class="btn btn-warning w-100 py-2 mt-2" data-bs-toggle="modal" data-bs-target="#loginModal">Check Availability</button>
                                    <%
                                    } else {
                                    %>
                                        <button type="submit" class="btn btn-warning w-100 py-2 mt-2">Check Availability</button>
                                    <%
                                    }
                                    %>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>