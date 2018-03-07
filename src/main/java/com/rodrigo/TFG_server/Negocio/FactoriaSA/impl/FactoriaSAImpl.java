package com.rodrigo.TFG_server.Negocio.FactoriaSA.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;

public class FactoriaSAImpl extends FactoriaSA {
    @Override
    public SA_Empleado crearSAEmpleado(){
        return new SA_EmpleadoImpl();
    }
}
