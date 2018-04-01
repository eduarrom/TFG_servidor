package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;

import java.util.HashSet;
import java.util.Set;

public class DeptApplication extends javax.ws.rs.core.Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Broker_SA_DepartamentoImpl.class);
        return classes;
    }
}