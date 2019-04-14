package com.eduardosergio.TFG_server.seguridad.mbeans.factory;

import com.eduardosergio.TFG_server.seguridad.mbeans.ControlDepartamentosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.ControlProyectosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.factory.impl.MBeansFactoryImpl;

public abstract class MBeansFactory {
	private static MBeansFactory instance;
	
	public static MBeansFactory getInstance() {
		if (instance == null) {
			instance = new MBeansFactoryImpl();
		}
		
		return instance;
	}
	
	public abstract ControlEmpleadosMBean getEmpleadoMBean();
	public abstract ControlDepartamentosMBean getDepartamentoMBean();
	public abstract ControlProyectosMBean getProyectoMBean();
}
