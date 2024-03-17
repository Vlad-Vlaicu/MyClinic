CREATE TABLE users (

    id              INTEGER PRIMARY KEY,
    email           VARCHAR(64) UNIQUE NOT NULL,
    password        TEXT NOT NULL ,
    role            VARCHAR NOT NULL

);

INSERT INTO users (id, email, password, role)
VALUES (1, 'admin', '$2a$10$BiEDMLg9TDcyHR7EeQuEd.KiFoNJQ1Yj1plqZcDym6HKBKnozHpAi', 'ADMIN')
    ON CONFLICT (email) DO NOTHING;