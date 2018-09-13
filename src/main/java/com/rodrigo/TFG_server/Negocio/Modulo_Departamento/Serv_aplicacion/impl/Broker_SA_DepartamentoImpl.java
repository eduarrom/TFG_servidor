package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoYaExisteExcepcion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/departamento")
public class Broker_SA_DepartamentoImpl {

    private final static Logger log = LoggerFactory.getLogger(Broker_SA_DepartamentoImpl.class);

    public Broker_SA_DepartamentoImpl() {
    }


    /**
     * @param departamentoNuevo
     * @return - CREATED y depart
     * - BAD_REQUEST depart existente
     * - INTERNAL_SERVER_ERROR otro error
     */
    @PUT
    @Produces("application/xml")
    public Response crearDepartamento(TDepartamento departamentoNuevo) {
        System.out.println("********************************************");
        System.out.println("************ crearDepartamento **********");
        System.out.println("********************************************");
        System.out.println("departamentoNuevo = [" + departamentoNuevo + "]");

        try {

            TDepartamento dept = FactoriaSA
                    .getInstance()
                    .crearSA_Departamento()
                    .crearDepartamento(departamentoNuevo);


            return Response
                    .status(Response.Status.CREATED)
                    .entity(dept)
                    .build();


        } catch (DepartamentoYaExisteExcepcion e) {
            System.out.println("********* DEPARTAMENTO EXISTENTE!!");
            return Response.status(Response.Status.BAD_GATEWAY).build();
        } catch (DepartamentoFieldInvalidException e3) {
            e3.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (DepartamentoException e2) {
            e2.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * @param id
     * @return - OK y depart
     * - NOT_FOUND
     * - BAD_REQUEST
     * - INTERNAL_SERVER_ERROR otro error
     */
    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public Response buscarByID(@PathParam("id") Long id) {
        System.out.println("********************************************");
        System.out.println("************ buscarByID **********");
        System.out.println("id = [" + id + "]");
        System.out.println("********************************************");


        try {

            TDepartamentoCompleto dept = FactoriaSA
                    .getInstance()
                    .crearSA_Departamento()
                    .buscarByID(id);

            System.out.println("dept = [" + dept + "]");

            if (dept != null) {
                return Response
                        .status(Response.Status.OK)
                        .entity(dept)
                        .build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }

        } catch (DepartamentoFieldInvalidException e2) {
            System.out.println("BAD_REQUEST!!");
            System.out.println("e2.getMessage() = [" + e2.getMessage() + "]");
            return Response.status(Response.Status.BAD_REQUEST).entity(e2.getMessage()).build();
        } catch (DepartamentoException e3) {
            e3.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GET
    @Path("bySiglas/{siglas}")
    @Produces("application/xml")
    public Response buscarBySiglas(@PathParam("siglas") String siglas)  {
        System.out.println("********************************************");
        System.out.println("************ buscarBySiglas **********");
        System.out.println("siglas = [" + siglas + "]");
        System.out.println("********************************************");

        try {

            TDepartamentoCompleto dept = FactoriaSA
                    .getInstance()
                    .crearSA_Departamento()
                    .buscarBySiglas(siglas);

            System.out.println("dept = [" + dept + "]");

            if (dept != null) {
                return Response
                        .status(Response.Status.OK)
                        .entity(dept)
                        .build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }

        } catch (DepartamentoFieldInvalidException e2) {
            System.out.println("BAD_REQUEST!!");
            System.out.println("e2.getMessage() = [" + e2.getMessage() + "]");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (DepartamentoException e3) {
            e3.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    public Response eliminarDepartamento(@PathParam("id") Long id) {
        System.out.println("********************************************");
        System.out.println("************ eliminarDepart **********");
        System.out.println("id = [" + id + "]");
        System.out.println("********************************************");

        Response.Status status;


        try {
            log.info("Creando SA para eliminar...");
            boolean result = FactoriaSA
                    .getInstance()
                    .crearSA_Departamento()
                    .eliminarDepartamento(id);


            System.out.println("result = [" + result + "]");
            if (result) {

                status = Response.Status.OK;
            } else {
                log.info("no se eliminó el departamento");
                status = Response.Status.INTERNAL_SERVER_ERROR;
            }

        } catch (DepartamentoConEmpleadosException e1) {
            e1.printStackTrace();

            log.error("e.getMessage() = '" + e1.getMessage() + "'");

            status = Response.Status.BAD_GATEWAY;

        } catch (DepartamentoFieldInvalidException e2) {
            e2.printStackTrace();

            log.error("e.getMessage() = '" + e2.getMessage() + "'");

            status = Response.Status.BAD_REQUEST;

        } catch (DepartamentoException e3) {
            log.error("e.getMessage() = '" + e3.getMessage() + "'");

            status = Response.Status.INTERNAL_SERVER_ERROR;
        }


        log.debug("status repuesta = '" + status + "'");
        return Response
                .status(status)
                .build();

    }


    @GET
    @Path("/listar")
    @Produces("application/xml")
    public TDepartamento[] listarDepartamentos() {
        System.out.println("Listando Departamentos simple");
        List<TDepartamento> lista = FactoriaSA.getInstance().crearSA_Departamento().listarDepartamentos();

        lista.stream()
                .forEach(System.out::println);

        return lista.toArray(new TDepartamento[]{});
    }


}
