package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Proyecto {

    @WebMethod(operationName="crearProyecto")
    public TProyecto crearProyecto(@WebParam(name = "Proyecto") TProyecto proyectoNuevo) throws ProyectoException;

    @WebMethod(operationName="buscarByID")
    public TProyectoCompleto buscarByID(@WebParam(name = "id") Long id);


    @WebMethod(operationName="eliminarProyecto")
    public boolean eliminarProyecto(@WebParam(name = "Proyecto") TProyecto proyectoEliminar) ;


    @WebMethod(operationName="listarProyectos")
    public List<TProyecto> listarProyectos();


}

