package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoNullException;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Empleado {

    Empleado crearEmpleado(Empleado empleadoNuevo) throws EmpleadoException;

    Empleado buscarByID(Long id);

    boolean eliminarEmpleado(Empleado empleadoEliminar);

    List<Empleado> listarEmpleados();

    String saludar(String nombre);

    Boolean loginEmpleado(String email, String pass) throws EmpleadoException;

    Empleado buscarByEmail(String email) throws EmpleadoException, EmpleadoFieldNullException ;

}
