package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.sun.xml.bind.CycleRecoverable;
//import org.eclipse.persistence.oxm.annotations.XmlCustomizer;
//import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
//@IdClass(ClavesEmpleadoProyecto.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"empleado_id", "proyecto_id"})
})
@XmlRootElement/*(name = "EmpleadoProyecto")*/
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlCustomizer(EmpleadoProyectoCustomizer.class)
public class EmpleadoProyecto implements Serializable/*, CycleRecoverable*/ {

    private final static Logger log = LoggerFactory.getLogger(EmpleadoProyecto.class);
    private static final long serialVersionUID = 0;

    @EmbeddedId
    private ClavesEmpleadoProyecto id = new ClavesEmpleadoProyecto();

    @ManyToOne
    @MapsId("idEmpleado")
//    @XmlInverseReference(mappedBy = "proyectos")
    private Empleado empleado;

    @ManyToOne
    @MapsId("idProyecto")
//    @XmlInverseReference(mappedBy = "empleados")
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
//        new EmpleadoTParcial((EmpleadoTParcial) ep.empleado):
//                new EmpleadoTCompleto((EmpleadoTCompleto) ep.empleado);

//        this.proyecto = new Proyecto(ep.proyecto);
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
                ", empleado=" + empleado +
                ", proyecto=" + proyecto +
                ", horas=" + horas +
                ", version=" + version +
                '}';
    }





    /*@Override
    public Object onCycleDetected(Context context) {

        log.info("EmpleadoProyecto.onCycleDetected");

        EmpleadoProyecto ep = new EmpleadoProyecto(this);
        return null;
    }*/
}
