package com.epam.esm.service;

import com.epam.esm.response.DaoException;

import java.util.List;

public interface CRDService<T> {
    List<T> getAll() throws DaoException;
    T getById(long id) throws DaoException;
    long insert(T t) throws DaoException;
    boolean deleteById(long id) throws DaoException;
}
