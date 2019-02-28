package com.eduardosergio.TFG_server.seguridad.messageInterceptorGateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class MessageInterceptorGateway extends AbstractPhaseInterceptor<Message> {

	public MessageInterceptorGateway() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
        try {
        	
        	HttpServletRequest req = (HttpServletRequest)message.get("HTTP.REQUEST");
        	HttpServletResponse res = (HttpServletResponse)message.get("HTTP.RESPONSE");
        	
        	if (req != null) {
	        	String ipAddress = req.getHeader("X-FORWARDED-FOR");  
	            if (ipAddress == null) {  
	              ipAddress = req.getRemoteAddr();
	            }
	            
	            if (ipAddress.equals("192.168.2.1")) {
	            	res.sendError(500);
	            }
	
	            String b64credentials = req.getHeader("Authorization").substring("Basic".length()).trim();
	    	    String credentials = new String(Base64.getDecoder().decode(b64credentials), StandardCharsets.UTF_8);
	    	    
	    	    System.out.println(credentials);
	    	    if (!credentials.equals("usuario:contra") && !credentials.equals("user:pass")) {
	
	    			res.sendError(500);
	    		}  
        	}

    	    /*
        	InputStream reader = message.getContent(InputStream.class);
    		
        	CachedOutputStream writer = new CachedOutputStream();
        	IOUtils.copy(reader, writer);
        	String content = writer.toString().substring(47);
        	
        	System.out.println(content);
        	
        	message.setContent(InputStream.class, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
			*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
