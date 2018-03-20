package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Departamento {

    @WebMethod(operationName="crearDepartamento")
    public Departamento crearDepartamento(@WebParam(name = "Departamento") Departamento departamentoNuevo) throws DepartamentoException;

    @WebMethod(operationName="buscarByID")
    public Departamento buscarDepartamentoByID(@WebParam(name = "id") Long id);


    @WebMethod(operationName="eliminarDepartamento")
    public boolean eliminarDepartamento(@WebParam(name = "Departamento") Departamento departamentoEliminar) ;


    @WebMethod(operationName="listarDepartamentos")
    public List<Departamento> listarDepartamentos();


    @WebMethod(operationName="buscarBySiglas")
    public Departamento buscarBySiglas(String siglas) throws DepartamentoException;



}

