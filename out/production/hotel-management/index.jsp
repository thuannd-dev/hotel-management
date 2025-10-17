<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<% // Set the content page to home page
    request.setAttribute("contentPage", "/WEB-INF/views/layout/home_page.jsp");
%>
<jsp:include page="/WEB-INF/views/layout/main_layout.jsp" />
