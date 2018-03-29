package com.rodrigo.TFG_server.Negocio.FactoriaSA;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.impl.FactoriaSAImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;

public abstract class FactoriaSA {

    private static FactoriaSA ourInstance = new FactoriaSAImpl();

    public static FactoriaSA getInstance() {
        return ourInstance;
    }


    public abstract SA_Empleado crearSAEmpleado();

    public abstract SA_Departamento crearSADepartamento();



}
