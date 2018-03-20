package com.rodrigo.TFG_server.Negocio.Modulo_Recurso.Entidad;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClavesProyectoRecurso implements Serializable {


    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    private static final long serialVersionUID = 0;

//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Id private Long id;


    private Long id_proyecto;

    private Long id_recurso;
}
