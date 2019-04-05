package com.eduardosergio.TFG_server.negocio.holaMundo.impl;

import com.eduardosergio.TFG_server.negocio.holaMundo.HolaMundo;

public class HolaMundoImpl implements HolaMundo{

	public HolaMundoImpl() {}
	
	@Override
	public String saludar() {
		return "Hola Mundo";
	}

}
