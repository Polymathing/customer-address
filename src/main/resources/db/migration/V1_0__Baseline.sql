CREATE TABLE IF NOT EXISTS customer
(
    "id"                BIGINT       NOT NULL,
    "name"              VARCHAR(255) NOT NULL,
    "age"               INTEGER      NOT NULL,
    "registration_date" TIMESTAMP    NOT NULL,
    "last_updated"       TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS address
(
    "id"       BIGINT AUTO_INCREMENT,
    "zip_code" VARCHAR(255) NOT NULL,
    "number"   INTEGER      NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX zip_code_number_idx ON address (zip_code, number);

CREATE TABLE IF NOT EXISTS customer_addresses
(
    "customer_id" BIGINT NOT NULL,
    "address_id"  BIGINT NOT NULL,
    PRIMARY KEY (customer_id, address_id)
);

CREATE INDEX customer_addresses_customer_id_idx ON customer_addresses (customer_id);
CREATE INDEX customer_addresses_address_id_idx ON customer_addresses (address_id);