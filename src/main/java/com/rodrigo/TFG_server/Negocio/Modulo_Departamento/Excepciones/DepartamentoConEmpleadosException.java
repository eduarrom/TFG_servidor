package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones;

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
