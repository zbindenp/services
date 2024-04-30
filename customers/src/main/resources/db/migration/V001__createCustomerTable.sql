CREATE TABLE customer
(
    id   bigint NOT NULL IDENTITY,
    name NVARCHAR(64) NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);
INSERT INTO customer(name)
values ('Hugo');
INSERT INTO customer(name)
values ('Franz');
INSERT INTO customer(name)
values ('Josef');