package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.response.DaoException;
import com.epam.esm.handler.DateHandler;
import com.epam.esm.mapper.GiftCertificateConverter;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.TagDao;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private GiftCertificateDao giftDoa;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private DateHandler dateHandler;


    @Override
    public List<GiftCertificateDto> getAll() throws DaoException {
        List<GiftCertificate> giftCertificates = giftDoa.list();

        return giftCertificates
                .stream()
                .map(GiftCertificateConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getById(long id) throws DaoException {
        return GiftCertificateConverter.toDto(
                giftDoa.getById(id));
    }

    @Override
    public long insert(GiftCertificateDto giftCertificateDto) throws DaoException {
        GiftCertificate giftCertificate;
        giftCertificateDto.setCreate_date(dateHandler.getCurrentDate());
        giftCertificateDto.setLast_update_date(dateHandler.getCurrentDate());
        giftCertificate = GiftCertificateConverter.toEntity(giftCertificateDto);

        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> tagList = tagDao.list();
        List<Tag> reqTagsWithIds = saveNewTags(requestTags, tagList);

        giftCertificate.setTags(reqTagsWithIds);

        System.out.println(giftCertificate);
        return giftDoa.insert(giftCertificate);
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        return giftDoa.removeById(id);
    }

    @Override
    public boolean update(GiftCertificateDto giftCertificateDto) throws DaoException {
        giftCertificateDto.setLast_update_date(dateHandler.getCurrentDate());
        GiftCertificate giftCertificate =
                GiftCertificateConverter.toEntity(giftCertificateDto);

        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> tagList = tagDao.list();

        List<Tag> reqTagWithIds = saveNewTags(requestTags, tagList);
        giftCertificate.setTags(reqTagWithIds);
        System.out.println(giftCertificate);
        return giftDoa.update(giftCertificate);
    }


    private List<Tag> saveNewTags(List<Tag> requestTags, List<Tag> tagList) throws DaoException {
        if (requestTags == null) {
            return null;
        }
        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag tag : tagList) {
                if (Objects.equals(requestTag.getName(), tag.getName())) {
                    isExist = true;
                    requestTag.setId(tag.getId());
                    break;
                }
            }
            if (!isExist) {
                long savedId = tagDao.insert(requestTag);
                requestTag.setId(savedId);
            }
        }
        return requestTags;
    }

    @Override
    public List<GiftCertificateDto> doFilter(String tag_name, String name, String description, String sortBy, String sortDir) throws DaoException {
        return giftDoa.getWithFilters(tag_name, name, description, sortBy, sortDir)
                .stream()
                .map(GiftCertificateConverter::toDto)
                .collect(Collectors.toList());
    }
}
