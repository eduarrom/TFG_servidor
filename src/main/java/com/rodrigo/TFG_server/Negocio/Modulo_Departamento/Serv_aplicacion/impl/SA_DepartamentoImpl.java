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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

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
    /*public Departamento crearDepartamento(Departamento departamentoNuevo) throws DepartamentoException {
        Departamento depart;

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            log.info("Persistiendo departamento en BBDD");
            depart = em.merge(departamentoNuevo);
            System.out.println(depart);

            log.info("TRANSACCION --> COMMIT");
            em.getTransaction().commit();
        }
        em.close();

        return depart;
    }*/
    @Override
    public TDepartamento crearDepartamento(TDepartamento departamentoNuevo) throws DepartamentoException {
        log.info("creando departamento...");

        Departamento depart = null;
        log.debug("departamentoNuevo = '" + departamentoNuevo + "'");

        if (departamentoNuevo == null) {
            log.error("Departamento es null");
            throw new DepartamentoException("El departamento para persistir en null");
        }

        if (departamentoNuevo.getSiglas() == null || departamentoNuevo.getSiglas().equals("")) {
            log.error("Siglas de departamento es null");
            throw new DepartamentoException("Ocurrio un erro con las Siglas.");

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
                        //throw new EmpleadoFieldNullException((PropertyValueException) e2.getCause());

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
                    throw new DepartamentoYaExisteExcepcion("departamento ya existente");
                }
            }
        }

        return depart.crearTransferSimple();
    }


    @Override
    public TDepartamentoCompleto buscarByID(Long id) throws DepartamentoException {
        Departamento depart;

        log.info("id = [" + id + "]");

        if (id == null || id <= 0) {
            log.error("El id para buscar en null, 0 o negativo");
            throw new DepartamentoException("El id para buscar en null, 0 o negativo");
        }

        log.info("Creando Entity Manager");
        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();
            try {


                log.info("Buscando departamento en BBDD");
                depart = em.find(Departamento.class, id);


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

        return depart.crearTransferCompleto();
    }

    @Override
    public boolean eliminarDepartamento(TDepartamento departEliminar) {

        boolean result;

        EntityManager em = EMFSingleton.getInstance().createEntityManager();

        {
            log.info("TRANSACCION --> BEGIN");
            em.getTransaction().begin();

            try {
                em.remove(em.find(Departamento.class, departEliminar.getId()));
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
                .map((d)-> d.crearTransferSimple())
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        List<TDepartamento> lista = new SA_DepartamentoImpl().listarDepartamentos();

        System.out.println("lista = [" + lista + "]");

    }


    public TDepartamentoCompleto buscarBySiglas(String siglas) throws DepartamentoException {
        Departamento depart = null;
        log.debug("siglas = '" + siglas + "'");


        //Validacion del email
        if (siglas == null || siglas.equals("")) {
            log.error("Las siglas es invalido");

//            try {
//                throw new DepartamentoFieldNullException(
//                        new PropertyValueException("Departamento.siglas es erroneo.",
//                                Departamento.class.toString(),
//                                Departamento.class.getDeclaredField("siglas").toString()));
//            } catch (NoSuchFieldException e) {
            log.error("Ocurrio un error inesperado.");
            throw new DepartamentoException("Ocurrio un error con las siglas.");
//            }
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

        return depart.crearTransferCompleto();
    }


    public String saludoREST(String nombre) {
        return "Hola " + nombre + " desde servico REST :)";
    }

}
