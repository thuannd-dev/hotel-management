<%--
    Document   : bookingForm 
    Created on : Oct 4, 2025, 11:10:10 PM
    Author     : TR_NGHIA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<section class="booking-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-11">
                <div class="card shadow-lg booking-card">
                    <div class="card-body p-4">
                        <form id="bookingForm">
                            <div class="row g-3">
                                <div class="col-lg-4 col-md-6">
                                    <label class="form-label text-muted small">Arrival Date</label>
                                    <input type="date" class="form-control" id="arrivalDate" name="arrivalDate" required>
                                </div>
                                <div class="col-lg-4 col-md-6">
                                    <label class="form-label text-muted small">Departure Date</label>
                                    <input type="date" class="form-control" id="departureDate" name="departureDate" required>
                                </div>
                                <div class="col-lg-4 col-md-12">
                                    <label class="form-label text-muted small">Room Type</label>
                                    <select class="form-select" name="roomType">
                                        <option selected value="">All Room Types</option>
                                        <option value="1">Deluxe Ocean Room</option>
                                        <option value="2">Master Garden Suite</option>
                                        <option value="3">Marina Deluxe Room</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label text-muted small">Adults</label>
                                    <input type="number" class="form-control" id="adults" name="adults" value="2" min="1">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label text-muted small">Children</label>
                                    <input type="number" class="form-control" id="children" name="children" value="0" min="0">
                                    <div class="form-text mt-1" style="font-size: 0.75rem;">Under 18 years old</div>
                                </div>

                                <div class="col-12">
                                    <div id="childrenAgesContainer" class="row g-3">
                                        </div>
                                </div>

                                <div class="col-12">
                                    <button type="submit" class="btn btn-warning w-100 py-2 mt-2">Check Availability</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>