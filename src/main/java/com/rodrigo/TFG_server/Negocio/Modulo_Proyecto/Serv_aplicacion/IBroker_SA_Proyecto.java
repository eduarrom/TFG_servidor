package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
@WebService
public interface IBroker_SA_Proyecto {

    @WebMethod(operationName="crearProyecto")
    public TProyecto crearProyecto(@WebParam(name = "Proyecto") TProyecto proyectoNuevo) throws ProyectoYaExistenteException, ProyectoFieldInvalidException, ProyectoException;

    @WebMethod(operationName="buscarByID")
    public TProyectoCompleto buscarByID(@WebParam(name = "id") Long id) throws ProyectoFieldInvalidException, ProyectoException;

    @WebMethod(operationName="agregarEmpleadoAProyecto")
    TEmpleadoProyecto agregarEmpleadoAProyecto(@WebParam(name = "e") TEmpleado e, @WebParam(name = "p") TProyecto p, @WebParam(name = "horas") int horas) throws EmpleadoException, ProyectoException;

    @WebMethod(operationName="eliminarEmpleadoAProyecto")
    boolean eliminarEmpleadoAProyecto(Long idEmple, Long idProy) throws ProyectoException, EmpleadoException;


    @WebMethod(operationName="buscarByNombre")
    TProyectoCompleto buscarByNombre(@WebParam(name = "nombre") String nombre) throws ProyectoFieldInvalidException, ProyectoException;

    @WebMethod(operationName="eliminarProyecto")
    public boolean eliminarProyecto(@WebParam(name = "id") Long id) throws ProyectoConEmpleadosException, ProyectoFieldInvalidException, ProyectoException;


    @WebMethod(operationName="listarProyectos")
    public List<TProyecto> listarProyectos();


}

