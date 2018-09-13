package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id")
public class EmpleadoTParcial extends Empleado implements Serializable {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    @NotNull
    private int horasJornada = 5;

    @NotNull
    private int precioHora = 10;


    private final static Logger log = LoggerFactory.getLogger(EmpleadoTParcial.class);


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public EmpleadoTParcial() {
    }

    public EmpleadoTParcial(String nombre, String password) {
        super(nombre, password);
        this.departamento = new Departamento();
    }

    public EmpleadoTParcial(String nombre, String password, Departamento d) {
        super(nombre, password);
        this.departamento = d;
    }


    public EmpleadoTParcial(String nombre, String password, int horasJornada, int precioHora) {
        super(nombre, password);
        this.horasJornada = horasJornada;
        this.precioHora = precioHora;
    }


    public EmpleadoTParcial(Long id, String nombre, String password, int horasJornada, int precioHora) {
        super(id, nombre, password);
        this.horasJornada = horasJornada;
        this.precioHora = precioHora;
    }

    /**
     * Copia el empleado con:
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
        return horasJornada * precioHora * 22;
    }


    @Override
    public TEmpleado crearTransferSimple() {
        return new TEmpleadoTParcial(id, nombre, email, password,
                departamento.getId(), horasJornada, precioHora);
    }

    @Override
    public TEmpleadoCompleto crearTransferCompleto() {


        //Crear asociacion de proyectos del empleado
        HashMap<Long, TProyecto> tProyectos = new HashMap<>();

        HashMap<Long, TEmpleadoProyecto> tEmpleadosProyectos = new HashMap<>();

        proyectos.stream().forEach((ep) -> {
            tProyectos.put(ep.getProyecto().getId(),ep.getProyecto().crearTransferSimple());

            tEmpleadosProyectos.put(ep.getProyecto().getId(), ep.crearTransferSimple());

        });


        TEmpleadoCompleto tec = new TEmpleadoCompleto(crearTransferSimple(), departamento.crearTransferSimple(), tProyectos, tEmpleadosProyectos);

        System.out.println("tec = [" + tec + "]");

        return tec;
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
        return super.toString() + "EmpleadoTParcial{" +
                "horasJornada=" + horasJornada +
                ", precioHora=" + precioHora +
                "} ";
    }

}
