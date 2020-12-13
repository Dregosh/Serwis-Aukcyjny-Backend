INSERT INTO category
VALUES (null, 'elektronika', 'komputery, telewizory, radia', ''),
       (null, 'sprzęt agd', 'lodówki, kuchenki', ''),
       (null, 'motoryzacja', 'samochody, akcesoria samochodowe', '');


INSERT INTO user
VALUES (null, 'user1@op.pl', 'user1', '{"city":"Warszawa","state":"mazowieckie",
"street":"Lotna","number":"27","postal":"05-500"}', '2020-12-06 12:00:00',
        'ACTIVE', 'NORMAL', 0, 0, 'Jan', 'Nowak'),
       (null, 'user2@op.pl', 'user2', '{"city":"Kraków","state":"małopolskie",
"street":"Wronia","number":"15","postal":"12-500"}', '2020-12-07 12:00:00',
        'ACTIVE', 'NORMAL', 0, 0, 'Andrzej', 'Kamyk'),
       (null, 'user3@op.pl', 'user3', '{"city":"Poznań","state":"wielkopolskie",
"street":"Ptasia","number":"10","postal":"15-500"}', '2020-12-08 12:00:00',
        'ACTIVE', 'NORMAL', 0, 0, 'Michał', 'Domer'),
       (null, 'user4@op.pl', 'user4', '{"city":"Warszawa","state":"mazowieckie",
"street":"Krucza","number":"8","postal":"12-200"}', '2020-12-09 12:00:00',
        'ACTIVE', 'NORMAL', 0, 0, 'Witold', 'Jak'),
       (null, 'user5@op.pl', 'user5', '{"city":"Kraków","state":"małopolskie",
"street":"Kacza","number":"5","postal":"10-100"}', '2020-12-10 12:00:00',
        'ACTIVE', 'NORMAL', 0, 0, 'Anna', 'Gazda');

INSERT INTO auction
VALUES (null, 0, 'Komputer X200', 'Zestaw komputerowy', 100, 200, 0,
        '{"city":"Warszawa"}', '2020-12-13 12:00:00', '2020-12-20 12:00:00', 'CREATED',
        1),
       (null, 0, 'TV Sony-5', 'Telewizor', 200, 500, 0, '{"city":"Kraków"}', '2020-12-11
12:00:00', '2020-12-18 12:00:00', 'CREATED', 2),
       (null, 0, 'Radio', 'Radioodbiornik', 50, 100, 0, '{"city":"Poznań"}',
        '2020-12-05 12:00:00', '2020-12-12 12:00:00', 'ENDED', 3),
       (null, 0, 'Komputer P300', 'Zestaw komputerowy', 290, 600, 0, '{"city":"Poznań"}',
        '2020-12-06 12:00:00', '2020-12-13 12:00:00', 'CREATED', 3),
       (null, 0, 'TV Samsung-4', 'Telewizor', 200, 500, 0, '{"city":"Warszawa"}',
        '2020-12-10 12:00:00', '2020-12-24 12:00:00', 'STARTED', 2);

INSERT INTO bid
VALUES (null, 1, 2, 110),
       (null, 2, 3, 220);

INSERT INTO observation
VALUES (null, 2, 3);
