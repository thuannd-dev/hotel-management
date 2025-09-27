<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<% // Set the content page to home page
    request.setAttribute("contentPage", "home-page.jsp");
%>
<jsp:include page="/WEB-INF/views/layout/main-layout.jsp" />
