package com.eduardosergio.TFG_server.negocio.helloWorld.impl;

import org.apache.cxf.annotations.Policies;
import org.apache.cxf.annotations.Policy;

import com.eduardosergio.TFG_server.negocio.helloWorld.HelloWorld;

public class HelloWorldImpl implements HelloWorld{

	public HelloWorldImpl() {}
	
	@Override
	public String salute() {
		
		return "Hello World";
	}

}
