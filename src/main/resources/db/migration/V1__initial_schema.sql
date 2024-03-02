CREATE TABLE users (

    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(64) NOT NULL,
    password        TEXT NOT NULL ,
    role            VARCHAR NOT NULL

);