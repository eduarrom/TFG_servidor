package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidatorTest;
import com.sun.org.apache.xalan.internal.xsltc.runtime.ErrorMessages_es;
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

    static SA_Empleado sa;

    private Empleado e1;

    final static Logger log = LoggerFactory.getLogger(SA_EmpleadoTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/

    @BeforeAll
    static void initSA() {
        log.info("Creando SA...");
        sa = FactoriaSA.getInstance().crearSAEmpleado();
    }

    @BeforeEach
    void iniciarContexto() throws EmpleadoException {
        e1 = new Empleado("empleado", "1234", Rol.valueOf("EMPLEADO"));
        log.info("Creando empleado ");
        if (sa.buscarByEmail(e1.getEmail()) == null) {
            e1 = sa.crearEmpleado(e1);
        }
    }


    @AfterEach
    void finalizarContexto() {

       /* assertFalse(sa.transactionIsActive(), "Transacción no cerrada");

        assertFalse(sa.emIsOpen(), "Entity Manager no cerrado");
*/
        log.info("Eliminado empleado");
        sa.eliminarEmpleado(e1);
    }


    /******************************************************************
     ********************   TEST CREAR EMPLEADO   *********************
     ******************************************************************/


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


        Throwable exception = assertThrows(EmpleadoException.class, () -> {
            Empleado empleCreado;
            empleCreado = sa.crearEmpleado(null);

            assertNull(empleCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

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




    /******************************************************************
     *******************   TEST BUSCAR EMPLEADO   *********************
     ******************************************************************/


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
        Boolean result = sa.loginEmpleado(email, pass);

        log.debug("result = '" + result + "'");
        assertTrue(result);

    }

    @Test
    void loginParamErroneosTest() {

        log.info("Login email erroneo");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = sa.loginEmpleado("kajsdnflaf", "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void loginEmpleadoInexistenteTest() {

        log.info("Login empleado inexistente");
        Throwable ex1 = assertThrows(EmpleadoLoginErroneo.class, () -> {

            boolean login = sa.loginEmpleado("kajsdnflaf@gmail.com", "1234");
            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void loginEmailNulloVacioTest() {

        log.info("Login email null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = sa.loginEmpleado(null, "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());


        log.info("Login email vacio");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = sa.loginEmpleado("", "1234");

            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex2.getMessage());


    }

    @Test
    void loginPassNulloVacioTest() {

        log.info("Login pass null");
        Throwable ex1 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = sa.loginEmpleado("kajsdnflaf", null);
            assertFalse(login);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

        log.info("Login pass vacia");
        Throwable ex2 = assertThrows(EmpleadoFieldNullException.class, () -> {

            boolean login = sa.loginEmpleado("kajsdnflaf", "");
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





    /******************************************************************
     *********************   METODOS AUXILIARES   *********************
     ******************************************************************/



    public static Object[][] InvalidEmailProvider() {
        return EmailValidatorTest.InvalidEmailProvider();
    }


    @Test
    void buscarUnEmple() throws EmpleadoException {

        log.info(sa.buscarByEmail("administrador@gmail.com").toString());
    }
}