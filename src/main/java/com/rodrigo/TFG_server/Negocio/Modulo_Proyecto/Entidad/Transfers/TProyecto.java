package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class TProyecto {


    private Long id;

    private String nombre;

    private String descripcion;


    private Date fechaInicio;


    private Date fechaFin;


    public TProyecto() {
    }


    public TProyecto(Long id, String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;

        System.out.println("fechaInicio = [" + fechaInicio + "]");
        System.out.println("fechaFin = [" + fechaFin + "]");



        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

    }


    public TProyecto(String nombre) {
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
                ", fechaInicio=" + "[" + fechaInicio + "]" +
                ", fechaFin=" + "[" + fechaFin + "]" +
                '}';


    }
}
