package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Integracion.EMFSingleton;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.EmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.*;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

/**
 * The type Sa empleado.
 */
public class SA_EmpleadoImpl implements SA_Empleado {

    private final static Logger log = LoggerFactory.getLogger(SA_EmpleadoImpl.class);

    /**
     * Inserta un empleado en la BBDD
     *
     * @param empleadoNuevo
     * @return Empleado insertado en BBDD o null si la entidad ya existe
     * @throws EmpleadoNullException      Si el param es null
     * @throws EmpleadoFieldNullException Si el parametro es
     */
    public Empleado crearEmpleado(Empleado empleadoNuevo) throws EmpleadoException {
        log.info("creando empleado...");

        Empleado emple = null;
        log.debug("empleadoNuevo = '" + empleadoNuevo + "'");

        if (empleadoNuevo == null) {
            log.error("Empleado es null");
            throw new EmpleadoException("El empleado para persistir en null", new EmpleadoNullException("El empleado para persistir en null"));
        }

        if (empleadoNuevo.getEmail() == null || empleadoNuevo.getEmail() == "") {
            log.error("Email de empleado es null");

            try {
                throw new EmpleadoFieldNullException(
                        new PropertyValueException("Empleado.email es erroneo.",
                                Empleado.class.toString(),
                                empleadoNuevo.getClass().getDeclaredField("email").toString()));
            } catch (NoSuchFieldException e) {
                log.error("Ocurrio un error inesperado.");
                throw new EmpleadoException("Ocurrio un erro con el email.");
            }
        }

        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            log.debug("Iniciando transacción...");
            em.getTransaction().begin();
            {

                log.info("Buscando por email...");
                try {
                    Object obj = em
                            .createNamedQuery("Empleado.buscarPorEmail")
                            .setParameter("email", empleadoNuevo.getEmail())
                            .getSingleResult();

                    emple = (obj instanceof EmpleadoTParcial)?(EmpleadoTParcial) obj:(EmpleadoTCompleto) obj;


                } catch (NoResultException e) {
                    log.info("Empleado con email '" + empleadoNuevo.getEmail() + "' no encontrado");
                }


                if (emple == null) {

                    try {

                        log.info("Persistiendo empleado en BBDD...");
                        emple = em.merge(empleadoNuevo);
                        log.debug("result = '" + emple + "'");


                        log.debug("Terminando transacción...");
                        em.getTransaction().commit();


                    } catch (PersistenceException e2) {
                        log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
                        log.error(e2.getStackTrace().toString());
                        //em.getTransaction().rollback();

                        throw e2;
                        //throw new EmpleadoFieldNullException((PropertyValueException) e2.getCause());

                    } catch (Exception e) {
                        log.error("Ocurrió una error al persisitir en BBDD: " + e.getMessage());
                        log.error("EXCEPCION!", e);
                        em.getTransaction().rollback();

                        throw new EmpleadoException("Ocurrió una error al persisitir en BBDD.");
                    } finally {

                        if (em.isOpen())
                            em.close();
                    }

                } else {
                    throw new EmpleadoYaExisteExcepcion("Empleado ya existente");
                }
            }
        }

        return emple;
    }


    @Override
    public Empleado buscarByID(Long id) {
        Empleado user;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Buscando empleado en BBDD");
            user = em.find(Empleado.class, id);
            log.debug("user = '" + user + "'");
            log.debug("user.getDepartamento() = '" + user.getDepartamento() + "'");
            em.getTransaction().commit();
        }
        em.close();

        return user;
    }

    @Override
    public boolean eliminarEmpleado(Empleado empleadoEliminar) {


        boolean result;

        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            em.getTransaction().begin();

            try {
                em.remove(em.find(Empleado.class, empleadoEliminar.getId()));
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
    public List<Empleado> listarEmpleados() {

        List<Empleado> lista;


        EntityManager em = EMFSingleton.getInstance().createEntityManager();
        {
            em.getTransaction().begin();

            lista = em.createNamedQuery("Empleado.listar").getResultList();

            em.getTransaction().commit();
        }
        em.close();

        return lista;
    }

    public String saludar(String nombre) {
        return "Hola " + nombre + ", un saludo desde el servidor CXF :)";
    }

    public Boolean loginEmpleado(String email, String pass) throws EmpleadoException {
        Empleado emple = null;
        log.debug("email = '" + email + "'");


        //Validacion del email
        if (email == null || email == "" || !new EmailValidator().validate(email)) {
            log.error("El email es invalido");

            try {
                throw new EmpleadoFieldNullException(
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
                throw new EmpleadoFieldNullException(
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
                em.getTransaction().commit();
            }

        /*} catch (PersistenceException e2) {
            log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
            log.error(e2.getStackTrace().toString());
            em.getTransaction().rollback();

            throw new EmpleadoFieldNullException((PropertyValueException) e2.getCause());*/

        } catch (Exception e) {
            log.error("Ocurrió una error al persisitir en BBDD.");
            log.error("Mensaje: " + e.getMessage());
            log.error(e.getStackTrace().toString());
            //em.getTransaction().rollback();

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
    public Empleado buscarByEmail(String email) throws EmpleadoException {
        Empleado emple = null;
        log.debug("email = '" + email + "'");


        //Validacion del email
        if (email == null || email == "" || !new EmailValidator().validate(email)) {
            log.error("El email es invalido");

            try {
                throw new EmpleadoFieldNullException(
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
            em.getTransaction().begin();

            log.info("Buscando empleado...");
            try {
                Object obj = (Object) em
                        .createNamedQuery("Empleado.buscarPorEmail")
                        .setParameter("email", email)
                        .getSingleResult();

                emple = (obj instanceof EmpleadoTParcial)?(EmpleadoTParcial) obj:(EmpleadoTCompleto) obj;

                log.info("emple = '" + emple + "'");


            } catch (NoResultException e) {
                log.info("Empleado con email '" + email + "' no encontrado");
                emple = null;
            }
            /*if (result.size() > 0) {
                emple = (Empleado) result.get(0);
            }*/
            log.info("*********************************************************");
            log.info("*********************************************************");
            log.debug("emple = '" + emple + "'");
            log.info("*********************************************************");
            log.info("*********************************************************");
            em.getTransaction().commit();
        }
        if (em.isOpen())
            em.close();



        return emple;
    }


  /*  public boolean emIsOpen() {
        return em.isOpen();
    }

    public boolean transactionIsActive() {
        return em.getTransaction().isActive();
    }*/

  /*  @Override
    protected void finalize() throws Throwable {
        log.info("CERRANDO EMF");
        EMFSingleton.getInstance().close();
        super.finalize();
    }*/
}
