package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;


import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id")
public class EmpleadoTCompleto extends Empleado implements Serializable {


    /****************************
     ********* ATRIBUTOS ********
     ****************************/

    @NotNull
    private int antiguedad = 0;

    @NotNull
    private int sueldoBase = 1200;



    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public EmpleadoTCompleto() {
    }

    public EmpleadoTCompleto(String nombre, String password) {
        super(nombre, password);
        this.departamento = new Departamento();
    }
    public EmpleadoTCompleto(String nombre, String password, Departamento d) {
        super(nombre, password);
        this.departamento = d;
    }

    public EmpleadoTCompleto(String nombre, String password, int antiguedad, int sueldoBase) {
        super(nombre, password);
        this.antiguedad = antiguedad;
        this.sueldoBase = sueldoBase;
    }

    public EmpleadoTCompleto(Long id, String nombre, String password, int antiguedad, int sueldoBase) {
        super(id, nombre, password);
        this.antiguedad = antiguedad;
        this.sueldoBase = sueldoBase;
    }



    /** Copia el empleado con:
     * - Departamento vacio
     * - Lista de proyectos vacia
     *
     * @param e EmpeladoTCompleto
     */
    public EmpleadoTCompleto(EmpleadoTCompleto e) {
        super(e);
        antiguedad = e.antiguedad;
        sueldoBase = e.sueldoBase;
    }


    /****************************
     ********** METODOS *********
     ****************************/

    @Override
    public double calcularNominaMes() {
        return sueldoBase+sueldoBase*0.05*antiguedad;
    }


    @Override
    public TEmpleado crearTransferSimple() {
        return new TEmpleadoTCompleto(id, nombre, email, password,
                departamento.getId(), antiguedad, sueldoBase);
    }


    @Override
    public TEmpleadoCompleto crearTransferCompleto() {

        //Crear asociacion de proyectos del empleado
        HashMap<Long, TProyecto> tProyectos = new HashMap<>();

        HashMap<Long, TEmpleadoProyecto> tEmpleadosProyectos = new HashMap<>();

        proyectos.stream().forEach((ep)->{
            tProyectos.put(ep.getProyecto().getId(), ep.getProyecto().crearTransferSimple());

            tEmpleadosProyectos.put(ep.getProyecto().getId(), ep.crearTransferSimple());

        });


        TEmpleadoCompleto tec = new TEmpleadoCompleto(crearTransferSimple(), departamento.crearTransferSimple(), tProyectos, tEmpleadosProyectos);

        System.out.println("tec = [" + tec + "]");

        return tec;
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
        return super.toString() + "EmpleadoTCompleto{" +
                "antiguedad=" + antiguedad +
                ", sueldoBase=" + sueldoBase +
                "} ";
    }

}
