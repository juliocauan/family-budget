package br.com.juliocauan.familybudget.domain.application.service.base;

import java.util.List;

public abstract class BaseService<E, ID> {
    protected abstract List<? extends E> getAll();
    protected abstract E save(E entity);
    protected abstract E findOne(ID id);
    protected abstract E update(ID oldEntityId, E newEntity);
    protected abstract void delete(ID id);
    protected abstract String getClassName();
    protected final String getNotFoundExceptionMessage(ID id){
        return String.format("Couldn't find %s with id %s!", getClassName(), id);
    }
    protected final String getDuplicatedExceptionMessage(){
        return String.format("%s duplicate: Same description in the same month!", getClassName());
    }
}
