package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class TEmpleadoTCompleto extends TEmpleado {


    private int antiguedad = 0;

    private int sueldoBase = 1200;


    public TEmpleadoTCompleto() {
    }


    public TEmpleadoTCompleto(String nombre,String password, Long departamento) {
        super(nombre, password, departamento);
    }

    public TEmpleadoTCompleto(String nombre, String email, String password,  Long departamento) {
        super(nombre, email, password, departamento);
    }

    public TEmpleadoTCompleto(Long id, String nombre, String email, String password,  Long departamento) {
        super(id, nombre, email, password,departamento);
    }


    public TEmpleadoTCompleto(Long id, String nombre, String email, String password,  Long departamento, int antiguedad, int sueldoBase) {
        super(id, nombre, email, password,  departamento);
        this.antiguedad = antiguedad;
        this.sueldoBase = sueldoBase;
    }

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
        return  super.toString() + " TEmpleadoTCompleto{" +
                "antiguedad=" + antiguedad +
                ", sueldoBase=" + sueldoBase +
                "} ";
    }
}
