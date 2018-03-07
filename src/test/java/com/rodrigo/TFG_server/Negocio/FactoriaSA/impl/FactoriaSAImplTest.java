package com.rodrigo.TFG_server.Negocio.FactoriaSA.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoriaSAImplTest {


    @Test
    void getInstance() {
        assertTrue(FactoriaSA.getInstance() instanceof FactoriaSAImpl);


    }

    @Test
    void crearSAEmpleado() {

        assertTrue(FactoriaSA.getInstance().crearSAEmpleado() instanceof SA_EmpleadoImpl);

    }
}