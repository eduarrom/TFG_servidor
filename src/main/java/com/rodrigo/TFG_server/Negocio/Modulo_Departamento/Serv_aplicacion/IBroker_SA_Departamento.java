package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import java.util.List;

public interface IBroker_SA_Departamento {

    public Departamento crearDepartamento( Departamento departamentoNuevo) throws DepartamentoException;

    public Departamento buscarDepartamentoByID(Long id);


    public boolean eliminarDepartamento( Departamento departamentoEliminar) ;


    public List<Departamento> listarDepartamentos();


    public Departamento buscarBySiglas(String siglas) throws DepartamentoException;


    public String saludoREST(String nombre);

    public DeptSencillo getDepartamentoSencillo();

}

