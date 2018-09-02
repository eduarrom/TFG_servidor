package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Empleados/", portName = "Broker_SA_EmpleadoImpl", serviceName = "Broker_SA_EmpleadoImpl")
@WebService(
//        targetNamespace = "https://impl.Serv_aplicacion.Modulo_Empleado.Negocio.TFG_server.rodrigo.com/",
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.IBroker_SA_Empleado",
        serviceName="Broker_SA_EmpleadoImpl"
        /*portName = "Broker_SA_EmpleadoPort"*/)
public class Broker_SA_EmpleadoImpl implements IBroker_SA_Empleado {

    public Broker_SA_EmpleadoImpl() {}

    @Override
    public TEmpleadoCompleto crearEmpleado(@WebParam(name="Empleado") TEmpleado empleadoNuevo) throws EmpleadoYaExisteExcepcion, EmpleadoException {

        return FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(empleadoNuevo);
        //return null;
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
