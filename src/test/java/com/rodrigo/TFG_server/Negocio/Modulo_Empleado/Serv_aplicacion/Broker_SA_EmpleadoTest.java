package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.Broker_SA_EmpleadoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class Broker_SA_EmpleadoTest {


    private static Broker_SA_EmpleadoImpl b;

    final static Logger log = LoggerFactory.getLogger(Broker_SA_EmpleadoTest.class);


    @BeforeAll
    static void initSA() {
        log.info("before");
        b = new Broker_SA_EmpleadoImpl();
    }

    @ParameterizedTest(name = "-> {0}, {1}")
    @CsvSource({"juan, 1234",",1234","rodri,"})
    void crearUsuario(String nombre, String pass) {
        log.info("crearUserTest");

        log.info("nombre = '" + nombre + "'");

        Empleado user = new Empleado("juan", "1234");

        Empleado nuevo = b.crearUsuario(user);

        assertNotNull(nuevo);
        assertNotNull(nuevo.getId());
    }

    @Test
    void buscarUsuarioByID() {
        log.info("BuscarUserTest");
        Empleado nuevo = b.crearUsuario(new Empleado("test2", "1234"));
        Empleado userB = b.buscarUsuarioByID(nuevo.getId());

        log.info(userB.toString());



        assertNotNull(userB);
        assertEquals(nuevo.getId(), userB.getId());
        assertEquals(nuevo.getNombre(), userB.getNombre());

    }

    @Test
    void eliminarUsuario() {
        log.info("EliminarUser Test");

        Empleado u = new Empleado("Eliminar", "pass");
        u = b.crearUsuario(u);

        assertTrue(b.eliminarUsuario(u));

        assertNull(b.buscarUsuarioByID(u.getId()));
    }

    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        assertNotNull(b.listarUsuarios());

    }

    @Test
    void saludo(){
        log.info("---- SA_EmpleadoTest.saludo ---- ");

        String nombre = "Rodrigo";
        String str = "Hola " + nombre + ", un saludo desde el servidor CXF :)";

        assertNotNull(b.saludar(nombre));

        assertTrue(b.saludar(nombre).equals(str));
    }

    @Test
    void testLogger(){
        log.debug("comentario en DEBUG");
        log.info("comentario en INFO");
        log.warn("comentario en WARN");
        log.error("comentario en ERROR");
        try{
            throw new Exception("excepcion mala");
        }catch (Exception e){
            log.trace("comentario en TRACE: " + e);
        }

        log.info("prueba auto run");


    }

}