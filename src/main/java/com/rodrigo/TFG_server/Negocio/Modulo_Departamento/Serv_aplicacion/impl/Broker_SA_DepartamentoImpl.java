package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.IBroker_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/departamento")
public class Broker_SA_DepartamentoImpl implements IBroker_SA_Departamento {

    public Broker_SA_DepartamentoImpl() {}

    @GET
    @Path("/saludo/{nombre}")
    @Produces("application/json")
    //@Produces("text/plain")
    @Override
    public String saludoREST(@PathParam("nombre") String nombre) {
        return FactoriaSA.getInstance().crearSA_Departamento().saludoREST(nombre);
    }

    @GET
    @Path("/deptSencillo")
    @Produces("application/xml")
    @Override
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



    @PUT
    //@Path("/crear")
    @Produces("application/xml")
    @Override
    public Departamento crearDepartamento( Departamento departamentoNuevo) throws DepartamentoException {
        return new SA_DepartamentoImpl().crearDepartamento(departamentoNuevo);
    }


    @GET
    @Path("/{id}")
    @Produces("application/xml")
    @Override
    public Departamento buscarByID(@PathParam("id") Long id) {
        System.out.println("********************************************");
        System.out.println("************ buscarByID **********");
        System.out.println("id = [" + id + "]");
        System.out.println("********************************************");

        return new SA_DepartamentoImpl().buscarByID(id);
    }


    @GET
    @Path("bySiglas/{siglas}")
    @Produces("application/xml")
    @Override
    public Departamento buscarBySiglas(@PathParam("siglas") String siglas) throws DepartamentoException {
        System.out.println("********************************************");
        System.out.println("************ buscarBySiglas **********");
        System.out.println("siglas = [" + siglas + "]");
        System.out.println("********************************************");
        return new SA_DepartamentoImpl().buscarBySiglas(siglas);
    }


    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    @Override
    public boolean eliminarDepartamento(Long id) {
        return new SA_DepartamentoImpl().eliminarDepartamento(id);
    }


    @GET
    @Path("/listar")
    @Produces("application/xml")
    @Override
    public Departamento[] listarDepartamentos() {
        System.out.println("Listando Departamentos simple");
        List<Departamento> lista = new SA_DepartamentoImpl().listarDepartamentos();

        lista.stream()
                .forEach(System.out::println);

        lista.stream()
                .forEach((d)->d.getEmpleados().stream()
                        .forEach((e -> e.setProyectos(null))));

        return lista.toArray(new Departamento[]{});
    }




}
