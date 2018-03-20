package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import javax.persistence.Entity;

@Entity
public class EmpleadoTParcial extends Empleado {

    public EmpleadoTParcial(String nombre, String password, Rol rol) {
        super(nombre, password, rol);
    }

    public EmpleadoTParcial() {
    }
}
