package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoConEmpleadosException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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

                        log.info("Persistiendo proyecto en BBDD...");
                        proy = em.merge(new Proyecto(proyectoNuevo));
                        log.debug("result = '" + proy + "'");


                        log.info("TRANSACCION --> COMMIT");
                        em.getTransaction().commit();


                    } catch (PersistenceException e2) {
                        log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
                        log.error(e2.getStackTrace().toString());
                        //log.info("TRANSACCION --> ROLLBACK");
                        // em.getTransaction().rollback();


                        throw e2;

                    } catch (Exception e) {
                        log.error("Ocurrió una error al persisitir en BBDD: " + e.getMessage());
                        log.error("EXCEPCION!", e);
                        log.info("TRANSACCION --> ROLLBACK");
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


    /*@Override
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
    }*/


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
                em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);
                log.info("TRANSACCION --> ROLLBACK");
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


                proy = em.find(Proyecto.class, id);


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
                em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);
                log.info("TRANSACCION --> ROLLBACK");
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

                Proyecto proy = em.find(Proyecto.class, id);
                if (proy != null) {
                    log.debug("Proyectos: ");
                    proy.getEmpleados().stream()
                            .forEach(ep -> {
                                System.out.println("Eliminando ep: " + ep);
                                em.remove(ep);
                            });
                }

                em.remove(proy);
                result = true;
                log.info("TRANSACCION --> COMMIT");
                em.getTransaction().commit();

            } catch (Exception e) {
                //log.info("TRANSACCION --> ROLLBACK");
                // em.getTransaction().rollback();
                result = false;
                if(e.getCause().getCause() instanceof ConstraintViolationException){
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
            em.getTransaction().commit();
        }
        em.close();

        return lista.stream()
                .map((p) -> p.crearTransferSimple())
                .collect(Collectors.toList());
    }

}
