package com.eduardosergio.TFG_server.seguridad.messageInterceptorGateway;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		//String user = ((HttpServletRequest) request).getUserPrincipal().getName();
		//Como asi podia sacar el usuario pero la contraseña no he optado por sacar las credenciales
		//del header
		
		String b64credentials = ((HttpServletRequest) request).getHeader("Authorization").substring("Basic".length()).trim();
	    String credentials = new String(Base64.getDecoder().decode(b64credentials), StandardCharsets.UTF_8);
	    
		if (credentials.equals("usuario:contra") || credentials.equals("user:pass")) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse resp = (HttpServletResponse) response;
			
			resp.sendError(400);
		}
		
		
	}

	@Override
	public void destroy() 
	{
	   this.filterConfig = null;
	}

}
