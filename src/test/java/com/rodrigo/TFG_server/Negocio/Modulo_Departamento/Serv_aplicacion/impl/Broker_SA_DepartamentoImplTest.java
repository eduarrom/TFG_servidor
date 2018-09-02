package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
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


import javax.ws.rs.core.Response;
import java.util.Arrays;

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

        Response res = b.buscarBySiglas("DdP");
        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());

        dept = res.readEntity(TDepartamentoCompleto.class);

        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);


    }

    @BeforeEach
    void iniciarContexto() throws EmpleadoException {
        String siglas = "DT";
        TDepartamentoCompleto auxD = null;

        log.info("Creando departamento ");
        Response res = b.buscarBySiglas(siglas);

        if(res.getStatus() == Response.Status.OK.getStatusCode()) {
            d1 = res.readEntity(TDepartamentoCompleto.class);

        }else if (res.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {

            res = b.crearDepartamento(new TDepartamento("Departamento Test"));
            assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());
            TDepartamento dept = res.readEntity(TDepartamento.class);
            d1 = new TDepartamentoCompleto();
            d1.setDepartamento(dept);
        }


        String nombre = "empleTest";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            e1 = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", d1.getId()));
        } else
            e1 = auxE;


        res = b.buscarBySiglas(siglas);
        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());

        d1 = res.readEntity(TDepartamentoCompleto.class);


    }


    @AfterEach
    void finalizarContexto() throws DepartamentoException, EmpleadoException {

        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(e1.getEmpleado().getId());

        log.info("Eliminado departamento");
        b.eliminarDepartamento(d1.getId());

    }


    /******************************************************************
     ******************   TEST CREAR DEPARTAMENTO   *******************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearDepartamento(String nombre) throws DepartamentoException {

        TDepartamento d = new TDepartamento(nombre);

        Response res = b.crearDepartamento(d);
        assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());


        TDepartamento departCreado = res.readEntity(TDepartamento.class);


        d.setId(departCreado.getId());
        log.debug("departCreado = '" + departCreado + "'");
        log.debug("d         = '" + d + "'");

        assertNotNull(departCreado);
        assertNotNull(departCreado.getId());
        assertEquals(d.toString(), departCreado.toString());


        b.eliminarDepartamento(departCreado.getId());
    }


    @Test
    void crearDepartamentoExistente() throws DepartamentoException {


        TDepartamento d = new TDepartamento("Existente");

        log.info("Creando departamento 1");
        Response res = b.crearDepartamento(d);
        assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());
        d = res.readEntity(TDepartamento.class);


        TDepartamento d2 = d;
        log.info("Creando departamento EXISTENTE");
        Response res2 = b.crearDepartamento(d2);


        assertTrue(res2.getStatus() == Response.Status.BAD_GATEWAY.getStatusCode());


        b.eliminarDepartamento(d.getId());

    }

    @Test
    void crearDepartamentoNull() {


        Response res = b.crearDepartamento(null);

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }


    @Test
    void crearDepartamentoVacio() {

        Response res = b.crearDepartamento(new TDepartamento());

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }


    @Test
    void crearDepartamentoSiglasNull() {


        log.info("forzando siglas = null");

        TDepartamento d = new TDepartamento("Siglas null");
        d.setSiglas(null);
        log.debug("d= " + d);
        Response res = b.crearDepartamento(d);

        System.out.println("res.getStatus() = [" + res.getStatus() + "]");
        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }

    @Test
    void crearDepartamentoSiglasVacio() {


        log.info("forzando siglas = ''");

        TDepartamento d = new TDepartamento("Siglas null");
        d.setSiglas("");
        log.debug("d= " + d);
        Response res = b.crearDepartamento(d);

        System.out.println("res.getStatus() = [" + res.getStatus() + "]");
        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }


    /******************************************************************
     ****************   TEST BUSCAR DEPARTAMENTO ID  ******************
     ******************************************************************/


    @Test
    void buscarByID() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByID");

        Response res = b.buscarByID(d1.getId());

        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());

        TDepartamentoCompleto d = res.readEntity(TDepartamentoCompleto.class);

        log.info(d.toString());


        assertNotNull(d);
        assertEquals(d.getId(), d1.getId());
        assertEquals(d.getDepartamento().getNombre(), d1.getDepartamento().getNombre());
        assertEquals(d.toString(), d1.toString());

        //b.eliminarDepartamento(nuevo);

    }


    @Test
    void buscarByIDNegativo() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDNegativo");

        Response res = b.buscarByID(-2L);
        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }

    @Test
    void buscarByIDCero() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDCero");

        Response res = b.buscarByID(0L);
        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());

    }


    @Test
    void buscarByIDInexixtente() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDInexixtente");

        Response res = b.buscarByID(30000L);
        System.out.println("res.getStatus() = [" + res.getStatus() + "]");
        assertTrue(res.getStatus() == Response.Status.NOT_FOUND.getStatusCode());


    }


    /******************************************************************
     ******************   TEST ELIMINAR DEPARTAMENTO   ********************
     ******************************************************************/


    @Test
    void eliminarDepartamento() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        TDepartamento d = new TDepartamento("Eliminar 1");

        log.info("Creando departamento");
        Response res = b.crearDepartamento(d);
        assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());
        d = res.readEntity(TDepartamento.class);

        log.info("Eliminando departamento");
        res = b.eliminarDepartamento(d.getId());
        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());


        assertNull((Departamento) b.buscarByID(d.getId()).getEntity());

    }


    @Test
    void eliminarDepartamentoConEmpleados() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        TDepartamentoCompleto d = new TDepartamentoCompleto(new TDepartamento("Eliminar 2"));
        log.info("Creando departamento");
        Response res = b.crearDepartamento(d.getDepartamento());
        assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());
        d.setDepartamento(res.readEntity(TDepartamento.class));


        String nombre = "EmpleEliminar";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            auxE = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", d.getId()));
        }


        log.info("Buscando departamento completo");
        res = b.buscarBySiglas(d.getSiglas());
        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());
        d = res.readEntity(TDepartamentoCompleto.class);


        TDepartamento finalD = d.getDepartamento();

        log.info("Eliminando departamento con empleados");
        Response res2 = b.eliminarDepartamento(finalD.getId());

        assertTrue(res2.getStatus() == Response.Status.BAD_GATEWAY.getStatusCode());


        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(auxE.getEmpleado().getId());
        res2 = b.eliminarDepartamento(finalD.getId());

    }


    @Test
    void eliminarDepartamentoNull() {


        Response res = b.eliminarDepartamento(null);

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }


    @Test
    void eliminarDepartamentoIDNegativo() {


        Response res = b.eliminarDepartamento(-2L);

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


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
        Response res = b.crearDepartamento(d1.getDepartamento());
        assertTrue(res.getStatus() == Response.Status.CREATED.getStatusCode());
        nuevo = res.readEntity(TDepartamento.class);

        log.info("buscando departamento");
        res = b.buscarBySiglas(siglas);
        assertTrue(res.getStatus() == Response.Status.OK.getStatusCode());


        d1 = res.readEntity(TDepartamentoCompleto.class);

        assertNotNull(d1);
        assertNotNull(nuevo);

        assertEquals(d1.getDepartamento().toString(), nuevo.toString());

        b.eliminarDepartamento(nuevo.getId());

    }


    @Test
    void buscarBySiglasInexistente() {

        Response res = b.buscarBySiglas("asdfawefafafdwefa");

        assertTrue(res.getStatus() == Response.Status.NOT_FOUND.getStatusCode());

    }


    @Test
    void buscarBySiglasVacio() {

        Response res = b.buscarBySiglas("");

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());

    }


    @Test
    void buscarBySiglasNull() {

        Response res = b.buscarBySiglas(null);

        assertTrue(res.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());


    }


/******************************************************************
 *********************   METODOS AUXILIARES   *********************
 ******************************************************************/


}
