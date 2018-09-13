package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Empleado {

    TEmpleadoCompleto crearEmpleado(TEmpleado empleadoNuevo) throws EmpleadoYaExisteExcepcion, EmpleadoFieldInvalidException, EmpleadoException;

    TEmpleadoCompleto buscarByID(Long id) throws EmpleadoException;

    boolean eliminarEmpleado(Long id) throws EmpleadoFieldInvalidException, EmpleadoException;

    List<TEmpleado> listarEmpleados();


    TEmpleadoCompleto buscarByEmail(String email) throws EmpleadoFieldInvalidException, EmpleadoException ;

}
