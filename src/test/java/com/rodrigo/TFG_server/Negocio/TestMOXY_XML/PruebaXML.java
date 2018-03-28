package com.rodrigo.TFG_server.Negocio.TestMOXY_XML;


import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import javax.xml.bind.*;

public class PruebaXML {

    private final static Logger log = LoggerFactory.getLogger(PruebaXML.class);
    public static void main(String[] args) throws Exception {

        Empleado emple = new EmpleadoTParcial("rodri", "1234", Rol.ADMIN);
        Empleado emple2 = new EmpleadoTCompleto("Juan", "1234", Rol.EMPLEADO);
        Departamento dept = new Departamento("Ingenieria del Software");
        dept.getEmpleados().add(emple);
        dept.getEmpleados().add(emple2);
        emple.setDepartamento(dept);
        emple2.setDepartamento(dept);

        System.out.println(dept);


        //JAXBContext ctx = JAXBContext.newInstance(new Class[] { Department.class, DepartmentPointer.class }
        //JAXBContext jc = JAXBContext.newInstance(Departamento.class);
        JAXBContext jc = JAXBContext.newInstance(new Class[] { Departamento.class, Empleado.class, EmpleadoTCompleto.class, EmpleadoTParcial.class});
        //Serializar
        log.info("******************************************************");
        log.info("************* SERIALIZANDO OBJETOS *******************");
        log.info("******************************************************");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(dept, System.out);

        //Desserializar
        log.info("******************************************************");
        log.info("************* DESSERIALIZANDO OBJETOS ****************");
        log.info("******************************************************");

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("src/test/resources/input.xml");
        Departamento dept1 = (Departamento) unmarshaller.unmarshal(xml);

        log.debug("dept1 = '" + dept1 + "'");

        for(Empleado e : dept1.getEmpleados()) {
            System.out.println("e = [" + e + "]");
            System.out.println(e.calcularNominaMes());
        }




    }

}
