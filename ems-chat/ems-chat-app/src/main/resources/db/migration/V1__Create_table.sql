CREATE TABLE MESSAGE
(
    message_id          SERIAL PRIMARY KEY,
    receiver_id         UUID         NOT NULL,
    sender_id           UUID         NOT NULL,
    sent_at             TIMESTAMP    NOT NULL,
    content             VARCHAR(300) NOT NULL,
    seen                BOOLEAN      NOT NULL,
    message_external_id UUID         NOT NULL UNIQUE
);

CREATE TABLE CONTACT
(
    contact_id       SERIAL PRIMARY KEY,
    firstname        VARCHAR(300) NOT NULL,
    lastname         VARCHAR(300) NOT NULL,
    email            VARCHAR(300) NOT NULL,
    user_external_id UUID         NOT NULL
);

INSERT INTO CONTACT
    (firstname, lastname, email, user_external_id)
VALUES ('Super', 'Admin', 'super_admin@ems.ro', 'ce4c58dd-249c-4daa-8748-eb73140d6146');