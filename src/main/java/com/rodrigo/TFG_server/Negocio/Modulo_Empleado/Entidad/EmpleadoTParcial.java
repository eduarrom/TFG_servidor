package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id")
//@XmlRootElement/*(name = "EmpleadoTParcial")*/
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlDiscriminatorValue("EmpleadoTParcial")
//@XmlType/*(name = "EmpleadoTParcial")*/
public class EmpleadoTParcial extends Empleado implements Serializable {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    @NotBlank
//    @XmlAttribute
    private int horasJornada = 5;

    @NotBlank
//    @XmlAttribute
    private int precioHora = 10;


    private final static Logger log = LoggerFactory.getLogger(EmpleadoTParcial.class);



    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public EmpleadoTParcial() {
    }

    public EmpleadoTParcial(String nombre, String password, Rol rol) {
        super(nombre, password, rol);
        this.departamento = new Departamento();
    }

    public EmpleadoTParcial(String nombre, String password, Rol rol, Departamento d) {
        super(nombre, password, rol);
        this.departamento = d;
    }

    /** Copia el empleado con:
     * - Departamento vacio
     * - Lista de proyectos vacia
     *
     * @param e EmpleadoTParcial
     */
    public EmpleadoTParcial(EmpleadoTParcial e) {
        super(e);
        this.horasJornada = e.horasJornada;
        this.precioHora = e.precioHora;
    }


    /****************************
     ********** METODOS *********
     ****************************/

    @Override
    public double calcularNominaMes() {
        return horasJornada*precioHora*22;
    }


    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/

    public int getHorasJornada() {
        return horasJornada;
    }

    public void setHorasJornada(int horasJornada) {
        this.horasJornada = horasJornada;
    }


    public int getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(int precioHora) {
        this.precioHora = precioHora;
    }


    @Override
    public String toString() {
        return "EmpleadoTParcial{" +
                "horasJornada=" + horasJornada +
                ", precioHora=" + precioHora +
                "} " + super.toString();
    }

    /*@Override
    public Object onCycleDetected(Context cycleRecoveryContext) {
        // Context provides access to the Marshaller being used:
        //System.out.println("JAXB Marshaller is: " + cycleRecoveryContext.getMarshaller());

        System.out.println(" -------- EmpleadoTParcial.onCycleDetected -------- ");


        EmpleadoTParcial e = new EmpleadoTParcial(this);
        //e.getDepartamento().setEmpleados(new ArrayList<Empleado>());

        return e;
    }*/
}
