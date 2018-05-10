
package Modulo_Proyecto.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl.Broker_SA_ProyectoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Broker_SA_ProyectoImplTest {


        static Broker_SA_ProyectoImpl b;

        static private TProyecto p1;
        static TProyectoCompleto p2;
        //    static Proyecto emple2;
        static TDepartamentoCompleto dept;

        static TEmpleadoCompleto emple1;

        final static Logger log = LoggerFactory.getLogger(Broker_SA_ProyectoImplTest.class);


        /*******************************************************************
         **********************   METODOS INICIALES   **********************
         *******************************************************************/

        @BeforeAll
        static void initSA() throws DepartamentoException, ProyectoException, EmpleadoException {
            log.info("Creando SA...");
            b = new Broker_SA_ProyectoImpl();


//        p2 = new ProyectoTParcial("p2", "1234", Rol.ADMIN);
            p2 = b.buscarByNombre("Proy1");

//        dept1 = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());

            dept = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas("DdP");

            emple1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);



        /*//dept = new Departamento("Ingenieria del Software");
        dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(3L);

        p1 = new ProyectoTCompleto("empleTest", "1234", Rol.PROYECTO, dept);

        //dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(1L);
        //dept = FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(dept);

        dept.getProyectos().add(p1);
        p1.setDepartamento(dept);

        p1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByEmail(p1.getEmail());*/
        }

    @BeforeEach
    void iniciarContexto() throws ProyectoException {

        String nombre = "ProyectoPrueba";

        log.info("Creando proyecto ");

        TProyectoCompleto aux = b.buscarByNombre(nombre);

        if (aux == null) {
            p1 = b.crearProyecto(new TProyecto(nombre));
        } else
            p1 = aux.getProyecto();
    }


    @AfterEach
    void finalizarContexto() throws ProyectoException {

       /* assertFalse(b.transactionIsActive(), "Transacción no cerrada");

        assertFalse(b.emIsOpen(), "Entity Manager no cerrado");
*/
        log.info("Eliminado proyecto");
        b.eliminarProyecto(p1);
    }


    /******************************************************************
     ********************   TEST CREAR PROYECTO   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearProyecto(String nombre) throws ProyectoException {


        TProyecto p = new TProyecto(nombre);

        TProyecto proyCreado = b.crearProyecto(p);


        p.setId(proyCreado.getId());
        log.debug("proyCreado = '" + proyCreado + "'");
        log.debug("p1          = '" + p + "'");

        assertNotNull(proyCreado);
        assertNotNull(proyCreado.getId());

        assertEquals(p.toString(), proyCreado.toString());


        b.eliminarProyecto(proyCreado);
    }


    @Test
    void crearProyectoExistente() throws ProyectoException {

        /*Proyecto p1 = new ProyectoTParcial("juan", "1234", Rol.valueOf(rol), dept);

        log.info("Creando proyecto 1");
        p1 = b.crearProyecto(p1);*/


        Throwable exception = assertThrows(ProyectoYaExistenteException.class, () -> {

            TProyecto e2 = p1;

            log.info("Creando proyecto 2");
            e2 = b.crearProyecto(e2);

        });


        /*b.eliminarProyecto(p1);*/
    }

    @Test
    void crearProyectoNull() {


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {
            TProyecto proyCreado;
            proyCreado = b.crearProyecto(null);

            assertNull(proyCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearProyectoVacio() {

        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyecto proyCreado = b.crearProyecto(new TProyecto());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearProyectoNombreNull() {


        log.info("forzando nombre = null");
        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            p1.setNombre(null);
            log.debug("p1= " + p1);
            TProyecto proyCreado = b.crearProyecto(p1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearProyectoNombreVacio() {


        log.info("forzando nombre = ''");
        Throwable ex2 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            p1.setNombre("");
            log.debug("p1 = '" + p1 + "'");
            TProyecto proyCreado = b.crearProyecto(p1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     ******************   TEST BUSCAR PROYECTO ID  ********************
     ******************************************************************/


    @Test
    void buscarByID() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarUsuarioByID");

        TProyectoCompleto p = b.buscarByID(p1.getId());
        log.info(p.toString());


        assertNotNull(p);
        assertEquals(p.getProyecto().getId(), p1.getId());
        assertEquals(p.getProyecto().getNombre(), p1.getNombre());
        assertEquals(p.getProyecto().getDescripcion(), p1.getDescripcion());

        log.debug("p.getFechaInicio() = '" + p.getProyecto().getFechaInicio() + "'");
        log.debug("p1.getFechaInicio() = '" + p1.getFechaInicio() + "'");
        log.debug("");
        log.debug("p.getFechaInicio().getTime =  '" + p.getProyecto().getFechaInicio().getTime() + "'");
        log.debug("p1.getFechaInicio().getTime = '" + p1.getFechaInicio().getTime() + "'");

        assertTrue(p.getProyecto().getFechaInicio().getYear() == p1.getFechaInicio().getYear());
        assertTrue(p.getProyecto().getFechaInicio().getMonth() == p1.getFechaInicio().getMonth());
        assertTrue(p.getProyecto().getFechaInicio().getDate() == p1.getFechaInicio().getDate());

        assertTrue(p.getProyecto().getFechaFin().getYear() == p1.getFechaFin().getYear());
        assertTrue(p.getProyecto().getFechaFin().getMonth() == p1.getFechaFin().getMonth());
        assertTrue(p.getProyecto().getFechaFin().getDate() == p1.getFechaFin().getDate());


        //b.eliminarProyecto(nuevo);

    }


    @Test
    void buscarByIDNegativo() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarByIDNegativo");

        Throwable ex2 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e = b.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e = b.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarByIDInexixtente");

        TProyectoCompleto buscado = b.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR PROYECTO   ********************
     ******************************************************************/

    @Test
    void eliminarProyecto() throws ProyectoException {
        log.info("SA_ProyectoImplTest.eliminarProyecto");


        log.info("Creando proyecto");
        TProyecto p = b.crearProyecto(new TProyecto("Eliminar3"));

        if (p != null) {

            log.info("Eliminando proyecto");
            boolean resutl = b.eliminarProyecto(p);

            log.debug("resutl = '" + resutl + "'");
            assertNull(b.buscarByID(p.getId()));

        }else
            fail("Proyecto no creado");


    }


    @Test
    void eliminarProyectoConEmpleados() throws ProyectoException {
        log.info("SA_ProyectoImplTest.eliminarProyecto");


        log.info("Creando proyecto");
        TProyectoCompleto p = new TProyectoCompleto();
        p.setProyecto(b.crearProyecto(new TProyecto("Eliminar2")));

        log.info("Asignando empleado a proyecto");
        TEmpleadoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().añadirEmpleadoAProyecto(emple1.getEmpleado(), p.getProyecto(), 5);

        emple1.agregarEmpleadoProyecto(ep, p.getProyecto());
        p.agregarEmpleadoProyecto(ep, emple1.getEmpleado());

        log.info("Eliminando proyecto");
        boolean resutl = b.eliminarProyecto(p.getProyecto());

        log.debug("resutl = '" + resutl + "'");


        assertNull(b.buscarByID(p.getId()));

    }

    @Test
    void eliminarProyectoNull() throws ProyectoException {


        Throwable exception = assertThrows(ProyectoException.class, () -> {
            boolean proy = b.eliminarProyecto(null);

            assertNull(proy);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarProyectoIDNegativo() throws ProyectoException {

        TProyecto proy = new TProyecto("eliminar1");
        proy.setId(-23L);


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {

            boolean result = b.eliminarProyecto(proy);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    /******************************************************************
     *******************   TEST LISTAR PROYECTOS   ********************
     ******************************************************************/


    @Test
    void listarProyectos() {
        log.info("ListarUsersTest");

        List<TProyecto> lista = b.listarProyectos();

        assertNotNull(lista);

        log.info("************************************************************");
        log.info("************************************************************");
        lista.stream().forEach(System.out::println);
        log.info("************************************************************");
        log.info("************************************************************");

    }


    /******************************************************************
     *******************   TEST BUSCAR BY NOMBRE   ********************
     ******************************************************************/

    @ParameterizedTest
    @CsvSource({"buscar1", "buscar2"})
    void buscarByNombre(String nombre) throws ProyectoException {
        TProyecto nuevo;
        TProyectoCompleto p1 = new TProyectoCompleto(new TProyecto(nombre));

        String nombre1 = p1.getNombre();

        log.info("Creando proyecto");
        nuevo = b.crearProyecto(p1.getProyecto());

        log.info("buscnado proyecto");
        p1 = b.buscarByNombre(nombre1);


//        assertEquals(p1.getProyecto().toString(), nuevo.toString());

        assertEquals(nuevo.getId(), p1.getId());
        assertEquals(nuevo.getNombre(), p1.getNombre());
        assertEquals(nuevo.getDescripcion(), p1.getProyecto().getDescripcion());

        assertTrue(nuevo.getFechaInicio().getYear() == p1.getProyecto().getFechaInicio().getYear());
        assertTrue(nuevo.getFechaInicio().getMonth() == p1.getProyecto().getFechaInicio().getMonth());
        assertTrue(nuevo.getFechaInicio().getDate() == p1.getProyecto().getFechaInicio().getDate());

        assertTrue(nuevo.getFechaFin().getYear() == p1.getProyecto().getFechaFin().getYear());
        assertTrue(nuevo.getFechaFin().getMonth() == p1.getProyecto().getFechaFin().getMonth());
        assertTrue(nuevo.getFechaFin().getDate() == p1.getProyecto().getFechaFin().getDate());


        b.eliminarProyecto(nuevo);

    }


    @Test
    void buscarByNombreInexistente() throws ProyectoException {


        TProyectoCompleto e2 = b.buscarByNombre("awdffafgasgfsgag");

        assertNull(e2);


    }

    @Test
    void buscarByNombreVacio() {

        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e2 = b.buscarByNombre("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByNombreNull() {
        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e2 = b.buscarByNombre(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    /******************************************************************
     *********************   METODOS AUXILIARES   *********************
     ******************************************************************/


}