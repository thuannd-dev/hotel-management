package com.hotel_management.infrastructure.http;

import com.hotel_management.presentation.constants.Path;
import com.hotel_management.presentation.constants.SessionAttribute;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if(session != null) {
            StaffViewModel staff = (StaffViewModel) session.getAttribute(SessionAttribute.CURRENT_USER);
            String role = staff == null ? null : staff.getRole();
            if(uri.contains("/admin") && !"ADMIN".equals(role)) {
                this.context.log("Access denied for user: " + session.getAttribute("username"));
                request.getRequestDispatcher(Path.ACCESS_DENIED_PAGE).forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
