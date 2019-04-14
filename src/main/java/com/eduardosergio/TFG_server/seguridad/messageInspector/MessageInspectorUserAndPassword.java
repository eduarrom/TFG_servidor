package com.eduardosergio.TFG_server.seguridad.messageInspector;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.WebApplicationException;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class MessageInspectorUserAndPassword extends AbstractPhaseInterceptor<Message>  {

	public MessageInspectorUserAndPassword() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
    	HttpServletRequest req = (HttpServletRequest)message.get("HTTP.REQUEST");
    	
    	if (req != null) {
        	
            String b64credentials = req.getHeader("Authorization").substring("Basic".length()).trim();
    	    String credentials = new String(Base64.getDecoder().decode(b64credentials), StandardCharsets.UTF_8);
    	    
    	    System.out.println(credentials);
    	    if (!credentials.equals("usuario:contra") && !credentials.equals("user:pass")) {

    	    	throw new WebApplicationException("Las credenciales no son correctas");
    		}  
    	}
	}

}
