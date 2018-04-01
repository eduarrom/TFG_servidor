package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeptSencillo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeptSencillo {


    private int id;

    private String nombre;

    public DeptSencillo() {
    }

    public DeptSencillo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
