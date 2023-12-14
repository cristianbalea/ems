INSERT INTO EMS_USER (firstname, lastname, email, user_external_id)
VALUES ('Super', 'Admin', 'super_admin@ems.ro', 'ce4c58dd-249c-4daa-8748-eb73140d6146');

INSERT INTO DEVICE (description, maximum_hourly_consumption, device_external_id, user_external_id)
VALUES ('Dev#1', 10, '8fcef837-306a-4ea8-958e-018ab36dcf32', 'ce4c58dd-249c-4daa-8748-eb73140d6146'),
       ('Dev#2', 120, 'd791a7d2-ce5c-4e55-8e38-890136ce7385', 'ce4c58dd-249c-4daa-8748-eb73140d6146');

INSERT INTO ADDRESS (country, county, city, number, device_id)
VALUES ('Romania', 'Alba', 'Abrud', 'Republicii 12', 1),
       ('Romania', 'Alba', 'Abrud', 'Republicii 21', 2);