package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.ControlProyectosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlEmpleadosImpl;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlProyectosImpl;
import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.IBroker_SA_Proyecto;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@WebService(
        endpointInterface = "com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.IBroker_SA_Proyecto",
        serviceName = "Broker_SA_ProyectoImpl")
public class Broker_SA_ProyectoImpl implements IBroker_SA_Proyecto {
	
	private ControlProyectosImpl ControlProyectos;
	
    public Broker_SA_ProyectoImpl() {
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name;
		try {
			name = new ObjectName("Proyectos:type=com.eduardosergio.TFG_server.negocio.seguridad.mbeans.ControlProyectosMBean");
			ControlProyectos = new ControlProyectosImpl();
	        StandardMBean mbean = new StandardMBean(ControlProyectos,ControlProyectosMBean.class, false);
	        mbs.registerMBean(mbean, name);
		} catch (MalformedObjectNameException e) {

		} catch (InstanceAlreadyExistsException e) {

		} catch (MBeanRegistrationException e) {

		} catch (NotCompliantMBeanException e) {

		}
    }

    @Override
    public TProyecto crearProyecto(@WebParam(name = "proyectoNuevo") TProyecto proyectoNuevo) throws ProyectoYaExistenteException, ProyectoFieldInvalidException, ProyectoException {

        return FactoriaSA.getInstance().crearSA_Proyecto().crearProyecto(proyectoNuevo);
    }


    @Override
    public TProyectoCompleto buscarByID(@WebParam(name = "id") Long id) throws ProyectoFieldInvalidException, ProyectoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(id);
    }

    @Override
    public TEmpleadoProyecto agregarEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas) throws EmpleadoException, ProyectoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().agregarEmpleadoAProyecto(e, p, horas);
    }

    @Override
    public boolean eliminarEmpleadoAProyecto(Long idEmple, Long idProy) throws ProyectoException, EmpleadoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().eliminarEmpleadoAProyecto(idEmple, idProy);

    }

    @Override
    public TProyectoCompleto buscarByNombre(@WebParam(name = "nombre") String nombre) throws ProyectoFieldInvalidException, ProyectoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().buscarByNombre(nombre);
    }

    @Override
    public boolean eliminarProyecto(@WebParam(name = "id") Long id) throws ProyectoConEmpleadosException, ProyectoFieldInvalidException, ProyectoException {

        return FactoriaSA.getInstance().crearSA_Proyecto().eliminarProyecto(id);
    }

    @Override
    public List<TProyecto> listarProyectos() {

        return FactoriaSA.getInstance().crearSA_Proyecto().listarProyectos();
    }

}
