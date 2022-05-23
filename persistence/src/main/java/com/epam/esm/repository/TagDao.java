package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.response.DaoException;

/**
 * Interface {@code GiftCertificateDao} describes operations for working with gift certificate object in database tables.
 * @param <Tag> the type parameter
 */
public interface TagDao extends CRDDao<Tag> {
    /**
     * Method for updating object in db
     * @param name name is tag name which is to get
     * @return T entity is updated
     * @throws DaoException an exception thrown in case of updating error
     */
    Tag getByName(String name) throws DaoException;
}
