package com.educapps;

import javax.persistence.NoResultException;

public class UsuarioDAO extends GenericDAO<Usuario, Long> {

	public Usuario obterPorEmail(String email) {

		Usuario retorno = new Usuario();
		
		manager = factory.createEntityManager();
		
		try {
			retorno = (Usuario) manager.createQuery("from Usuario u where u.email = :email", Usuario.class)
					.setParameter("email", email).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		} finally {
			manager.close();
		}		
		
		return retorno;

	}

}
