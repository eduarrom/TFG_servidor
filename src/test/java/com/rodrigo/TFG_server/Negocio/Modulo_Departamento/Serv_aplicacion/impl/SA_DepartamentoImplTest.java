
package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SA_DepartamentoImplTest {

    static SA_Departamento sa;

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
        sa = FactoriaSA.getInstance().crearSA_Departamento();


//        emple2 = new DepartamentoTCompleto("emple2", "1234", Rol.EMPLEADO);
        empld1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

//        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());
        dept = sa.buscarBySiglas("DdP");

        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);
/*


        emple = new Departamento("Ingenieria del Software");
        emple = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(3L);

        d1 = new DepartamentoTCompleto("empleTest", "1234", Rol.EMPLEADO, emple);

        //emple = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(1L);
        //emple = FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(emple);

        emple.getDepartamentos().add(d1);
        d1.setDepartamento(emple);

        d1 = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(d1.getSiglas());
*/

    }

    @BeforeEach
    void iniciarContexto() throws DepartamentoException, EmpleadoException {
        String siglas = "DT";

        log.info("Creando departamento ");
        TDepartamentoCompleto auxD = sa.buscarBySiglas(siglas);
        if (auxD == null) {
            d1 = new TDepartamentoCompleto(sa.crearDepartamento(new TDepartamento("Departamento Test")));
        } else
            d1 = auxD;


        String nombre = "empleTest";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            e1 = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", d1.getId()));
        } else
            e1 = auxE;


        d1 = sa.buscarBySiglas(siglas);


    }


    @AfterEach
    void finalizarContexto() throws DepartamentoException, EmpleadoException {

        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(e1.getEmpleado().getId());

        log.info("Eliminado departamento");
        sa.eliminarDepartamento(d1.getId());

    }


    /******************************************************************
     ******************   TEST CREAR DEPARTAMENTO   *******************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearDepartamento(String nombre) throws DepartamentoException {

        TDepartamento d = new TDepartamento(nombre);
        TDepartamento departCreado = sa.crearDepartamento(d);


        d.setId(departCreado.getId());
        log.debug("departCreado = '" + departCreado + "'");
        log.debug("d         = '" + d + "'");

        assertNotNull(departCreado);
        assertNotNull(departCreado.getId());

        assertEquals(d.toString(), departCreado.toString());


        sa.eliminarDepartamento(departCreado.getId());
    }


    @Test
    void crearDepartamentoExistente() throws DepartamentoException {


        TDepartamento d = new TDepartamento("Existente");

        log.info("Creando departamento 1");
        d = sa.crearDepartamento(d);


        TDepartamento finalD = d;
        Throwable exception = assertThrows(DepartamentoYaExisteExcepcion.class, () -> {

            TDepartamento d2 = finalD;

            log.info("Creando departamento 2");
            d2 = sa.crearDepartamento(d2);

        });


        sa.eliminarDepartamento(d.getId());

    }

    @Test
    void crearDepartamentoNull() {


        Throwable exception = assertThrows(DepartamentoFieldInvalidException.class, () -> {
            TDepartamento departCreado = sa.crearDepartamento(null);

            assertNull(departCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearDepartamentoVacio() {

        Throwable exception = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            TDepartamento departCreado = sa.crearDepartamento(new TDepartamento());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearDepartamentoSiglasNull() {


        log.info("forzando siglas = null");
        Throwable ex1 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            d1.setSiglas(null);
            log.debug("d1= " + d1);
            TDepartamento departCreado = sa.crearDepartamento(d1.getDepartamento());

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearDepartamentoSiglasVacio() {


        log.info("forzando siglas = ''");
        Throwable ex2 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            d1.setSiglas("");
            log.debug("d1 = [" + d1 + "]");
            TDepartamento empleCreado = sa.crearDepartamento(d1.getDepartamento());

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ****************   TEST BUSCAR DEPARTAMENTO ID  ******************
     ******************************************************************/


    @Test
    void buscarByID() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByID");

        TDepartamentoCompleto d = sa.buscarByID(d1.getId());
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

        Throwable ex2 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            TDepartamentoCompleto d = sa.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            TDepartamentoCompleto d = sa.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws DepartamentoException {
        log.info("SA_DepartamentoImplTest.buscarByIDInexixtente");

        TDepartamentoCompleto buscado = sa.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR DEPARTAMENTO   ********************
     ******************************************************************/


    @Test
    void eliminarDepartamento() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        TDepartamento d = new TDepartamento("Eliminar 3");

        log.info("Creando departamento");
        d = sa.crearDepartamento(d);

        log.info("Eliminando departamento");
        boolean resutl = sa.eliminarDepartamento(d.getId());

        log.debug("resutl = '" + resutl + "'");

        assertNull(sa.buscarByID(d.getId()));

    }


    @Test
    void eliminarDepartamentoConEmpleados() throws DepartamentoException, EmpleadoException {
        log.info("SA_DepartamentoImplTest.eliminarDepartamento");

        log.info("Creando departamento");

        TDepartamentoCompleto d = new TDepartamentoCompleto(
                sa.crearDepartamento(new TDepartamento("Eliminar 3")));


        String nombre = "EmpleEliminar";

        log.info("Creando empleado ");
        TEmpleadoCompleto auxE = FactoriaSA.getInstance().crearSA_Empleado().buscarByEmail(nombre.toLowerCase().concat("@gmail.com"));

        if (auxE == null) {
            auxE = FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(new TEmpleadoTCompleto(nombre, "1234", d.getId()));
        }

        d = sa.buscarBySiglas(d.getSiglas());


        TDepartamentoCompleto finalD = d;
        Throwable ex1 = assertThrows(DepartamentoConEmpleadosException.class, () -> {

            log.info("Eliminando departamento");
            boolean result = sa.eliminarDepartamento(finalD.getId());


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

        FactoriaSA.getInstance().crearSA_Empleado().eliminarEmpleado(auxE.getEmpleado().getId());
        boolean result = sa.eliminarDepartamento(finalD.getId());

    }


    @Test
    void eliminarDepartamentoNull()  {


        Throwable exception = assertThrows(DepartamentoFieldInvalidException.class, () -> {
            boolean emple = sa.eliminarDepartamento(null);

            assertNull(emple);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarDepartamentoIDNegativo()  {

        TDepartamento depart = new TDepartamento("Eiminar");
        depart.setId(-23L);


        Throwable exception = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            boolean result = sa.eliminarDepartamento(depart.getId());

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    /******************************************************************
     *****************   TEST LISTAR DEPARTAMENTOS   ******************
     ******************************************************************/


    @Test
    void listarDepartamentos() {
        log.info("SA_DepartamentoImplTest.listarDepartamentos");

        List<TDepartamento> lista = sa.listarDepartamentos();

        assertNotNull(lista);

        log.info("************************************************************");
        log.info("************************************************************");
        lista.stream().forEach(System.out::println);
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
        nuevo = sa.crearDepartamento(d1.getDepartamento());

        log.info("buscando departamento");
        d1 = sa.buscarBySiglas(siglas);

        assertNotNull(d1);
        assertNotNull(nuevo);

        assertEquals(d1.getDepartamento().toString(), nuevo.toString());

        sa.eliminarDepartamento(nuevo.getId());

    }


    @Test
    void buscarBySiglasInexistente() throws DepartamentoException {


        TDepartamentoCompleto e2 = sa.buscarBySiglas("asdfawefafafdwefa");

        assertNull(e2);

    }


    @Test
    void buscarBySiglasVacio() {

        Throwable ex1 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            TDepartamentoCompleto e2 = sa.buscarBySiglas("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarBySiglasNull() {
        Throwable ex1 = assertThrows(DepartamentoFieldInvalidException.class, () -> {

            TDepartamentoCompleto e2 = sa.buscarBySiglas(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


/******************************************************************
 *********************   METODOS AUXILIARES   *********************
 ******************************************************************/


}
