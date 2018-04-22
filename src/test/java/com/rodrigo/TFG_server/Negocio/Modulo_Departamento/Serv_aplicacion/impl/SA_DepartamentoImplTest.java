/*
package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SA_DepartamentoImplTest {

    static SA_Departamento sa;

    static private Departamento d1;
    static Empleado empld1;
    //    static Departamento emple2;
    static Departamento dept;
    static Proyecto proy1;

    final static Logger log = LoggerFactory.getLogger(SA_DepartamentoImplTest.class);


    */
/*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************//*


    @BeforeAll
    static void initSA() throws DepartamentoException, EmpleadoException {
        log.info("Creando SA...");
        sa = FactoriaSA.getInstance().crearSA_Departamento();


        empld1 = new EmpleadoTParcial("empld1", "1234", Rol.ADMIN);
//        emple2 = new DepartamentoTCompleto("emple2", "1234", Rol.EMPLEADO);
        empld1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(23L);

        dept = new Departamento("Dept1");
//        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());
        dept = sa.buscarBySiglas("DdP");

        proy1 = new Proyecto("Proy1");
        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);



        */
/*//*
/dept = new Departamento("Ingenieria del Software");
        dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(3L);

        d1 = new DepartamentoTCompleto("empleTest", "1234", Rol.EMPLEADO, dept);

        //dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(1L);
        //dept = FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(dept);

        dept.getDepartamentos().add(d1);
        d1.setDepartamento(dept);

        d1 = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(d1.getSiglas());*//*

    }

    @BeforeEach
    void iniciarContexto() throws DepartamentoException {
        d1 = new Departamento("d1");

        log.info("Creando departamento ");
        Departamento aux = sa.buscarBySiglas(d1.getSiglas());
        if (aux == null) {
            d1 = sa.crearDepartamento(d1);
        } else
            d1 = aux;
    }


    @AfterEach
    void finalizarContexto() throws DepartamentoException {

       */
/* assertFalse(b.transactionIsActive(), "Transacción no cerrada");

        assertFalse(b.emIsOpen(), "Entity Manager no cerrado");
*//*

        log.info("Eliminado departamento");
        sa.eliminarDepartamento(d1);
    }


    */
/******************************************************************
     ******************   TEST CREAR DEPARTAMENTO   *******************
     ******************************************************************//*



    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearDepartamento(String nombre) throws DepartamentoException {

        Departamento d = new Departamento(nombre);
        Departamento departCreado = sa.crearDepartamento(d);


        d.setId(departCreado.getId());
        log.debug("departCreado = '" + departCreado + "'");
        log.debug("d         = '" + d + "'");

        assertNotNull(departCreado);
        assertNotNull(departCreado.getId());

        assertEquals(d.toString(), departCreado.toString());


        sa.eliminarDepartamento(departCreado);
    }


    @Test
    void crearDepartamentoExistente() throws DepartamentoException {

        */
/*Departamento d1 = new EmpleadoTParcial("juan", "1234", Rol.valueOf(rol), dept);

        log.info("Creando departamento 1");
        d1 = b.crearDepartamento(d1);*//*



        Throwable exception = assertThrows(DepartamentoYaExisteExcepcion.class, () -> {

            Departamento d2 = d1;

            log.info("Creando departamento 2");
            d2 = sa.crearDepartamento(d2);

        });


        */
/*b.eliminarDepartamento(d1);*//*

    }

    @Test
    void crearDepartamentoNull() {


        Throwable exception = assertThrows(DepartamentoException.class, () -> {
            Departamento departCreado;
            departCreado = sa.crearDepartamento(null);

            assertNull(departCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearDepartamentoVacio() {

        Throwable exception = assertThrows(DepartamentoException.class, () -> {

            Departamento departCreado = sa.crearDepartamento(new Departamento());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearDepartamentoSiglasNull() {


        log.info("forzando siglas = null");
        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            d1.setSiglas(null);
            log.debug("d1= " + d1);
            Departamento departCreado = sa.crearDepartamento(d1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearDepartamentoSiglaVacio() {


        log.info("forzando siglas = ''");
        Throwable ex2 = assertThrows(DepartamentoException.class, () -> {

            d1.setSiglas("");
            log.debug("d1 = '" + d1 + "'");
            Departamento empleCreado = sa.crearDepartamento(d1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    */
/******************************************************************
     ****************   TEST BUSCAR DEPARTAMENTO ID  ******************
     ******************************************************************//*



    @Test
    void buscarByID() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByID");

        Departamento d = sa.buscarByID(d1.getId());
        log.info(d.toString());


        assertNotNull(d);
        assertEquals(d.getId(), d1.getId());
        assertEquals(d.getNombre(), d1.getNombre());
        assertEquals(d.toString(), d1.toString());

        //b.eliminarDepartamento(nuevo);

    }


    @Test
    void buscarByIDNegativo() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDNegativo");

        Throwable ex2 = assertThrows(DepartamentoException.class, () -> {

            Departamento d = sa.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(DepartamentoException.class, () -> {

            Departamento d = sa.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDInexixtente");

        d1.setId(30000L);
        Departamento buscado = sa.buscarByID(d1.getId());

        assertNull(buscado);

    }


    */
/******************************************************************
     ******************   TEST ELIMINAR DEPARTAMENTO   ********************
     ******************************************************************//*



*/
/*    @Test
    void eliminarDepartamento() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        Departamento e = new EmpleadoTParcial("Eliminar4", "pass", Rol.EMPLEADO, dept);
        log.info("Creando departamento");
        e = b.crearDepartamento(e);
        log.info("Asignando proyecto a departamento");
        DepartamentoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().añadirDepartamentoAProyecto(e, proy1, 5);
        proy1.getDepartamentos().add(ep);
        e.getProyectos().add(ep);

        log.info("Eliminando departamento");
        boolean resutl = b.eliminarDepartamento(e);

        log.debug("resutl = '" + resutl + "'");


        assertNull(b.buscarByID(e.getId()));

    }*//*



    */
/******************************************************************
     *****************   TEST LISTAR DEPARTAMENTOS   ******************
     ******************************************************************//*



    @Test
    void listarDepartamentos() {
        log.info("SA_DepartamentoImplTest.listarDepartamentos");

        ArrayList<Departamento> lista = (ArrayList) sa.listarDepartamentos();

        assertNotNull(lista);

        log.info("************************************************************");
        log.info("************************************************************");
        lista.stream().forEach(System.out::println);
        log.info("************************************************************");
        log.info("************************************************************");

    }

*/
/*
    @Test
    void saludo() {
        log.info("---- SA_DepartamentoImplTest.saludo ---- ");

        String nombre = "Rodrigo";
        String str = "Hola " + nombre + ", un saludo desde el servidor CXF :)";

        assertNotNull(b.saludar(nombre));

        assertTrue(b.saludar(nombre).equals(str));
    }
    *//*





    */
/******************************************************************
     ******************   TEST BUSCAR BY SIGLAS   *********************
     ******************************************************************//*


    @ParameterizedTest
    @CsvSource({"Departamento busquedas 1", "Departamento busquedas 2"})
    void buscarBySiglas(String nombre) throws DepartamentoException {
        Departamento nuevo, d1 = new Departamento(nombre);

        String siglas = d1.getSiglas();

        log.info("Creando departamento");
        nuevo = sa.crearDepartamento(d1);

        log.info("buscnado departamento");
        d1 = sa.buscarBySiglas(siglas);

        assertNotNull(d1);
        assertNotNull(nuevo);

        assertEquals(d1.toString(), nuevo.toString());

        sa.eliminarDepartamento(nuevo);

    }


    @Test
    void buscarBySiglasInexistente() throws DepartamentoException {


        Departamento e2 = sa.buscarBySiglas("asdfawefafafdwefa");

        assertNull(e2);

    }



    @Test
    void buscarBySiglasVacio() {

        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            Departamento e2 = sa.buscarBySiglas("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarBySiglasNull() {
        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            Departamento e2 = sa.buscarBySiglas(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    */
/******************************************************************
     *********************   METODOS AUXILIARES   *********************
     ******************************************************************//*



}*/
