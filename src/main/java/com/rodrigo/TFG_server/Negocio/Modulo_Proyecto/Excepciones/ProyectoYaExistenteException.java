package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class ProyectoYaExistenteException extends ProyectoException {
    public ProyectoYaExistenteException(String message) {
        super(message);
    }

}
