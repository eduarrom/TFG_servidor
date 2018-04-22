package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.IBroker_SA_Proyecto;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Proyectos/", portName = "Broker_SA_ProyectoImpl", serviceName = "Broker_SA_ProyectoImpl")
@WebService(
        endpointInterface = "com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.IBroker_SA_Proyecto",
        serviceName = "Broker_SA_ProyectoImpl")
public class Broker_SA_ProyectoImpl implements IBroker_SA_Proyecto {

    public Broker_SA_ProyectoImpl() {
    }

    @Override
    public TProyecto crearProyecto(@WebParam(name = "proyectoNuevo") TProyecto proyectoNuevo) throws ProyectoException {

        return FactoriaSA.getInstance().crearSA_Proyecto().crearProyecto(proyectoNuevo);
        //return null;
    }


    @Override
    public TProyectoCompleto buscarByID(@WebParam(name = "id") Long id) {
        return FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(id);
    }

    @Override
    public boolean eliminarProyecto(@WebParam(name = "proyectoEliminar") TProyecto proyectoEliminar) {

        return FactoriaSA.getInstance().crearSA_Proyecto().eliminarProyecto(proyectoEliminar);
    }

    @Override
    public List<TProyecto> listarProyectos() {

        return FactoriaSA.getInstance().crearSA_Proyecto().listarProyectos();
    }

}
