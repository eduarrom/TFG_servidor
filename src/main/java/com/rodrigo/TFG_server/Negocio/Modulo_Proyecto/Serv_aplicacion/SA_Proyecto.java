package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Proyecto {

    TProyecto crearProyecto(TProyecto proyectoNuevo) throws ProyectoYaExistenteException, ProyectoFieldInvalidException, ProyectoException;

    TEmpleadoProyecto agregarEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas) throws EmpleadoException, ProyectoException;

    boolean eliminarEmpleadoAProyecto(Long idEmple, Long idProy) throws ProyectoException, EmpleadoException;

    TProyectoCompleto buscarByNombre(String nombre) throws ProyectoFieldInvalidException, ProyectoException;

    TProyectoCompleto buscarByID(Long id) throws ProyectoFieldInvalidException, ProyectoException;

    boolean eliminarProyecto(Long id) throws ProyectoConEmpleadosException, ProyectoFieldInvalidException, ProyectoException;

    List<TProyecto> listarProyectos();

}
