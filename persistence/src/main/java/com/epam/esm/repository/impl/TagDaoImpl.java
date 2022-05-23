package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.response.DaoException;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.repository.TagDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static com.epam.esm.response.ExceptionDaoMessageCode.*;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String SELECT_TABLE = "SELECT * FROM tag ";
    private static final String DELETE_QUERY = "DELETE FROM tag WHERE id = ?";
    private static final String FIND_BY_ID = "WHERE id = ? ";
    private static final String SELECT_BY_NAME = "SELECT * FROM tag WHERE tag_name = ?";
    private static final String INSERT_QUERY = "INSERT INTO tag (tag_name) VALUES (?)";
    private static final String DELETE_JOIN_TABLE_QUERY = "DELETE FROM gift_certificate_tag WHERE tag_id = ? ";

    private JdbcTemplate jdbc;

    public TagDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Method for getting a list of object from db
     *
     * @return List Entity object from table
     * @throws DaoException an exception thrown in case of not found any object
     */
    @Override
    public List<Tag> list() throws DaoException {
        try {
            return jdbc.query(SELECT_TABLE, new TagRowMapper());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }
    }

    /**
     * Method for getting an object from db by id
     *
     * @param id ID of object to get
     * @return Found entity object from table with given id
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    @Override
    public Tag getById(long id) throws DaoException {
        try {
            return jdbc.queryForObject(SELECT_TABLE + FIND_BY_ID, new TagRowMapper(), id);
        } catch (DataAccessException e) {
            throw new DaoException(id, NO_ENTITY_WITH_ID);
        }

    }

    /**
     * Method for inserting object to db
     *
     * @param tag - Object to save
     * @return tag entity is inserted
     * @throws DaoException an exception thrown in case of creating object error
     */
    @Override
    @Transactional
    public long insert(Tag tag) throws DaoException {
        try {
            KeyHolder key = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getName());
                return ps;
            }, key);
            return key.getKey().intValue();
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    /**
     * Method for removing object from db by id
     *
     * @param id ID of object to get
     * @return id of object
     * @throws DaoException an exception thrown in case entity with such ID not found
     */
    @Override
    public boolean removeById(long id) throws DaoException {
        try {
            jdbc.update(DELETE_JOIN_TABLE_QUERY, id);
            return jdbc.update(DELETE_QUERY, id) > 0;
        }catch (DataAccessException e){
            throw new DaoException(id, NO_ENTITY_WITH_ID);
        }

    }
    /**
     * Method for getting object from db with name
     *
     * @param name name is tag name which is to get
     * @return tag entity is found by name
     * @throws DaoException an exception thrown in case with such name not found
     */
    @Override
    public Tag getByName(String name) throws DaoException {
        try {
            return jdbc.queryForObject(SELECT_BY_NAME, new TagRowMapper(), name);
        }catch (DataAccessException e){
            throw new DaoException(NO_ENTITY_WITH_NAME);
        }
    }
}
