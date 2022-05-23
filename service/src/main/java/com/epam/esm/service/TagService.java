package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.response.DaoException;

public interface TagService extends CRDService<TagDto> {

    TagDto getByName(String name) throws DaoException;

}
