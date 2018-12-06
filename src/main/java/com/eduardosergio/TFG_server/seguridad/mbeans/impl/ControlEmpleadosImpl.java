package com.eduardosergio.TFG_server.seguridad.mbeans.impl;

import java.util.ArrayList;
import java.util.List;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;

public class ControlEmpleadosImpl implements ControlEmpleadosMBean{
	
	public ControlEmpleadosImpl() {
		super();
	}
	
	public void añadirEmpleadoVisto(TEmpleadoCompleto empleado) {
		empleados.add(empleado);
	}
	
	@Override
	public String ultimoEmpleadoVisto() { 	
		return this.empleados.size() > 0 ? this.empleados.get(this.empleados.size()-1).toString() : null;
	}
	
	@Override
	public String listarEmpleadosVistos() { 	
		return this.empleados.size() > 0 ? this.empleados.toString().replace(',', '\n') : null;
	}

}
