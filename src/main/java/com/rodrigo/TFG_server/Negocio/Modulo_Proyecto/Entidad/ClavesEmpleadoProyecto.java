package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClavesEmpleadoProyecto implements Serializable {


    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    private static final long serialVersionUID = 0;

//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Id private Long id;

    private Long idEmpleado;

    private Long idProyecto;
}
