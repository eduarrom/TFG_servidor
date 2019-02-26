package com.eduardosergio.TFG_server.negocio.holaMundo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "HolaMundo", targetNamespace = "http://holaMundo.negocio.TFG_server.eduardosergio.com/")
public interface HolaMundo {

	@WebMethod(operationName = "saludar", action = "urn:Saludar")
	public String saludar();
}
