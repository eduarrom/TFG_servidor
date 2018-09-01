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
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Sa empleado.
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
                                e = new EmpleadoTCompleto(empleadoNuevo.getNombre(), empleadoNuevo.getPassword(), empleadoNuevo.getRol(),
                                        ((TEmpleadoTCompleto) empleadoNuevo).getAntiguedad(), ((TEmpleadoTCompleto) empleadoNuevo).getSueldoBase());
                            } else if (empleadoNuevo instanceof TEmpleadoTParcial) {
                                e = new EmpleadoTParcial(empleadoNuevo.getNombre(), empleadoNuevo.getPassword(), empleadoNuevo.getRol(),
                                        ((TEmpleadoTParcial) empleadoNuevo).getHorasJornada(), ((TEmpleadoTParcial) empleadoNuevo).getPrecioHora());
                            }

                            log.info("Asignando departamento a empleado");
                            //e.setDepartamento(em.find(Departamento.class, empleadoNuevo.getDepartamento()));
                            e.getDepartamento().setId(empleadoNuevo.getDepartamento());

//                            Departamento dept = em.find(Departamento.class, e.getDepartamento().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                            if (dept != null) {
//                            prosegiir con la operacion
                            } else {
//                           rechazar operacion
                            }
                            log.info("Persistiendo empleado en BBDD...");
                            emple = em.merge(e);
                            log.debug("result = '" + emple + "'");

                            transfer = emple.crearTransferCompleto();


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
                em.getTransaction().commit();

            } catch (IllegalArgumentException e) {
                log.error("Ocurrió una error al buscar en BBDD: " + e.getMessage());
                log.error("EXCEPCION!", e);
                log.info("TRANSACCION --> ROLLBACK");
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
                Empleado emple = em.find(Empleado.class, id);
                if (emple != null) {
                    log.debug("Proyectos: ");
                    emple.getProyectos().stream()
                            .forEach(ep -> {
                                System.out.println("Eliminando ep: " + ep);
                                em.remove(ep);
                            });
                }


                em.remove(emple);
                result = true;
                log.info("TRANSACCION --> COMMIT");
                em.getTransaction().commit();
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
            em.getTransaction().commit();
        }


        log.info("Cerrando Entity Manager");
        if (em.isOpen())
            em.close();


        return lista.stream()
                .map((e) -> e.crearTransferSimple())
                .collect(Collectors.toList());
    }

    public String saludar(String nombre) {
        return "Hola " + nombre + ", un saludo desde el servidor CXF :)";
    }

    public Boolean loginEmpleado(String email, String pass) throws EmpleadoLoginErroneo, EmpleadoFieldInvalidException, EmpleadoException {
        Empleado emple = null;
        log.debug("email = '" + email + "'");


        //Validacion del email
        if (email == null || email == "" || !new EmailValidator().validate(email)) {
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


        //Validacion del pass
        log.debug("pass = '" + pass + "'");
        if (pass == null || pass == "") {
            log.error("La password es invalido");

            try {
                throw new EmpleadoFieldInvalidException(
                        new PropertyValueException("Empleado.password es erroneo.",
                                Empleado.class.toString(),
                                Empleado.class.getDeclaredField("password").toString()));
            } catch (NoSuchFieldException e) {
                log.error("Ocurrio un error inesperado.");
                throw new EmpleadoException("Ocurrio un error con la password.");
            }
        }

        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        try {


            {
                log.debug("Iniciando transacción...");
                log.info("TRANSACCION --> BEGIN");
                em.getTransaction().begin();
                {
                    log.info("Buscando empleado en BBDD...");

                    log.info("Buscando empleado...");
                    try {
                        emple = (Empleado) em
                                .createNamedQuery("Empleado.buscarPorEmail")
                                .setParameter("email", email)
                                .getSingleResult();


                    } catch (NoResultException e) {
                        log.info("Empleado con email '" + email + "' no encontrado");
                        emple = null;
                    }
                    /*if (result.size() > 0) {
                        emple = (Empleado) result.get(0);
                    }*/
                    log.debug("emple = '" + emple + "'");

                }
                log.debug("Terminando transacción...");
                log.info("TRANSACCION --> COMMIT");
                em.getTransaction().commit();
            }

        /*} catch (PersistenceException e2) {
            log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
            log.error(e2.getStackTrace().toString());
            log.info("TRANSACCION --> ROLLBACK");                 em.getTransaction().rollback();

            throw new EmpleadoFieldInvalidException((PropertyValueException) e2.getCause());*/

        } catch (Exception e) {
            log.error("Ocurrió una error al persisitir en BBDD.");
            log.error("Mensaje: " + e.getMessage());
            log.error(e.getStackTrace().toString());
            //log.info("TRANSACCION --> ROLLBACK");                 em.getTransaction().rollback();

            throw new EmpleadoException("Ocurrió una error al persisitir en BBDD.");
        } finally {

            if (em.isOpen())
                em.close();
        }


        if (emple == null) {
            throw new EmpleadoLoginErroneo("Datos loginEmpleado incorrectos");
        }

        log.info("Comporbando credenciales");

        if (email != null && emple.getEmail().equals(email) &&
                pass != null && emple.getPassword().equals(pass)) {

            log.info("LOGIN CORRECTO");
            return true;
        } else {
            log.info("LOGIN ERRONEO");
            return false;
        }

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

                //emple = (obj instanceof EmpleadoTParcial) ? (EmpleadoTParcial) obj : (EmpleadoTCompleto) obj;

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
            /*if (result.size() > 0) {
                emple = (Empleado) result.get(0);
            }*/
            log.info("TRANSACCION --> COMMIT");
            em.getTransaction().commit();
        }
        if (em.isOpen())
            em.close();


        return transfer;
    }

}
