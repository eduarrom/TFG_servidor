package com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS1.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS1.PasswordSynchronizerSTS1;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS1.entidad.CredencialesSTS1;
import com.rodrigo.TFG_server.Integracion.EMFSingleton;

public class PasswordSynchronizerSTS1Impl implements PasswordSynchronizerSTS1{

	public PasswordSynchronizerSTS1Impl() {}
	
	@Override
	public void synchronize(String user, String pass) {
		EntityManager em = EMFSingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		try {
		CredencialesSTS1 c = (CredencialesSTS1) em
                .createNamedQuery("CredencialesSTS1.buscarPorUsuario")
                .setParameter("user", user)
                .getSingleResult();
		
		c.setPass(pass);
		
		em.getTransaction().commit();
		} catch (NoResultException e) {
            em.persist(new CredencialesSTS1(user, pass));
            
            em.getTransaction().commit();
        }  finally {

            if (em.isOpen()) {
                em.close();
            }
        }
		
		

	}

}
