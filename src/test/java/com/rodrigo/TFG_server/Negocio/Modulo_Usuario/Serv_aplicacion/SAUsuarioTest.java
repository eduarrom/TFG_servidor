package com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.imp.SAUsuarioImp;
import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Entidad.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.*;

class SAUsuarioTest {

    static SAUsuarioImp sa;

    final static Logger log = LoggerFactory.getLogger(SAUsuarioTest.class);

    @BeforeAll
    static void initSA() {
        log.info("before");
        sa = new SAUsuarioImp();
    }

    @Test
    void crearUsuario() {
        log.info("crearUserTest");
        Usuario user = new Usuario("juan", "1234");

        Usuario nuevo = sa.crearUsuario(user);

        assertNotNull(nuevo);
        assertNotNull(nuevo.getId());
    }

    @Test
    void buscarUsuarioByID() {
        log.info("BuscarUserTest");
        Usuario nuevo = sa.crearUsuario(new Usuario("test2", "1234"));
        Usuario userB = sa.buscarUsuarioByID(nuevo.getId());

        log.info(userB.toString());



        assertNotNull(userB);
        assertEquals(nuevo.getId(), userB.getId());
        assertEquals(nuevo.getNombre(), userB.getNombre());

    }

    @Test
    void eliminarUsuario() {
        log.info("EliminarUser Test");

        Usuario u = new Usuario("Eliminar", "pass");
        u = sa.crearUsuario(u);

        assertTrue(sa.eliminarUsuario(u));

        assertNull(sa.buscarUsuarioByID(u.getId()));
    }

    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        assertNotNull(sa.listarUsuarios());

    }

    @Test
    void saludo(){
        log.info("---- SAUsuarioTest.saludo ---- ");

        String nombre = "Rodrigo";
        String str = "Hola " + nombre + ", un saludo desde el servidor CXF :)";

        assertNotNull(sa.saludar(nombre));

        assertTrue(sa.saludar(nombre).equals(str));
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