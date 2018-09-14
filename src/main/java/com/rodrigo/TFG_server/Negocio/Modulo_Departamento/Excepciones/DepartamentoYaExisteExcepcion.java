package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartamentoYaExisteExcepcion extends DepartamentoException {


    public DepartamentoYaExisteExcepcion() {
        super("Departamento ya existente.");
    }

    public DepartamentoYaExisteExcepcion(String msg) {
        super(msg);
    }
}
