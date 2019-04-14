package com.eduardosergio.TFG_server.seguridad.mbeans.impl;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlDepartamentosMBean;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;

public class ControlDepartamentosImpl implements ControlDepartamentosMBean {

	public ControlDepartamentosImpl() {
		super();
	}
	
	@Override
	public void añadirDepartamentoVisto(TDepartamentoCompleto departamento) {
		departamentos.add(departamento);
	}
	
	@Override
	public String ultimoDepartamentoVisto() {
		return departamentos.size() > 0 ? departamentos.get(departamentos.size()-1).toString() : "No se ha visualizado ningun departamento";
	}

	@Override
	public String listarDepartamentosVistos() {
		return departamentos.size() > 0 ? departamentos.toString().replace(',', '\n') :  "No se ha visualizado ningun departamento";
	}

}
