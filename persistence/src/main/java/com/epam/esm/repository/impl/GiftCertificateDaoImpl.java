package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateListRowMapper;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.response.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.epam.esm.response.ExceptionDaoMessageCode.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_TABLE = "SELECT * FROM gift_certificate ";
    private static final String FIND_ID = "WHERE gc.id = ? ";
    private static final String JOIN_TABLE = "gc LEFT JOIN gift_certificate_tag gct ON " +
            "gc.id=gct.gift_certificate_id LEFT JOIN tag t ON gct.tag_id=t.id ";
    private static final String INSERT_QUERY = "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) " +
            "VALUES (?,?,?,?,?,?);";
    private static final String DELETE_QUERY = "DELETE FROM gift_certificate WHERE id = ? ";
    private static final String DELETE_JOIN_TABLE_QUERY = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id = ? ";
    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Method for getting an object from db by id
     *
     * @param id ID of object to get
     * @return List Entity object from table
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    @Override
    public GiftCertificate getById(long id) throws DaoException {
        try {
            List<GiftCertificate> giftList = jdbc.query(SELECT_TABLE + JOIN_TABLE + FIND_ID, new GiftCertificateListRowMapper(), id);
            if (giftList != null) return giftList.size() == 1 ? giftList.get(0) : null;
            return null;
        } catch (DataAccessException e) {
            throw new DaoException(id, NO_ENTITY_WITH_ID);
        }
    }

    /**
     * Method for getting a list of object from db
     *
     * @return List Entity object from table
     * @throws DaoException an exception thrown in case of not found any object
     */
    @Override
    public List<GiftCertificate> list() throws DaoException {
        try {
            return jdbc.query(SELECT_TABLE + JOIN_TABLE, new GiftCertificateListRowMapper());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }

    }

    /**
     * Method for inserting object to db
     *
     * @param gift - Object to save
     * @return T entity is inserted
     * @throws DaoException an exception thrown in case of creating object error
     */
    @Override
    @Transactional
    public long insert(GiftCertificate gift) throws DaoException {
        try {
            KeyHolder key = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, gift.getName());
                ps.setString(2, gift.getDescription());
                ps.setString(3, String.valueOf(gift.getPrice()));
                ps.setString(4, String.valueOf(gift.getDuration()));
                ps.setString(5, String.valueOf(gift.getCreateDate()));
                ps.setString(6, String.valueOf(gift.getLastUpdateTime()));
                return ps;
            }, key);
            updateTags(gift, key.getKey().longValue());
            return key.getKey().longValue();
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    /**
     * Method for removing object from db by id
     *
     * @param id ID of object to get
     * @return boolean if operation successfully completed true else false
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    @Override
    @Transactional
    public boolean removeById(long id) throws DaoException {
        try {
            jdbc.update(DELETE_JOIN_TABLE_QUERY, id);
            return jdbc.update(DELETE_QUERY, id) > 0;
        } catch (DataAccessException e) {
            throw new DaoException(id, NO_ENTITY_WITH_ID);
        }

    }

    /**
     * Method for updating object in db
     *
     * @param giftC object is to update
     * @return T entity is updated
     * @throws DaoException an exception thrown in case of updating error
     */
    @Override
    @Transactional
    public boolean update(GiftCertificate giftC) throws DaoException {
        try {
            updateTags(giftC, giftC.getId());
            return jdbc.update(createUpdateQuery(giftC), giftC.getId()) > 0;
        } catch (DataAccessException e) {
            throw new DaoException(giftC.getId(), SAVING_ERROR);
        }
    }

    private void updateTags(GiftCertificate gift, Long giftCertificateId) {
        String addTags = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)" +
                "VALUES(?,?)";
        if (gift.getTags() == null) {
            return;
        }
        List<Long> tagIds = getTagIds(gift);
        for (Long tagId : tagIds) {
            jdbc.update(addTags, giftCertificateId, tagId);
        }
    }


    @Override
    public List<GiftCertificate> getWithFilters(String tag_name, String name, String description, String sortBy, String sortDir) throws DaoException {
        try{
        String filterQuery = createFilterQuery(tag_name, name, description, sortBy, sortDir);
        return jdbc.query(filterQuery, new GiftCertificateListRowMapper());
        }catch (DataAccessException e){
            throw new DaoException(NO_ENTITY);
        }
    }
    private String createFilterQuery(String tag_name, String name, String description, String sortBy, String sortDir) {
        String filterQuery = SELECT_TABLE + JOIN_TABLE;
        String sortQuery = "";
        StringJoiner findQuery =new StringJoiner( " AND ");
        if (tag_name != null) {
            findQuery.add("t.tag_name = '" + tag_name + "' ");
        }
        if (name != null) {
            findQuery.add("gc.name LIKE '%" + name + "%' ");
        }
        if (description != null) {
            findQuery.add("gc.description LIKE '%" + description + "%' ");
        }
        if (sortBy != null) {
            sortQuery = " ORDER BY " + sortBy;
            if (sortDir != null) {
                sortQuery += " " + sortDir;
            }
        }
        filterQuery += findQuery.toString().isEmpty()?sortQuery: " WHERE " + findQuery+sortQuery;
        return filterQuery;
    }
    private String createUpdateQuery(GiftCertificate giftC) {
        String updateQuery = "UPDATE gift_certificate SET ";
        StringJoiner stringJoiner = new StringJoiner(", ");
        if (giftC.getName() != null) {
            stringJoiner.add("name  = '" + giftC.getName() + "' ");
        }
        if (giftC.getDescription() != null) {
            stringJoiner.add("description = '" + giftC.getDescription() + "' ");
        }
        if (giftC.getPrice() != null) {
            stringJoiner.add("price = '" + giftC.getPrice() + "' ");
        }
        if (giftC.getDuration() != 0) {
            stringJoiner.add("duration = '" + giftC.getDuration() + "' ");
        }
        updateQuery += stringJoiner + " WHERE id = ?";
        return updateQuery;
    }
    private List<Long> getTagIds(GiftCertificate giftCertificate) {
        return giftCertificate.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
    }
}