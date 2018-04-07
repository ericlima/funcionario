package com.educapps;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.educapps.Usuario.usuarioSituacao;

public class CriaTabelas {

	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionario");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		Usuario usuario = new Usuario();

		usuario.setEmail("eric.george.lima@gmail.com");
		usuario.setSenha("s3gredo");
		usuario.setNomeContato("Eric Lima");
		usuario.setTelefone("11951208931");
		usuario.setSituacao(usuarioSituacao.ATIVO);
		manager.persist(usuario);

		trx.commit();

		usuario = manager.createQuery("from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", "eric.george.lima@gmail.com").getSingleResult();
		
		System.out.println(usuario.getNomeContato());
		

	}

}
