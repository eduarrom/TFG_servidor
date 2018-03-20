package com.rodrigo.TFG_server.Negocio.Modulo_Recurso.Expeciones;

public class RecursoException extends Exception{
    public RecursoException(String message) {
        super(message);
    }


    public RecursoException(Throwable cause) {
        super(cause);
    }


    public RecursoException(String message, Throwable cause) {
        super(message, cause);
    }
}
