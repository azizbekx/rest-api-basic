INSERT INTO tag(tag_name)
VALUES ('tag_1');
INSERT INTO tag(tag_name)
VALUES ('tag_2');
INSERT INTO tag(tag_name)
VALUES ('tag_3');
INSERT INTO tag(tag_name)
VALUES ('tag_4');


INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Jony', 'For celebraties', 11.20, 20, '2019-08-29T06:12:15.156', '2021-08-29T06:12:15.156');

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Jhonas', 'For good work', 1229.20, 20, '2011-08-29T06:12:15.156', '2021-08-29T06:12:15.156');

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Elon', 'For occupation mars', 100000.20, 20, '2020-08-29T06:12:15.156', '2021-09-29T06:12:15.156');

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Thomas', 'For birthday', 19.20, 20, '2019-08-29T06:12:15.156', '2021-08-29T06:12:15.156');

INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id)
VALUES  (1, 2);

INSERT INTO gift_certificate_tag( gift_certificate_id, tag_id)
VALUES (1, 3);

INSERT INTO gift_certificate_tag( gift_certificate_id, tag_id)
VALUES (2, 1)

