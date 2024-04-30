CREATE TABLE product
(
    id   bigint NOT NULL IDENTITY,
    name NVARCHAR(64) NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);
INSERT INTO product(name)
values ('Banane');
INSERT INTO product(name)
values ('Apfel');
INSERT INTO product(name)
values ('Birne');