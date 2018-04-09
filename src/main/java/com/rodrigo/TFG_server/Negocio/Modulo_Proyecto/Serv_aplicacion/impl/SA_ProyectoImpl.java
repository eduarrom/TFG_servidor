package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * The type Sa proyecto.
 */
public class SA_ProyectoImpl implements SA_Proyecto {

    private final static Logger log = LoggerFactory.getLogger(SA_ProyectoImpl.class);

    /**
     * Inserta un proyecto en la BBDD
     *
     * @param proyectoNuevo
     * @return Proyecto insertado en BBDD o null si la entidad ya existe
     */
    public Proyecto crearProyecto(Proyecto proyectoNuevo) throws ProyectoException {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Persistiendo proyecto en BBDD");
            proy = em.merge(proyectoNuevo);

            em.getTransaction().commit();
        }
        em.close();

        return proy;
    }

    @Override
    public EmpleadoProyecto añadirEmpleadoAProyecto(Empleado e, Proyecto p, int horas) {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();


        EmpleadoProyecto ep = new EmpleadoProyecto(e, p, horas);
        {
            em.getTransaction().begin();
            log.info("Persistiendo proyecto en BBDD");
            ep = em.merge(ep);

            em.getTransaction().commit();
        }
        em.close();

        return ep;
    }


    @Override
    public Proyecto buscarByID(Long id) {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Buscando proyecto en BBDD");
            proy = em.find(Proyecto.class, id);
            log.debug("proy = '" + proy + "'");
            proy.getEmpleados().stream()
                    .forEach(ep -> {
                        ep.getEmpleado().toString();
                        ep.getProyecto().toString();
                    });

            em.getTransaction().commit();
        }
        em.close();

        return proy;
    }

    @Override
    public boolean eliminarProyecto(Proyecto proyectoEliminar) {

        boolean result;

        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();

            try {
                em.remove(em.find(Proyecto.class, proyectoEliminar.getId()));
                result = true;
                em.getTransaction().commit();
            } catch (Exception e) {
                //em.getTransaction().rollback();
                result = false;
            }

        }
        if (em.isOpen())
            em.close();


        return result;
    }

    @Override
    public List<Proyecto> listarProyectos() {

        List<Proyecto> lista;


        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            em.getTransaction().begin();

            lista = em.createNamedQuery("Proyecto.listar").getResultList();

            em.getTransaction().commit();
        }
        em.close();

        return lista;
    }

}
