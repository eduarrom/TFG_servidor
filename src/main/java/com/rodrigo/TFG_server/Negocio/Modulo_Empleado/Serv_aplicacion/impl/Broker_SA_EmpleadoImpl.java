package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.eduardosergio.TFG_server.seguridad.mbeans.ControlEmpleadosMBean;
import com.eduardosergio.TFG_server.seguridad.mbeans.impl.ControlEmpleadosImpl;
import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado;

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
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado",
        serviceName="Broker_SA_EmpleadoImpl"
        )
public class Broker_SA_EmpleadoImpl implements IBroker_SA_Empleado {

    public Broker_SA_EmpleadoImpl() {
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name;
		try {
			name = new ObjectName("controlEmpleados:type=Control");
			ControlEmpleadosImpl impl = new ControlEmpleadosImpl(listarEmpleados());
	        StandardMBean mbean = new StandardMBean(impl,ControlEmpleadosMBean.class, false);
	        mbs.registerMBean(mbean, name);
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public TEmpleadoCompleto crearEmpleado(@WebParam(name="Empleado") TEmpleado empleadoNuevo) throws EmpleadoYaExisteExcepcion, EmpleadoException {

        return FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(empleadoNuevo);
    }


    @Override
    public TEmpleadoCompleto buscarByID(@WebParam(name="id") Long id) throws EmpleadoException {
        TEmpleadoCompleto emple = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(id);

        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println();

        System.out.println("emple = [" + emple + "]");

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");

        return emple;
    }

    public boolean eliminarEmpleado(@WebParam(name="Empleado") Long id) throws EmpleadoException {

        return FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(id);
    }

    public List<TEmpleado> listarEmpleados() {
        return FactoriaSA.getInstance().crearSA_Empleado().listarEmpleados();
    }

    @Override
    public TEmpleadoCompleto buscarByEmail(String email) throws EmpleadoFieldInvalidException, EmpleadoException {
        return FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(email);
    }






    @Override
    public TEmpleadoCompleto buscarByIDTransfer(Long id) throws EmpleadoException {


        TEmpleadoCompleto tec = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(id);

        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println();

        System.out.println("tec = [" + tec + "]");

        System.out.println();
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");


        return tec;
    }
}
