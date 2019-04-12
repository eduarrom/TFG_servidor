package com.eduardosergio.TFG_server.negocio.ciaoMondo.impl;

import javax.jws.WebService;

import com.eduardosergio.TFG_server.negocio.ciaoMondo.CiaoMondo;

@WebService(targetNamespace = "http://impl.ciaoMondo.negocio.TFG_server.eduardosergio.com/", endpointInterface = "com.eduardosergio.TFG_server.negocio.ciaoMondo.CiaoMondo", portName = "CiaoMondoImplPort", serviceName = "CiaoMondoImplService")
public class CiaoMondoImpl implements CiaoMondo{

	public CiaoMondoImpl() {}
	
	@Override
	public String saludar() {
		
		return "CiaoMondo";
	}

}
