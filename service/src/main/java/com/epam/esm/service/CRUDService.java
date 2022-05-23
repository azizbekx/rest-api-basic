package com.epam.esm.service;

import com.epam.esm.response.DaoException;

public interface CRUDService<T> extends CRDService<T> {
    boolean update(T t) throws DaoException;
}
