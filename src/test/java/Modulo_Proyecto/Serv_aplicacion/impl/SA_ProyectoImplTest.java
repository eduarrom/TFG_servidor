package Modulo_Proyecto.Serv_aplicacion.impl;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
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

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
class SA_ProyectoImplTest {

    static SA_Proyecto sa;

    static private TProyecto p1;
    static TProyectoCompleto p2;
    //    static Proyecto emple2;
    static TDepartamentoCompleto dept;

    static TEmpleadoCompleto emple1;

    final static Logger log = LoggerFactory.getLogger(SA_ProyectoImplTest.class);


    /*******************************************************************
     **********************   METODOS INICIALES   **********************
     *******************************************************************/

    @BeforeAll
    static void initSA() throws DepartamentoException, ProyectoException, EmpleadoException {
        log.info("Creando SA...");
        sa = FactoriaSA.getInstance().crearSA_Proyecto();


//        p2 = new ProyectoTParcial("p2", "1234", Rol.ADMIN);
        p2 = sa.buscarByNombre("Proy1");

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

        TProyectoCompleto aux = sa.buscarByNombre(nombre);

        if (aux == null) {
            p1 = sa.crearProyecto(new TProyecto(nombre));
        } else
            p1 = aux.getProyecto();
    }


    @AfterEach
    void finalizarContexto() throws ProyectoException {

       /* assertFalse(b.transactionIsActive(), "Transacción no cerrada");

        assertFalse(b.emIsOpen(), "Entity Manager no cerrado");
*/
        log.info("Eliminado proyecto");
        sa.eliminarProyecto(p1.getId());
    }


    /******************************************************************
     ********************   TEST CREAR PROYECTO   *********************
     ******************************************************************/


    @ParameterizedTest
    @CsvSource({"crear1", "crear2", "crear3"})
    void crearProyecto(String nombre) throws ProyectoException {


        TProyecto p = new TProyecto(nombre);

        TProyecto proyCreado = sa.crearProyecto(p);


        p.setId(proyCreado.getId());
        log.debug("proyCreado = '" + proyCreado + "'");
        log.debug("p1          = '" + p + "'");

        assertNotNull(proyCreado);
        assertNotNull(proyCreado.getId());

        assertEquals(p.toString(), proyCreado.toString());


        sa.eliminarProyecto(proyCreado.getId());
    }


    @Test
    void crearProyectoExistente() throws ProyectoException {

        /*Proyecto p1 = new ProyectoTParcial("juan", "1234", Rol.valueOf(rol), dept);

        log.info("Creando proyecto 1");
        p1 = b.crearProyecto(p1);*/


        Throwable exception = assertThrows(ProyectoYaExistenteException.class, () -> {

            TProyecto e2 = p1;

            log.info("Creando proyecto 2");
            e2 = sa.crearProyecto(e2);

        });


        /*b.eliminarProyecto(p1);*/
    }

    @Test
    void crearProyectoNull() {


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {
            TProyecto proyCreado;
            proyCreado = sa.crearProyecto(null);

            assertNull(proyCreado);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void crearProyectoVacio() {

        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyecto proyCreado = sa.crearProyecto(new TProyecto());

        });

        log.info("Excepcion capturada:" + exception.getMessage());
    }


    @Test
    void crearProyectoNombreNull() {


        log.info("forzando nombre = null");
        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            p1.setNombre(null);
            log.debug("p1= " + p1);
            TProyecto proyCreado = sa.crearProyecto(p1);

        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }

    @Test
    void crearProyectoNombreVacio() {


        log.info("forzando nombre = ''");
        Throwable ex2 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            p1.setNombre("");
            log.debug("p1 = '" + p1 + "'");
            TProyecto proyCreado = sa.crearProyecto(p1);

        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    /******************************************************************
     **************   TEST ASIGNAR EMPLEADO PROYECTO   ***************
     ******************************************************************/

    @Test
    void asignarEmpleadoAProyecto() throws ProyectoException, EmpleadoException {


        TEmpleadoProyecto tep = FactoriaSA
                .getInstance()
                .crearSA_Proyecto()
                .agregarEmpleadoAProyecto(emple1.getEmpleado(), p1, 555);

        assertNotNull(tep);


        TEmpleadoCompleto e = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(emple1.getId());

        assertTrue(e.getProyectos().containsKey(p1.getId()));

        TProyectoCompleto p = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(p1.getId());

        assertTrue(p.getEmpleados().containsKey(emple1.getId()));


    }

    @Test
    void asignarEmpleadoAProyectoExistente() throws ProyectoException, EmpleadoException {


        TEmpleadoProyecto tep = FactoriaSA
                .getInstance()
                .crearSA_Proyecto()
                .agregarEmpleadoAProyecto(emple1.getEmpleado(), p1, 222);

        assertNotNull(tep);

        tep = FactoriaSA
                .getInstance()
                .crearSA_Proyecto()
                .agregarEmpleadoAProyecto(emple1.getEmpleado(), p1, 555);


        assertNotNull(tep);


        TEmpleadoCompleto e = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(emple1.getId());

        assertTrue(e.getProyectos().containsKey(p1.getId()));

        TProyectoCompleto p = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(p1.getId());

        assertTrue(p.getEmpleados().containsKey(emple1.getId()));


    }


    @Test
    void asignarEmpleadoAProyecto_EmpleNull() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(EmpleadoFieldInvalidException.class, () -> {
            TEmpleadoProyecto tep = FactoriaSA.getInstance().crearSA_Proyecto().agregarEmpleadoAProyecto(null, p1, 5);

            assertNull(tep);

        });

        log.error("----  EXCEPCION! ----", exception);

    }

    @Test
    void asignarEmpleadoAProyecto_ProyNull() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {
            TEmpleadoProyecto tep = FactoriaSA.getInstance().crearSA_Proyecto().agregarEmpleadoAProyecto(emple1.getEmpleado(), null, 5);

            assertNull(tep);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void asignarEmpleadoAProyecto_horasNull() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {
            TEmpleadoProyecto tep = FactoriaSA
                    .getInstance()
                    .crearSA_Proyecto()
                    .agregarEmpleadoAProyecto(emple1.getEmpleado(), p1, 0);

            assertNull(tep);

        });

        log.error("----  EXCEPCION! ----", exception);

    }

    @Test
    void asignarEmpleadoAProyecto_EmpleInvalido() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto ec = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

            ec.setId(3000L);
            TEmpleadoProyecto tep = FactoriaSA.getInstance().crearSA_Proyecto().agregarEmpleadoAProyecto(ec.getEmpleado(), p1, 5);

            assertNull(tep);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void asignarEmpleadoAProyecto_ProyInvalido() throws ProyectoException, EmpleadoException {


        TProyecto p = sa.crearProyecto(new TProyecto("prueba"));

        Long idAux = p.getId();
        p.setId(3000L);
        Throwable exception = assertThrows(ProyectoException.class, () -> {


            TEmpleadoProyecto tep = FactoriaSA
                    .getInstance()
                    .crearSA_Proyecto()
                    .agregarEmpleadoAProyecto(emple1.getEmpleado(), p, 5);

            assertNull(tep);


        });

        log.error("----  EXCEPCION! ----", exception);

        boolean result = sa.eliminarProyecto(idAux);

        assertTrue(result);

    }




    /******************************************************************
     **************   TEST ELIMINAR EMPLEADO PROYECTO   ***************
     ******************************************************************/

    @Test
    void eliminarEmpleadoAProyecto() throws ProyectoException, EmpleadoException {


        TEmpleadoProyecto tep = sa.agregarEmpleadoAProyecto(emple1.getEmpleado(), p1, 6);

        assertNotNull(tep);

        boolean result = sa.eliminarEmpleadoAProyecto(tep.getEmpleado(), tep.getProyecto());

        assertTrue(result);

        assertFalse(emple1.getProyectos().containsKey(p1.getId()));


    }


    @Test
    void eliminarEmpleadoAProyecto_EmpleNull() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(EmpleadoFieldInvalidException.class, () -> {

            boolean result = sa.eliminarEmpleadoAProyecto(null, 23L);


        });



        log.error("----  EXCEPCION! ----", exception);

    }

    @Test
    void eliminarEmpleadoAProyecto_ProyNull() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {
            boolean result = sa.eliminarEmpleadoAProyecto(33L, null);


        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarEmpleadoAProyecto_EmpleInvalido() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(EmpleadoException.class, () -> {

            TEmpleadoCompleto ec = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

            ec.setId(3000L);
            boolean result = sa.eliminarEmpleadoAProyecto(ec.getEmpleado().getId(), p1.getId());

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarEmpleadoAProyecto_ProyInvalido() throws ProyectoException, EmpleadoException {


        Throwable exception = assertThrows(ProyectoException.class, () -> {

            TEmpleadoCompleto ec = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

            boolean result = sa.eliminarEmpleadoAProyecto(ec.getEmpleado().getId(),3000L);

        });

        log.error("----  EXCEPCION! ----", exception);


    }





    /******************************************************************
     ******************   TEST BUSCAR PROYECTO ID  ********************
     ******************************************************************/


    @Test
    void buscarByID() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarUsuarioByID");

        TProyectoCompleto p = sa.buscarByID(p1.getId());
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

            TProyectoCompleto e = sa.buscarByID(-2L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());

    }

    @Test
    void buscarByIDCero() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarByIDCero");

        Throwable ex2 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e = sa.buscarByID(0L);


        });
        log.info("Excepcion capturada:" + ex2.getMessage());


    }


    @Test
    void buscarByIDInexixtente() throws ProyectoException {
        log.info("SA_ProyectoImplTest.buscarByIDInexixtente");

        TProyectoCompleto buscado = sa.buscarByID(30000L);

        assertNull(buscado);

    }


    /******************************************************************
     ******************   TEST ELIMINAR PROYECTO   ********************
     ******************************************************************/

    @Test
    void eliminarProyecto() throws EmpleadoException, ProyectoException {
        log.info("SA_EmpleadoImplTest.eliminarEmpleado");


        log.info("Creando proyecto");
        TProyecto p = sa.crearProyecto(new TProyecto("Eliminar1"));
        TProyectoCompleto tpc = new TProyectoCompleto(p);

        log.info("Eliminando proyecto");
        boolean resutl = sa.eliminarProyecto(tpc.getProyecto().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(tpc.getId()));

    }

    @Test
    void eliminarProyecto_conEmpleados() throws EmpleadoException, ProyectoException {
        log.info("SA_EmpleadoImplTest.eliminarEmpleado");


        log.info("Creando proyecto");
        TProyecto p = sa.crearProyecto(new TProyecto("Eliminar1"));
        TProyectoCompleto tpc = new TProyectoCompleto(p);

        log.info("Asignando proyecto a empleado");
        TEmpleadoProyecto ep = sa.agregarEmpleadoAProyecto(emple1.getEmpleado(), tpc.getProyecto(), 5);

        tpc.agregarEmpleadoProyecto(ep, emple1.getEmpleado());
        emple1.agregarEmpleadoProyecto(ep, tpc.getProyecto());

        log.info("Eliminando proyecto");
        boolean resutl = sa.eliminarProyecto(tpc.getProyecto().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(tpc.getId()));

    }

    @Test
    void eliminarProyectoConEmpleados() throws ProyectoException, EmpleadoException {
        log.info("SA_ProyectoImplTest.eliminarProyecto");


        log.info("Creando proyecto");
        TProyectoCompleto p = new TProyectoCompleto();
        p.setProyecto(sa.crearProyecto(new TProyecto("Eliminar 6")));

        log.info("Asignando empleado a proyecto");
        TEmpleadoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().agregarEmpleadoAProyecto(emple1.getEmpleado(), p.getProyecto(), 5);

        emple1.agregarEmpleadoProyecto(ep, p.getProyecto());
        p.agregarEmpleadoProyecto(ep, emple1.getEmpleado());

        log.info("Eliminando proyecto");
        boolean resutl = sa.eliminarProyecto(p.getProyecto().getId());

        log.debug("resutl = '" + resutl + "'");


        assertNull(sa.buscarByID(p.getId()));

    }

    @Test
    void eliminarProyectoNull() throws ProyectoException {


        Throwable exception = assertThrows(ProyectoException.class, () -> {
            boolean proy = sa.eliminarProyecto(null);

            assertNull(proy);

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    @Test
    void eliminarProyectoIDNegativo() throws ProyectoException {

        TProyecto proy = new TProyecto("eliminar1");
        proy.setId(-23L);


        Throwable exception = assertThrows(ProyectoFieldInvalidException.class, () -> {

            boolean result = sa.eliminarProyecto(proy.getId());

        });

        log.error("----  EXCEPCION! ----", exception);

    }


    /******************************************************************
     *******************   TEST LISTAR PROYECTOS   ********************
     ******************************************************************/


    @Test
    void listarProyectos() {
        log.info("ListarUsersTest");

        List<TProyecto> lista = sa.listarProyectos();

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
        nuevo = sa.crearProyecto(p1.getProyecto());

        log.info("buscnado proyecto");
        p1 = sa.buscarByNombre(nombre1);


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


        sa.eliminarProyecto(nuevo.getId());

    }


    @Test
    void buscarByNombreInexistente() throws ProyectoException {


        TProyectoCompleto e2 = sa.buscarByNombre("awdffafgasgfsgag");

        assertNull(e2);


    }

    @Test
    void buscarByNombreVacio() {

        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e2 = sa.buscarByNombre("");


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    @Test
    void buscarByNombreNull() {
        Throwable ex1 = assertThrows(ProyectoFieldInvalidException.class, () -> {

            TProyectoCompleto e2 = sa.buscarByNombre(null);


        });

        log.info("Excepcion capturada:" + ex1.getMessage());

    }


    /******************************************************************
     *********************   METODOS AUXILIARES   *********************
     ******************************************************************/


}