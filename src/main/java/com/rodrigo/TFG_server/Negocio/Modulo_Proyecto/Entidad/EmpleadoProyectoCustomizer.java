package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.oxm.mappings.XMLCompositeObjectMapping;
import org.eclipse.persistence.oxm.mappings.XMLObjectReferenceMapping;

public class EmpleadoProyectoCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        //********************************************************
        //*******************   PARA EL ID     *******************
        //********************************************************
        XMLCompositeObjectMapping idMapping =
                (XMLCompositeObjectMapping) descriptor.getMappingForAttributeName("id");
        idMapping.setXPath(".");

        descriptor.addPrimaryKeyFieldName("idEmpleado/text()");
        descriptor.addPrimaryKeyFieldName("idProyecto/text()");




    }
}
