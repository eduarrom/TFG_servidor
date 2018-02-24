package com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion;

import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Entidad.Usuario;

import java.util.List;

/**
 * Created by Rodrigo de Miguel on 06/05/2017.
 */
public interface SAUsuario {

    Usuario crearUsuario( Usuario usuarioNuevo);

    Usuario buscarUsuarioByID(Long id);

    boolean eliminarUsuario( Usuario usuarioEliminar);

    List<Usuario> listarUsuarios();

    String saludar(String nombre);

}
