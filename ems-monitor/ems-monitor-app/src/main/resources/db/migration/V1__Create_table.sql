CREATE TABLE MEASURE
(
    measure_id        SERIAL PRIMARY KEY,
    timestamp         TIMESTAMP,
    device_id         UUID,
    measurement_value DOUBLE PRECISION
);

CREATE TABLE DEVICE
(
    id                         SERIAL PRIMARY KEY,
    device_id                  UUID UNIQUE,
    name                       VARCHAR(100),
    measure_number             INTEGER,
    maximum_hourly_consumption DOUBLE PRECISION,
    user_id                    UUID
);

INSERT INTO DEVICE (device_id, name, measure_number, maximum_hourly_consumption, user_id)
VALUES ('8fcef837-306a-4ea8-958e-018ab36dcf32', 'Dev#1', 0, 10, 'ce4c58dd-249c-4daa-8748-eb73140d6146'),
       ('d791a7d2-ce5c-4e55-8e38-890136ce7385', 'Dev#2', 0, 120, 'ce4c58dd-249c-4daa-8748-eb73140d6146');