package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class EmpleadoTest {

    private final static Logger log = LoggerFactory.getLogger(EmpleadoTest.class);

    private static TEmpleadoCompleto emple;
    private static TEmpleadoCompleto admin;



    @BeforeAll
    static void beforeAll() throws EmpleadoException {

        String email1 = "empleado@gmail.com";
        String email2 = "admin@gmail.com";
        emple = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(email1);
        admin = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(email2);

    }

    @Test
    void equalsWithOutVersion() {

        TEmpleadoCompleto emple2;
        emple2 = emple;



        log.debug("emple = '" + emple + "'");
        log.debug("emple2 = '" + emple2.toString() + "'");
        log.debug("admin = '" + admin + "'");

        log.debug("emple.equalsWithOutVersion(emple2)");
        assertTrue(emple.equals(emple2));

        log.debug("emple2.equalsWithOutVersion(emple)");
        assertTrue(emple2.equals(emple));

        log.debug("emple.equalsWithOutVersion(emple)");
        assertTrue(emple.equals(emple));

        log.debug("emple.equalsWithOutVersion(admin)");
        assertFalse(emple.equals(admin));

        log.debug("admin.equalsWithOutVersion(emple)");
        assertFalse(admin.equals(emple));


        Empleado emple3 = new EmpleadoTParcial("emple3", "1234");
        log.debug("emple3 = '" + emple3 + "'");

        log.debug("emple.equalsWithOutVersion(emple3)");
        assertFalse(emple.equals(emple3));

        log.debug("emple3.equalsWithOutVersion(emple)");
        assertFalse(emple3.equalsWithOutVersion(emple));

        log.debug("emple3.equalsWithOutVersion(emple3)");
        assertTrue(emple3.equalsWithOutVersion(emple3));


    }

    @Test
    void equalsWithOutVersionConBBDD() throws EmpleadoException {
        log.info("creando SA_Proyecto");

        TEmpleadoCompleto admin2 = admin;
        admin.setId(admin2.getId());

        log.debug("admin2 = '" + admin2 + "'");

        log.debug("admin.equalsWithOutVersion(admin2)");
        assertTrue(admin.equals(admin2));

        log.debug("admin2.equalsWithOutVersion(admin)");
        assertTrue(admin2.equals(admin));

    }

}