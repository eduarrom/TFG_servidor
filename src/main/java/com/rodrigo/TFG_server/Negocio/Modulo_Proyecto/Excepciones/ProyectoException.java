package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones;

public class ProyectoException extends Exception{
    public ProyectoException(String message) {
        super(message);
    }


    public ProyectoException(Throwable cause) {
        super(cause);
    }


    public ProyectoException(String message, Throwable cause) {
        super(message, cause);
    }
}
