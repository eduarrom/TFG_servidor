package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones;

import org.hibernate.PropertyValueException;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class DepartamentoFieldInvalidException extends DepartamentoException {



    private String message;
    private String entityName;
    private String propertyName;



    public DepartamentoFieldInvalidException(String message) {
        super(message);
        this.message = message;
    }


    public DepartamentoFieldInvalidException(PropertyValueException cause) {
        super(cause);

        //Nombre de la entidad
        String[] arr = cause.getEntityName().split("\\.");
        this.entityName = arr[arr.length-1];

        //nombre del atributo de la clase
        arr = cause.getPropertyName().split("\\.");
        this.propertyName = arr[arr.length-1];

        this.message = String.format("Error en el atributo %s.%s", entityName, propertyName);
    }



    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
