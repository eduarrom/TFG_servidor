package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IBroker_SA_Proyecto {

    @WebMethod(operationName="crearProyecto")
    public Proyecto crearProyecto(@WebParam(name = "Proyecto") Proyecto proyectoNuevo) throws ProyectoException;

    @WebMethod(operationName="buscarByID")
    public Proyecto buscarProyectoByID(@WebParam(name = "id") Long id);


    @WebMethod(operationName="eliminarProyecto")
    public boolean eliminarProyecto(@WebParam(name = "Proyecto") Proyecto proyectoEliminar) ;


    @WebMethod(operationName="listarProyectos")
    public List<Proyecto> listarProyectos();


}

