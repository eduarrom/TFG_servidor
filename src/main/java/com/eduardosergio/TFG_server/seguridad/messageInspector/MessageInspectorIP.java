package com.eduardosergio.TFG_server.seguridad.messageInspector;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class MessageInspectorIP extends AbstractPhaseInterceptor<Message>  {

	public MessageInspectorIP() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
    	HttpServletRequest req = (HttpServletRequest)message.get("HTTP.REQUEST");
    	
    	if (req != null) {
        	String ipAddress = req.getHeader("X-FORWARDED-FOR");  
            if (ipAddress == null) {  
              ipAddress = req.getRemoteAddr();
            }
            
            if (ipAddress.equals("127.0.0.1")) {
            	throw new WebApplicationException("La IP no esta autorizada");
            }
            System.out.println(ipAddress);
    	}
	}

}