package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import javax.persistence.Entity;

@Entity
public class EmpleadoTCompleto extends Empleado {


    public EmpleadoTCompleto(String nombre, String password, Rol rol) {
        super(nombre, password, rol);
    }

    public EmpleadoTCompleto() {
    }
}
