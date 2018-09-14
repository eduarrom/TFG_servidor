package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldInvalidException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class SA_EmpleadoImpl implements SA_Empleado {

    private final static Logger log = LoggerFactory.getLogger(SA_EmpleadoImpl.class);

    /**
     * Inserta un empleado en la BBDD
     *
     * @param empleadoNuevo
     * @return Empleado  insertado en BBDD o null si la entidad ya existe
     * @throws EmpleadoNullException         Si el param es null
     * @throws EmpleadoFieldInvalidException Si algún parámetro es null
     */
    public TEmpleadoCompleto crearEmpleado(TEmpleado empleadoNuevo) throws EmpleadoYaExisteExcepcion, EmpleadoException {
        log.info("creando empleado...");

        Empleado emple = null;
        TEmpleadoCompleto transfer = null;
        log.debug("empleadoNuevo = '" + empleadoNuevo + "'");

        if (empleadoNuevo == null) {
            log.error("Empleado es null");
            throw new EmpleadoException("El empleado para persistir en null", new EmpleadoNullException("El empleado para persistir en null"));
        }

        if (empleadoNuevo.getEmail() == null || empleadoNuevo.getEmail() == "" || !new EmailValidator().validate(empleadoNuevo.getEmail())) {
            log.error("Email de empleado es null");
            throw new EmpleadoException("Ocurrio un erro con el email.");

        }


        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            {

                log.info("Buscando por email...");
                try {
                    emple = (Empleado) em
                            .createNamedQuery("Empleado.buscarPorEmail")
                            .setParameter("email", empleadoNuevo.getEmail())
                            .getSingleResult();


                } catch (NoResultException e) {
                    log.info("Empleado con email '" + empleadoNuevo.getEmail() + "' no encontrado");
                }


                if (emple == null) {
                    Departamento dept = em.find(Departamento.class, empleadoNuevo.getDepartamento(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                    if (dept != null) {
                        try {


                            Empleado e = null;
                            if (empleadoNuevo instanceof TEmpleadoTCompleto) {
                                e = new EmpleadoTCompleto(empleadoNuevo.getNombre(), empleadoNuevo.getPassword(),
                                        ((TEmpleadoTCompleto) empleadoNuevo).getAntiguedad(), ((TEmpleadoTCompleto) empleadoNuevo).getSueldoBase());
                            } else if (empleadoNuevo instanceof TEmpleadoTParcial) {
                                e = new EmpleadoTParcial(empleadoNuevo.getNombre(), empleadoNuevo.getPassword(),
                                        ((TEmpleadoTParcial) empleadoNuevo).getHorasJornada(), ((TEmpleadoTParcial) empleadoNuevo).getPrecioHora());
                            }

                            log.info("Asignando departamento a empleado");
                            e.setDepartamento(dept);


                            log.info("Persistiendo empleado en BBDD...");
                            em.persist(e);

                            log.debug("result = '" + emple + "'");

                            transfer = e.crearTransferCompleto();


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

                            throw new EmpleadoException("Ocurrió una error al persisitir en BBDD.");
                        } finally {

                            if (em.isOpen())
                                em.close();
                        }
                    } else {
                        throw new EmpleadoException("El departamento no existe");
                    }

                } else {
                    throw new EmpleadoYaExisteExcepcion("Empleado ya existente");
                }
            }
        }

        return transfer;
    }


    @Override
    public TEmpleadoCompleto buscarByID(Long id) throws EmpleadoException {
        Empleado emple;

        log.info("id = [" + id + "]");

        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new EmpleadoException("El id para buscar en null, 0 o negativo");
        }

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            log.info("Buscando empleado en BBDD");
            try {


                emple = em.find(Empleado.class, id);


                log.debug("emple = '" + emple + "'");
                if (emple != null) {
                    log.debug("emple.getDepartamento() = '" + emple.getDepartamento() + "'");
                    log.debug("Proyectos: ");
                    emple.getProyectos().stream().forEach(System.out::println);
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

                throw new EmpleadoException("Ocurrió una error al buscar en BBDD.");
            }

        }
        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();

        return (emple != null) ? emple.crearTransferCompleto() : null;
    }

    @Override
    public boolean eliminarEmpleado(Long id) throws EmpleadoNullException, EmpleadoException {

        boolean result;

        log.info("id = [" + id + "]");

        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new EmpleadoException("El id para buscar en null, 0 o negativo");
        }


        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            try {
                //si se coloca el LockModeType.OPTIMISTIC salta excepcion al commit por tenerlo bloqueado
                Empleado emple = em.find(Empleado.class, id);
                if (emple != null) {
                    log.debug("Proyectos: ");
                    emple.getProyectos().stream()
                            .forEach(ep -> {
                                System.out.println("Eliminando ep: " + ep);
                                //em.remove(ep);
                                em.createNamedQuery("EmpleadoProyecto.eliminarByID")
                                        .setParameter("id", ep.getId())
                                        .executeUpdate();
                            });
                }


                em.createNamedQuery("Empleado.eliminarByID")
                        .setParameter("id", emple.getId())
                        .executeUpdate();

                result = true;
                log.info("TRANSACCION --> COMMIT");
                if(em.getTransaction().isActive())
                    em.getTransaction().commit();
            } catch (Exception e) {
                log.error("EXCEPCION!!", e);
                log.info("TRANSACCION --> ROLLBACK");
                if(em.getTransaction().isActive())
                    em.getTransaction().rollback();
                result = false;
            }


        }
        if (em.isOpen())
            em.close();


        return result;
    }

    @Override
    public List<TEmpleado> listarEmpleados() {

        List<Empleado> lista;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("Iniciando trasaccion");
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            log.info("Buscando empleado en BBDD");
            lista = em.createNamedQuery("Empleado.listar").getResultList();

            log.info("TRANSACCION --> COMMIT");
            if (em.getTransaction().isActive())
            em.getTransaction().commit();
        }


        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();


        return lista.stream()
                .map((e) -> e.crearTransferSimple())
                .collect(Collectors.toList());
    }


    /**
     * Busca un Empleado por email
     *
     * @param email
     * @return retrona el empleado de la BBDD
     * Null si no existe
     */
    public TEmpleadoCompleto buscarByEmail(String email) throws EmpleadoFieldInvalidException, EmpleadoException {
        Empleado emple = null;
        TEmpleadoCompleto transfer = null;
        log.debug("email = '" + email + "'");


        //Validacion del email
        if (email == null || email.equals("") || !new EmailValidator().validate(email)) {
            log.error("El email es invalido");

            try {
                throw new EmpleadoFieldInvalidException(
                        new PropertyValueException("Empleado.email es erroneo.",
                                Empleado.class.toString(),
                                Empleado.class.getDeclaredField("email").toString()));
            } catch (NoSuchFieldException e) {
                log.error("Ocurrio un error inesperado.");
                throw new EmpleadoException("Ocurrio un error con el email.");
            }
        }


        log.info("Email correcto");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            log.info("Buscando empleado...");
            try {
                emple = (Empleado) em
                        .createNamedQuery("Empleado.buscarPorEmail")
                        .setParameter("email", email)
                        .getSingleResult();


                log.info("*********************************************************");
                log.info("*********************************************************");
                log.info("emple = '" + emple + "'");
                log.info("*********************************************************");
                log.info("*********************************************************");

                transfer = (emple != null) ? emple.crearTransferCompleto() : null;
            } catch (NoResultException e) {
                log.info("Empleado con email '" + email + "' no encontrado");
                emple = null;
            }
            log.info("TRANSACCION --> COMMIT");
            if (em.getTransaction().isActive())
            em.getTransaction().commit();
        }
        if (em.isOpen())
            em.close();


        return transfer;
    }

}
