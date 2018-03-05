package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Empleado {

    @WebMethod(operationName="crearEmpleado")
    public Empleado crearEmpleado(@WebParam(name = "Empleado") Empleado empleadoNuevo) throws EmpleadoException;

    @WebMethod(operationName="buscarByID")
    public Empleado buscarEmpleadoByID(@WebParam(name = "id") Long id);


    @WebMethod(operationName="eliminarEmpleado")
    public boolean eliminarEmpleado(@WebParam(name = "Empleado") Empleado empleadoEliminar) ;


    @WebMethod(operationName="listarEmpleados")
    public List<Empleado> listarEmpleados();

    @WebMethod(operationName="saludar")
    public String saludar(@WebParam(name="nombre") String nombre);

    @WebMethod(operationName="loginEmpleado")
    public boolean loginEmpleado(String email, String pass) throws EmpleadoException;

    @WebMethod(operationName="buscarByEmail")
    public Empleado buscarByEmail(String email) throws EmpleadoException;



}

