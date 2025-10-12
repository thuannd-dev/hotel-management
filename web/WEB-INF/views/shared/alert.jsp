<%--
    Document   : alert 
    Created on : Oct 5, 2025, 3:55:12 PM
    Author     : TR_NGHIA
--%>

<%@page import="edu.hotel_management.presentation.constants.RequestAttribute"%>
<%-- Hi?n th? thông báo thành công (t? session)--%>
<%
    Object successMessageObj = session.getAttribute(RequestAttribute.SUCCESS_REGISTER_MESSAGE);
    if (successMessageObj != null) {
        String successMessage = (String) successMessageObj;
%>
<div class="container" style="margin-top: 100px;">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <%= successMessage%>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</div>
<%
        // Xóa message kh?i session ?? không hi?n th? l?i
        session.removeAttribute("SUCCESS_MESSAGE");
    }
%>

<%-- Hi?n th? thông báo th?t b?i (t? request)--%>
<%
    Object errorMessageObj = request.getAttribute(RequestAttribute.ERROR_REGISTER_MESSAGE);
    if (errorMessageObj != null) {
        String errorMessage = (String) errorMessageObj;
%>
<div class="container" style="margin-top: 100px;">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= errorMessage%>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</div>
<%
    }
%>