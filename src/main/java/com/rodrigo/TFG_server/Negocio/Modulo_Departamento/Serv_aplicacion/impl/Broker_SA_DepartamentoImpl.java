package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.IBroker_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Departamentos/", portName = "Broker_SA_ProyectoImpl", serviceName = "Broker_SA_ProyectoImpl")
@WebService(
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.IBroker_SA_Departamento",
        serviceName="Broker_SA_ProyectoImpl")
public class Broker_SA_DepartamentoImpl implements IBroker_SA_Departamento {

    public Broker_SA_DepartamentoImpl() {}

    @Override
    public Departamento crearDepartamento(@WebParam(name="Departamento") Departamento departamentoNuevo) throws DepartamentoException {

        return new SA_DepartamentoImpl().crearDepartamento(departamentoNuevo);
        //return null;
    }


    public Departamento buscarDepartamentoByID(@WebParam(name="id") Long id) {
        return new SA_DepartamentoImpl().buscarByID(id);
    }

    public boolean eliminarDepartamento(@WebParam(name="Departamento") Departamento departamentoEliminar) {

        return new SA_DepartamentoImpl().eliminarDepartamento(departamentoEliminar);
    }

    public List<Departamento> listarDepartamentos() {

        return new SA_DepartamentoImpl().listarDepartamentos();
    }


    @Override
    public Departamento buscarBySiglas(String siglas) throws DepartamentoException {
        return new SA_DepartamentoImpl().buscarBySiglas(siglas);
    }

    @Override
    public String saludoREST(String nombre) {
        return FactoriaSA.getInstance().crearSA_Departamento().saludoREST(nombre);
    }

}
