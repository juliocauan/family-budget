package br.com.juliocauan.familybudget.domain.application.dao.base;

import java.util.List;

public interface BaseDAO<E, ID>{
    List<? extends E> getAll();
    void save(E entity);
    E findOne(ID id);
    void update(ID oldEntityId, E newEntity);
    void delete(ID id);
}
