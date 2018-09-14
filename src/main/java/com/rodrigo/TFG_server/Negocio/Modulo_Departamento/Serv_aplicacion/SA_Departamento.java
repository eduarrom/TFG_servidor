package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;

import java.util.List;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public interface SA_Departamento {

    TDepartamento crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoYaExisteExcepcion, DepartamentoException;
    
    TDepartamentoCompleto buscarByID(Long id) throws DepartamentoException;

    boolean eliminarDepartamento(Long id) throws DepartamentoFieldInvalidException, DepartamentoConEmpleadosException, DepartamentoException;

    List<TDepartamento> listarDepartamentos();

    TDepartamentoCompleto buscarBySiglas(String siglas) throws DepartamentoException;

}
