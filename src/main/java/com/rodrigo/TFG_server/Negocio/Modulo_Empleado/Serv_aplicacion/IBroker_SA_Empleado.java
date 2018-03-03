package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Empleado {

    @WebMethod(operationName="crearEmpleado")
    public Empleado crearUsuario(@WebParam(name = "Empleado") Empleado empleadoNuevo);

    @WebMethod(operationName="buscarByID")
    public Empleado buscarUsuarioByID(@WebParam(name = "id") Long id);


    @WebMethod(operationName="eliminarEmpleado")
    public boolean eliminarUsuario(@WebParam(name = "Empleado") Empleado empleadoEliminar) ;


    @WebMethod(operationName="listarEmpleados")
    public List<Empleado> listarUsuarios();

    @WebMethod(operationName="saludar")
    public String saludar(@WebParam(name="nombre") String nombre);


}

