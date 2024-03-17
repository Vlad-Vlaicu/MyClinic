CREATE TABLE product_info (

                              id               NUMERIC PRIMARY KEY,
                              name             VARCHAR NOT NULL,
                              description      TEXT,
                              price            VARCHAR NOT NULL,
                              category         VARCHAR NOT NULL

);

CREATE INDEX idx_product_info ON product_info (id);