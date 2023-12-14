CREATE TABLE DEVICE
(
    device_id                  SERIAL PRIMARY KEY,
    description                VARCHAR(255)     NOT NULL,
    maximum_hourly_consumption DOUBLE PRECISION NOT NULL,
    device_external_id         UUID             NOT NULL UNIQUE,
    user_external_id           UUID             NOT NULL
);

CREATE TABLE ADDRESS
(
    address_id SERIAL PRIMARY KEY,
    country    VARCHAR(100) NOT NULL,
    county     VARCHAR(100) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    number     VARCHAR(100) NOT NULL,
    device_id  SERIAL       NOT NULL UNIQUE,
    CONSTRAINT FK_address_device FOREIGN KEY (device_id) REFERENCES DEVICE (device_id)
);

CREATE TABLE EMS_USER
(
    user_id          SERIAL PRIMARY KEY,
    firstname        VARCHAR(20) NOT NULL,
    lastname         VARCHAR(20) NOT NULL,
    email            VARCHAR(20) NOT NULL UNIQUE,
    user_external_id UUID        NOT NULL UNIQUE
);