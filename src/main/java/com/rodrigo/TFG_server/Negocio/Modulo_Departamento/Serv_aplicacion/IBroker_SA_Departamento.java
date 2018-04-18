package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface IBroker_SA_Departamento {

    public Departamento crearDepartamento( Departamento departamentoNuevo) throws DepartamentoException;

    public Departamento buscarByID(Long id) throws DepartamentoException;


    boolean eliminarDepartamento(Departamento departEliminar);

    public Departamento[] listarDepartamentos();


    public Departamento buscarBySiglas(String siglas) throws DepartamentoException;


    public String saludoREST(String nombre);

    public DeptSencillo getDepartamentoSencillo();

}

