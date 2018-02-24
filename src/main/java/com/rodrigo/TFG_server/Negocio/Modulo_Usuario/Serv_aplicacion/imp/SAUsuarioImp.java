package com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.imp;


import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Serv_aplicacion.SAUsuario;
import com.rodrigo.TFG_server.Negocio.Modulo_Usuario.Entidad.Usuario;

import javax.persistence.*;
import java.util.List;

public class SAUsuarioImp implements SAUsuario {

    static private EntityManagerFactory emf;
    //@PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public SAUsuarioImp() {
        emf = Persistence.createEntityManagerFactory("aplicacion");
    }

    public Usuario crearUsuario(Usuario usuarioNuevo) {

        Usuario result;

        System.out.println("creando usuario...");
        em = emf.createEntityManager();

        {
            em.getTransaction().begin();

            result = em.merge(usuarioNuevo);

            em.getTransaction().commit();
        }
        em.close();
        return result;
    }

    @Override
    public Usuario buscarUsuarioByID(Long id) {
        Usuario user;

        System.out.println("Creando Entity Manager");
        em = emf.createEntityManager();

        {
            em.getTransaction().begin();
            System.out.println("Buscando usuario en BBDD");
            user = em.find(Usuario.class, id);

            em.getTransaction().commit();
        }
        em.close();

        return user;
    }

    @Override
    public boolean eliminarUsuario(Usuario usuarioEliminar) {


        boolean result;

        em = emf.createEntityManager();

        {
            em.getTransaction().begin();

            try {
                em.remove(em.find(Usuario.class, usuarioEliminar.getId()));
                result = true;
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                result = false;
            }


        }
        em.close();


        return result;
    }

    @Override
    public List<Usuario> listarUsuarios() {

        List<Usuario> lista;


        em = emf.createEntityManager();
        {
            em.getTransaction().begin();

            lista = em.createNamedQuery("Usuario.listar").getResultList();

            em.getTransaction().commit();
        }
        em.close();

        return lista;
    }

    public String saludar(String nombre) {
        return "Hola " + nombre + ", un saludo desde el servidor CXF :)";
    }
}
