CREATE TABLE tickets (

                              id               NUMERIC PRIMARY KEY,
                              clientId         INTEGER NOT NULL REFERENCES users (id),
                              employeeId       INTEGER REFERENCES users (id),
                              productId        NUMERIC NOT NULL REFERENCES product_info (id),
                              creationDate     VARCHAR NOT NULL,
                              reserved_time    VARCHAR NOT NULL,
                              paymentId        NUMERIC NOT NULL REFERENCES payment_info (id),
                              status           VARCHAR NOT NULL

);

CREATE INDEX idx_tickets ON tickets (id);