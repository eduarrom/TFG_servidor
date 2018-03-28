package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class EmpleadoTest {

    private final static Logger log = LoggerFactory.getLogger(EmpleadoTest.class);

    private static Empleado emple;
    private static Empleado admin;



    @BeforeAll
    static void beforeAll(){

        emple = new EmpleadoTParcial("emple", "1234", Rol.EMPLEADO);
        admin = new EmpleadoTParcial("admin", "1234", Rol.ADMIN);

    }

    @Test
    void equalsWithOutVersion() {

        Empleado emple2;
        emple2 = emple;



        log.debug("emple = '" + emple + "'");
        log.debug("emple2 = '" + emple2.toString() + "'");
        log.debug("admin = '" + admin + "'");

        log.debug("emple.equalsWithOutVersion(emple2)");
        assertTrue(emple.equalsWithOutVersion(emple2));

        log.debug("emple2.equalsWithOutVersion(emple)");
        assertTrue(emple2.equalsWithOutVersion(emple));

        log.debug("emple.equalsWithOutVersion(emple)");
        assertTrue(emple.equalsWithOutVersion(emple));

        log.debug("emple.equalsWithOutVersion(admin)");
        assertFalse(emple.equalsWithOutVersion(admin));

        log.debug("admin.equalsWithOutVersion(emple)");
        assertFalse(admin.equalsWithOutVersion(emple));


        Empleado emple3 = new EmpleadoTParcial("emple3", "1234", Rol.EMPLEADO);
        log.debug("emple3 = '" + emple3 + "'");

        log.debug("emple.equalsWithOutVersion(emple3)");
        assertFalse(emple.equalsWithOutVersion(emple3));

        log.debug("emple3.equalsWithOutVersion(emple)");
        assertFalse(emple3.equalsWithOutVersion(emple));

        log.debug("emple3.equalsWithOutVersion(emple3)");
        assertTrue(emple3.equalsWithOutVersion(emple3));


    }

    @Test
    void equalsWithOutVersionConBBDD() throws EmpleadoException {
        log.info("creando SA_Proyecto");

        Empleado admin2 = new SA_EmpleadoImpl().crearEmpleado(admin);
        admin.setId(admin2.getId());

        log.debug("admin2 = '" + admin2 + "'");

        log.debug("admin.equalsWithOutVersion(admin2)");
        assertTrue(admin.equalsWithOutVersion(admin2));

        log.debug("admin2.equalsWithOutVersion(admin)");
        assertTrue(admin2.equalsWithOutVersion(admin));

        Boolean resutl = new SA_EmpleadoImpl().eliminarEmpleado(admin);
        log.debug("resutl = '" + resutl + "'");
    }

}