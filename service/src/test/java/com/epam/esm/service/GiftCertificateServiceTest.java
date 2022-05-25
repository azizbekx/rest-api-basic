package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.handler.DateHandler;
import com.epam.esm.mapper.GiftCertificateConverter;
import com.epam.esm.repository.impl.GiftCertificateDaoImpl;
import com.epam.esm.repository.impl.TagDaoImpl;
import com.epam.esm.response.DaoException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateDaoImpl giftDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    private final DateHandler dateHandler = Mockito.mock(DateHandler.class);
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static final Tag TAG_1 = new Tag(1, "tagName1");

    private static final GiftCertificate GIFT_CERTIFICATE_1 =
            new GiftCertificate(1, "Jony", "For celebraties", new BigDecimal("11.2"), 20,
                    LocalDateTime.parse("2019-08-29T06:12:15.156"),
                    LocalDateTime.parse("2022-08-29T06:12:15.156"),
                    Arrays.asList(new Tag(2, "tag_2"),
                            new Tag(3, "tag_3")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 =
            new GiftCertificate(2, "Jhonas", "For good work", new BigDecimal("1229.2"), 20,
                    LocalDateTime.parse("2011-08-29T06:12:15.156"),
                    LocalDateTime.parse("2022-08-29T06:12:15.156"),
                    Collections.singletonList(new Tag(1, "tag_1")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 =
            new GiftCertificate(3, "Elon", "For occupation mars", new BigDecimal("100000.2"), 20,
                    LocalDateTime.parse("2022-09-29T06:12:15.156"),
                    LocalDateTime.parse("2022-09-29T06:12:15.156"));

    private static final GiftCertificate GIFT_CERTIFICATE_4 =
            new GiftCertificate(4, "Thomas", "For birthday", new BigDecimal("19.2"), 20,
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    LocalDateTime.parse("2022-08-29T06:12:15.156"));

    @Test
    public void getById_test() throws DaoException {
        when(giftDao.getById(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        GiftCertificate actual = GiftCertificateConverter.toEntity(giftCertificateService.getById(GIFT_CERTIFICATE_1.getId()));

        assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    public void getAllTest() throws DaoException {
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_4);
        when(giftDao.list()).thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getAll()
                .stream()
                .map(GiftCertificateConverter::toEntity)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }
    @Test
    public void update_test() throws DaoException {

        GiftCertificate newGiftC = new GiftCertificate();
        newGiftC.setId(6L);
        newGiftC.setName("Neil Armstrong");
        newGiftC.setDescription("For moon");
        newGiftC.setCreateDate(LocalDateTime.parse("2022-01-29T06:12:15.156"));

        GiftCertificate expected = new GiftCertificate();
        expected.setId(6L);
        expected.setName("Neil Armstrong");
        expected.setDescription("For moon");
        expected.setCreateDate(LocalDateTime.parse("2022-01-29T06:12:15.156"));
        expected.setLastUpdateTime(LocalDateTime.parse("2022-09-29T06:12:15.156"));

        when(dateHandler.getCurrentDate()).thenReturn("2022-09-29T06:12:15.156");
        when(giftDao.update(expected)).thenReturn(true);
        boolean success = giftCertificateService.update(GiftCertificateConverter.toDto(newGiftC));

        assertTrue(success);
    }


    @Test
    public void insert_test() throws DaoException {
        List<Tag> tags = Arrays.asList(
                new Tag("tag_99"),
                new Tag("tag_1")
        );
        GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate();
        GIFT_CERTIFICATE_5.setName("Jimmy");
        GIFT_CERTIFICATE_5.setDescription("Jimmy");
        GIFT_CERTIFICATE_5.setPrice(new BigDecimal("192.3"));
        GIFT_CERTIFICATE_5.setDuration(10);
        GIFT_CERTIFICATE_5.setTags(tags);

        List<Tag> reqTagsWithIds = createReqTagsWithIds();
        GiftCertificate setGiftC = new GiftCertificate();
        setGiftC.setName("Jimmy");
        setGiftC.setDescription("Jimmy");
        setGiftC.setPrice(new BigDecimal("192.3"));
        setGiftC.setDuration(10);
        setGiftC.setTags(reqTagsWithIds);
        setGiftC.setCreateDate(LocalDateTime.parse("2022-09-29T06:12:15.156"));
        setGiftC.setLastUpdateTime(LocalDateTime.parse("2022-09-29T06:12:15.156"));

        when(dateHandler.getCurrentDate()).thenReturn("2022-09-29T06:12:15.156");
        when(tagDao.insert(new Tag("tag_99"))).thenReturn(4L);
        when(giftDao.insert(setGiftC)).thenReturn(5L);
        when(tagDao.list()).thenReturn(createTagList());

        long id = giftCertificateService.insert(GiftCertificateConverter.toDto(GIFT_CERTIFICATE_5));


        setGiftC.setId(id);
        GiftCertificate expected = new GiftCertificate(5L,"Jimmy","Jimmy",new BigDecimal("192.3"),10,
                LocalDateTime.parse("2022-09-29T06:12:15.156"),
                LocalDateTime.parse("2022-09-29T06:12:15.156"),
                reqTagsWithIds);

        assertEquals(expected,setGiftC);
        assertEquals(5L, id);

    }

    @Test
    void doFilter_test() throws DaoException {
        String tag_name = null, name = "as", description = null, sortBy = null, sortDir = null;
        when(giftDao.getWithFilters(tag_name, name, description, sortBy, sortDir)).thenReturn(
                Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_4));

        List<GiftCertificate> actual =
                giftCertificateService.doFilter(tag_name, name, description, sortBy, sortDir)
                        .stream()
                        .map(GiftCertificateConverter::toEntity)
                        .collect(Collectors.toList());
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_4);
        assertEquals(expected, actual);
    }

    public List<Tag> createTagList() {
        return Arrays.asList(
                new Tag(1, "tag_1"),
                new Tag(2, "tag_2"),
                new Tag(3, "tag_3")
        );
    }

    private List<Tag> createReqTagsWithIds() {
        return Arrays.asList(
                new Tag(4, "tag_99"),
                new Tag(1, "tag_1")
        );
    }
}
