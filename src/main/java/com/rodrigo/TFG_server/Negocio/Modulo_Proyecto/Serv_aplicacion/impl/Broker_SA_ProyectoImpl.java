package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.FactoriaSA.FactoriaSA;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;
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
    public TProyecto crearProyecto(@WebParam(name = "proyectoNuevo") TProyecto proyectoNuevo) throws ProyectoYaExistenteException, ProyectoFieldInvalidException, ProyectoException {

        return FactoriaSA.getInstance().crearSA_Proyecto().crearProyecto(proyectoNuevo);
        //return null;
    }


    @Override
    public TProyectoCompleto buscarByID(@WebParam(name = "id") Long id) throws ProyectoFieldInvalidException, ProyectoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().buscarByID(id);
    }

    @Override
    public TEmpleadoProyecto añadirEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas) {
        return FactoriaSA.getInstance().crearSA_Proyecto().añadirEmpleadoAProyecto(e, p, horas);
    }

    @Override
    public TProyectoCompleto buscarByNombre(@WebParam(name = "nombre") String nombre) throws ProyectoFieldInvalidException, ProyectoException {
        return FactoriaSA.getInstance().crearSA_Proyecto().buscarByNombre(nombre);
    }

    @Override
    public boolean eliminarProyecto(@WebParam(name = "proyectoEliminar") TProyecto proyectoEliminar) throws ProyectoConEmpleadosException, ProyectoFieldInvalidException, ProyectoException {

        return FactoriaSA.getInstance().crearSA_Proyecto().eliminarProyecto(proyectoEliminar);
    }

    @Override
    public List<TProyecto> listarProyectos() {

        return FactoriaSA.getInstance().crearSA_Proyecto().listarProyectos();
    }

}
