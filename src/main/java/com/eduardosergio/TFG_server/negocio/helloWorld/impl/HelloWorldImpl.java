package com.eduardosergio.TFG_server.negocio.helloWorld.impl;

import javax.jws.WebService;

import com.eduardosergio.TFG_server.negocio.helloWorld.HelloWorld;

@WebService( 
portName="HelloWorldPort",
endpointInterface="com.eduardosergio.TFG_server.negocio.helloWorld.HelloWorld")
public class HelloWorldImpl implements HelloWorld{

	public HelloWorldImpl() {}
	
	@Override
	public String salute() {
		return "Hello World";
	}

}
