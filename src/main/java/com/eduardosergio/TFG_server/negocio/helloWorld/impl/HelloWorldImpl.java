package com.eduardosergio.TFG_server.negocio.helloWorld.impl;

import com.eduardosergio.TFG_server.negocio.helloWorld.HelloWorld;

public class HelloWorldImpl implements HelloWorld{

	public HelloWorldImpl() {}
	
	@Override
	public String salute() {
		
		return "Hello World";
	}

}
