-- DROP DATABASE giftsystem;
--
-- CREATE DATABASE IF NOT EXISTS giftsystem;
--
-- USE giftsystem;

CREATE TABLE IF NOT EXISTS gift_certificate(
                                               id BIGINT AUTO_INCREMENT,
                                               name VARCHAR(40) NOT NULL,
    description TEXT(300),
    price DECIMAL(10,2)  NOT NULL,
    duration SMALLINT  NOT NULL,
    create_date VARCHAR(24) NOT NULL,
    last_update_date VARCHAR(24) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS tag
(
    id       BIGINT AUTO_INCREMENT,
    tag_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    id BIGINT AUTO_INCREMENT,
    gift_certificate_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(gift_certificate_id) REFERENCES gift_certificate(id),
    FOREIGN KEY(tag_id) REFERENCES tag(id)
    );


