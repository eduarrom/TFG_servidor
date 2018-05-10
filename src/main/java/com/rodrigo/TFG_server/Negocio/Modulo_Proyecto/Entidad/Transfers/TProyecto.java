package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TProyecto {


    private Long id;

    private String nombre;

    private String descripcion;


    private Date fechaInicio;


    private Date fechaFin;


    //private Collection<EmpleadoProyecto> empleados;


    public TProyecto() {
    }


    public TProyecto(Long id, String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;

        System.out.println("fechaInicio = [" + fechaInicio + "]");
        System.out.println("fechaFin = [" + fechaFin + "]");


//        this.fechaInicio = new Date (fechaInicio.getTime());
//        this.fechaFin = new Date (fechaFin.getTime());

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

    }


    public TProyecto(String nombre) {
        this.nombre = nombre;
        this.descripcion = "Descripción del proyecto " + this.nombre;

//        Date aux = new Date();
//        System.out.println("aux.getDay() = [" + aux.getDay() + "]");
//        System.out.println("aux.getDate() = [" + aux.getDate() + "]");

//        fechaInicio = new Date(aux.getYear(), aux.getMonth(), aux.getDate());
        fechaInicio = new Date();


        try {
            this.fechaFin = new SimpleDateFormat("dd-MM-yyyy HH").parse("31-12-2018 1");
        } catch (ParseException e) {
            e.printStackTrace();
            this.fechaFin = new Date();
        }

    }


    public TProyecto(Long id, String nombre, long version) {
        this.id = id;
        this.nombre = nombre;
    }

    public TProyecto(String nombre, String descripcion, Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
    }


    public TProyecto(String nombre, String descripcion, Date fechaInicio,
                     Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


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


    @Override
    public String toString() {
        return "TProyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
//                ", fechaInicio=" + ((fechaInicio != null) ? (fechaInicio.getDate() + "-" + fechaInicio.getMonth() + "-" + fechaInicio.getYear()) : "null") +
                ", fechaInicio=" + "[" + fechaInicio + "]" +
//                ", fechaFin=" + ((fechaInicio != null) ? (fechaFin.getDate() + "-" + fechaFin.getMonth() + "-" + fechaFin.getYear()) : "null") +
                ", fechaFin=" + "[" + fechaFin + "]" +
                '}';


    }
}
