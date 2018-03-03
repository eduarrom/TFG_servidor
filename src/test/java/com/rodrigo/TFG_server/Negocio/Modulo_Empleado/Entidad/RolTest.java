package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    private static Rol rolUser;
    private static Rol rolAdmin;

    private final static Logger log = LoggerFactory.getLogger(RolTest.class);


    @BeforeAll
    static void beforeAll(){
        rolUser = Rol.EMPLEADO;
        rolAdmin = Rol.ADMIN;

    }

    @Test
    void toStringTest() {

        log.info("rolUser.toString() = '" + rolUser.toString() + "'");
        assertEquals(rolUser.toString(), "EMPLEADO");


        log.info("rolAdmin.toString() = '" + rolAdmin.toString() + "'");
        assertEquals(rolAdmin.toString(), "ADMIN");

    }
}