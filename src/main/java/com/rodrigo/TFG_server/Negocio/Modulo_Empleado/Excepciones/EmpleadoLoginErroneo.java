package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class EmpleadoLoginErroneo extends EmpleadoException {
    public EmpleadoLoginErroneo(String message) {
        super(message);
    }
}
