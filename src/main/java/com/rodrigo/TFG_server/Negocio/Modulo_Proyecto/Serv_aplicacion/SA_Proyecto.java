package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Proyecto {

    TProyecto crearProyecto(TProyecto proyectoNuevo) throws ProyectoException;

    TEmpleadoProyecto añadirEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas);

    TProyectoCompleto buscarByID(Long id);

    boolean eliminarProyecto(TProyecto proyectoEliminar);

    List<TProyecto> listarProyectos();

}
