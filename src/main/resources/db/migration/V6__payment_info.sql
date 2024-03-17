CREATE TABLE payment_info (

                               id               NUMERIC PRIMARY KEY,
                               user_id          INTEGER UNIQUE NOT NULL REFERENCES users (id) ON DELETE CASCADE,
                               creation_date    VARCHAR NOT NULL,
                               product          TEXT NOT NULL,
                               paymentType      VARCHAR NOT NULL,
                               additionalData   TEXT,
                               status           VARCHAR NOT NULL

);

CREATE INDEX idx_payment_info ON payment_info (id);