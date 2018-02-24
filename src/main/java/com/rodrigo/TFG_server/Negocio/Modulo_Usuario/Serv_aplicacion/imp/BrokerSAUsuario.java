package com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.imp;

import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.IBrokerSAUsuario;
import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Entidad.Usuario;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

//@WebService(targetNamespace = "http://Servicio_Usuarios/", portName = "BrokerSAUsuario", serviceName = "BrokerSAUsuario")
@WebService(
        endpointInterface= "com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.IBrokerSAUsuario",
        serviceName="BrokerSAUsuario")
public class BrokerSAUsuario implements IBrokerSAUsuario {

    public BrokerSAUsuario() {}

    @Override
    public Usuario crearUsuario(@WebParam(name="Usuario") Usuario usuarioNuevo) {

        return new SAUsuarioImp().crearUsuario(usuarioNuevo);
    }


    public Usuario buscarUsuarioByID(@WebParam(name="id") Long id) {
        return new SAUsuarioImp().buscarUsuarioByID(id);
    }

    public boolean eliminarUsuario(@WebParam(name="Usuario") Usuario usuarioEliminar) {

        return new SAUsuarioImp().eliminarUsuario(usuarioEliminar);
    }

    public List<Usuario> listarUsuarios() {

        return new SAUsuarioImp().listarUsuarios();
    }

    @Override
    public String saludar(@WebParam(name="nombre") String nombre) {
        return new SAUsuarioImp().saludar(nombre);
    }


}
