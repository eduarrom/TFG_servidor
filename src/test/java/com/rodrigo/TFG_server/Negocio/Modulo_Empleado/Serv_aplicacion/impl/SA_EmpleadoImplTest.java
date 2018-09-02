package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
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

class SA_EmpleadoImplTest {

    static SA_Empleado sa;

    static private TEmpleadoCompleto e1;
    static TEmpleadoCompleto empleCompleto;
    //    static Empleado emple2;
    static TDepartamentoCompleto dept;
    static TProyectoCompleto proy1;

    final static Logger log = LoggerFactory.getLogger(SA_EmpleadoImplTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/

    @BeforeAll
    static void initSA() throws DepartamentoException, EmpleadoException, ProyectoException {
        log.info("Creando SA...");
        sa = FactoriaSA.getInstance().crearSA_Empleado();


//        empleCompleto = new EmpleadoTParcial("empleCompleto", "1234", Rol.ADMIN);
        empleCompleto = sa.buscarByID(20L);

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
        TEmpleadoCompleto aux = sa.buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (aux == null) {
            e1 = sa.crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", dept.getId()));
        } else
            e1 = aux;
    }


    @AfterEach
    void finalizarContexto() throws EmpleadoException {

       /* assertFalse(b.transactionIsActive(), "Transacción no cerrada");

        assertFalse(b.emIsOpen(), "Entity Manager no cerrado");
*/
        log.info("Eliminado empleado");
        sa.eliminarEmpleado(e1.getEmpleado().getId());
    }


    /******************************************************************
     ********************   TEST CREAR EMPLEADO   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1, 1234, ADMIN", "crear2, 1234", "crear3, 1234"})
    void crearEmpleado(String nombre, String pass) throws EmpleadoException {


        TEmpleadoTParcial e = new TEmpleadoTParcial(nombre, pass, dept.getId());

        TEmpleadoCompleto empleCreado = sa.crearEmpleado(e);


        e.setId(empleCreado.getEmpleado().getId());
        log.debug("empleCreado = '" + empleCreado + "'");
        log.debug("e1          = '" + e + "'");

        assertNotNull(empleCreado);
        assertNotNull(empleCreado.getEmpleado().getId());

        assertEquals(e.toString(), empleCreado.getEmpleado().toString());


        sa.eliminarEmpleado(empleCreado.getEmpleado().getId());
    }


    @Test
    void crearEmpleadoExistente() throws EmpleadoException {

        /*Empleado e1 = new EmpleadoTParcial("juan", "1234", Rol.valueOf(rol), dept);

        log.info("Creando empleado 1");
        e1 = b.crearEmpleado(e1);*/


        Throwable exception = assertThrows(EmpleadoYaExisteExcepcion.class, () -> {

            TEmpleadoCompleto e2 = e1;

            log.info("Creando empleado 2");
            e2 = sa.crearEmpleado(e2.getEmpleado());

        });


        /*b.eliminarEmpleado(e1);*/
    }

    @Test
    void crearEmpleadoNull() {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {
            TEmpleadoCompleto empleCreado;
            empleCreado = sa.crearEmpleado(null);

            assertNull(empleCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearEmpleadoVacio() {

        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto empleCreado = sa.crearEmpleado(new TEmpleadoTParcial());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearEmpleadoEmailNull() {


        log.info("forzando email = null");
        Throwable ex1 = assertThrows(EmpleadoException.class, () -> {

            e1.getEmpleado().setEmail(null);
            log.debug("e1= " + e1);
            TEmpleadoCompleto empleCreado = sa.crearEmpleado(e1.getEmpleado());

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearEmpleadoEmailVacio() {


        log.info("forzando email = ''");
        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            e1.getEmpleado().setEmail("");
            log.debug("e1 = '" + e1 + "'");
            TEmpleadoCompleto empleCreado = sa.crearEmpleado(e1.getEmpleado());

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ******************   TEST BUSCAR EMPLEADO ID  ********************
     ******************************************************************/


    @Test
    void buscarByID() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarUsuarioByID");

        TEmpleadoCompleto e = sa.buscarByID(e1.getEmpleado().getId());
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

            TEmpleadoCompleto e = sa.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto e = sa.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws EmpleadoException {
        log.info("SA_EmpleadoImplTest.buscarByIDInexixtente");

        TEmpleadoCompleto buscado = sa.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR EMPLEADO   ********************
     ******************************************************************/


    @Test
    void eliminarEmpleado() throws EmpleadoException, ProyectoException {
        log.info("SA_EmpleadoImplTest.eliminarEmpleado");


        log.info("Creando empleado");
        TEmpleadoCompleto e = sa.crearEmpleado(new TEmpleadoTCompleto("Eliminar1", "pass", dept.getId()));


        log.info("Eliminando empleado");
        boolean resutl = sa.eliminarEmpleado(e.getEmpleado().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(e.getId()));

    }


    @Test
    void eliminarEmpleado_conProyectos() throws EmpleadoException, ProyectoException {
        log.info("SA_EmpleadoImplTest.eliminarEmpleado");


        log.info("Creando empleado");
        TEmpleadoCompleto e = sa.crearEmpleado(new TEmpleadoTCompleto("Eliminar1", "pass", dept.getId()));

        log.info("Asignando proyecto a empleado");
        TEmpleadoProyecto ep = FactoriaSA
                .getInstance()
                .crearSA_Proyecto()
                .añadirEmpleadoAProyecto(e.getEmpleado(), proy1.getProyecto(), 5);

        proy1.agregarEmpleadoProyecto(ep, e.getEmpleado());
        e.agregarEmpleadoProyecto(ep, proy1.getProyecto());

        log.info("Eliminando empleado");
        boolean resutl = sa.eliminarEmpleado(e.getEmpleado().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(e.getId()));

    }

    @Test
    void eliminarEmpleadoNull() throws EmpleadoException {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {
            boolean emple = sa.eliminarEmpleado(null);

            assertNull(emple);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarEmpleadoIDNegativo() throws EmpleadoException {

        TEmpleado emple = new TEmpleadoTParcial("juan", "1234", dept.getId());
        emple.setId(-23L);


        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            boolean result = sa.eliminarEmpleado(emple.getId());

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    /******************************************************************
     *******************   TEST LISTAR EMPLEADOS   ********************
     ******************************************************************/


    @Test
    void listarUsuarios() {
        log.info("ListarUsersTest");

        List<TEmpleado> lista = sa.listarEmpleados();

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
        TEmpleadoCompleto nuevo, e1 = new TEmpleadoCompleto(new TEmpleadoTParcial(nombre, pass, dept.getId()), dept.getDepartamento());
        dept.getEmpleados().put(e1.getId(), e1.getEmpleado());

        String email = e1.getEmail();

        log.info("Creando empleado");
        nuevo = sa.crearEmpleado(e1.getEmpleado());

        log.info("buscnado empleado");
        e1 = sa.buscarByEmail(email);

        assertEquals(e1.toString(), nuevo.toString());

        sa.eliminarEmpleado(nuevo.getEmpleado().getId());

    }

    /*public static void main(String[] args) throws EmpleadoException, DepartamentoException {
        initSA();
        Empleado nuevo, e1 = new EmpleadoTParcial("empleado", "1234", Rol.EMPLEADO, dept);
        String email = e1.getEmail();

        //log.info("Creando empleado");
        //nuevo = b.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = b.buscarByEmail(email);

    }*/

/*    @Test
    void buscarByEmailSimple() throws EmpleadoException {
        Empleado nuevo, e1 = new EmpleadoTParcial("administrador", "1234", Rol.ADMIN, dept);
        String email = e1.getEmail();

        log.info("Creando empleado");
        //nuevo = b.crearEmpleado(e1);

        log.info("buscnado empleado");
        e1 = b.buscarByEmail(email);

        //assertTrue(e1.equalsWithOutVersion(nuevo));

        //b.eliminarEmpleado(nuevo);

    }*/

    @Test
    void buscarByEmailInexistente() throws EmpleadoException {


        TEmpleadoCompleto e2 = sa.buscarByEmail("emailInexistente@gmail.com");

        assertNull(e2);


    }


    @ParameterizedTest
    @MethodSource("InvalidEmailProvider")
    void buscarByEmailIncorrecto(String[] Email) {


        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            for (String temp : Email) {
                sa.buscarByEmail(temp);
            }


        });

        log.info("Excepcion capturada:" + ex1.getMessage());


    }

    @Test
    void buscarByEmailVacio() {

        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            TEmpleadoCompleto e2 = sa.buscarByEmail("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByEmailNull() {
        Throwable ex1 = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            TEmpleadoCompleto e2 = sa.buscarByEmail(null);


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