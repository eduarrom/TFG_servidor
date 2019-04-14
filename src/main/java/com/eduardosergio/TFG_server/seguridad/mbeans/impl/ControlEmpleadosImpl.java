package com.eduardosergio.TFG_server.seguridad.mbeans.impl;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;

public class ControlEmpleadosImpl implements ControlEmpleadosMBean{
	
	public ControlEmpleadosImpl() {
		super();
	}
	
	@Override
	public void añadirEmpleadoVisto(TEmpleadoCompleto empleado) {
		empleados.add(empleado);
	}
	
	@Override
	public String ultimoEmpleadoVisto() { 	
		return empleados.size() > 0 ? empleados.get(empleados.size()-1).toString() : "No se ha visualizado ningun empleado";
	}
	
	@Override
	public String listarEmpleadosVistos() { 	
		return empleados.size() > 0 ? empleados.toString().replace(',', '\n') :  "No se ha visualizado ningun empleado";
	}

}
