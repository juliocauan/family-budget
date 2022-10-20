package br.com.juliocauan.familybudget.domain.application.service.base;

import java.util.List;

public abstract class BaseService<E, ID> {
    public abstract List<? extends E> getAll();
    public abstract void save(E entity);
    public abstract E findOne(ID id);
    public abstract void update(ID oldEntityId, E newEntity);
    public abstract void delete(ID id);
    protected abstract Boolean hasDuplicate(E entity);
    protected abstract String getClassName();
    protected final String getNotFoundExceptionMessage(ID id){
        return String.format("Couldn't find %s with id %s!", getClassName(), id);
    }
    protected final String getDuplicatedExceptionMessage(){
        return String.format("%s duplicate: Same description in the same month!", getClassName());
    }
}
