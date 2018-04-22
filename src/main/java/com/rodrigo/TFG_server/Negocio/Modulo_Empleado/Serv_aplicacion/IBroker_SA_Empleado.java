package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Empleado {

    @WebMethod(operationName="crearEmpleado")
    public TEmpleadoCompleto crearEmpleado(@WebParam(name = "Empleado") TEmpleado empleadoNuevo) throws EmpleadoException;

    @WebMethod(operationName="buscarByID")
    public TEmpleadoCompleto buscarByID(@WebParam(name = "id") Long id) throws EmpleadoException;


    @WebMethod(operationName="eliminarEmpleado")
    public boolean eliminarEmpleado(@WebParam(name = "empleadoEliminar") TEmpleado empleadoEliminar) throws EmpleadoException;


    @WebMethod(operationName="listarEmpleados")
    public List<TEmpleado> listarEmpleados();

    @WebMethod(operationName="saludar")
    public String saludar(@WebParam(name="nombre") String nombre);

    @WebMethod(operationName="loginEmpleado")
    public boolean loginEmpleado(String email, String pass) throws EmpleadoException;

    @WebMethod(operationName="buscarByEmail")
    public TEmpleadoCompleto buscarByEmail(String email) throws EmpleadoException;



    @WebMethod(operationName="buscarByIDTransfer")
    public TEmpleadoCompleto buscarByIDTransfer(@WebParam(name = "id") Long id) throws EmpleadoException;




}

