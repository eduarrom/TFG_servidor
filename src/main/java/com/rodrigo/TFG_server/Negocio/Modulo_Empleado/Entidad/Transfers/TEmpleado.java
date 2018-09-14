package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@XmlSeeAlso({TEmpleadoTParcial.class, TEmpleadoTCompleto.class})
public class TEmpleado {

    protected Long id;

    protected String nombre;


    protected String email;

    protected String password;

    protected Long departamento;


    public TEmpleado() {
    }


    public TEmpleado(String nombre, String password,  Long departamento) {
        this.nombre = nombre;
        this.email = nombre.toLowerCase().concat("@gmail.com");

        this.password = password;
        this.departamento = departamento;
    }

    public TEmpleado(String nombre, String email, String password, Long departamento) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.departamento = departamento;
    }


    public TEmpleado(Long id, String nombre, String email, String password, Long departamento) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.departamento = departamento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Long departamento) {
        this.departamento = departamento;
    }


    @Override
    public String toString() {
        return "TEmpleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", departamento=" + departamento +
                '}';
    }
}
