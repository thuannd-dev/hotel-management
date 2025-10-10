package com.hotel_management.infrastructure.http;

import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.SessionAttribute;
import com.hotel_management.domain.dto.guest.GuestViewModel;
import com.hotel_management.domain.dto.staff.StaffViewModel;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author thuannd.dev
 */
public class AuthorizationFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig fConfig) { this.context = fConfig.getServletContext(); }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if(session == null) {
            if (uri.contains("/admin")) {
                req.getRequestDispatcher(Page.ACCESS_DENIED_PAGE).forward(req, res);
                return;
            }
            chain.doFilter(req, res);
            return;
        }

        Object  currentUser  = session.getAttribute(SessionAttribute.CURRENT_USER);
        String role = "";
        if (currentUser instanceof StaffViewModel) {
            StaffViewModel staff = (StaffViewModel) currentUser;
            role = staff.getRole();
        } else if (currentUser instanceof GuestViewModel) {
            role = "GUEST";
        } else {
            role = "UNKNOWN";
        }

        if(uri.contains("/admin") && !"ADMIN".equals(role)) {
            this.context.log("Access denied for user: " + session.getAttribute("username"));
            request.getRequestDispatcher(Page.ACCESS_DENIED_PAGE).forward(req, res);
            return;
        } else if (uri.contains("/service-staff") && !"SERVICE_STAFF".equals(role)) {
            this.context.log("Access denied for user: " + session.getAttribute("username"));
            request.getRequestDispatcher(Page.ACCESS_DENIED_PAGE).forward(req, res);
            return;
        }
        chain.doFilter(request, response);
    }

}
