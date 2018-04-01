package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DepartamentoPointer")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentPointer {

    public Long id;

    public DepartmentPointer(Long id) {
        this.id = id;
    }
}