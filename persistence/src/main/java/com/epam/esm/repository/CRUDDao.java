package com.epam.esm.repository;

import com.epam.esm.response.DaoException;

/**
 * Interface {@code CRDDao} describes CRD operations for working with database tables.
 * @param <T> the type parameter
 */

public interface CRUDDao<T> extends CRDDao<T> {
    /**
     * Method for updating object in db
     * @param object object is to update
     * @return T entity is updated
     * @throws DaoException an exception thrown in case of updating error
     */
    boolean update(T object) throws DaoException;

}
