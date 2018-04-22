package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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
    public TProyecto crearProyecto(TProyecto proyectoNuevo) throws ProyectoException {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");                 em.getTransaction().begin();
            log.info("Persistiendo proyecto en BBDD");
            proy = em.merge(new Proyecto(proyectoNuevo));

            log.info("TRANSACCION --> COMMIT");                 em.getTransaction().commit();
        }
        em.close();

        return proy.crearTrasferSimple();
    }

    @Override
    public TEmpleadoProyecto añadirEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas) {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();


        EmpleadoProyecto ep = new EmpleadoProyecto(
                Empleado.crearEmpleado(e),
                new Proyecto(p),
                horas);
        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            log.info("Persistiendo proyecto en BBDD");
            ep = em.merge(ep);

            log.info("TRANSACCION --> COMMIT");
            em.getTransaction().commit();
        }
        em.close();

        return ep.crearTransferSimple();
    }


    @Override
    public TProyectoCompleto buscarByID(Long id) {
        Proyecto proy;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");                 em.getTransaction().begin();
            log.info("Buscando proyecto en BBDD");
            proy = em.find(Proyecto.class, id);
            log.debug("proy = '" + proy + "'");
            proy.getEmpleados().stream()
                    .forEach(ep -> {
                        ep.getEmpleado().toString();
                        ep.getProyecto().toString();
                    });

            log.info("TRANSACCION --> COMMIT");                 em.getTransaction().commit();
        }
        em.close();

        return proy.crearTransferCompleto();
    }

    @Override
    public boolean eliminarProyecto(TProyecto proyectoEliminar) {

        boolean result;

        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");                 em.getTransaction().begin();

            try {
                em.remove(em.find(Proyecto.class, proyectoEliminar.getId()));
                result = true;
                log.info("TRANSACCION --> COMMIT");                 em.getTransaction().commit();
            } catch (Exception e) {
                //log.info("TRANSACCION --> ROLLBACK");                 em.getTransaction().rollback();
                result = false;
            }

        }
        if (em.isOpen())
            em.close();


        return result;
    }

    @Override
    public List<TProyecto> listarProyectos() {

        List<Proyecto> lista;


        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");                 em.getTransaction().begin();

            lista = em.createNamedQuery("Proyecto.listar").getResultList();

            log.info("TRANSACCION --> COMMIT");                 em.getTransaction().commit();
        }
        em.close();

        return lista.stream()
                .map((p)-> p.crearTrasferSimple())
                .collect(Collectors.toList());
    }

}
