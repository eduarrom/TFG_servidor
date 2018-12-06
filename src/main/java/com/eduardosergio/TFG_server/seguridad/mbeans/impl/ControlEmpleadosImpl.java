package com.eduardosergio.TFG_server.seguridad.mbeans.impl;

import java.util.List;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;

public class ControlEmpleadosImpl implements ControlEmpleadosMBean{

	List<TEmpleado> empleados;
	
	public ControlEmpleadosImpl(List<TEmpleado> empleados) {
		super();
		this.empleados = empleados;
	}
	
	@Override
	public String ultimoEmpleadoVisto() { 	
		return this.empleados.size() > 0 ? this.empleados.get(this.empleados.size() - 1).toString() : null;
	}

}
