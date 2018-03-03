package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidatorTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.*;

class SA_EmpleadoTest {

    static SA_EmpleadoImpl sa;

    private Empleado e1;

    final static Logger log = LoggerFactory.getLogger(SA_EmpleadoTest.class);

    @BeforeAll
    static void initSA() {
        log.info("Creando SA...");
        sa = new SA_EmpleadoImpl();
    }

    @BeforeEach
    void initEmpleado() throws EmpleadoException {
        e1 = new Empleado("empleado", "1234", Rol.valueOf("EMPLEADO"));
        log.info("Creando empleado ");
        e1 = sa.crearEmpleado(e1);
    }


    @AfterEach
    void eliminarEmpleado() {
        log.info("Eliminado empleado");
        sa.eliminarEmpleado(e1);
    }


    @ParameterizedTest
    @CsvSource({"Admin, 1234, ADMIN", "rodri, 1234, EMPLEADO", "emple,1234, EMPLEADO"})
    void crearEmpleado(String nombre, String pass, String rol) throws EmpleadoException {

        Empleado e1 = new Empleado(nombre, pass, Rol.valueOf(rol));
        Empleado empleCreado = sa.crearEmpleado(e1);

        assertNotNull(empleCreado);

        e1.setId(empleCreado.getId());
        assertTrue(empleCreado.equalsWithOutVersion(e1));


        sa.eliminarEmpleado(empleCreado);
    }


    @Test
    void crearEmpleadoExistente() throws EmpleadoException {

        /*Empleado e1 = new Empleado("juan", "1234", Rol.valueOf("EMPLEADO"));

        log.info("Creando empleado 1");
        e1 = sa.crearEmpleado(e1);*/


        Throwable exception = assertThrows(EmpleadoYaExisteExcepcion.class, () -> {

            Empleado e2 = e1;

            log.info("Creando empleado 2");
            e2 = sa.crearEmpleado(e2);

        });


        /*sa.eliminarEmpleado(e1);*/
    }

    @Test
    void crearEmpleadoNull() {


        Throwable exception = assertThrows(EmpleadoNullException.class, () -> {
            Empleado empleCreado;
            empleCreado = sa.crearEmpleado(null);

            assertNull(empleCreado);

        });

    }


    @Test
    void crearEmpleadoVacio() {

        Throwable exception = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado empleCreado = sa.crearEmpleado(new Empleado());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearEmpleadoEmailNull() {


        log.info("forzando email = null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            e1.setEmail(null);
            log.debug("e1= " + e1);
            Empleado empleCreado = sa.crearEmpleado(e1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearEmpleadoEmailVacio() {


        log.info("forzando email = ''");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            e1.setEmail("");
            log.debug("e1= " + e1);
            Empleado empleCreado = sa.crearEmpleado(e1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }

/*

    @Test
    void buscarUsuarioByID() throws EmpleadoException {
        log.info("BuscarUserTest");
        Empleado e = new Empleado("test2", "1234");
        Empleado nuevo = null;

        nuevo = sa.buscarByEmail(e.getEmail());
        if (nuevo == null) {
            //nuevo = sa.crearEmpleado(e);
        }

        Empleado userB = sa.buscarByID(nuevo.getId());

        log.info(userB.toString());


        assertNotNull(userB);
        assertEquals(nuevo.getId(), userB.getId());
        assertEquals(nuevo.getNombre(), userB.getNombre());

        sa.eliminarEmpleado(nuevo);

    }

    @Test
    void eliminarUsuario() throws EmpleadoException {
        log.info("EliminarUser Test");

        Empleado u = new Empleado("Eliminar", "pass");


        if (!sa.buscarByEmail(u.getEmail()).equalsWithOutVersion(u)) {
            //u = sa.crearEmpleado(u);
        }


        assertTrue(sa.eliminarEmpleado(u));

        assertNull(sa.buscarByID(u.getId()));

        sa.eliminarEmpleado(u);
    }


    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        assertNotNull(sa.listarEmpleados());

        log.info("************************************************************");
        sa.listarEmpleados().stream().forEach((user ->
                log.debug("user = '" + user + "'")
        ));

    }

    @Test
    void saludo() {
        log.info("---- SA_EmpleadoTest.saludo ---- ");

        String nombre = "Rodrigo";
        String str = "Hola " + nombre + ", un saludo desde el servidor CXF :)";

        assertNotNull(sa.saludar(nombre));

        assertTrue(sa.saludar(nombre).equals(str));
    }


    @ParameterizedTest(name = "-> {0}, {1}")
    @CsvSource({"emple, 1234, EMPLEADO", "admin, 1234, ADMIN"})
    void loginTest(String nombre, String pass, String rol) throws EmpleadoException {
        String email = nombre.concat("@gmail.com");

        Empleado emple = new Empleado(nombre, pass, email, Rol.valueOf(rol));

        if (!sa.buscarByEmail(email).equalsWithOutVersion(emple)) {
            //sa.crearEmpleado(emple);
        }

        log.info("Login: {email='" + email + ", pass='" + pass + "'}");
        Boolean result = sa.login(email, pass);

        log.debug("result = '" + result + "'");
        assertTrue(result);

        sa.eliminarEmpleado(emple);

    }


    @ParameterizedTest(name = "-> {0}, {1}, {2}")
    @CsvSource({",1234, EMPLEADO", "rodri,,EMPLEADO"})
    void loginErroneoTest(String nombre, String pass, String rol) {
        *//*String email = nombre.concat("@gmail.com");*//*
        String email = "";

        Empleado emple = new Empleado(nombre, pass, email, Rol.valueOf(rol));


        log.info("Login: {email='" + email + ", pass='" + pass + "'}");
        Boolean result = sa.login(email, pass);

        log.debug("result = '" + result + "'");
        assertFalse(result);

        sa.eliminarEmpleado(emple);


    }

    */

    @ParameterizedTest
    @CsvSource({"Admin, 1234, ADMIN", "rodri, 1234, EMPLEADO", "emple,1234, EMPLEADO"})
    void buscarByEmail(String nombre, String pass, String rol) throws EmpleadoException {
        Empleado nuevo, e1 = new Empleado(nombre, pass, Rol.valueOf(rol));
        String email = e1.getEmail();

        log.info("Creando empleado");
        nuevo = sa.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = sa.buscarByEmail(email);

        assertTrue(e1.equalsWithOutVersion(nuevo));

        sa.eliminarEmpleado(nuevo);

    }

    @Test
    void buscarByEmailInexistente() throws EmpleadoException {


        Empleado e2 = sa.buscarByEmail("emailInexistente@gmail.com");

        assertNull(e2);

        sa.eliminarEmpleado(e2);


    }


    public static Object[][] InvalidEmailProvider() {
        return EmailValidatorTest.InvalidEmailProvider();
    }


    @ParameterizedTest
    @MethodSource("InvalidEmailProvider")
    void buscarByEmailIncorrecto(String[] Email) {


        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            for (String temp : Email) {
                sa.buscarByEmail(temp);
            }


        });

        log.info("Excepcion capturada:" + ex1.getMessage());


    }

    @Test
    void buscarByEmailVacio() {

        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado e2 = sa.buscarByEmail("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByEmailNull() {
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado e2 = sa.buscarByEmail(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


}