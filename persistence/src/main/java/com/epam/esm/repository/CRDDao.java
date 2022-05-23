package com.epam.esm.repository;

import com.epam.esm.response.DaoException;

import java.util.List;

/**
 * Interface {@code CRDDao} describes CRD operations for working with database tables.
 *
 * @param <T> the type parameter
 */
public interface CRDDao<T> {
    /**
     * Method for getting a list of object from db
     * @return List Entity object from table
     * @throws DaoException an exception thrown in case of not found any object
     */
    List<T> list() throws DaoException;

    /**
     * Method for getting an object from db by id
     * @param id ID of object to get
     * @return List Entity object from table
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    T getById(long id) throws DaoException;

    /**
     * Method for inserting object to db
     *
     * @param object - Object to save
     * @return T entity is inserted
     * @throws DaoException an exception thrown in case of creating object error
     */
    long insert(T object) throws DaoException;

    /**
     * Method for removing object from db by id
     *
     * @param id ID of object to get
     * @return
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    boolean removeById(long id) throws DaoException;
}
