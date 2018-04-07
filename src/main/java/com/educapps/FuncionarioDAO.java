package com.educapps;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class FuncionarioDAO {
	
	private EntityManagerFactory factory;
	private EntityManager manager;

	FuncionarioDAO() {
		factory = Persistence.createEntityManagerFactory("funcionario");
	}
	
	public Funcionario cadastra(Funcionario funcionario) {
		manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			trx.begin();
			funcionario.setDataUltimaAlteracao(new Date());
			funcionario.setUsuario("ericlima");
			manager.persist(funcionario);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
		} finally {
			manager.close();
		}
		return funcionario;
	}
	
	public Funcionario altera(Funcionario funcionario) {
		manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			trx.begin();
			funcionario.setDataUltimaAlteracao(new Date());
			funcionario.setUsuario("ericlima");
			manager.merge(funcionario);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
		} finally {
			manager.close();
		}
		return funcionario;
	}

	public boolean exclui(Funcionario funcionario) {
		boolean retorno = true;
		manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			trx.begin();
			funcionario = manager.find(Funcionario.class, funcionario.getId());
			manager.remove(funcionario);
			trx.commit();
		} catch (Exception e) {
			retorno = false;
			trx.rollback();
		} finally {
			manager.close();
		}				
		return retorno;
	}

	public Funcionario obterPorRG(String RG) {
		manager = factory.createEntityManager();
		try {
		return (Funcionario)manager.createQuery("from Funcionario where rg = :rg", 
				Funcionario.class).setParameter("rg", RG).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		} finally {
			manager.close();
		}
		
	}
	

}
