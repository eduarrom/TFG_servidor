package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"empleado_id", "proyecto_id"})
})
@NamedQueries({
        @NamedQuery(name = "EmpleadoProyecto.buscarEmpleProy",
                query = "from EmpleadoProyecto ep where ep.empleado.id = :idEmple and ep.proyecto.id = :idProy" ),
        @NamedQuery(name = "EmpleadoProyecto.eliminarByID",
                query = "delete from EmpleadoProyecto where id = :id" ),
        @NamedQuery(name = "EmpleadoProyecto_eliminarByEmpleProy",
                query = "delete from EmpleadoProyecto ep where ep.empleado.id = :idEmple and ep.proyecto.id = :idProy" )

})
public class EmpleadoProyecto implements Serializable {

    private final static Logger log = LoggerFactory.getLogger(EmpleadoProyecto.class);
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


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public EmpleadoProyecto() {
    }


    public EmpleadoProyecto(Empleado empleado, Proyecto proyecto, int horas) {
        this.empleado = empleado;
        this.proyecto = proyecto;
        this.horas = horas;
    }

    /**
     * Copia el EmpleadoProyecto con:
     * - Empleado vacio
     *
     * @param ep EmpleadoProyecto
     */
    public EmpleadoProyecto(EmpleadoProyecto ep) {

        this.empleado = (ep.empleado instanceof EmpleadoTParcial) ?
                new EmpleadoTParcial() :
                new EmpleadoTCompleto();
        this.proyecto = new Proyecto(ep.proyecto);
    }





    public TEmpleadoProyecto crearTransferSimple(){
        return new TEmpleadoProyecto(empleado.getId(), proyecto.getId(), horas);
    }





    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ClavesEmpleadoProyecto getId() {
        return id;
    }

    public void setId(ClavesEmpleadoProyecto id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "EmpleadoProyecto{" +
                "id=" + id +
                ", horas=" + horas +
                ", empleado=" + empleado +
                ", proyecto=" + proyecto +
                ", version=" + version +
                '}';
    }


}
