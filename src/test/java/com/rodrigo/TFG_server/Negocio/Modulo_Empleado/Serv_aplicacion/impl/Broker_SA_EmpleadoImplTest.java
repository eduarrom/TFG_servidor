package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Broker_SA_EmpleadoImplTest {

    static Broker_SA_EmpleadoImpl b;

    static private TEmpleadoCompleto e1;
    static TEmpleadoCompleto empleCompleto;
    //    static Empleado emple2;
    static TDepartamentoCompleto dept;
    static TProyectoCompleto proy1;

    final static Logger log = LoggerFactory.getLogger(Broker_SA_EmpleadoImplTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/

    @BeforeAll
    static void initSA() throws DepartamentoException, EmpleadoException, ProyectoException {
        log.info("Creando SA...");
        b = new Broker_SA_EmpleadoImpl();


//        empleCompleto = new EmpleadoTParcial("empleCompleto", "1234", Rol.ADMIN);
        empleCompleto = b.buscarByID(20L);

//        dept1 = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());

        dept = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas("DdP");

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

        String nombre = "empleTest";

        log.info("Creando empleado ");
        TEmpleadoCompleto aux = b.buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (aux == null) {
            e1 = b.crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", dept.getId()));

        } else
            e1 = aux;
    }


    @AfterEach
    void finalizarContexto() throws EmpleadoException {

       /* assertFalse(b.transactionIsActive(), "Transacción no cerrada");

        assertFalse(b.emIsOpen(), "Entity Manager no cerrado");
*/
        log.info("Eliminado empleado");
        b.eliminarEmpleado(e1.getEmpleado().getId());
    }


    /******************************************************************
     ********************   TEST CREAR EMPLEADO   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1, 1234, ADMIN", "crear2, 1234, EMPLEADO", "crear3, 1234, EMPLEADO"})
    void crearEmpleado(String nombre, String pass, String rol) throws EmpleadoException {


        TEmpleadoTParcial e = new TEmpleadoTParcial(nombre, pass, dept.getId());

        TEmpleadoCompleto empleCreado = b.crearEmpleado(e);


        e.setId(empleCreado.getEmpleado().getId());
        log.debug("empleCreado = '" + empleCreado + "'");
        log.debug("e1          = '" + e + "'");

        assertNotNull(empleCreado);
        assertNotNull(empleCreado.getEmpleado().getId());

        assertEquals(e.toString(), empleCreado.getEmpleado().toString());


        b.eliminarEmpleado(empleCreado.getEmpleado().getId());
    }


    @Test
    void crearEmpleadoExistente() throws EmpleadoException {

        /*Empleado e1 = new EmpleadoTParcial("juan", "1234", Rol.valueOf(rol), dept);

        log.info("Creando empleado 1");
        e1 = b.crearEmpleado(e1);*/


        Throwable exception = assertThrows(EmpleadoYaExisteExcepcion.class, () -> {

            TEmpleadoCompleto e2 = e1;

            log.info("Creando empleado 2");
            e2 = b.crearEmpleado(e2.getEmpleado());

        });


        /*b.eliminarEmpleado(e1);*/
    }

    @Test
    void crearEmpleadoNull() {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {
            TEmpleadoCompleto empleCreado;
            empleCreado = b.crearEmpleado(null);

            assertNull(empleCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearEmpleadoVacio() {

        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto empleCreado = b.crearEmpleado(new TEmpleadoTParcial());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearEmpleadoEmailNull() {


        log.info("forzando email = null");
        Throwable ex1 = assertThrows(EmpleadoException.class, () -> {

            e1.getEmpleado().setEmail(null);
            log.debug("e1= " + e1);
            TEmpleadoCompleto empleCreado = b.crearEmpleado(e1.getEmpleado());

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearEmpleadoEmailVacio() {


        log.info("forzando email = ''");
        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            e1.getEmpleado().setEmail("");
            log.debug("e1 = '" + e1 + "'");
            TEmpleadoCompleto empleCreado = b.crearEmpleado(e1.getEmpleado());

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ******************   TEST BUSCAR EMPLEADO ID  ********************
     ******************************************************************/


    @Test
    void buscarByID() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarUsuarioByID");

        TEmpleadoCompleto e = b.buscarByID(e1.getEmpleado().getId());
        log.info(e.toString());


        assertNotNull(e);
        assertEquals(e.getEmpleado().getId(), e1.getEmpleado().getId());
        assertEquals(e.getEmpleado().getNombre(), e1.getEmpleado().getNombre());
        assertEquals(e.toString(), e1.toString());

        //b.eliminarEmpleado(nuevo);

    }


    @Test
    void buscarByIDNegativo() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDNegativo");

        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto e = b.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto e = b.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDInexixtente");

        TEmpleadoCompleto buscado = b.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR EMPLEADO   ********************
     ******************************************************************/


    @Test
    void eliminarEmpleado() throws EmpleadoException, ProyectoException {
        log.info("Broker_SA_EmpleadoImplTest.eliminarEmpleado");

        log.info("Creando empleado");
        TEmpleadoCompleto e = b.crearEmpleado(new TEmpleadoTParcial("Eliminar4", "pass", dept.getId()));

        log.info("Asignando proyecto a empleado");
        TEmpleadoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().añadirEmpleadoAProyecto(e.getEmpleado(), proy1.getProyecto(), 5);

        proy1.agregarEmpleadoProyecto(ep, e.getEmpleado());
        e.agregarEmpleadoProyecto(ep, proy1.getProyecto());

        log.info("Eliminando empleado");
        boolean resutl = b.eliminarEmpleado(e.getEmpleado().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(b.buscarByID(e.getId()));

    }


    /******************************************************************
     *******************   TEST LISTAR EMPLEADOS   ********************
     ******************************************************************/


    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        List<TEmpleado> lista = b.listarEmpleados();

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

        assertNotNull(b.saludar(nombre));

        assertTrue(b.saludar(nombre).equals(str));
    }
    */




    /******************************************************************
     *******************   TEST BUSCAR BY EMAIL   *********************
     ******************************************************************/

    @ParameterizedTest
    @CsvSource({"buscar1, 1234, EMPLEADO", "buscar2, 1234, EMPLEADO"})
    void buscarByEmail(String nombre, String pass, String rol) throws EmpleadoException {

        TEmpleadoCompleto nuevo;

        TEmpleadoCompleto emple = new TEmpleadoCompleto(new TEmpleadoTParcial(nombre, pass, dept.getId()), dept.getDepartamento());
        dept.getEmpleados().put(emple.getId(), emple.getEmpleado());

        String email = emple.getEmail();

        log.info("Creando empleado");
        nuevo = b.crearEmpleado(emple.getEmpleado());

        log.info("buscnado empleado");
        emple = b.buscarByEmail(email);

        assertEquals(emple.toString(), nuevo.toString());

        b.eliminarEmpleado(nuevo.getEmpleado().getId());

    }

    @Test
    void buscarByEmailInexistente() throws EmpleadoException {


        TEmpleadoCompleto e2 = b.buscarByEmail("emailInexistente@gmail.com");

        assertNull(e2);


    }


    @ParameterizedTest
    @MethodSource("InvalidEmailProvider")
    void buscarByEmailIncorrecto(String[] Email) {


        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            for (String temp : Email) {
                b.buscarByEmail(temp);
            }


        });

        log.info("Excepcion capturada:" + ex1.getMessage());


    }

    @Test
    void buscarByEmailVacio() {

        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            TEmpleadoCompleto e2 = b.buscarByEmail("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByEmailNull() {
        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            TEmpleadoCompleto e2 = b.buscarByEmail(null);


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