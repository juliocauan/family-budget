package br.com.juliocauan.familybudget.domain.application.dao.base;

import java.util.List;

public interface BaseDAO<E>{
    List<? extends E> getAll();
    void save(E entity);
}
