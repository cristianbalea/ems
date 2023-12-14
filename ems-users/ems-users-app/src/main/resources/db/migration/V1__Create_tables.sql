CREATE TABLE EMS_USER
(
    user_id          SERIAL PRIMARY KEY,
    firstname        VARCHAR(20) NOT NULL,
    lastname         VARCHAR(20) NOT NULL,
    phone_number     VARCHAR(20) NOT NULL,
    cnp              VARCHAR(13) NOT NULL UNIQUE,
    user_external_id UUID        NOT NULL UNIQUE
);

CREATE TABLE ADDRESS
(
    address_id SERIAL PRIMARY KEY,
    country    VARCHAR(100) NOT NULL,
    county     VARCHAR(100) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    number     VARCHAR(100) NOT NULL,
    user_id    SERIAL       NOT NULL,
    CONSTRAINT FK_address_user FOREIGN KEY (user_id) REFERENCES EMS_USER (user_id)
);

INSERT INTO EMS_USER (firstname, lastname, phone_number, cnp, user_external_id)
VALUES ('Super', 'Admin', '+40751678112', '5010101012233', 'ce4c58dd-249c-4daa-8748-eb73140d6146');

INSERT INTO ADDRESS (country, county, city, number, user_id)
VALUES ('Romania', 'Cluj', 'Cluj-Napoca', 'Baritiu 26', 1);