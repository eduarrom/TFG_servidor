package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Empleados/", portName = "Broker_SA_EmpleadoImpl", serviceName = "Broker_SA_EmpleadoImpl")
@WebService(
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado",
        serviceName="Broker_SA_EmpleadoImpl")
public class Broker_SA_EmpleadoImpl implements IBroker_SA_Empleado {

    public Broker_SA_EmpleadoImpl() {}

    @Override
    public Empleado crearEmpleado(@WebParam(name="Empleado") Empleado empleadoNuevo) throws EmpleadoException {

        return new SA_EmpleadoImpl().crearEmpleado(empleadoNuevo);
        //return null;
    }


    public Empleado buscarEmpleadoByID(@WebParam(name="id") Long id) {
        return new SA_EmpleadoImpl().buscarByID(id);
    }

    public boolean eliminarEmpleado(@WebParam(name="Empleado") Empleado empleadoEliminar) {

        return new SA_EmpleadoImpl().eliminarEmpleado(empleadoEliminar);
    }

    public List<Empleado> listarEmpleados() {

        return new SA_EmpleadoImpl().listarEmpleados();
    }

    @Override
    public String saludar(@WebParam(name="nombre") String nombre) {
        return new SA_EmpleadoImpl().saludar(nombre);
    }



    public boolean loginEmpleado(String email, String pass) throws EmpleadoException {
        return new SA_EmpleadoImpl().loginEmpleado(email, pass);
    }

    @Override
    public Empleado buscarByEmail(String email) throws EmpleadoException {
        return new SA_EmpleadoImpl().buscarByEmail(email);
    }

}
