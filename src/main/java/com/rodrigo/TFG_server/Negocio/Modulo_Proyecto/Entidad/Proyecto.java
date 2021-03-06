package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;


import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Proyecto.buscarPorNombre", query = "from Proyecto e where e.nombre = :nombre"),
        @NamedQuery(name = "Proyecto.listar", query = "FROM Proyecto"),
        @NamedQuery(name = "Proyecto.eliminarByID", query = "delete from Proyecto where id = :id")
})
public class Proyecto implements Serializable {


    private final static Logger log = LoggerFactory.getLogger(Proyecto.class);

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column
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
    private Date fechaInicio;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date fechaFin;


    /****************************
     ******   PROYECTO   ******
     ****************************/

    @OneToMany(mappedBy = "proyecto", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<EmpleadoProyecto> empleados;


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


        fechaInicio = new Date();

        try {
            this.fechaFin = new SimpleDateFormat("dd-MM-yyyy HH").parse("31-12-2018 1");
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

    public Proyecto(Long id, @NotBlank String nombre, @NotBlank String descripcion, Date fechaInicio,
                    @NotNull Date fechaFin) {
        this.id = id;
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


    public Proyecto(TProyecto tp) {
        this.id = tp.getId();
        this.nombre = tp.getNombre();
        this.descripcion = tp.getDescripcion();
        this.fechaInicio = tp.getFechaInicio();
        this.fechaFin = tp.getFechaFin();
    }



    /****************************
     ********** METODOS *********
     ****************************/

    public TProyecto crearTransferSimple(){
        return new TProyecto(id, nombre, descripcion, fechaInicio, fechaFin);
    }


    public TProyectoCompleto crearTransferCompleto() {


        HashMap<Long, TEmpleado> tEmpleados = new HashMap<>();


        HashMap<Long, TEmpleadoProyecto> tEmpleadosProyectos = new HashMap<>();


        empleados.stream().forEach((ep) -> {

            tEmpleados.put(ep.getEmpleado().getId(),
                    ep.getEmpleado().crearTransferSimple());

            tEmpleadosProyectos.put(ep.getEmpleado().getId(),
                    ep.crearTransferSimple());

        });


        TProyectoCompleto tpc = new TProyectoCompleto(crearTransferSimple(), tEmpleados, tEmpleadosProyectos);

        System.out.println("tpc = [" + tpc + "]");

        return tpc;
    }




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

    /**
     * Agrega un empleadoProyeto al proyecto this y al empleado pasado por param
     *
     * @param ep EmpleadoProyecto
     * @return true si es correcto
     */
    public boolean agregarEmpleado(EmpleadoProyecto ep) {
        boolean ok = false;
        //EmpleadoProyecto ep = new EmpleadoProyecto(ep.getEmpleado(), ep.getProyecto(), ep.getHoras());

        log.info("Agregando empleado '" + ep.getEmpleado().getNombre() + "' a proyecto '" + ep.getProyecto().nombre + "'");
        if (ep.getEmpleado().getProyectos().add(ep)) {
            if (this.empleados.add(ep)) {
                ok = true;
                log.info("Agregado correctamente");
            } else {
                ep.getEmpleado().getProyectos().remove(ep);
            }

        }
        return ok;
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

    public Collection<EmpleadoProyecto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Collection<EmpleadoProyecto> empleados) {
        this.empleados = empleados;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
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
                ", empleadosSize=" + ((empleados == null) ? "null" : empleados.size()) +
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


}

