package br.com.juliocauan.familybudget.domain.application.service.base;

import java.util.List;

public abstract class BaseService<E, I> {
    protected abstract List<E> getAll();
    protected abstract E save(E entity);
    protected abstract E findOne(I id);
    protected abstract E update(I oldEntityId, E newEntity);
    protected abstract void delete(I id);
    protected abstract String getClassName();
    protected final String getNotFoundExceptionMessage(I id){
        return String.format("Couldn't find %s with id %s!", getClassName(), id);
    }
    protected final String getDuplicatedExceptionMessage(){
        return String.format("%s duplicate: Same description in the same month!", getClassName());
    }
}
