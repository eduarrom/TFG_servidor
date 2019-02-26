package com.eduardosergio.TFG_server.negocio.helloWorld;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "HelloWorld", targetNamespace = "http://helloWorld.negocio.TFG_server.eduardosergio.com/")
public interface HelloWorld {

	@WebMethod(operationName = "salute", action = "urn:Salute")
	public String salute();
}
