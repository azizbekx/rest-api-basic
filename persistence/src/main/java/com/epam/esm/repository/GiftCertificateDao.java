package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.response.DaoException;

import java.util.List;

/**
 * Interface {@code GiftCertificateDao} describes operations for working with gift certificate object in database tables.
 */

public interface GiftCertificateDao extends CRUDDao<GiftCertificate>{

    List<GiftCertificate> getWithFilters(String tag_name, String name, String description, String sortBy, String sortDir) throws DaoException;

}
