package com.educapps;

import java.io.Serializable;
import java.util.List;

public interface InterfaceGenericDAO<T, ID extends Serializable> {
	
	public T buscarPorId(ID id);
	public T salvar(T entidade);
	public void excluir(ID id);
	public List<T> obterTodos();

}
