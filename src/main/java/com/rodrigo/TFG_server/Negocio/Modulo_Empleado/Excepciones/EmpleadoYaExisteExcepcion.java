package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class EmpleadoYaExisteExcepcion extends EmpleadoException {
    public EmpleadoYaExisteExcepcion(String message) {
        super(message);
    }
}
