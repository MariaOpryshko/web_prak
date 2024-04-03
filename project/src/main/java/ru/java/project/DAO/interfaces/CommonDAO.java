package ru.java.project.DAO.interfaces;

import ru.java.project.bd_classes.basic.Template;

import java.util.Collection;

public interface CommonDAO<T extends Template<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}
