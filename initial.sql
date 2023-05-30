CREATE TABLE users
(
    id      Serial NOT NULL,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255),
    PRIMARY KEY (id)
);
ALTER TABLE users
    ADD is_deleted boolean;
ALTER TABLE users
ALTER COLUMN is_deleted SET DEFAULT false;