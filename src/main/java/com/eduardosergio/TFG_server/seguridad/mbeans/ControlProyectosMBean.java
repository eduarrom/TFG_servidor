package com.eduardosergio.TFG_server.seguridad.mbeans;

import java.util.ArrayList;
import java.util.List;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;

public interface ControlProyectosMBean {
	
	List<TProyectoCompleto> proyectos = new ArrayList<TProyectoCompleto>();
	
	public void añadirProyectoVisto(TProyectoCompleto proyecto);
	
	public String ultimoProyectoVisto();
	
	public String listarProyectosVistos();
} 
