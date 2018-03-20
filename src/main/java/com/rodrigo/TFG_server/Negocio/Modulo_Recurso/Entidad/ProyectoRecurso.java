package com.rodrigo.TFG_server.Negocio.Modulo_Recurso.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"proyecto_id", "recurso_id"})
})
public class ProyectoRecurso implements Serializable{


    private static final long serialVersionUID = 0;

    @EmbeddedId
    private ClavesProyectoRecurso id = new ClavesProyectoRecurso();


    @ManyToOne
    @MapsId("id_proyecto")
    private Proyecto proyecto;

    @ManyToOne
    @MapsId("id_recurso")
    private Recurso recurso;



    @Version
    long version;

}
