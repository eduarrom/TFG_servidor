package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SA_Departamento {

    Departamento crearDepartamento(Departamento departamentoNuevo) throws DepartamentoException, DepartamentoYaExisteExcepcion;
    
    Departamento buscarByID(Long id) throws DepartamentoException;

    boolean eliminarDepartamento(Departamento departEliminar);

    List<Departamento> listarDepartamentos();

    Departamento buscarBySiglas(String siglas) throws DepartamentoException;

    public String saludoREST(String nombre);

}
