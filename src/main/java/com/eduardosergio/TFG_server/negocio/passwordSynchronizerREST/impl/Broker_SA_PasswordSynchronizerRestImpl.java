package com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.impl;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;

@Path("/psrest")
public class Broker_SA_PasswordSynchronizerRestImpl {

	public Broker_SA_PasswordSynchronizerRestImpl() {
		
	}
	
	@POST
	@Path("/synchronize")
	public void synchronize(@FormParam("user") String user, @FormParam("pass") String pass) {	
		FactoriaSA.getInstance().crearSA_PasswordSynchronizerRest().synchronize(user, pass);
	}
}
