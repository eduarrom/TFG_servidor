package com.eduardosergio.TFG_server.negocio.helloWorld;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.annotations.Policies;
import org.apache.cxf.annotations.Policy;

@WebService
public interface HelloWorld {
	
	@Policies({
		@Policy(uri="policy.xml",
				placement=Policy.Placement.BINDING_OPERATION,
				includeInWSDL=true),
		@Policy(uri="inputPolicy.xml",
				placement=Policy.Placement.BINDING_OPERATION_INPUT,
				includeInWSDL=true),
		@Policy(uri="outputPolicy.xml",
				placement=Policy.Placement.BINDING_OPERATION_OUTPUT,
				includeInWSDL=true)
	})
	
	@WebMethod
	public String salute();
}
