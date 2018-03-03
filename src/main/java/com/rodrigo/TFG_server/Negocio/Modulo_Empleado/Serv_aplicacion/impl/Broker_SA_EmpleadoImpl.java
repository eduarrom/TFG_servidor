package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Usuarios/", portName = "Broker_SA_EmpleadoImpl", serviceName = "Broker_SA_EmpleadoImpl")
@WebService(
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado",
        serviceName="Broker_SA_EmpleadoImpl")
public class Broker_SA_EmpleadoImpl implements IBroker_SA_Empleado {

    public Broker_SA_EmpleadoImpl() {}

    @Override
    public Empleado crearUsuario(@WebParam(name="Empleado") Empleado empleadoNuevo) {

        //return new SA_EmpleadoImpl().crearEmpleado(empleadoNuevo);
        return null;
    }


    public Empleado buscarUsuarioByID(@WebParam(name="id") Long id) {
        return new SA_EmpleadoImpl().buscarByID(id);
    }

    public boolean eliminarUsuario(@WebParam(name="Empleado") Empleado empleadoEliminar) {

        return new SA_EmpleadoImpl().eliminarEmpleado(empleadoEliminar);
    }

    public List<Empleado> listarUsuarios() {

        return new SA_EmpleadoImpl().listarEmpleados();
    }

    @Override
    public String saludar(@WebParam(name="nombre") String nombre) {
        return new SA_EmpleadoImpl().saludar(nombre);
    }



    public boolean loginEmpleado(String email, String pass) throws EmpleadoException {
        return new SA_EmpleadoImpl().loginEmpleado(email, pass);
    }

}
