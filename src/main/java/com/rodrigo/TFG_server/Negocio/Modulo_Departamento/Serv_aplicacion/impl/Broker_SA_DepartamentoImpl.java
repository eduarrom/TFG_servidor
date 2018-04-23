package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.DeptSencillo;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.IBroker_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;

import javax.ws.rs.*;
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
    public TDepartamentoCompleto getDepartamentoCompleto(@PathParam("id") Long id) throws DepartamentoException {
        System.out.println("********************************************");
        System.out.println("************ getDepartamentoCompleto **********");
        System.out.println("********************************************");
        return FactoriaSA.getInstance().crearSA_Departamento().buscarByID(id);
    }



    @PUT
    //@Path("/crear")
    @Produces("application/xml")
    @Override
    public TDepartamento crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoException {
        return FactoriaSA.getInstance().crearSA_Departamento().crearDepartamento(departamentoNuevo);
    }


    @GET
    @Path("/{id}")
    @Produces("application/xml")
    @Override
    public TDepartamentoCompleto buscarByID(@PathParam("id") Long id) throws DepartamentoException {
        System.out.println("********************************************");
        System.out.println("************ buscarByID **********");
        System.out.println("id = [" + id + "]");
        System.out.println("********************************************");

        TDepartamentoCompleto dept = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(id);
//        dept.setEmpleados(null);

        System.out.println("dept = [" + dept + "]");

        return dept;
    }


    @GET
    @Path("bySiglas/{siglas}")
    @Produces("application/xml")
    @Override
    public TDepartamentoCompleto buscarBySiglas(@PathParam("siglas") String siglas) throws DepartamentoException {
        System.out.println("********************************************");
        System.out.println("************ buscarBySiglas **********");
        System.out.println("siglas = [" + siglas + "]");
        System.out.println("********************************************");
        return FactoriaSA.getInstance().crearSA_Departamento().buscarBySiglas(siglas);
    }


    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    @Override
    public boolean eliminarDepartamento(TDepartamento departEliminar) throws DepartamentoException {
        return FactoriaSA.getInstance().crearSA_Departamento().eliminarDepartamento(departEliminar);
    }


    @GET
    @Path("/listar")
    @Produces("application/xml")
    @Override
    public TDepartamento[] listarDepartamentos() {
        System.out.println("Listando Departamentos simple");
        List<TDepartamento> lista = FactoriaSA.getInstance().crearSA_Departamento().listarDepartamentos();

        lista.stream()
                .forEach(System.out::println);

        return lista.toArray(new TDepartamento[]{});
    }





    @GET
    @Path("/tranfer/{id}")
    @Produces("application/xml")
    @Override
    public TDepartamentoCompleto getDepartamentoTranfer(@PathParam("id") Long id) throws DepartamentoException {
        System.out.println("********************************************");
        System.out.println("************ getDepartamentoTranfer **********");
        System.out.println("********************************************");
        TDepartamentoCompleto td = FactoriaSA.getInstance().crearSA_Departamento().buscarByID(id);
        System.out.println("td = [" + td + "]");
        return td;
    }




}
