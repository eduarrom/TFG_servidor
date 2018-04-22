package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;

public class TEmpleadoTCompleto extends TEmpleado {


    private int antiguedad = 0;

    private int sueldoBase = 1200;


    public TEmpleadoTCompleto() {
    }


    public TEmpleadoTCompleto(String nombre,String password, Rol rol, Long departamento) {
        super(nombre, password, rol, departamento);
    }

    public TEmpleadoTCompleto(String nombre, String email, String password, Rol rol, Long departamento) {
        super(nombre, email, password, rol, departamento);
    }

    public TEmpleadoTCompleto(Long id, String nombre, String email, String password, Rol rol, Long departamento) {
        super(id, nombre, email, password, rol, departamento);
    }


    public TEmpleadoTCompleto(Long id, String nombre, String email, String password, Rol rol, Long departamento, int antiguedad, int sueldoBase) {
        super(id, nombre, email, password, rol, departamento);
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
        return "TEmpleadoTCompleto{" +
                "antiguedad=" + antiguedad +
                ", sueldoBase=" + sueldoBase +
                "} " + super.toString();
    }
}
