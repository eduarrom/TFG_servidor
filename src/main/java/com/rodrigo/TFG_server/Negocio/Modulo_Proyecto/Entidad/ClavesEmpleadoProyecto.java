package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@Embeddable
public class ClavesEmpleadoProyecto implements Serializable {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    private static final long serialVersionUID = 0;


    private Long idEmpleado;

    private Long idProyecto;





    public ClavesEmpleadoProyecto() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }


    @Override
    public String toString() {
        return "Clave{" +
                "e_id=" + idEmpleado +
                ", p_id=" + idProyecto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClavesEmpleadoProyecto)) return false;
        ClavesEmpleadoProyecto that = (ClavesEmpleadoProyecto) o;
        return Objects.equals(getIdEmpleado(), that.getIdEmpleado()) &&
                Objects.equals(getIdProyecto(), that.getIdProyecto());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdEmpleado(), getIdProyecto());
    }
}
