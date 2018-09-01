package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Serv_aplicacion.SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Sa departamento.
 */
public class SA_DepartamentoImpl implements SA_Departamento {

    private final static Logger log = LoggerFactory.getLogger(SA_DepartamentoImpl.class);


    @Override
    public TDepartamento crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoFieldInvalidException, DepartamentoYaExisteExcepcion, DepartamentoException {
        log.info("creando departamento...");

        Departamento depart = null;
        log.debug("departamentoNuevo = '" + departamentoNuevo + "'");

        if (departamentoNuevo == null) {
            log.error("Departamento es null");
            throw new DepartamentoFieldInvalidException("El departamento para persistir en null");
        }

        if (departamentoNuevo.getSiglas() == null || departamentoNuevo.getSiglas().equals("")) {
            log.error("Siglas de departamento es null");
            throw new DepartamentoFieldInvalidException("Ocurrio un error con las Siglas.");

        }


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            {

                log.info("Buscando por siglas...");
                try {
                    depart = (Departamento) em
                            .createNamedQuery("Departamento.buscarPorSiglas")
                            .setParameter("siglas", departamentoNuevo.getSiglas())
                            .getSingleResult();


                } catch (NoResultException e) {
                    log.info("Departamento con siglas '" + departamentoNuevo.getSiglas() + "' no encontrado");
                }


                if (depart == null) {

                    try {

                        log.info("Persistiendo departamento en BBDD...");
                        depart = em.merge(new Departamento(departamentoNuevo));
                        log.debug("result = '" + depart + "'");


                        log.info("TRANSACCION --> COMMIT");
                        em.getTransaction().commit();


                    } catch (PersistenceException e2) {
                        log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
                        log.error(e2.getStackTrace().toString());
                        //log.info("TRANSACCION --> ROLLBACK");                 em.getTransaction().rollback();


                        throw e2;
                        //throw new EmpleadoFieldInvalidException((PropertyValueException) e2.getCause());

                    } catch (Exception e) {
                        log.error("Ocurrió una error al persisitir en BBDD: " + e.getMessage());
                        log.error("EXCEPCION!", e);
                        log.info("TRANSACCION --> ROLLBACK");
                        em.getTransaction().rollback();

                        throw new DepartamentoException("Ocurrió una error al persisitir en BBDD.");
                    } finally {

                        if (em.isOpen())
                            em.close();
                    }

                } else {
                    throw new DepartamentoYaExisteExcepcion();
                }
            }
        }

        return depart.crearTransferSimple();
    }


    @Override
    public TDepartamentoCompleto buscarByID(Long id) throws DepartamentoFieldInvalidException, DepartamentoException {
        Departamento depart;

        log.info("id = [" + id + "]");

        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new DepartamentoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            try {


                log.info("Buscando departamento en BBDD");
                depart = em.find(Departamento.class, id, LockModeType.OPTIMISTIC);


                log.debug("depart = '" + depart + "'");
                if (depart != null) {
                    log.debug("Empleados: ");
                    depart.getEmpleados().stream().forEach(System.out::println);
                }

                log.info("TRANSACCION --> COMMIT");
                em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);

                log.info("TRANSACCION --> ROLLBACK");
                em.getTransaction().rollback();

                throw new DepartamentoException("Ocurrió una error al buscar en BBDD.");
            }

        }

        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();

        return (depart != null) ? depart.crearTransferCompleto() : null;
    }

    @Override
    public boolean eliminarDepartamento(Long id) throws DepartamentoFieldInvalidException, DepartamentoConEmpleadosException, DepartamentoException {

        boolean result;

        log.info("departEliminar = [" + id + "]");


        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new DepartamentoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }



        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            try {
//                COMPORABAR QUE TNEGA EMPLEADOS Y REVCHZAR SI ES ASI
                em.remove(em.find(Departamento.class, id));
                result = true;
                log.info("TRANSACCION --> COMMIT");
                em.getTransaction().commit();
            } catch (Exception e) {
                //log.info("TRANSACCION --> ROLLBACK");
                //em.getTransaction().rollback();

                log.debug("e = '" + e + "'");
                result = false;

                if(e.getCause().getCause() instanceof ConstraintViolationException ){
                    throw new DepartamentoConEmpleadosException("Departamento con empleados asignados.");
                }

                throw new DepartamentoException("Hubo un error al eliminar el departamento");
            }

        }
        if (em.isOpen())
            em.close();


        return result;
    }

    @Override
    public List<TDepartamento> listarDepartamentos() {

        List<Departamento> lista;


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("Iniciando trasaccion");
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            log.info("Buscando departamento en BBDD");
            lista = em.createNamedQuery("Departamento.listar").getResultList();
            log.debug("lista = '" + lista + "'");

            log.info("Cerrando transaccion");
            log.info("TRANSACCION --> COMMIT");
            em.getTransaction().commit();
        }

        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();


        return lista.stream()
                .map((d) -> d.crearTransferSimple())
                .collect(Collectors.toList());
    }




    public TDepartamentoCompleto buscarBySiglas(String siglas) throws DepartamentoException {
        Departamento depart = null;
        log.debug("siglas = '" + siglas + "'");


        //Validacion del siglas
        if (siglas == null || siglas.equals("")) {
            log.error("Las siglas es invalido");

            log.error("Ocurrio un error inesperado.");
            throw new DepartamentoFieldInvalidException("Ocurrio un error con las siglas.");
        }


        log.info("siglas correcto");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");
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

            log.info("depart = [" + depart + "]");
            if (depart != null)
                depart.getEmpleados().stream().forEach(System.out::println);

            log.info("Cerrando transaccion");
            log.info("TRANSACCION --> COMMIT");
            em.getTransaction().commit();
        }

        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();

        return (depart != null) ? depart.crearTransferCompleto() : null;
    }









    }
