package app.dao;

import app.controller.components.serviceMediator.Service;

import java.util.List;

public interface DAO<T> extends Service {
    void create(T entity) throws DAOException;

    void creat(List<T> entities) throws DAOException;

    void update(T entity) throws DAOException;

    void delete(T entity) throws DAOException;

    List<T> getAll() throws DAOException;

    List<T> get(GetQuery<T> getQuery) throws DAOException;
}
