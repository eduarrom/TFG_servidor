package com.eduardosergio.TFG_server.seguridad.mbeans.factory.impl;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlDepartamentosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.ControlProyectosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.factory.MBeansFactory;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlDepartamentosImpl;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlEmpleadosImpl;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlProyectosImpl;

public class MBeansFactoryImpl extends MBeansFactory {

	@Override
	public ControlEmpleadosMBean getEmpleadoMBean() {
		return new ControlEmpleadosImpl();
	}

	@Override
	public ControlDepartamentosMBean getDepartamentoMBean() {
		return new ControlDepartamentosImpl();
	}

	@Override
	public ControlProyectosMBean getProyectoMBean() {
		return new ControlProyectosImpl();
	}

}
