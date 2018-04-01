package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.IBroker_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/departamento")
public class Broker_SA_DepartamentoImpl implements IBroker_SA_Departamento {

    public Broker_SA_DepartamentoImpl() {}

    @GET
    @Path("/saludo/{nombre}")
    @Produces("application/json")
    //@Produces("text/plain")
    public String saludoREST(@PathParam("nombre") String nombre) {
        return FactoriaSA.getInstance().crearSA_Departamento().saludoREST(nombre);
    }

    @GET
    @Path("/deptSencillo")
    @Produces("application/xml")
    public DeptSencillo getDepartamentoSencillo() {
        System.out.println("********************************************");
        System.out.println("************ getDepartamentoSencillo **********");
        System.out.println("********************************************");
        return new DeptSencillo(1, "deptREST");
    }


    @GET
    @Path("/deptCompleto/{id}")
    @Produces("application/xml")
    public Departamento getDepartamentoCompleto(@PathParam("id") Long id) {
        System.out.println("********************************************");
        System.out.println("************ getDepartamentoCompleto **********");
        System.out.println("********************************************");
        return FactoriaSA.getInstance().crearSA_Departamento().buscarByID(id);
    }


    @Override
    public Departamento crearDepartamento( Departamento departamentoNuevo) throws DepartamentoException {
        return new SA_DepartamentoImpl().crearDepartamento(departamentoNuevo);
    }


    public Departamento buscarDepartamentoByID(Long id) {
        return new SA_DepartamentoImpl().buscarByID(id);
    }

    public boolean eliminarDepartamento(Departamento departamentoEliminar) {

        return new SA_DepartamentoImpl().eliminarDepartamento(departamentoEliminar);
    }

    public List<Departamento> listarDepartamentos() {
        return new SA_DepartamentoImpl().listarDepartamentos();
    }


    @Override
    public Departamento buscarBySiglas(String siglas) throws DepartamentoException {
        return new SA_DepartamentoImpl().buscarBySiglas(siglas);
    }

}
