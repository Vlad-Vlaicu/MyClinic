CREATE TABLE tickets (

                              id               NUMERIC PRIMARY KEY,
                              client_id         INTEGER NOT NULL REFERENCES users (id),
                              employee_id       INTEGER REFERENCES users (id),
                              product_id        NUMERIC NOT NULL REFERENCES product_info (id),
                              creation_date     VARCHAR NOT NULL,
                              reserved_time    VARCHAR NOT NULL,
                              payment_id        NUMERIC NOT NULL REFERENCES payment_info (id),
                              status           VARCHAR NOT NULL

);

CREATE INDEX idx_tickets ON tickets (id);