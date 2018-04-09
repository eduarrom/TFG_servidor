package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;


import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.sun.xml.bind.CycleRecoverable;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Proyecto.listar", query = "FROM Empleado"),
})
@XmlRootElement(name = "Proyecto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Proyecto implements Serializable/*, CycleRecoverable*/ {


    private final static Logger log = LoggerFactory.getLogger(Proyecto.class);

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column/*(name = "id_proyecto")*/
    @Id
    protected Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String descripcion;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicio = new Date();


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date fechaFin;





//    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER)
//    private List<EmpleadoProyecto> empleados = null;
    @OneToMany(mappedBy = "proyecto", fetch = FetchType.EAGER/*, cascade = CascadeType.ALL*/)
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    //@XmlInverseReference(mappedBy = "proyecto")
    private List<EmpleadoProyecto> empleados = new ArrayList<>();





    @Version
    protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public Proyecto() {
    }

    public Proyecto(String nombre) {
        this.nombre = nombre;
        this.descripcion = "Descripción del proyecto " + this.nombre;

        try {
            this.fechaFin = new SimpleDateFormat("dd-MM-yyyy").parse("08-09-2018");
        } catch (ParseException e) {
            e.printStackTrace();
            this.fechaFin = new Date();
        }

    }


    public Proyecto(Long id, String nombre, long version) {
        this.id = id;
        this.nombre = nombre;
        this.version = version;
    }

    public Proyecto(@NotBlank String nombre, @NotBlank String descripcion, @NotNull Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
    }


    public Proyecto(@NotBlank String nombre, @NotBlank String descripcion, Date fechaInicio,
                    @NotNull Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Proyecto(@NotBlank String nombre, @NotBlank String descripcion, Date fechaInicio,
                    @NotNull Date fechaFin, List<EmpleadoProyecto> empleados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.empleados = empleados;
    }

    /**
     * Copia el proyecto con:
     * - El listado de empleados vacio
     *
     * @param p proyecto
     */
    public Proyecto(Proyecto p) {
        this.nombre = p.nombre;
        this.descripcion = p.descripcion;
        this.fechaInicio = p.fechaInicio;
        this.fechaFin = p.fechaFin;
    }

    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<EmpleadoProyecto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoProyecto> empleados) {
        this.empleados = empleados;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    /****************************
     ********** METODOS *********
     ****************************/

    public boolean agregarEmpleado(Empleado e, int horas) {
        boolean ok = false;
        EmpleadoProyecto ep = new EmpleadoProyecto(e, this, horas);
        log.info("Agregando empleado '" + e.getNombre() + "' a proyecto '" + this.nombre + "'");
        if (e.getProyectos().add(ep)) {
            if (this.empleados.add(ep)) {
                ok = true;
                log.info("Agregado correctamente");
            } else {
                e.getProyectos().remove(ep);
            }

        }
        return ok;
    }

    public boolean agregarEmpleado(EmpleadoProyecto ep) {
        boolean ok = false;
        //EmpleadoProyecto ep = new EmpleadoProyecto(ep.getEmpleado(), ep.getProyecto(), ep.getHoras());

        log.info("Agregando empleado '" + ep.getEmpleado().getNombre() + "' a proyecto '" + ep.getProyecto().nombre + "'");
        if (ep.getEmpleado().getProyectos().add(ep)) {
            if (ep.getProyecto().empleados.add(ep)) {
                ok = true;
                log.info("Agregado correctamente");
            } else {
                ep.getEmpleado().getProyectos().remove(ep);
            }

        }
        return ok;
    }


    /****************************
     ****** OTHER METHODS *******
     ****************************/


    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", empleadosSize=" + ((empleados==null)?"null":empleados.size()) +
                ", version=" + version +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto) o;
        return getVersion() == proyecto.getVersion() &&
                Objects.equals(getId(), proyecto.getId()) &&
                Objects.equals(getNombre(), proyecto.getNombre()) &&
                Objects.equals(getDescripcion(), proyecto.getDescripcion()) &&
                Objects.equals(getFechaInicio(), proyecto.getFechaInicio()) &&
                Objects.equals(getFechaFin(), proyecto.getFechaFin()) &&
                Objects.equals(getEmpleados(), proyecto.getEmpleados());
    }


    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(getId(), proyecto.getId()) &&
                Objects.equals(getNombre(), proyecto.getNombre()) &&
                Objects.equals(getDescripcion(), proyecto.getDescripcion()) &&
                Objects.equals(getFechaInicio(), proyecto.getFechaInicio()) &&
                Objects.equals(getFechaFin(), proyecto.getFechaFin()) &&
                Objects.equals(getEmpleados(), proyecto.getEmpleados());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getDescripcion(),
                getFechaInicio(), getFechaFin(), getEmpleados(), getVersion());
    }

/*
    @Override
    public Object onCycleDetected(Context context) {
        // Context provides access to the Marshaller being used:
        //log.info("JAXB Marshaller is: " + cycleRecoveryContext.getMarshaller());

        log.info("Proyecto.onCycleDetected");
        Proyecto p = new Proyecto(this);
        return null;
    }
*/

}

