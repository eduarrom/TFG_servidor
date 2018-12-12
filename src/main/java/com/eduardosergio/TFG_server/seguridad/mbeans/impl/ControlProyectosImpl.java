package com.eduardosergio.TFG_server.seguridad.mbeans.impl;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlProyectosMBean;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;

public class ControlProyectosImpl implements ControlProyectosMBean {
	
	public ControlProyectosImpl() {
		super();
	}
	
	public void añadirProyectoVisto(TProyectoCompleto proyecto) {
		proyectos.add(proyecto);
	}
	
	@Override
	public String ultimoProyectoVisto() {
		return proyectos.size() > 0 ? proyectos.get(proyectos.size()-1).toString() : "No se ha visualizado ningun proyecto";
	}

	@Override
	public String listarProyectosVistos() {
		return proyectos.size() > 0 ? proyectos.toString().replace(',', '\n') :  "No se ha visualizado ningun proyecto";
	}

}
