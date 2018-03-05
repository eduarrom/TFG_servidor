package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones;

public class EmpleadoException extends Exception{
    public EmpleadoException(String message) {
        super(message);
    }


    public EmpleadoException(Throwable cause) {
        super(cause);
    }


    public EmpleadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
