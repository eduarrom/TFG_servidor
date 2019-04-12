package com.eduardosergio.TFG_server.seguridad.messageInspector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPFault;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.http.HttpStatus;

public class MessageInspectorUserAndPassword extends AbstractPhaseInterceptor<Message>  {

	public MessageInspectorUserAndPassword() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
    	HttpServletRequest req = (HttpServletRequest)message.get("HTTP.REQUEST");
    	HttpServletResponse res = (HttpServletResponse)message.get("HTTP.RESPONSE");
    	
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
