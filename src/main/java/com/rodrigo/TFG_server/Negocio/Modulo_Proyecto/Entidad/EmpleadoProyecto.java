package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@IdClass(ClavesEventoServicio.class)
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"empleado_id", "proyecto_id"})
})
public class EmpleadoProyecto implements Serializable{


    private static final long serialVersionUID = 0;

    @EmbeddedId
    private ClavesEmpleadoProyecto id = new ClavesEmpleadoProyecto();

    @ManyToOne
    @MapsId("idEmpleado")
    private Empleado empleado;

    @ManyToOne
    @MapsId("idProyecto")
    private Proyecto proyecto;


    private int horas;


    @Version
    long version;

}
