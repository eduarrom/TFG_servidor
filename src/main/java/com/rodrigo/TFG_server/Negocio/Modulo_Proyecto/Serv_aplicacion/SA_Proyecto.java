package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Proyecto {

    Proyecto crearProyecto(Proyecto proyectoNuevo) throws ProyectoException;

    EmpleadoProyecto añadirEmpleadoAProyecto(Empleado e, Proyecto p, int horas);

    Proyecto buscarByID(Long id);

    boolean eliminarProyecto(Proyecto proyectoEliminar);

    List<Proyecto> listarProyectos();

}
