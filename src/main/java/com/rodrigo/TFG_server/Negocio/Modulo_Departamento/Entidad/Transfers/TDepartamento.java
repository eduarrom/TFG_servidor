package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TDepartamento {

    private long id;

    private String nombre;

    private String siglas;

    private double nominaMes;


    public TDepartamento() {
    }


    public TDepartamento(Long id) {
       this.id = id;
    }

    public TDepartamento(String nombre) {
        this.nombre = nombre;
        this.siglas = String.valueOf(
                Arrays.stream(nombre.split(" "))
                        .reduce("", (acum, pal) -> acum + String.valueOf(pal.charAt(0))));
    }


    public TDepartamento(long id, String nombre, String siglas) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
    }


    public TDepartamento(long id, String nombre, String siglas, double nominaMes) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
        this.nominaMes = nominaMes;
    }








    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }


    public double getNominaMes() {
        return nominaMes;
    }

    public void setNominaMes(double nominaMes) {
        this.nominaMes = nominaMes;
    }

    @Override
    public String toString() {
        return "TDepartamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", nominaMes=" + nominaMes +
                '}';
    }
}
