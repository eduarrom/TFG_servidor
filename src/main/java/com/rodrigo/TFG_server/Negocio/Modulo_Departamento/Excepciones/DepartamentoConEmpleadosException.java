package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones;
/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class DepartamentoConEmpleadosException extends DepartamentoException {
    public DepartamentoConEmpleadosException(String message) {
        super(message);
    }

    public DepartamentoConEmpleadosException(Throwable cause) {
        super(cause);
    }

    public DepartamentoConEmpleadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
