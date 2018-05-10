package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TProyectoCompleto {


    private TProyecto proyecto;


    private HashMap<Long, TEmpleadoProyecto> empleadoProyecto = new HashMap<>();

    private HashMap<Long, TEmpleado> empleados = new HashMap<>();




    public TProyectoCompleto() {
    }


    public TProyectoCompleto(TProyecto proyecto, HashMap<Long, TEmpleado> empleados, HashMap<Long, TEmpleadoProyecto> empleadoProyecto) {
        this.proyecto = proyecto;
        this.empleadoProyecto = empleadoProyecto;
        this.empleados = empleados;
    }

    public TProyectoCompleto(TProyecto tProyecto) {
        this.proyecto = tProyecto;
    }


    public void agregarEmpleadoProyecto(TEmpleadoProyecto ep, TEmpleado e){

        empleadoProyecto.put(ep.getEmpleado(), ep);

        empleados.put(e.getId(), e);

    }



    public TEmpleado addTEmpeado(TEmpleado te) {
        return empleados.put(te.getId(), te);
    }

    public TEmpleado removeTEmpleado(Long id) {
        return empleados.remove(id);
    }


    public TEmpleadoProyecto addTEmpleadoProyecto(TEmpleadoProyecto tep) {
        return empleadoProyecto.put(tep.getEmpleado(), tep);
    }

    public TEmpleadoProyecto removeTEmpleadoProyecto(Long id) {
        return empleadoProyecto.remove(id);
    }




    public Long getId(){
        return proyecto.getId();
    }

    public String getNombre(){
        return proyecto.getNombre();
    }




    public TProyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(TProyecto proyecto) {
        this.proyecto = proyecto;
    }

    public HashMap<Long, TEmpleadoProyecto> getHoras() {
        return empleadoProyecto;
    }

    public void setHoras(HashMap<Long, TEmpleadoProyecto> horas) {
        this.empleadoProyecto = horas;
    }

    public HashMap<Long, TEmpleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(HashMap<Long, TEmpleado> empleados) {
        this.empleados = empleados;
    }


    @Override
    public String toString() {
        return "TProyectoCompleto{" +
                "proyecto=" + proyecto +
                ", empleadoProyecto=" + empleadoProyecto +
                ", empleados=" + empleados +
                '}';
    }
}
