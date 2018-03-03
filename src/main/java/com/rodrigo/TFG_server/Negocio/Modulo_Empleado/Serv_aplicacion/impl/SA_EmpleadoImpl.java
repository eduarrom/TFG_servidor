package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.impl;


import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoYaExisteExcepcion;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoFieldNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Excepciones.EmpleadoNullException;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Serv_aplicacion.SA_Empleado;
import com.rodrigo.TFG_server.Negocio.Utils.EmailValidator;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * The type Sa empleado.
 */
public class SA_EmpleadoImpl implements SA_Empleado {

    private final static Logger log = LoggerFactory.getLogger(SA_EmpleadoImpl.class);

    static private EntityManagerFactory emf;

    private EntityManager em;

    /**
     * Instantiates a new Sa empleado.
     */
    public SA_EmpleadoImpl() {
        emf = Persistence.createEntityManagerFactory("aplicacion");
    }


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

        Empleado result = null;
        log.debug("empleadoNuevo = '" + empleadoNuevo + "'");

        if (empleadoNuevo == null) {
            log.error("Empleado es null");
            throw new EmpleadoNullException("Argument empleadoNuevo is null");
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


        log.info("Buscando por email...");
        if (buscarByEmail(empleadoNuevo.getEmail()) == null) {
            em = emf.createEntityManager();
            {
                log.debug("Iniciando transacción...");
                em.getTransaction().begin();
                {


                    try {

                        log.info("Persistiendo empleado en BBDD...");
                        result = em.merge(empleadoNuevo);
                        log.debug("result = '" + result + "'");


                        log.debug("Terminando transacción...");
                        em.getTransaction().commit();


                    } catch (PersistenceException e2) {
                        log.error("Ocurrio una excepcion al persisitir: " + e2.getMessage());
                        log.error(e2.getStackTrace().toString());
                        em.getTransaction().rollback();

                        throw new EmpleadoFieldNullException((PropertyValueException) e2.getCause());

                    } catch (Exception e) {
                        log.error("Ocurrió una error al persisitir en BBDD.");
                        log.error(e.getStackTrace().toString());
                        em.getTransaction().rollback();

                        throw new EmpleadoException("Ocurrió una error al persisitir en BBDD.");
                    }

                }
            }
            em.close();
        } else {
            throw new EmpleadoYaExisteExcepcion("Empleado ya existente");
        }

        return result;
    }


    @Override
    public Empleado buscarByID(Long id) {
        Empleado user;

        log.info("Creando Entity Manager");
        em = emf.createEntityManager();

        {
            em.getTransaction().begin();
            log.info("Buscando empleado en BBDD");
            user = em.find(Empleado.class, id);

            em.getTransaction().commit();
        }
        em.close();

        return user;
    }

    @Override
    public boolean eliminarEmpleado(Empleado empleadoEliminar) {


        boolean result;

        em = emf.createEntityManager();

        {
            em.getTransaction().begin();

            try {
                em.remove(em.find(Empleado.class, empleadoEliminar.getId()));
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
    public List<Empleado> listarEmpleados() {

        List<Empleado> lista;


        em = emf.createEntityManager();
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

    public Boolean login(String email, String pass) {
        Empleado emple;


        em = emf.createEntityManager();
        {
            em.getTransaction().begin();

            log.info("Buscando empleado...");
            emple = (Empleado) em
                    .createNamedQuery("Empleado.buscarPorEmail")
                    .setParameter("email", email)
                    .getResultList().get(0);


            em.getTransaction().commit();
        }
        em.close();

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
        em = emf.createEntityManager();
        {
            em.getTransaction().begin();

            log.info("Buscando empleado...");
            List result = em
                    .createNamedQuery("Empleado.buscarPorEmail")
                    .setParameter("email", email)
                    .getResultList();

            if (result.size() > 0) {
                emple = (Empleado) result.get(0);
            }

            em.getTransaction().commit();
        }
        em.close();

        return emple;
    }
}
