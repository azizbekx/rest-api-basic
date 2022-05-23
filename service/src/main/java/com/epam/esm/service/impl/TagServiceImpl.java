package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.response.DaoException;
import com.epam.esm.mapper.TagConverter;
import com.epam.esm.repository.impl.TagDaoImpl;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDaoImpl tagDao;

    @Override
    public List<TagDto> getAll() throws DaoException {
        return tagDao.list().stream().map(TagConverter::toDto).collect(Collectors.toList());
    }

    @Override
    public TagDto getById(long id) throws DaoException {
        Tag tag = tagDao.getById(id);
        return TagConverter.toDto(tag);
    }

    @Override
    public TagDto getByName(String name) throws DaoException {
        Tag tag = tagDao.getByName(name);
        return TagConverter.toDto(tag);
    }

    @Override
    public long insert(TagDto tagDto) throws DaoException {
        return tagDao.insert(TagConverter.toEntity(tagDto));
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        return tagDao.removeById(id);
    }

}
