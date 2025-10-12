/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.infrastructure.http;

import edu.hotel_management.presentation.constants.IConstant;
import edu.hotel_management.presentation.constants.SessionAttribute;
import edu.hotel_management.presentation.dto.guest.GuestPresentationModel;
import edu.hotel_management.presentation.dto.staff.StaffPresentationModel;
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
 * @author TR_NGHIA
 */

public class AuthorizationFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        if (uri.contains("/admin")) {
            
            if (session == null) {
                this.context.log("Access denied to /admin: User is not logged in.");
                request.getRequestDispatcher(IConstant.PAGE_GUEST).forward(httpRequest, httpResponse);
                return;
            }

            String userRole = getUserRoleFromSession(session);

            if (!"ADMIN".equals(userRole)) {
                Object currentUser = session.getAttribute(SessionAttribute.USER);
                this.context.log("Access denied for user '" + currentUser + "' with role '" + userRole + "' to URI: " + uri);
                
                request.getRequestDispatcher(IConstant.PAGE_HOME).forward(httpRequest, httpResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String getUserRoleFromSession(HttpSession session) {
        Object currentUser = session.getAttribute(SessionAttribute.USER);

        if (currentUser instanceof StaffPresentationModel) {
            StaffPresentationModel staff = (StaffPresentationModel) currentUser;
            return staff.getRole();
        } else if (currentUser instanceof GuestPresentationModel) {
            return "GUEST";
        } else {
            return "UNKNOWN";
        }
    }
}