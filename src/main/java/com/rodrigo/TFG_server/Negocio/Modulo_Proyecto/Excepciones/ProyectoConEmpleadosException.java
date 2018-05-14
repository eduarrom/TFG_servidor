package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones;

public class ProyectoConEmpleadosException extends ProyectoException {




    public ProyectoConEmpleadosException() {
        super("Proyecto con empleados asignados.");
    }

    public ProyectoConEmpleadosException(String message) {
        super(message);
    }
}
