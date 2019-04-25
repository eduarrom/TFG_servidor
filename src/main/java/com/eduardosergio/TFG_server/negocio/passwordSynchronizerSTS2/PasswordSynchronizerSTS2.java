package com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.annotations.Policies;
import org.apache.cxf.annotations.Policy;

@WebService
public interface PasswordSynchronizerSTS2 {
	
	@Policies({
		@Policy(uri="policy2.xml",
				placement=Policy.Placement.BINDING_OPERATION,
				includeInWSDL=true),
		@Policy(uri="inputPolicy2.xml",
				placement=Policy.Placement.BINDING_OPERATION_INPUT,
				includeInWSDL=true),
		@Policy(uri="outputPolicy2.xml",
				placement=Policy.Placement.BINDING_OPERATION_OUTPUT,
				includeInWSDL=true)
	})

	@WebMethod
	public void synchronize(String user, String pass);
}
