package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * The type Sa departamento.
 */
public class SA_DepartamentoImpl implements SA_Departamento {

    private final static Logger log = LoggerFactory.getLogger(SA_DepartamentoImpl.class);

    /**
     * Inserta un departamento en la BBDD
     *
     * @param departamentoNuevo
     * @return Departamento insertado en BBDD o null si la entidad ya existe
     */
    public Departamento crearDepartamento(Departamento departamentoNuevo) throws DepartamentoException {
        Departamento depart;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Persistiendo departamento en BBDD");
            depart = em.merge(departamentoNuevo);

            em.getTransaction().commit();
        }
        em.close();

        return depart;
    }


    @Override
    public Departamento buscarByID(Long id) {
        Departamento depart;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Buscando departamento en BBDD");
            depart = em.find(Departamento.class, id);

            em.getTransaction().commit();
        }
        em.close();

        return depart;
    }

    @Override
    public boolean eliminarDepartamento(Departamento departamentoEliminar) {

        boolean result;

        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();

            try {
                em.remove(em.find(Departamento.class, departamentoEliminar.getId()));
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
    public List<Departamento> listarDepartamentos() {

        List<Departamento> lista;


        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            em.getTransaction().begin();

            lista = em.createNamedQuery("Departamento.listar").getResultList();

            em.getTransaction().commit();
        }
        em.close();

        return lista;
    }


    public Departamento buscarBySiglas(String siglas) throws DepartamentoException {
        Departamento depart = null;
        log.debug("siglas = '" + siglas + "'");


        //Validacion del email
        if (siglas == null || siglas == "") {
            log.error("Las siglas es invalido");

            try {
                throw new DepartamentoFieldNullException(
                        new PropertyValueException("Departamento.siglas es erroneo.",
                                Departamento.class.toString(),
                                Departamento.class.getDeclaredField("siglas").toString()));
            } catch (NoSuchFieldException e) {
                log.error("Ocurrio un error inesperado.");
                throw new DepartamentoException("Ocurrio un error con las siglas.");
            }
        }


        log.info("siglas correcto");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            em.getTransaction().begin();

            log.info("Buscando departamento...");
            try {
                depart = (Departamento) em
                        .createNamedQuery("Departamento.buscarPorSiglas")
                        .setParameter("siglas", siglas)
                        .getSingleResult();


            } catch (NoResultException e) {
                log.info("Departamento con email '" + siglas + "' no encontrado");
                depart = null;
            }


            em.getTransaction().commit();
        }
        if (em.isOpen())
            em.close();

        return depart;
    }

    @Override
    public String saludoREST(String nombre) {
        return "Hola " + nombre + " desde servicio REST :)";
    }

}
