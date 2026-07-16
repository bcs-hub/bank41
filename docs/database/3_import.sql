INSERT INTO bank.role (id, name) VALUES (default, 'admin');
INSERT INTO bank.role (id, name) VALUES (default, 'customer');

INSERT INTO bank."user" (id, role_id, username, password, status) VALUES (default, 1, 'admin', '123', 'A');
INSERT INTO bank."user" (id, role_id, username, password, status) VALUES (default, 2, 'rain', '123', 'A');
INSERT INTO bank."user" (id, role_id, username, password, status) VALUES (default, 2, 'mitteaktiivne', '123', 'D');


INSERT INTO bank.city (id, name) VALUES (default, 'Tartu');
INSERT INTO bank.city (id, name) VALUES (default, 'Tallinn');
INSERT INTO bank.city (id, name) VALUES (default, 'Pärnu');


INSERT INTO bank.location (id, city_id, name, number_of_atms, status, lng, lat) VALUES (default, 2, 'Sikupilli Prisma', 5, 'A', 24.7795000, 59.4369000);
INSERT INTO bank.location (id, city_id, name, number_of_atms, status, lng, lat) VALUES (default, 2, 'Tondi Selver', 3, 'A', 24.7120000, 59.4136000);


INSERT INTO bank.transaction_type (id, name) VALUES (default, 'raha sisse');
INSERT INTO bank.transaction_type (id, name) VALUES (default, 'raha välja');
INSERT INTO bank.transaction_type (id, name) VALUES (default, 'maksed');


INSERT INTO bank.location_transaction_type (id, location_id, transaction_type_id) VALUES (default, 1, 1);
INSERT INTO bank.location_transaction_type (id, location_id, transaction_type_id) VALUES (default, 1, 2);
INSERT INTO bank.location_transaction_type (id, location_id, transaction_type_id) VALUES (default, 1, 3);
INSERT INTO bank.location_transaction_type (id, location_id, transaction_type_id) VALUES (default, 2, 2);

