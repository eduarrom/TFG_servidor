package com.rodrigo.TFG_server.Negocio.FactoriaSA.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl.SA_DepartamentoImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl.SA_ProyectoImpl;

public class FactoriaSAImpl extends FactoriaSA {


    @Override
    public SA_Empleado crearSAEmpleado(){
        return new SA_EmpleadoImpl();
    }

    @Override
    public SA_Departamento crearSADepartamento() {
        return new SA_DepartamentoImpl();
    }

    @Override
    public SA_Proyecto crearSAProyecto() {
        return new SA_ProyectoImpl();
    }


}
