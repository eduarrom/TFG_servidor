package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name ="EmpleadoTCompleto")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpleadoTCompleto extends Empleado {


    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    @NotBlank
    private int antiguedad = 0;

    @NotBlank
    private int sueldoBase = 1200;



    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public EmpleadoTCompleto() {
    }

    public EmpleadoTCompleto(String nombre, String password, Rol rol) {
        super(nombre, password, rol);
    }


    /****************************
     ********** METODOS *********
     ****************************/

    @Override
    public double calcularNominaMes() {
        return sueldoBase+sueldoBase*0.05*antiguedad;
    }


    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/


    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(int sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    @Override
    public String toString() {
        return "EmpleadoTCompleto{" +
                "antiguedad=" + antiguedad +
                ", sueldoBase=" + sueldoBase +
                "} " + super.toString();
    }
}
