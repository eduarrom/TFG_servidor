package com.eduardosergio.TFG_server.seguridad.messageInterceptorGateway;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MessageInterceptorGateway implements Filter{

FilterConfig filterConfig= null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException 
	{
	      this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("ESTOY EN EL MIG");
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() 
	{
	   this.filterConfig = null;
	}

}
