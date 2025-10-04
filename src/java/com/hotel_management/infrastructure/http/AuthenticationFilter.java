package com.hotel_management.infrastructure.http;

import com.hotel_management.presentation.constants.Path;
import com.hotel_management.presentation.constants.RequestAttribute;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author thuannd.dev
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}
	
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+uri);
		
		HttpSession session = req.getSession(false);

		if(session == null && !(uri.endsWith("html") || uri.endsWith("login") || uri.endsWith("register"))){
			this.context.log("Unauthorized access request");
            req.setAttribute(RequestAttribute.ERROR_MESSAGE, "Please sign in to continue your action");
            req.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);
		}else{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}


	}

//        @Override
//	public void destroy() {
//		//close any resources here
//	}

}
