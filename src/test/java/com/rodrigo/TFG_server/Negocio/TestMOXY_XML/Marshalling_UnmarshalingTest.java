package com.rodrigo.TFG_server.Negocio.TestMOXY_XML;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.ClavesEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
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

    static Empleado emple1;
//    static Empleado emple2;
    static Departamento dept1;
    static Proyecto proy1;
//    static Proyecto proy2;

    @BeforeAll
    static void setUp() throws JAXBException, ParseException, ProyectoException, DepartamentoException, EmpleadoException {
        jc = JAXBContext.newInstance(new Class[]{Departamento.class, Empleado.class,
                EmpleadoTCompleto.class, EmpleadoTParcial.class, Proyecto.class, EmpleadoProyecto.class, ClavesEmpleadoProyecto.class});


        emple1 = new EmpleadoTParcial("emple1", "1234", Rol.ADMIN);
//        emple2 = new EmpleadoTCompleto("emple2", "1234", Rol.EMPLEADO);
        emple1 = FactoriaSA.getInstance().crearSA_Empleado().buscarByID(23L);

        dept1 = new Departamento("Dept1");
//        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(dept1.getSiglas());
        dept1=FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas("DdP");

        proy1 = new Proyecto("Proy1");
        proy1 = FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(1L);
        proy1.setFechaInicio(new SimpleDateFormat("dd-MM-yyyy").parse("10-07-2018"));


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



//        System.out.println(emple1);
    }


    @Test
    void MarshalDepartamento() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL DEPARTAMENTO  ******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File xmlOut = new File("src/test/resources/MarshallingXML/outputDepart.xml");
        FileWriter fw = new FileWriter(xmlOut);
        marshaller.marshal(dept1, System.out);
        marshaller.marshal(dept1, fw);

        File xmlIn = new File("src/test/resources/MarshallingXML/outputDepart.xml");

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
        File xmlIn = new File("src/test/resources/MarshallingXML/outputDepart.xml");

        Departamento deptIn = (Departamento) unmarshaller.unmarshal(xmlIn);

        log.debug("dept1  = '" + dept1 + "'");
        log.debug("deptIn = '" + deptIn + "'");

        for (Empleado e : deptIn.getEmpleados()) {
            System.out.println("e = [" + e + "]");
            System.out.println(e.calcularNominaMes());
        }


        assertEquals(dept1.toString(), deptIn.toString());
        assertEquals(dept1.getEmpleados(), deptIn.getEmpleados());
        assertEquals(dept1.getEmpleados().toString(), deptIn.getEmpleados().toString());


    }


    @Test
    void MarshalEmpleado() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL EMPLEADO  ******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File xmlOut = new File("src/test/resources/MarshallingXML/outputEmple.xml");
        FileWriter fw = new FileWriter(xmlOut);

        marshaller.marshal(emple1, System.out);
        marshaller.marshal(emple1, fw);

        File xmlIn = new File("src/test/resources/MarshallingXML/outputEmple.xml");

        assertEquals(FileUtils.readFileToString(xmlIn, "utf-8"),
                FileUtils.readFileToString(xmlOut, "utf-8"));


    }

    @Test
    void UnMarchalEmpleado() throws JAXBException {

        //Desserializar
        log.info("******************************************************");
        log.info("************  UNMARSHAL EMPLEADO  ***************");
        log.info("******************************************************");

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xmlIn = new File("src/test/resources/MarshallingXML/outputEmple.xml");

        Empleado empleIn = (Empleado) unmarshaller.unmarshal(xmlIn);

        log.debug("empleIn = '" + empleIn + "'");


        for (Empleado e : empleIn.getDepartamento().getEmpleados()) {
            System.out.println("e = [" + e + "]");
            System.out.println(e.calcularNominaMes());
        }

        assertEquals(emple1.toString(), empleIn.toString());
        assertEquals(emple1.getDepartamento(), empleIn.getDepartamento());
        assertEquals(emple1.getDepartamento().getEmpleados(), empleIn.getDepartamento().getEmpleados());
        assertEquals(emple1.calcularNominaMes(), empleIn.calcularNominaMes());

    }


    @Test
    void MarshalProyecto() throws JAXBException, IOException {

        //Serializar
        log.info("******************************************************");
        log.info("************  MARSHAL PROYECTO  ******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(proy1, System.out);

        marshaller.marshal(proy1, new FileWriter("src/test/resources/MarshallingXML/outputProyecto.xml"));

//        File xmlIn = new File("src/test/resources/MarshallingXML/outputProyecto.xml.xml");
//
//        assertEquals(FileUtils.readFileToString(xmlIn, "utf-8"),
//                FileUtils.readFileToString(xmlOut, "utf-8"));


    }

    @Test
    void UnMarchalProyecto() throws JAXBException {

        //Desserializar
        log.info("******************************************************");
        log.info("************  UNMARSHAL PROYECTO  ***************");
        log.info("******************************************************");

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xmlIn = new File("src/test/resources/MarshallingXML/outputProyecto.xml");

        Proyecto proyIn = (Proyecto) unmarshaller.unmarshal(xmlIn);

        log.debug("empleIn = '" + proyIn + "'");


        for (EmpleadoProyecto ep : proyIn.getEmpleados()) {
            System.out.println("ep.getEmpleado = [" + ep.getEmpleado() + "]");
            System.out.println(ep.getEmpleado().calcularNominaMes());
        }

        assertEquals(proy1.toString(), proyIn.toString());
        assertEquals(proy1.getEmpleados(), proyIn.getEmpleados());

    }


}
