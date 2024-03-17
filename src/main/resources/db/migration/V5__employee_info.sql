CREATE TABLE employee_info (

                             id               INTEGER PRIMARY KEY,
                             user_id          INTEGER UNIQUE NOT NULL REFERENCES users (id) ON DELETE CASCADE,
                             personal_data    TEXT NOT NULL ,
                             creation_date    VARCHAR NOT NULL

);

CREATE INDEX idx_employee_info ON employee_info (id);