package com.hotel_management.infrastructure.http;

import com.hotel_management.presentation.constants.Page;
import com.hotel_management.presentation.constants.RequestAttribute;

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
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
	}
	
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+ uri);

		// Skip validation for static files in /public/
		if (uri.contains("/public/")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = req.getSession(false);

		// URIs that do not require authentication
		boolean isPublicEndpoint = uri.endsWith("/") ||  // Root path (home)
									uri.endsWith("html") ||
									uri.endsWith("login") ||
									uri.endsWith("register") ||
									uri.equals("/hotel_management") ||
									uri.equals("/hotel-management");

		if(session == null && !isPublicEndpoint){
            req.setAttribute(RequestAttribute.ERROR_MESSAGE, "Please sign in to continue your action");
            req.getRequestDispatcher(Page.LOGIN_PAGE).forward(request, response);
		}else{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

}
