package com.epam.esm.repository;

import com.epam.esm.config.DataConfig;
import com.epam.esm.config.DataTestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.impl.GiftCertificateDaoImpl;
import com.epam.esm.response.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = DataConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftDao;

    private static final Tag TAG_1 = new Tag(1,"tag_1");
    private static final Tag TAG_2 = new Tag(2,"tag_2");
    private static final Tag TAG_3 = new Tag(3,"tag_3");
    private static final Tag TAG_4 = new Tag(4,"tag_4");

    private static final GiftCertificate GIFT_CERTIFICATE_1 =
            new GiftCertificate(1, "Jony", "For celebraties", new BigDecimal("11.20"), 20,
                    LocalDateTime.parse("2019-08-29T06:12:15.156"),
                    LocalDateTime.parse("2021-08-29T06:12:15.156"),
                    Arrays.asList(TAG_2, TAG_3));
    private static final GiftCertificate GIFT_CERTIFICATE_2 =
            new GiftCertificate(2, "Jhonas", "For good work", new BigDecimal("1229.20"), 20,
                    LocalDateTime.parse("2011-08-29T06:12:15.156"),
                    LocalDateTime.parse("2021-08-29T06:12:15.156"),
                    Collections.singletonList(TAG_1));
    private static final GiftCertificate GIFT_CERTIFICATE_3 =
            new GiftCertificate(3, "Elon", "For occupation mars", new BigDecimal("100000.20"), 20,
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    LocalDateTime.parse("2021-09-29T06:12:15.156"),
                    Collections.singletonList(new Tag()));
    private static final GiftCertificate GIFT_CERTIFICATE_4 =
            new GiftCertificate(4, "Thomas", "For birthday", new BigDecimal("19.20"), 20,
                    LocalDateTime.parse("2019-08-29T06:12:15.156"),
                    LocalDateTime.parse("2021-08-29T06:12:15.156"),
                    Collections.singletonList(new Tag()));
    @Test
    public void getById_test() throws DaoException {
        GiftCertificate actual = giftDao.getById(4);
        assertEquals(GIFT_CERTIFICATE_4,actual);
    }
    @Test
    public void getAll_test() throws DaoException {
        List<GiftCertificate> actual = giftDao.list();
        List<GiftCertificate> expected = Arrays.asList(
                GIFT_CERTIFICATE_1,
                GIFT_CERTIFICATE_2,
                GIFT_CERTIFICATE_3,
                GIFT_CERTIFICATE_4);
        assertEquals(expected, actual);
    }
    @Test
    public void insert_test() throws DaoException{
        GiftCertificate reqGiftC = new GiftCertificate(
                "The Beatles",
                "Album work",
                new BigDecimal("19992.20"),
                2,
                LocalDateTime.parse("2019-08-29T06:12:15.156"),
                LocalDateTime.parse("2021-08-29T06:12:15.156"),
                Arrays.asList(new Tag(1,"tag_1"), new Tag(5,"tag_44")));

        GiftCertificate expected = new GiftCertificate(
                5,
                "The Beatles",
                "Album work",
                new BigDecimal("19992.20"),
                2,
                LocalDateTime.parse("2019-08-29T06:12:15.156"),
                LocalDateTime.parse("2021-08-29T06:12:15.156"),
                Arrays.asList(TAG_3, TAG_4));

        long id = giftDao.insert(reqGiftC);
        reqGiftC.setId(id);
        assertEquals(5,id);
        assertEquals(expected, reqGiftC);
    }

    @Test
    public void delete_test() throws DaoException{
        boolean success = giftDao.removeById(1);
        assertTrue(success);
        assertNull(giftDao.getById(1));
    }

}
