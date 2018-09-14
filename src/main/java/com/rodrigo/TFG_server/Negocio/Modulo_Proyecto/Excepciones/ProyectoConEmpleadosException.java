package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class ProyectoConEmpleadosException extends ProyectoException {




    public ProyectoConEmpleadosException() {
        super("Proyecto con empleados asignados.");
    }

    public ProyectoConEmpleadosException(String message) {
        super(message);
    }
}
