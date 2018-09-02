package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Proyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TEmpleadoProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyecto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.Transfers.TProyectoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Excepciones.ProyectoYaExistenteException;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.SA_Proyecto;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
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
    public TProyecto crearProyecto(TProyecto proyectoNuevo) throws ProyectoYaExistenteException, ProyectoFieldInvalidException, ProyectoException {
        Proyecto proy = null;
        log.debug("proyectoNuevo = '" + proyectoNuevo + "'");

        if (proyectoNuevo == null) {
            log.error("Proyecto es null");
            throw new ProyectoFieldInvalidException("El Proyecto para persistir en null");
        }

        if (proyectoNuevo.getNombre() == null || proyectoNuevo.getNombre().equals("")) {
            log.error("Nombre de Proyecto es null");
            throw new ProyectoFieldInvalidException("Ocurrio un error con el nombre.");

        }


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            {

                log.info("Buscando por nombre...");
                try {
                    proy = (Proyecto) em
                            .createNamedQuery("Proyecto.buscarPorNombre")
                            .setParameter("nombre", proyectoNuevo.getNombre())
                            .getSingleResult();


                } catch (NoResultException e) {
                    log.info("Proyecto con nombre '" + proyectoNuevo.getNombre() + "' no encontrado");
                }


                if (proy == null) {

                    try {

                        proy = new Proyecto(proyectoNuevo);
                        log.info("Persistiendo proyecto en BBDD...");
                        proy = em.merge(proy);
                        log.debug("result = '" + proy + "'");


                        log.info("TRANSACCION --> COMMIT");
                        if (em.getTransaction().isActive())
                            em.getTransaction().commit();


                    } catch (PersistenceException e2) {
                        log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
                        log.error(e2.getStackTrace().toString());
                        log.info("TRANSACCION --> ROLLBACK");
                        if (em.getTransaction().isActive())
                            em.getTransaction().rollback();


                        throw e2;

                    } catch (Exception e) {
                        log.error("Ocurrió una error al persisitir en BBDD: " + e.getMessage());
                        log.error("EXCEPCION!", e);
                        log.info("TRANSACCION --> ROLLBACK");
                        if (em.getTransaction().isActive())
                            em.getTransaction().rollback();

                        throw new ProyectoException("Ocurrió una error al persisitir en BBDD.");
                    } finally {

                        if (em.isOpen())
                            em.close();
                    }

                } else {
                    throw new ProyectoYaExistenteException("Proyecto ya existente");
                }
            }
        }

        return proy.crearTransferSimple();
    }

    @Override
    public TEmpleadoProyecto añadirEmpleadoAProyecto(TEmpleado e, TProyecto p, int horas) throws ProyectoException, EmpleadoException {
        Proyecto proy;
        Empleado emple;
        EmpleadoProyecto ep;


        if (e == null || e.getId() == null || e.getId() <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new EmpleadoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }
        if (p == null || p.getId() == null || p.getId() <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new ProyectoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }
        if (horas <= 0) {
            log.error("El número de horas de la asigancion debe ser positivo");
            throw new ProyectoFieldInvalidException("EL número de horas de la asigancion debe ser positivo");
        }


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();


            log.info("Buscando empleado en BBDD");
            emple = em.find(Empleado.class, e.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            if (emple != null) {

                proy = em.find(Proyecto.class, p.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                if (proy != null) {

                    try {
                        ep = new EmpleadoProyecto(emple, proy, horas);

                        try {

                            log.debug("proy = '" + proy + "'");
                            log.info("emple = '" + emple + "'");

                            EmpleadoProyecto epAux = (EmpleadoProyecto) em.createNamedQuery("EmpleadoProyecto.buscarEmpleProy")
                                    .setParameter("idEmple", emple.getId())
                                    .setParameter("idProy", proy.getId())
                                    .getSingleResult();

                            if (epAux != null) {

                                epAux.setHoras(horas);
                                em.merge(epAux);
                            }


                        } catch (NoResultException ex) {

                            em.persist(ep);

                            log.info("TRANSACCION --> COMMIT");
                            if (em.getTransaction().isActive())
                                em.getTransaction().commit();

                        }

                    } catch (IllegalArgumentException ex2) {
                        log.error("EXCEPCION!", ex2);

                        log.info("TRANSACCION --> ROLLBACK");
                        if (em.getTransaction().isActive())
                            em.getTransaction().rollback();

                        throw new ProyectoException("Ocurrió una error al buscar en BBDD.");
                    } catch (EntityExistsException ex3) {
                        log.error("EXCEPCION!", ex3);

                        log.info("TRANSACCION --> ROLLBACK");
                        if (em.getTransaction().isActive())
                            em.getTransaction().rollback();

                        throw new ProyectoException("Esa asociacion ya existe");
                    }

                } else {
                    throw new ProyectoException("El proyecto no existe en BBDD");
                }
            } else {
                throw new EmpleadoException("El empleado no existe en BBDD");
            }

        }
        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();


        return ep.crearTransferSimple();
    }


    @Override
    public boolean eliminarEmpleadoAProyecto(Long idEmple, Long idProy) throws ProyectoException, EmpleadoException {
        Proyecto proy;
        Empleado emple;
        EmpleadoProyecto ep;
        boolean result = false;


        if (idEmple == null || idEmple <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new EmpleadoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }
        if (idProy == null || idProy <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new ProyectoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();


            log.info("Buscando empleado en BBDD");
            emple = em.find(Empleado.class, idEmple, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            if (emple != null) {

                proy = em.find(Proyecto.class, idProy, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                if (proy != null) {

                    try {

                        log.info("proy = '" + proy + "'");
                        log.info("emple = '" + emple + "'");


                        EmpleadoProyecto epAux = (EmpleadoProyecto) em.createNamedQuery("EmpleadoProyecto.buscarEmpleProy")
                                .setParameter("idEmple", emple.getId())
                                .setParameter("idProy", proy.getId())
                                .getSingleResult();

                        if (epAux != null) {

                            int resultSet = em.createNamedQuery("EmpleadoProyecto.eliminarByID")
                                    .setParameter("id", epAux.getId())
                                    .executeUpdate();

                            if (resultSet == 1) {
                                result = true;
                                log.info("TRANSACCION --> COMMIT");
                                if (em.getTransaction().isActive())
                                    em.getTransaction().commit();
                            }
                        }


                    } catch (NoResultException ex) {

                        log.info("TRANSACCION --> COMMIT");
                        if (em.getTransaction().isActive())
                            em.getTransaction().commit();

                    }

                } else {
                    throw new ProyectoException("El proyecto no existe en BBDD");
                }
            } else {
                throw new EmpleadoException("El empleado no existe en BBDD");
            }

        }
        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();


        return result;
    }


    @Override
    public TProyectoCompleto buscarByNombre(String nombre) throws ProyectoFieldInvalidException, ProyectoException {
        Proyecto proy;

        log.info("nombre = [" + nombre + "]");

        //Validacion del nombre
        if (nombre == null || nombre.equals("")) {
            log.error("El nombre es invalido");

            log.error("Ocurrio un error inesperado.");
            throw new ProyectoFieldInvalidException("Ocurrio un error con el nombre.");
        }

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            log.info("Buscando proyecto en BBDD");
            try {

                try {
                    proy = (Proyecto) em
                            .createNamedQuery("Proyecto.buscarPorNombre")
                            .setParameter("nombre", nombre)
                            .getSingleResult();


                } catch (NoResultException e) {
                    log.info("Poryecto con nombre '" + nombre + "' no encontrado");
                    proy = null;
                }
                log.debug("proy = '" + proy + "'");
                if (proy != null) {
                    log.debug("Proyectos: ");
                    proy.getEmpleados().stream()
                            .forEach(ep -> {
                                ep.getEmpleado().toString();
                                ep.getProyecto().toString();
                            });
                }

                log.info("TRANSACCION --> COMMIT");
                if (em.getTransaction().isActive())
                    em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);

                log.info("TRANSACCION --> ROLLBACK");
                if (em.getTransaction().isActive())
                    em.getTransaction().rollback();

                throw new ProyectoException("Ocurrió una error al buscar en BBDD.");
            }

        }
        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();

        return (proy != null) ? proy.crearTransferCompleto() : null;
    }


    @Override
    public TProyectoCompleto buscarByID(Long id) throws ProyectoFieldInvalidException, ProyectoException {
        Proyecto proy;

        log.info("id = [" + id + "]");

        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new ProyectoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            log.info("Buscando empleado en BBDD");
            try {


                proy = em.find(Proyecto.class, id, LockModeType.OPTIMISTIC);


                log.debug("proy = '" + proy + "'");
                if (proy != null) {
                    log.debug("Proyectos: ");
                    proy.getEmpleados().stream()
                            .forEach(ep -> {
                                ep.getEmpleado().toString();
                                ep.getProyecto().toString();
                            });
                }

                log.info("TRANSACCION --> COMMIT");
                if (em.getTransaction().isActive())
                    em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);

                log.info("TRANSACCION --> ROLLBACK");
                if (em.getTransaction().isActive())
                    em.getTransaction().rollback();

                throw new ProyectoException("Ocurrió una error al buscar en BBDD.");
            }

        }
        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();

        return (proy != null) ? proy.crearTransferCompleto() : null;
    }


    @Override
    public boolean eliminarProyecto(Long id) throws ProyectoConEmpleadosException, ProyectoFieldInvalidException, ProyectoException {

        boolean result;

        log.info("id = [" + id + "]");


        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new ProyectoFieldInvalidException("El id para buscar en null, 0 o negativo");
        }


        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            try {

                Proyecto proy = em.find(Proyecto.class, id/*, LockModeType.OPTIMISTIC*/);

                if (proy != null) {
                    log.debug("Proyectos: ");
                    proy.getEmpleados().stream()
                            .forEach(ep -> {
                                System.out.println("Eliminando ep: " + ep);
                                em.createNamedQuery("EmpleadoProyecto.eliminarByID")
                                        .setParameter("id", ep.getId())
                                        .executeUpdate();
                            });
                }

                //em.remove(proy);
                em.createNamedQuery("Proyecto.eliminarByID")
                        .setParameter("id", proy.getId())
                        .executeUpdate();
                result = true;

                log.info("TRANSACCION --> COMMIT");
                if (em.getTransaction().isActive())
                    em.getTransaction().commit();

            } catch (Exception e) {
                log.info("TRANSACCION --> ROLLBACK");
                if (em.getTransaction().isActive())
                    em.getTransaction().rollback();

                result = false;

                if (e.getCause().getCause() instanceof ConstraintViolationException) {
                    throw new ProyectoConEmpleadosException();
                }

                throw new ProyectoException("Hubo un error al eliminar el proyecto.");

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
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            lista = em.createNamedQuery("Proyecto.listar").getResultList();

            log.info("TRANSACCION --> COMMIT");
            if (em.getTransaction().isActive())
                em.getTransaction().commit();
        }
        em.close();

        return lista.stream()
                .map((p) -> p.crearTransferSimple())
                .collect(Collectors.toList());
    }

}
