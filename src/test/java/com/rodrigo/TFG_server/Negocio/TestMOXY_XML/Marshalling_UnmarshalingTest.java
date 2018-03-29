package com.rodrigo.TFG_server.Negocio.TestMOXY_XML;


import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.xml.bind.*;

import static org.junit.jupiter.api.Assertions.*;

public class Marshalling_UnmarshalingTest {

    private final static Logger log = LoggerFactory.getLogger(Marshalling_UnmarshalingTest.class);

    static JAXBContext jc;

    static Empleado emple1;
    static Empleado emple2;
    static Departamento dept;

    public static void main(String[] args) throws Exception {

    }


    @BeforeAll
    static void setUp() throws JAXBException {
        jc = JAXBContext.newInstance(new Class[]{Departamento.class, Empleado.class, EmpleadoTCompleto.class, EmpleadoTParcial.class});


        emple1 = new EmpleadoTParcial("emple1", "1234", Rol.ADMIN);
        emple2 = new EmpleadoTCompleto("emple2", "1234", Rol.EMPLEADO);
        dept = new Departamento("Ingenieria del Software");
        dept.getEmpleados().add(emple1);
        dept.getEmpleados().add(emple2);
        emple1.setDepartamento(dept);
        emple2.setDepartamento(dept);

        //log.info(dept.toString());
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

        marshaller.marshal(dept, fw);
        //marshaller.marshal(dept, System.out);

        File xmlIn = new File("src/test/resources/MarshallingXML/inputDepart.xml");

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
        File xmlIn = new File("src/test/resources/MarshallingXML/inputDepart.xml");

        Departamento deptIn = (Departamento) unmarshaller.unmarshal(xmlIn);

        log.debug("dept1 = '" + deptIn + "'");

        for (Empleado e : deptIn.getEmpleados()) {
            System.out.println("e = [" + e + "]");
            System.out.println(e.calcularNominaMes());
        }

        assertEquals(dept.toString(), deptIn.toString());
        assertEquals(dept.getEmpleados(), deptIn.getEmpleados());
        assertEquals(dept.getEmpleados().toString(), deptIn.getEmpleados().toString());


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

        //marshaller.marshal(emple1, System.out);
        marshaller.marshal(emple1, fw);

        File xmlIn = new File("src/test/resources/MarshallingXML/inputEmple.xml");

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
        File xmlIn = new File("src/test/resources/MarshallingXML/inputEmple.xml");

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
}
