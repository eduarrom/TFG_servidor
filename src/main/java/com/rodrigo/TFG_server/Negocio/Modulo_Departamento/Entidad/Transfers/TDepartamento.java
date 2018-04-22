package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class TDepartamento {

    private long id;

    private String nombre;

    private String siglas;


    public TDepartamento() {
    }

    public TDepartamento(long id, String nombre, String siglas) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
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


    @Override
    public String toString() {
        return "TDepartamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                '}';
    }
}
