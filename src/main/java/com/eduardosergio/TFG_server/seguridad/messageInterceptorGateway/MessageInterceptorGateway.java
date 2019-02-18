package com.eduardosergio.TFG_server.seguridad.messageInterceptorGateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        	
        	InputStream reader = message.getContent(InputStream.class);
    		
        	CachedOutputStream writer = new CachedOutputStream();
        	IOUtils.copy(reader, writer);
        	String content = writer.toString().substring(47);
        	
        	System.out.println(content);
        	
        	message.setContent(InputStream.class, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
