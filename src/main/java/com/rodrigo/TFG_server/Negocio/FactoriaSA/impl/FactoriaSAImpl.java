package com.rodrigo.TFG_server.Negocio.FactoriaSA.impl;

import com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.SA_PasswordSynchronizerRest;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.impl.SA_PasswordSynchronizerRestImpl;
import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl.SA_DepartamentoImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl.SA_EmpleadoImpl;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl.SA_ProyectoImpl;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class FactoriaSAImpl extends FactoriaSA {


    @Override
    public SA_Empleado crearSA_Empleado(){
        return new SA_EmpleadoImpl();
    }

    @Override
    public SA_Departamento crearSA_Departamento() {
        return new SA_DepartamentoImpl();
    }

    @Override
    public SA_Proyecto crearSA_Proyecto() {
        return new SA_ProyectoImpl();
    }
    
    @Override
    public SA_PasswordSynchronizerRest crearSA_PasswordSynchronizerRest() {
        return new SA_PasswordSynchronizerRestImpl();
    }
    
    
}
