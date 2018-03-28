package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.Broker_SA_EmpleadoImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
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

class Broker_SA_EmpleadoTest {


    private static Broker_SA_EmpleadoImpl b;

    final static Logger log = LoggerFactory.getLogger(Broker_SA_EmpleadoTest.class);

    private Empleado e1;



    /******************************************************************
     **********************   METODO INICIALES   **********************
     ******************************************************************/

    @BeforeAll
    static void initBroker() {
        log.info("Creando SA...");
        b = new Broker_SA_EmpleadoImpl();
    }

    @BeforeEach
    void iniciarContexto() throws EmpleadoException {
        e1 = new EmpleadoTParcial("empleado", "1234", Rol.valueOf("EMPLEADO"));
        log.info("Creando empleado ");
        if (b.buscarByEmail(e1.getEmail()) == null) {
            e1 = b.crearEmpleado(e1);
        }
    }


    @AfterEach
    void finalizarContexto() {


        log.info("Eliminado empleado");
        b.eliminarEmpleado(e1);
    }


    /******************************************************************
     ********************   TEST CREAR EMPLEADO   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"Admin, 1234, ADMIN", "rodri, 1234, EMPLEADO", "emple,1234, EMPLEADO"})
    void crearEmpleado(String nombre, String pass, String rol) throws EmpleadoException {

        Empleado e1 = new EmpleadoTParcial(nombre, pass, Rol.valueOf(rol));
        Empleado empleCreado = b.crearEmpleado(e1);

        assertNotNull(empleCreado);

        e1.setId(empleCreado.getId());
        assertTrue(empleCreado.equalsWithOutVersion(e1));


        b.eliminarEmpleado(empleCreado);
    }


    @Test
    void crearEmpleadoExistente() throws EmpleadoException {

        /*Empleado e1 = new EmpleadoTParcial("juan", "1234", Rol.valueOf("EMPLEADO"));

        log.info("Creando empleado 1");
        e1 = b.crearEmpleado(e1);*/


        Throwable exception = assertThrows(EmpleadoYaExisteExcepcion.class, () -> {

            Empleado e2 = e1;

            log.info("Creando empleado 2");
            e2 = b.crearEmpleado(e2);

        });


        /*b.eliminarEmpleado(e1);*/
    }

    @Test
    void crearEmpleadoNull() {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {
            Empleado empleCreado;
            empleCreado = b.crearEmpleado(null);

            assertNull(empleCreado);

        });

    }


    @Test
    void crearEmpleadoVacio() {

        Throwable exception = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado empleCreado = b.crearEmpleado(new EmpleadoTParcial());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearEmpleadoEmailNull() {


        log.info("forzando email = null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            e1.setEmail(null);
            log.debug("e1= " + e1);
            Empleado empleCreado = b.crearEmpleado(e1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearEmpleadoEmailVacio() {


        log.info("forzando email = ''");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            e1.setEmail("");
            log.debug("e1= " + e1);
            Empleado empleCreado = b.crearEmpleado(e1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }




    /******************************************************************
     *******************   TEST BUSCAR EMPLEADO   *********************
     ******************************************************************/

/*

    @Test
    void buscarUsuarioByID() throws EmpleadoException {
        log.info("BuscarUserTest");
        Empleado e = new EmpleadoTParcial("test2", "1234");
        Empleado nuevo = null;

        nuevo = b.buscarBySiglas(e.getEmail());
        if (nuevo == null) {
            //nuevo = b.crearEmpleado(e);
        }

        Empleado userB = b.buscarByID(nuevo.getId());

        log.info(userB.toString());


        assertNotNull(userB);
        assertEquals(nuevo.getId(), userB.getId());
        assertEquals(nuevo.getNombre(), userB.getNombre());

        b.eliminarEmpleado(nuevo);

    }

    @Test
    void eliminarUsuario() throws EmpleadoException {
        log.info("EliminarUser Test");

        Empleado u = new EmpleadoTParcial("Eliminar", "pass");


        if (!b.buscarBySiglas(u.getEmail()).equalsWithOutVersion(u)) {
            //u = b.crearEmpleado(u);
        }


        assertTrue(b.eliminarEmpleado(u));

        assertNull(b.buscarByID(u.getId()));

        b.eliminarEmpleado(u);
    }


    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        assertNotNull(b.listarEmpleados());

        log.info("************************************************************");
        b.listarEmpleados().stream().forEach((user ->
                log.debug("user = '" + user + "'")
        ));

    }

    @Test
    void saludo() {
        log.info("---- SA_EmpleadoTest.saludo ---- ");

        String nombre = "Rodrigo";
        String str = "Hola " + nombre + ", un saludo desde el servidor CXF :)";

        assertNotNull(b.saludar(nombre));

        assertTrue(b.saludar(nombre).equals(str));
    }
    */



    /******************************************************************
     ********************   TEST LOGIN EMPLEADO   *********************
     ******************************************************************/



    //@ParameterizedTest(name = "-> {0}, {1}")
    //@CsvSource({"emple, 1234, EMPLEADO", "admin, 1234, ADMIN"})
    @Test
    void loginTest() throws EmpleadoException {
        String email = e1.getEmail();
        String pass = e1.getPassword();


        log.info("Login: {email='" + email + ", pass='" + pass + "'}");
        Boolean result = b.loginEmpleado(email, pass);

        log.debug("result = '" + result + "'");
        assertTrue(result);

    }

    @Test
    void loginParamErroneosTest() {

        log.info("Login email erroneo");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = b.loginEmpleado("kajsdnflaf", "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void loginEmpleadoInexistenteTest() {

        log.info("Login empleado inexistente");
        Throwable ex1 = assertThrows(EmpleadoLoginErroneo.class, () -> {

            boolean login = b.loginEmpleado("kajsdnflaf@gmail.com", "1234");
            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void loginEmailNulloVacioTest() {

        log.info("Login email null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = b.loginEmpleado(null, "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());


        log.info("Login email vacio");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = b.loginEmpleado("", "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex2.getMessage());


    }

    @Test
    void loginPassNulloVacioTest() {

        log.info("Login pass null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = b.loginEmpleado("kajsdnflaf", null);
            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

        log.info("Login pass vacia");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = b.loginEmpleado("kajsdnflaf", "");
            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex2.getMessage());

    }



    /******************************************************************
     *******************   TEST BUSCAR BY EMAIL   *********************
     ******************************************************************/

    @ParameterizedTest
    @CsvSource({"Admin, 1234, ADMIN", "rodri, 1234, EMPLEADO", "emple,1234, EMPLEADO"})
    void buscarByEmail(String nombre, String pass, String rol) throws EmpleadoException {
        Empleado nuevo, e1 = new EmpleadoTParcial(nombre, pass, Rol.valueOf(rol));
        String email = e1.getEmail();

        log.info("Creando empleado");
        nuevo = b.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = b.buscarByEmail(email);

        assertTrue(e1.equalsWithOutVersion(nuevo));

        b.eliminarEmpleado(nuevo);

    }

    @Test
    void buscarByEmailInexistente() throws EmpleadoException {


        Empleado e2 = b.buscarByEmail("emailInexistente@gmail.com");

        assertNull(e2);

        b.eliminarEmpleado(e2);


    }



    @ParameterizedTest
    @MethodSource("InvalidEmailProvider")
    void buscarByEmailIncorrecto(String[] Email) {


        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            for (String temp : Email) {
                b.buscarByEmail(temp);
            }


        });

        log.info("Excepcion capturada:" + ex1.getMessage());


    }

    @Test
    void buscarByEmailVacio() {

        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado e2 = b.buscarByEmail("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByEmailNull() {
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            Empleado e2 = b.buscarByEmail(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }





    /******************************************************************
     *********************   METODOS AUXILIARES   *********************
     ******************************************************************/



    public static Object[][] InvalidEmailProvider() {
        return EmailValidatorTest.InvalidEmailProvider();
    }


    @Test
    void buscarUnEmple() throws EmpleadoException {

        log.info(b.buscarByEmail("administrador@gmail.com").toString());
    }

}