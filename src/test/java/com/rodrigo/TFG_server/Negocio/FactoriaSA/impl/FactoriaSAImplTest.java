package com.rodrigo.TFG_server.Negocio.FactoriaSA.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
class FactoriaSAImplTest {


    @Test
    void getInstance() {
        assertTrue(FactoriaSA.getInstance() instanceof FactoriaSAImpl);


    }

    @Test
    void crearSAEmpleado() {

        assertTrue(FactoriaSA.getInstance().crearSA_Empleado() instanceof SA_EmpleadoImpl);

    }
}