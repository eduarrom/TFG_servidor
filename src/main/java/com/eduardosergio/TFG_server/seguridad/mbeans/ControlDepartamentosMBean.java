package com.eduardosergio.TFG_server.seguridad.mbeans;

import java.util.ArrayList;
import java.util.List;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;

public interface ControlDepartamentosMBean {
	
	List<TDepartamentoCompleto> departamentos = new ArrayList<TDepartamentoCompleto>();
	
	public String ultimoDepartamentoVisto();
	
	public String listarDepartamentosVistos();
} 
