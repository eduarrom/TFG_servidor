package com.eduardosergio.TFG_server.seguridad.auditInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.eduardosergio.TFG_server.seguridad.auditInterceptor.entity.AuditInterceptorLog;
import com.rodrigo.TFG_server.Integracion.EMFSingleton;

public class AuditInterceptor extends AbstractPhaseInterceptor<Message>  {

	public AuditInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
    	HttpServletRequest req = (HttpServletRequest)message.get("HTTP.REQUEST");
    	HttpServletResponse res = (HttpServletResponse)message.get("HTTP.RESPONSE");
    	
    	
    	if (req != null) {
        	String ipAddress = req.getHeader("X-FORWARDED-FOR");  
            if (ipAddress == null) {  
              ipAddress = req.getRemoteAddr();
            }
            
            String b64credentials = req.getHeader("Authorization").substring("Basic".length()).trim();
    	    String credentials = new String(Base64.getDecoder().decode(b64credentials), StandardCharsets.UTF_8);
    	    String serviceURI = (String) message.get("org.apache.cxf.request.uri");
    	    
    	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFG_server");
    	    EntityManager em = emf.createEntityManager();
    	    
    	    AuditInterceptorLog log = new AuditInterceptorLog(ipAddress, credentials, serviceURI);
    	    
    	    em.getTransaction().begin();
    	    em.persist(log);
    	    em.getTransaction().commit();
    	    em.close();
    	    emf.close();
    	    
    	}
	}

}