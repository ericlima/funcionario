package com.educapps;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> implements InterfaceGenericDAO<T, ID> {

		protected EntityManagerFactory factory;
		protected EntityManager manager;
		
		private Class<T> classeEntidade;
		
		@SuppressWarnings("unchecked")
		public GenericDAO() {
			this.classeEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			        .getActualTypeArguments()[0];
			
			factory = Persistence.createEntityManagerFactory("funcionario");
			manager = factory.createEntityManager();
			
		}
		
		@Override
		public T buscarPorId(ID id) {
			manager = factory.createEntityManager();
			try {
				return manager.find(classeEntidade, id);
			} finally {
				manager.close();
			}			
		}

		@Override
		public T salvar(T entidade) {
			manager = factory.createEntityManager();
			EntityTransaction trx = manager.getTransaction();
			try {
				trx.begin();
				manager.persist(entidade);
				trx.commit();
			} catch (Exception e) {
				trx.rollback();
			} finally {
				manager.close();
			}
			return entidade;
			
		}

		@Override
		public void excluir(ID id) {
			manager = factory.createEntityManager();
			EntityTransaction trx = manager.getTransaction();
			try {
				trx.begin();
				T entidade = buscarPorId(id);
				manager.remove(entidade);
				trx.commit();
			} catch (Exception e) {
				trx.rollback();
			} finally {
				manager.close();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<T> obterTodos() {
			manager = factory.createEntityManager();
			try {
				return manager.createQuery(
				        String.format("from %s", this.classeEntidade.getName()))
				        .getResultList();
			} finally {
				manager.close();
			}
			
		}

	}
