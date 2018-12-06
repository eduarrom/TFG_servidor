package com.eduardosergio.TFG_server.seguridad.mbeans;

import java.util.ArrayList;
import java.util.List;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;

public interface ControlEmpleadosMBean {
	
	List<TEmpleadoCompleto> empleados = new ArrayList<TEmpleadoCompleto>();
	
	public String ultimoEmpleadoVisto();
	
	public String listarEmpleadosVistos();
} 
