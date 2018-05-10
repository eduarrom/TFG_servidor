package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Broker_SA_DepartamentoImplTest {

    static Broker_SA_DepartamentoImpl b;

    static private TDepartamentoCompleto d1;
    static private TEmpleadoCompleto e1;


    static TEmpleadoCompleto empld1;
    //    static Departamento emple2;
    static TDepartamentoCompleto dept;
    static TProyectoCompleto proy1;

    final static Logger log = LoggerFactory.getLogger(SA_DepartamentoImplTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/


    @BeforeAll
    static void initSA() throws DepartamentoException, EmpleadoException, ProyectoException {
        log.info("Creando SA...");
        b = new Broker_SA_DepartamentoImpl();


        empld1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

        dept = b.buscarBySiglas("DdP");

        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);


    }

    @BeforeEach
    void iniciarContexto() throws DepartamentoException, EmpleadoException {
        String siglas = "DT";

        log.info("Creando departamento ");
        TDepartamentoCompleto auxD = b.buscarBySiglas(siglas);
        if (auxD == null) {
            d1 = new TDepartamentoCompleto(b.crearDepartamento(new TDepartamento("Departamento Test")));
        } else
            d1 = auxD;


        String nombre = "empleTest";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            e1 = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", Rol.EMPLEADO, d1.getId()));
        } else
            e1 = auxE;


        d1 = b.buscarBySiglas(siglas);


    }


    @AfterEach
    void finalizarContexto() throws DepartamentoException, EmpleadoException {

        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(e1.getEmpleado());

        log.info("Eliminado departamento");
        b.eliminarDepartamento(d1);

    }


    /******************************************************************
     ******************   TEST CREAR DEPARTAMENTO   *******************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearDepartamento(String nombre) throws DepartamentoException {

        TDepartamento d = new TDepartamento(nombre);
        TDepartamento departCreado = b.crearDepartamento(d);


        d.setId(departCreado.getId());
        log.debug("departCreado = '" + departCreado + "'");
        log.debug("d         = '" + d + "'");

        assertNotNull(departCreado);
        assertNotNull(departCreado.getId());

        assertEquals(d.toString(), departCreado.toString());


        b.eliminarDepartamento(departCreado);
    }


    @Test
    void crearDepartamentoExistente() throws DepartamentoException {


        TDepartamento d = new TDepartamento("Existente");

        log.info("Creando departamento 1");
        d = b.crearDepartamento(d);


        TDepartamento finalD = d;
        Throwable exception = assertThrows(DepartamentoYaExisteExcepcion.class, () -> {

            TDepartamento d2 = finalD;

            log.info("Creando departamento 2");
            d2 = b.crearDepartamento(d2);

        });


        b.eliminarDepartamento(d);

    }

    @Test
    void crearDepartamentoNull() {


        Throwable exception = assertThrows(DepartamentoException.class, () -> {
            TDepartamento departCreado = b.crearDepartamento(null);

            assertNull(departCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearDepartamentoVacio() {

        Throwable exception = assertThrows(DepartamentoException.class, () -> {

            TDepartamento departCreado = b.crearDepartamento(new TDepartamento());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearDepartamentoSiglasNull() {


        log.info("forzando siglas = null");
        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            d1.setSiglas(null);
            log.debug("d1= " + d1);
            TDepartamento departCreado = b.crearDepartamento(d1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearDepartamentoSiglaVacio() {


        log.info("forzando siglas = ''");
        Throwable ex2 = assertThrows(DepartamentoException.class, () -> {

            d1.setSiglas("");
            log.debug("d1 = '" + d1 + "'");
            TDepartamento empleCreado = b.crearDepartamento(d1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ****************   TEST BUSCAR DEPARTAMENTO ID  ******************
     ******************************************************************/


    @Test
    void buscarByID() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByID");

        TDepartamentoCompleto d = b.buscarByID(d1.getId());
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

            TDepartamentoCompleto d = b.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(DepartamentoException.class, () -> {

            TDepartamentoCompleto d = b.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDInexixtente");

        TDepartamentoCompleto buscado = b.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR DEPARTAMENTO   ********************
     ******************************************************************/


    @Test
    void eliminarDepartamento() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        TDepartamento d = new TDepartamento("Eliminar");

        log.info("Creando departamento");
        d = b.crearDepartamento(d);

        log.info("Eliminando departamento");
        boolean resutl = b.eliminarDepartamento(d);

        log.debug("resutl = '" + resutl + "'");

        assertNull(b.buscarByID(d.getId()));

    }


    @Test
    void eliminarDepartamentoConEmpleados() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        TDepartamento d = new TDepartamento("Eliminar");
        log.info("Creando departamento");

        d = b.crearDepartamento(d);


        String nombre = "EmpleEliminar";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            auxE = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", Rol.EMPLEADO, d.getId()));
        }

        d = b.buscarBySiglas(d.getSiglas());


        TDepartamento finalD = d;
        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            log.info("Eliminando departamento");
            boolean result = b.eliminarDepartamento(finalD);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(auxE.getEmpleado());
        boolean result = b.eliminarDepartamento(finalD);

    }


    @Test
    void eliminarDepartamentoNull() {


        Throwable exception = assertThrows(DepartamentoException.class, () -> {
            boolean emple = b.eliminarDepartamento(null);

            assertNull(emple);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarDepartamentoIDNegativo() {

        TDepartamento depart = new TDepartamento("Eiminar");
        depart.setId(-23L);


        Throwable exception = assertThrows(DepartamentoException.class, () -> {

            boolean result = b.eliminarDepartamento(depart);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    /******************************************************************
     *****************   TEST LISTAR DEPARTAMENTOS   ******************
     ******************************************************************/


    @Test
    void listarDepartamentos() {
        log.info("SA_DepartamentoImplTest.listarDepartamentos");

        TDepartamento[] lista = b.listarDepartamentos();

        assertNotNull(lista);

        log.info("************************************************************");
        log.info("************************************************************");
        Arrays.asList(lista).stream().forEach(System.out::println);
        log.info("************************************************************");
        log.info("************************************************************");

    }


    /******************************************************************
     ******************   TEST BUSCAR BY SIGLAS   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"Departamento de Busquedas 1", "Departamento de Busquedas 2"})
    void buscarBySiglas(String nombre) throws DepartamentoException {
        TDepartamentoCompleto d1 = new TDepartamentoCompleto(new TDepartamento(nombre));
        TDepartamento nuevo;

        String siglas = d1.getSiglas();

        log.info("Creando departamento");
        nuevo = b.crearDepartamento(d1.getDepartamento());

        log.info("buscando departamento");
        d1 = b.buscarBySiglas(siglas);

        assertNotNull(d1);
        assertNotNull(nuevo);

        assertEquals(d1.getDepartamento().toString(), nuevo.toString());

        b.eliminarDepartamento(nuevo);

    }


    @Test
    void buscarBySiglasInexistente() throws DepartamentoException {


        TDepartamentoCompleto e2 = b.buscarBySiglas("asdfawefafafdwefa");

        assertNull(e2);

    }


    @Test
    void buscarBySiglasVacio() {

        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            TDepartamentoCompleto e2 = b.buscarBySiglas("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarBySiglasNull() {
        Throwable ex1 = assertThrows(DepartamentoException.class, () -> {

            TDepartamentoCompleto e2 = b.buscarBySiglas(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


/******************************************************************
 *********************   METODOS AUXILIARES   *********************
 ******************************************************************/


}
