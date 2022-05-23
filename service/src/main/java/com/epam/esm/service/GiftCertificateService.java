package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.response.DaoException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends CRUDService<GiftCertificateDto> {
    List<GiftCertificateDto> doFilter(String tag_name, String name, String description, String sortBy, String sortDir) throws DaoException;
}
