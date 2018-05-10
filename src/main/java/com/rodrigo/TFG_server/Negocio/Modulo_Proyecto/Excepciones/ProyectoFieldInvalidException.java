package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones;

import org.hibernate.PropertyValueException;

public class ProyectoFieldInvalidException extends ProyectoException {


    private String message;
    private String entityName;
    private String propertyName;


    public ProyectoFieldInvalidException(String message) {
        super(message);
    }



    public ProyectoFieldInvalidException(PropertyValueException cause) {
        super(cause);

        //Nombre de la entidad
        String[] arr = cause.getEntityName().split("\\.");
        this.entityName = arr[arr.length-1];

        //nombre del atributo de la clase
        arr = cause.getPropertyName().split("\\.");
        this.propertyName = arr[arr.length-1];

        this.message = String.format("Error en el atributo %s.%s", entityName, propertyName);
    }




    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
