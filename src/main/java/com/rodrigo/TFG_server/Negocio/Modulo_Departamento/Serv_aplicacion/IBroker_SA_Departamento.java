package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface IBroker_SA_Departamento {

    public TDepartamento crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoException;

    public TDepartamentoCompleto buscarByID(Long id) throws DepartamentoException;


    boolean eliminarDepartamento(TDepartamento departEliminar);

    public TDepartamento[] listarDepartamentos();


    public TDepartamentoCompleto buscarBySiglas(String siglas) throws DepartamentoException;


    public String saludoREST(String nombre);

    public DeptSencillo getDepartamentoSencillo();

    @GET
    @Path("/tranfer/{id}")
    @Produces("application/xml")
    TDepartamentoCompleto getDepartamentoTranfer(@PathParam("id") Long id) throws DepartamentoException;
}

