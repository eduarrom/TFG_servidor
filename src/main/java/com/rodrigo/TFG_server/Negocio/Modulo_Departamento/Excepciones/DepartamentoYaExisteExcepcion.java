package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones;

public class DepartamentoYaExisteExcepcion extends DepartamentoException {
    public DepartamentoYaExisteExcepcion(String msg) {
        super(msg);
    }
}
