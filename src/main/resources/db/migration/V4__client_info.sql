CREATE TABLE client_info (

                       id               INTEGER PRIMARY KEY,
                       user_id          INTEGER UNIQUE NOT NULL REFERENCES users (id) ON DELETE CASCADE,
                       personal_data    TEXT NOT NULL ,
                       creation_date    VARCHAR NOT NULL

);

CREATE INDEX idx_users ON users (id);
CREATE INDEX idx_client_info ON client_info (id);