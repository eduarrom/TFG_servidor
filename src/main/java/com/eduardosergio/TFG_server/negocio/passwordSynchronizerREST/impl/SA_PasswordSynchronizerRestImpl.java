package com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.SA_PasswordSynchronizerRest;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.entidad.CredencialesREST;
import com.eduardosergio.TFG_server.negocio.passwordSynchronizerSTS1.entidad.CredencialesSTS1;
import com.rodrigo.TFG_server.Integracion.EMFSingleton;

public class SA_PasswordSynchronizerRestImpl implements SA_PasswordSynchronizerRest{

	@Override
	public void synchronize(String user, String pass) {
		EntityManager em = EMFSingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		try {
		CredencialesREST c = (CredencialesREST) em
                .createNamedQuery("CredencialesREST.buscarPorUsuario")
                .setParameter("user", user)
                .getSingleResult();
		
		c.setPass(pass);
		
		em.getTransaction().commit();
		} catch (NoResultException e) {
            em.persist(new CredencialesREST(user, pass));
            
            em.getTransaction().commit();
        }  finally {

            if (em.isOpen()) {
                em.close();
            }
        }
	}

}
