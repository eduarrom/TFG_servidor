package com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS2.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.entidad.CredencialesREST;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS2.PasswordSynchronizerSTS2;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS2.entidad.CredencialesSTS2;
import com.rodrigo.TFG_server.Integracion.EMFSingleton;

public class PasswordSynchronizerSTS2Impl implements PasswordSynchronizerSTS2{

	public PasswordSynchronizerSTS2Impl() {}
	
	@Override
	public void synchronize(String user, String pass) {
		EntityManager em = EMFSingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		try {
		CredencialesSTS2 c = (CredencialesSTS2) em
                .createNamedQuery("CredencialesSTS2.buscarPorUsuario")
                .setParameter("user", user)
                .getSingleResult();
		
		c.setPass(pass);
		
		em.getTransaction().commit();
		} catch (NoResultException e) {
            em.persist(new CredencialesSTS2(user, pass));
            
            em.getTransaction().commit();
        }  finally {

            if (em.isOpen()) {
                em.close();
            }
        }
	}

}
