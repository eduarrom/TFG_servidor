package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TEmpleadoCompleto {


    private TEmpleado empleado;

    private TDepartamento departamento;


    private HashMap<Long, TEmpleadoProyecto> empleadoProyecto;


    private HashMap<Long, TProyecto> proyectos;




    public TEmpleadoCompleto() {}


    public TEmpleadoCompleto(TEmpleado empleado, TDepartamento departamento) {
        this.empleado = empleado;
        this.departamento = departamento;
    }


    public TEmpleadoCompleto(TEmpleado empleado, TDepartamento departamento, HashMap<Long, TProyecto> proyectos, HashMap<Long, TEmpleadoProyecto> empleadoProyecto) {
        this.empleado = empleado;
        this.departamento = departamento;
        this.proyectos = proyectos;
        this.empleadoProyecto = empleadoProyecto;
    }



    public void agregarEmpleadoProyecto(TEmpleadoProyecto ep, TProyecto p){

        empleadoProyecto.put(ep.getProyecto(), ep);
        proyectos.put(p.getId(), p);
    }



    public TProyecto addTEmpeado(TProyecto tp) {
        return proyectos.put(tp.getId(), tp);
    }

    public TProyecto removeTEmpleado(Long id) {
        return proyectos.remove(id);
    }


    public TEmpleadoProyecto addTEmpleadoProyecto(TEmpleadoProyecto tep) {
        return empleadoProyecto.put(tep.getEmpleado(), tep);
    }

    public TEmpleadoProyecto removeTEmpleadoProyecto(Long id) {
        return empleadoProyecto.remove(id);
    }


    public Long getId(){
        return empleado.getId();
    }

    public void setId(Long id){
        empleado.setId(id);
    }

    public String getEmail(){
        return empleado.getEmail();
    }

    public void setEmail(String email){
        empleado.setEmail(email);
    }




    public TEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(TEmpleado empleado) {
        this.empleado = empleado;
    }

    public TDepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(TDepartamento departamento) {
        this.departamento = departamento;
    }


    public Map<Long, TEmpleadoProyecto> getEmpleadoProyecto() {
        return empleadoProyecto;
    }

    public void setEmpleadoProyecto(HashMap<Long, TEmpleadoProyecto> empleadoProyecto) {
        this.empleadoProyecto = empleadoProyecto;
    }


    public Map<Long, TProyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(HashMap<Long, TProyecto> proyectos) {
        this.proyectos = proyectos;
    }


    @Override
    public String toString() {
        return "TEmpleadoCompleto{" +
                "empleado=" + empleado +
                ", departamento=" + departamento +
                ", empleadoProyecto=" + empleadoProyecto +
                ", proyectos=" + proyectos +
                '}';
    }
}
