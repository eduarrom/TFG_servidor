package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
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


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SA_EmpleadoImplTest {

    static SA_Empleado sa;

    static private Empleado e1;
    static Empleado emple1;
    //    static Empleado emple2;
    static Departamento dept;
    static Proyecto proy1;

    final static Logger log = LoggerFactory.getLogger(SA_EmpleadoImplTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/

    @BeforeAll
    static void initSA() throws DepartamentoException, EmpleadoException {
        log.info("Creando SA...");
        sa = FactoriaSA.getInstance().crearSA_Empleado();


        emple1 = new EmpleadoTParcial("emple1", "1234", Rol.ADMIN);
//        emple2 = new EmpleadoTCompleto("emple2", "1234", Rol.EMPLEADO);
        emple1 = sa.buscarByID(23L);

        dept = new Departamento("Dept1");
//        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());
        dept = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas("DdP");

        proy1 = new Proyecto("Proy1");
        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);



        /*//dept = new Departamento("Ingenieria del Software");
        dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(3L);

        e1 = new EmpleadoTCompleto("empleTest", "1234", Rol.EMPLEADO, dept);

        //dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(1L);
        //dept = FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(dept);

        dept.getEmpleados().add(e1);
        e1.setDepartamento(dept);

        e1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(e1.getEmail());*/
    }

    @BeforeEach
    void iniciarContexto() throws EmpleadoException {
        e1 = new EmpleadoTCompleto("emple3", "1234", Rol.EMPLEADO, dept);

        log.info("Creando empleado ");
        Empleado aux = sa.buscarByEmail(e1.getEmail());
        if (aux == null) {
            e1 = sa.crearEmpleado(e1);
        } else
            e1 = aux;
    }


    @AfterEach
    void finalizarContexto() throws EmpleadoException {

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
    @CsvSource({"crear1, 1234, ADMIN", "crear2, 1234, EMPLEADO", "crear3, 1234, EMPLEADO"})
    void crearEmpleado(String nombre, String pass, String rol) throws EmpleadoException {

        Empleado e = new EmpleadoTParcial(nombre, pass, Rol.valueOf(rol), dept);
        Empleado empleCreado = sa.crearEmpleado(e);


        e.setId(empleCreado.getId());
        log.debug("empleCreado = '" + empleCreado + "'");
        log.debug("e1          = '" + e + "'");

        assertNotNull(empleCreado);
        assertNotNull(empleCreado.getId());

        assertEquals(e.toString(), empleCreado.toString());


        sa.eliminarEmpleado(empleCreado);
    }


    @Test
    void crearEmpleadoExistente() throws EmpleadoException {

        /*Empleado e1 = new EmpleadoTParcial("juan", "1234", Rol.valueOf(rol), dept);

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

        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            Empleado empleCreado = sa.crearEmpleado(new EmpleadoTParcial());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearEmpleadoEmailNull() {


        log.info("forzando email = null");
        Throwable ex1 = assertThrows(EmpleadoException.class, () -> {

            e1.setEmail(null);
            log.debug("e1= " + e1);
            Empleado empleCreado = sa.crearEmpleado(e1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearEmpleadoEmailVacio() {


        log.info("forzando email = ''");
        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            e1.setEmail("");
            log.debug("e1 = '" + e1 + "'");
            Empleado empleCreado = sa.crearEmpleado(e1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ******************   TEST BUSCAR EMPLEADO ID  ********************
     ******************************************************************/


    @Test
    void buscarByID() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarUsuarioByID");

        Empleado e = sa.buscarByID(e1.getId());
        log.info(e.toString());


        assertNotNull(e);
        assertEquals(e.getId(), e1.getId());
        assertEquals(e.getNombre(), e1.getNombre());
        assertEquals(e.toString(), e1.toString());

        //sa.eliminarEmpleado(nuevo);

    }


    @Test
    void buscarByIDNegativo() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDNegativo");

        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            Empleado e = sa.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            Empleado e = sa.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDInexixtente");

        e1.setId(30000L);
        Empleado buscado = sa.buscarByID(e1.getId());

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR EMPLEADO   ********************
     ******************************************************************/


    @Test
    void eliminarEmpleado() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.eliminarEmpleado");

        Empleado e = new EmpleadoTParcial("Eliminar4", "pass", Rol.EMPLEADO, dept);
        log.info("Creando empleado");
        e = sa.crearEmpleado(e);
        log.info("Asignando proyecto a empleado");
        EmpleadoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().añadirEmpleadoAProyecto(e, proy1, 5);
        proy1.getEmpleados().add(ep);
        e.getProyectos().add(ep);

        log.info("Eliminando empleado");
        boolean resutl = sa.eliminarEmpleado(e);

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(e.getId()));

    }


    /******************************************************************
     *******************   TEST LISTAR EMPLEADOS   ********************
     ******************************************************************/


    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        ArrayList<Empleado> lista = (ArrayList) sa.listarEmpleados();

        assertNotNull(lista);

        log.info("************************************************************");
        log.info("************************************************************");
        lista.stream().forEach(System.out::println);
        log.info("************************************************************");
        log.info("************************************************************");

    }

/*
    @Test
    void saludo() {
        log.info("---- SA_EmpleadoImplTest.saludo ---- ");

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
    @CsvSource({"buscar1, 1234, EMPLEADO", "buscar2, 1234, EMPLEADO"})
    void buscarByEmail(String nombre, String pass, String rol) throws EmpleadoException {
        Empleado nuevo, e1 = new EmpleadoTParcial(nombre, pass, Rol.valueOf(rol), dept);
        dept.getEmpleados().add(e1);

        String email = e1.getEmail();

        log.info("Creando empleado");
        nuevo = sa.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = sa.buscarByEmail(email);

        assertEquals(e1.toString(), nuevo.toString());

        sa.eliminarEmpleado(nuevo);

    }

    /*public static void main(String[] args) throws EmpleadoException, DepartamentoException {
        initSA();
        Empleado nuevo, e1 = new EmpleadoTParcial("empleado", "1234", Rol.EMPLEADO, dept);
        String email = e1.getEmail();

        //log.info("Creando empleado");
        //nuevo = sa.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = sa.buscarByEmail(email);

    }*/

    @Test
    void buscarByEmailSimple() throws EmpleadoException {
        Empleado nuevo, e1 = new EmpleadoTParcial("administrador", "1234", Rol.ADMIN, dept);
        String email = e1.getEmail();

        log.info("Creando empleado");
        //nuevo = sa.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = sa.buscarByEmail(email);

        //assertTrue(e1.equalsWithOutVersion(nuevo));

        //sa.eliminarEmpleado(nuevo);

    }

    @Test
    void buscarByEmailInexistente() throws EmpleadoException {


        Empleado e2 = sa.buscarByEmail("emailInexistente@gmail.com");

        assertNull(e2);


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

}