package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Empleado {

    TEmpleadoCompleto crearEmpleado(TEmpleado empleadoNuevo) throws EmpleadoException;

    TEmpleadoCompleto buscarByID(Long id) throws EmpleadoException;

    boolean eliminarEmpleado(TEmpleado empleadoEliminar) throws EmpleadoException;

    List<TEmpleado> listarEmpleados();

    String saludar(String nombre);

    Boolean loginEmpleado(String email, String pass) throws EmpleadoException;

    TEmpleadoCompleto buscarByEmail(String email) throws EmpleadoException ;

}
