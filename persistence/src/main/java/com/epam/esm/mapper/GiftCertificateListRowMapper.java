package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateListRowMapper implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getBigDecimal("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(LocalDateTime.parse(rs.getString("create_date")));
            giftCertificate.setLastUpdateTime(LocalDateTime.parse(rs.getString("last_update_date")));

            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getLong("id") == giftCertificate.getId()) {
                long tagId = rs.getLong("tag_id");
                String tagName = rs.getString("tag_name");
                tags.add(new Tag(tagId, tagName));
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
