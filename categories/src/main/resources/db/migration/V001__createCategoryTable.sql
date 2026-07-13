CREATE TABLE category
(
    id   bigint NOT NULL,
    name NVARCHAR(64) NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);
INSERT INTO category(id, name)
values (1, 'Food');
INSERT INTO category(id, name)
values (2, 'Tools');

CREATE SEQUENCE category_id_seq START WITH 1 MINVALUE 0 INCREMENT BY 1;