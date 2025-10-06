<%--
    Document   : alert 
    Created on : Oct 5, 2025, 3:55:12 PM
    Author     : TR_NGHIA
--%>

<%-- Hi?n th? th�ng b�o th�nh c�ng (t? session)--%>
<%
    Object successMessageObj = session.getAttribute("SUCCESS_MESSAGE");
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
        // X�a message kh?i session ?? kh�ng hi?n th? l?i
        session.removeAttribute("SUCCESS_MESSAGE");
    }
%>

<%-- Hi?n th? th�ng b�o th?t b?i (t? request)--%>
<%
    Object errorMessageObj = request.getAttribute("REGISTER_ERROR");
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