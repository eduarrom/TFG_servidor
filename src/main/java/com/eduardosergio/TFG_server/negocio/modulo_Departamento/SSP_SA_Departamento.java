package com.eduardosergio.TFG_server.negocio.modulo_Departamento;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SSP_SA_Departamento {
    @WebMethod(operationName="buscarByID")
    public TDepartamentoCompleto buscarByID(@WebParam(name = "id") Long id) throws DepartamentoException;
}

