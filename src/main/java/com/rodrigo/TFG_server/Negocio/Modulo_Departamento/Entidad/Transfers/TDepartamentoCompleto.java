package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;

import java.util.Map;

public class TDepartamentoCompleto extends TDepartamento {


    private TDepartamento departamento;

    private Map<Long, TEmpleado> empleados;



    public TDepartamentoCompleto() {}


    public TDepartamentoCompleto(TDepartamento departamento, Map<Long, TEmpleado> empleados) {
        this.departamento = departamento;
        this.empleados = empleados;
    }



    public TEmpleado addTEmpeado(TEmpleado te){
       return empleados.put(te.getId(), te);
    }

    public TEmpleado removeTEmpleado(Long id){
        return empleados.remove(id);
    }


    public Long getId(){
        return departamento.getId();
    }



    public TDepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(TDepartamento departamento) {
        this.departamento = departamento;
    }

    public Map<Long, TEmpleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<Long, TEmpleado> empleados) {
        this.empleados = empleados;
    }





    @Override
    public String toString() {
        return "TDepartamentoCompleto{" +
                "departamento=" + departamento +
                ", empleados=" + empleados +
                '}';
    }
}
