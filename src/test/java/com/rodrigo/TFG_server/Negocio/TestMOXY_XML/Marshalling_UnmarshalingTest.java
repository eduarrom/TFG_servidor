package com.rodrigo.TFG_server.Negocio.TestMOXY_XML;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.ClavesEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.bind.*;

import static org.junit.jupiter.api.Assertions.*;

public class Marshalling_UnmarshalingTest {

    private final static Logger log = LoggerFactory.getLogger(Marshalling_UnmarshalingTest.class);

    static JAXBContext jc;

    static TEmpleadoCompleto emple1;
    //    static Empleado emple2;
    static TDepartamento dept1;
    static TProyectoCompleto proy1;
    private static Marshaller ms;
    private static Unmarshaller um;
//    static Proyecto proy2;

    @BeforeAll
    static void setUp() throws JAXBException, ParseException, ProyectoException, DepartamentoException, EmpleadoException {
        jc = JAXBContext.newInstance(Departamento.class, Empleado.class,
                EmpleadoTCompleto.class, EmpleadoTParcial.class, Proyecto.class, EmpleadoProyecto.class, ClavesEmpleadoProyecto.class);

        System.out.println(jc.getClass());

        ms = jc.createMarshaller();
        ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        um = jc.createUnmarshaller();

//        emple2 = new EmpleadoTCompleto("emple2", "1234", Rol.EMPLEADO);
        emple1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(20L);

//        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());
        dept1 = FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas("DdP");

        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);
        //proy1.setFechaInicio(new SimpleDateFormat("dd-MM-yyyy").parse("10-07-2018"));


        //PRUBAS PARA MARSHALING DE DEPT Y EMPLE
        //emple1.setProyectos(null);
        // proy1.setEmpleados(null);
        //dept1.getEmpleados().stream().forEach((emple)->{ emple.setProyectos(null);});


//        proy2 = new Proyecto("Proy2");
//        proy2.setFechaInicio(new SimpleDateFormat("dd-MM-yyyy").parse("10-07-2018"));


        //dept1.agregarEmpleado(emple1);
//        dept1.agregarEmpleado(emple2);


        //proy1.agregarEmpleado(emple2, 5);

//        proy2.agregarEmpleado(emple1, 5);

//        FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(dept1);
//        FactoriaSA.getInstance().crearSA_Empleado().crearEmpleado(emple1);

        //FactoriaSA.getInstance().crearSA_Proyecto().crearProyecto(proy1);
        //proy1.agregarEmpleado(emple1, 5);
        //EmpleadoProyecto ep = FactoriaSA.getInstance().crearSA_Proyecto().añadirEmpleadoAProyecto(emple1, proy1, 5);
        //proy1.agregarEmpleado(ep);

        //FactoriaSA.getInstance().crearSA_Proyecto().crearProyecto(proy1);


//        log.info(emple1);
    }

/*

    @Test
    void MarshalDepartamento() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL DEPARTAMENTO  ******************");
        log.info("******************************************************");


        File xmlOut = new File("src/test/resources/MarshallingXML/Departamento/outputDepart.xml");
        FileWriter fw = new FileWriter(xmlOut);

        ms.marshal(dept1, System.out);
        ms.marshal(dept1, fw);

        File xmlIn = new File("src/test/resources/MarshallingXML/Departamento/inputDepart.xml");

        assertEquals(FileUtils.readFileToString(xmlIn, "utf-8"),
                FileUtils.readFileToString(xmlOut, "utf-8"));


    }

    @Test
    void UnMarchalDepartamento() throws JAXBException {

        //Desserializar
        log.info("******************************************************");
        log.info("************  UNMARSHAL DEPARTAMENTO  ***************");
        log.info("******************************************************");

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        Departamento deptIn = (Departamento) unmarshaller.unmarshal(new File("src/test/resources/MarshallingXML/Departamento/inputDepart.xml"));

        log.debug("dept1  = '" + dept1 + "'");
        log.debug("deptIn = '" + deptIn + "'");

        for (Empleado e : deptIn.getEmpleados()) {
            log.info("e = [" + e + "]");
            log.info(String.valueOf(e.calcularNominaMes()));
        }

        log.debug("Nomina deptIn = '" + deptIn.calcularNominaMes() + "'");


        assertEquals(dept1.toString(), deptIn.toString());

        assertEquals(dept1.getEmpleados().toString(), deptIn.getEmpleados().toString());

        assertEquals(dept1.calcularNominaMes(), deptIn.calcularNominaMes());


    }


    @Test
    void MarshalEmpleado() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL EMPLEADO  ******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File xmlOut = new File("src/test/resources/MarshallingXML/Empleado/outputEmple.xml");
        FileWriter fw = new FileWriter(xmlOut);

        marshaller.marshal(emple1, System.out);
        marshaller.marshal(emple1, fw);

        File xmlIn = new File("src/test/resources/MarshallingXML/Empleado/inputEmple.xml");

        assertEquals(FileUtils.readFileToString(xmlIn, "utf-8"),
                FileUtils.readFileToString(xmlOut, "utf-8"));


    }


    @Test
    void UnMarchalEmpleado() throws JAXBException, IOException {

        //Desserializar
        log.info("******************************************************");
        log.info("************  UNMARSHAL EMPLEADO  ***************");
        log.info("******************************************************");



        ms.marshal(emple1, System.out);
//        jc.createMarshaller().marshal(emple1, new FileWriter("src/test/resources/MarshallingXML/Empleado/outputEmpleXXXX.xml"));


        EmpleadoTParcial empleIn = (EmpleadoTParcial) um.unmarshal(new File("src/test/resources/MarshallingXML/Empleado/inputEmple.xml"));
        Empleado empleado = (Empleado) um.unmarshal(new File("src/test/resources/MarshallingXML/Empleado/inputEmple.xml"));

        log.debug("empleIn = '" + empleIn + "'");
        log.debug("empleado = '" + empleado + "'");


        assertEquals(emple1.getId(), empleIn.getId());
        assertEquals(emple1.getNombre(), empleIn.getNombre());
        assertEquals(emple1.getEmail(), empleIn.getEmail());
        assertEquals(emple1.getPassword(), empleIn.getPassword());
        assertEquals(emple1.getRol(), empleIn.getRol());
        //assertEquals(emple1.getDepartamento(), empleIn.getDepartamento());
        assertEquals(emple1.getProyectos().size(), empleIn.getProyectos().size());

        assertEquals(((EmpleadoTParcial) emple1).getHorasJornada(), empleIn.getHorasJornada());
        assertEquals(((EmpleadoTParcial) emple1).getPrecioHora(), empleIn.getPrecioHora());

        assertEquals(emple1.calcularNominaMes(), empleIn.calcularNominaMes());
        assertEquals(emple1.getProyectos().size(), empleIn.getProyectos().size());

    }


    @Test
    void MarshalProyecto() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL PROYECTO  ******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File xmlOut = new File("src/test/resources/MarshallingXML/Proyecto/outputProyecto.xml");

        marshaller.marshal(proy1, System.out);
        marshaller.marshal(proy1, new FileWriter(xmlOut));


        File xmlIn = new File("src/test/resources/MarshallingXML/Proyecto/inputProyecto.xml");

        assertEquals(FileUtils.readFileToString(xmlIn, "utf-8"),
                FileUtils.readFileToString(xmlOut, "utf-8"));


    }


    @Test
    void UnMarchalProyecto() throws JAXBException {

        //Desserializar
        log.info("******************************************************");
        log.info("************  UNMARSHAL PROYECTO  ***************");
        log.info("******************************************************");

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xmlIn = new File("src/test/resources/MarshallingXML/Proyecto/inputProyecto.xml");

        Proyecto proyIn = (Proyecto) unmarshaller.unmarshal(xmlIn);

        log.debug("empleIn = '" + proyIn + "'");


        assertEquals(proy1.getId(), proyIn.getId());
        assertEquals(proy1.getNombre(), proyIn.getNombre());
        assertEquals(proy1.getDescripcion(), proyIn.getDescripcion());
        assertEquals(proy1.getFechaInicio(), proyIn.getFechaInicio());
        assertEquals(proy1.getFechaFin(), proyIn.getFechaFin());

    }

*/

}
