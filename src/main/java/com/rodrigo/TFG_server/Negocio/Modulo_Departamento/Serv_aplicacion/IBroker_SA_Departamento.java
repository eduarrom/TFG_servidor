package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public interface IBroker_SA_Departamento {

    public Response crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoException;

    public Response buscarByID(Long id) throws DepartamentoException;


    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    Response eliminarDepartamento(@PathParam("id") Long id) throws DepartamentoException;

    public TDepartamento[] listarDepartamentos();


    public Response buscarBySiglas(String siglas) throws DepartamentoException;


}
