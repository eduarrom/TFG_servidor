package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Expeciones;

public class DepartamentoException extends Exception{
    public DepartamentoException(String message) {
        super(message);
    }


    public DepartamentoException(Throwable cause) {
        super(cause);
    }


    public DepartamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
