--Create - Insert
--Read - Select
--Update
--Delete

select *
from bank.role;


SELECT *
FROM bank."user" u
         JOIN bank.role r ON r.id = u."role_id";

-- =====================================================
-- SELECT
-- =====================================================

-- Näita kõiki kasutajaid
SELECT *
FROM bank."user";


-- Näita ainult kasutajanime ja staatust
SELECT username, status
FROM bank."user";


-- Leia kasutaja id järgi
SELECT *
FROM bank."user"
WHERE id = 3;


-- Leia kasutaja kasutajanime järgi
SELECT *
FROM bank."user"
WHERE username = 'asya';


-- Näita ainult aktiivseid kasutajaid
SELECT *
FROM bank."user"
WHERE status = 'A';


-- =====================================================
-- USER + ROLE
-- =====================================================

-- Näita kasutajaid koos nende rollidega
SELECT *
FROM bank."user" u
         JOIN bank.role r
              ON r.id = u.role_id;

-- u = tabeli user lühinimi
-- r = tabeli role lühinimi
-- r.id = rolli id
-- u.role_id = kasutaja juures salvestatud rolli id
-- JOIN ühendab read, kus need väärtused kattuvad


-- Näita kasutajanime ja rolli nime
SELECT
    u.username,
    r.name
FROM bank."user" u
         JOIN bank.role r
              ON r.id = u.role_id;


-- Anna rolli nime veerule uus nimi
SELECT
    u.username,
    r.name AS role_name
FROM bank."user" u
         JOIN bank.role r
              ON r.id = u.role_id;

-- AS role_name tähendab:
-- näita r.name veergu nimega role_name


-- =====================================================
-- USER + PROFILE
-- =====================================================

-- Ühenda kasutaja tema profiiliga
SELECT *
FROM bank."user" u
         JOIN bank.profile p
              ON p.user_id = u.id;

-- p.user_id sisaldab kasutaja id-d
-- seetõttu võrdleme profile.user_id ja user.id


-- Näita kasutajanime, aadressi ja telefoninumbrit
SELECT
    u.username,
    p.address,
    p.phone_number
FROM bank."user" u
         JOIN bank.profile p
              ON p.user_id = u.id;


-- =====================================================
-- USER + ROLE + PROFILE
-- =====================================================

-- Ühenda korraga kolm tabelit
SELECT
    u.username,
    r.name AS role,
    p.address,
    p.phone_number
FROM bank."user" u

-- Ühenda kasutaja roll
         JOIN bank.role r
              ON r.id = u.role_id

-- Ühenda kasutaja profiil
         JOIN bank.profile p
              ON p.user_id = u.id;


-- =====================================================
-- USER + USER_IMAGE
-- =====================================================

-- Näita kasutajat koos tema pildiga
SELECT *
FROM bank."user" u
         JOIN bank.user_image ui
              ON ui.user_id = u.id;

-- ui.user_id sisaldab kasutaja id-d
-- seetõttu ühendame selle u.id-ga


-- =====================================================
-- CITY
-- =====================================================

-- Näita kõiki linnu
SELECT *
FROM bank.city;


-- Näita ainult linnade nimesid
SELECT name
FROM bank.city;


-- Leia linn Tallinn
SELECT *
FROM bank.city
WHERE name = 'Tallinn';


-- =====================================================
-- LOCATION
-- =====================================================

-- Näita kõiki ATM asukohti
SELECT *
FROM bank.location;


-- Näita asukoha nime ja ATM-ide arvu
SELECT
    name,
    number_of_atms
FROM bank.location;


-- Leia asukoht id järgi
SELECT *
FROM bank.location
WHERE id = 2;


-- =====================================================
-- LOCATION + CITY
-- =====================================================

-- Näita iga asukohta koos linnaga
SELECT *
FROM bank.location l
         JOIN bank.city c
              ON c.id = l.city_id;

-- l = location tabeli lühinimi
-- c = city tabeli lühinimi
-- location.city_id sisaldab linna id-d
-- city.id on linna enda id


-- Näita ainult vajalikke veerge
SELECT
    l.name AS location_name,
    c.name AS city_name,
    l.number_of_atms
FROM bank.location l
         JOIN bank.city c
              ON c.id = l.city_id;


-- Näita ainult Tallinna asukohti
SELECT
    l.name,
    l.number_of_atms
FROM bank.location l
         JOIN bank.city c
              ON c.id = l.city_id
WHERE c.name = 'Tallinn';

-- Kõigepealt ühendame location ja city tabelid
-- Seejärel WHERE jätab alles ainult Tallinna read


-- =====================================================
-- LOCATION + IMAGE
-- =====================================================

-- Näita asukohta koos tema pildiga
SELECT *
FROM bank.location l
         JOIN bank.location_image li
              ON li.location_id = l.id;

-- location_image.location_id
-- sisaldab location tabeli id-d


-- =====================================================
-- LOCATION + TRANSACTION TYPE
-- =====================================================

-- Ühel asukohal võib olla mitu tehingutüüpi
-- Seetõttu on location ja transaction_type vahel
-- vahetabel location_transaction_type

SELECT
    l.name AS location_name,
    tt.name AS transaction_type
FROM bank.location l

-- Kõigepealt ühendame location vahetabeliga
         JOIN bank.location_transaction_type ltt
              ON ltt.location_id = l.id

-- Seejärel ühendame vahetabeli transaction_type tabeliga
         JOIN bank.transaction_type tt
              ON tt.id = ltt.transaction_type_id;


-- Loogika:
--
-- location.id
--      ↓
-- location_transaction_type.location_id
--
-- location_transaction_type.transaction_type_id
--      ↓
-- transaction_type.id


-- =====================================================
-- WHERE
-- =====================================================

-- Näita asukohti, kus on rohkem kui üks ATM
SELECT *
FROM bank.location
WHERE number_of_atms > 1;


-- Näita asukohti, kus on täpselt 3 ATM-i
SELECT *
FROM bank.location
WHERE number_of_atms = 3;


-- Näita aktiivseid asukohti
SELECT *
FROM bank.location
WHERE status = 'A';


-- =====================================================
-- AND
-- =====================================================

-- Kasutaja peab vastama mõlemale tingimusele:
-- status peab olema A
-- ja role_id peab olema 1
SELECT *
FROM bank."user"
WHERE status = 'A'
  AND role_id = 1;


-- =====================================================
-- OR
-- =====================================================

-- Leia kasutajad, kelle status on A või D
SELECT *
FROM bank."user"
WHERE status = 'A'
   OR status = 'D';


-- =====================================================
-- ORDER BY
-- =====================================================

-- Sorteeri kasutajad kasutajanime järgi
SELECT *
FROM bank."user"
ORDER BY username;


-- Sorteeri A-st Z-ni
SELECT *
FROM bank."user"
ORDER BY username ASC;


-- Sorteeri Z-st A-ni
SELECT *
FROM bank."user"
ORDER BY username DESC;


-- Näita kõige suurema ATM-ide arvuga asukohti esimesena
SELECT *
FROM bank.location
ORDER BY number_of_atms DESC;


-- =====================================================
-- COUNT
-- =====================================================

-- Loenda kõik kasutajad
SELECT COUNT(*)
FROM bank."user";


-- Loenda kõik linnad
SELECT COUNT(*)
FROM bank.city;


-- Loenda ainult aktiivsed kasutajad
SELECT COUNT(*)
FROM bank."user"
WHERE status = 'A';


-- =====================================================
-- INSERT
-- =====================================================

-- Lisa uus linn
INSERT INTO bank.city (name)
VALUES ('Tallinn');

-- id-d ei ole vaja ise sisestada,
-- sest id on serial
-- andmebaas genereerib id automaatselt


-- Lisa uus roll
INSERT INTO bank.role (name)
VALUES ('ADMIN');


-- Lisa uus tehingutüüp
INSERT INTO bank.transaction_type (name)
VALUES ('Cash withdrawal');


-- Lisa uus kasutaja
INSERT INTO bank."user"
(role_id, username, password, status)
VALUES
    (1, 'asya', 'password123', 'A');

-- 1 = rolli id
-- asya = kasutajanimi
-- password123 = parool
-- A = kasutaja status


-- Lisa kasutaja profiil
INSERT INTO bank.profile
(user_id, address, phone_number)
VALUES
    (1, 'Tallinn', '5551234');

-- user_id = 1 tähendab,
-- et see profiil kuulub kasutajale, kelle id = 1


-- Lisa uus asukoht
INSERT INTO bank.location
(city_id, name, number_of_atms, status, lng, lat)
VALUES
    (1, 'Viru Keskus', 3, 'A', 24.7561000, 59.4369000);

-- city_id = 1 tähendab,
-- et asukoht kuulub linnale, mille id = 1


-- =====================================================
-- UPDATE
-- =====================================================

-- Muuda kasutajanime
UPDATE bank."user"
SET username = 'anastasija'
WHERE id = 1;

-- SET = mida muuta
-- WHERE = millisel real muuta


-- Muuda kasutaja staatust
UPDATE bank."user"
SET status = 'D'
WHERE id = 3;


-- Muuda telefoninumbrit
UPDATE bank.profile
SET phone_number = '5559999'
WHERE user_id = 1;


-- Muuda ATM-ide arvu
UPDATE bank.location
SET number_of_atms = 5
WHERE id = 2;


-- TÄHTIS:
-- kui UPDATE päringus puudub WHERE,
-- muudetakse kõiki tabeli ridu

-- Ohtlik näide:
-- UPDATE bank."user"
-- SET status = 'D';


-- =====================================================
-- DELETE
-- =====================================================

-- Kustuta kasutaja id järgi
DELETE FROM bank."user"
WHERE id = 7;


-- Kustuta linn
DELETE FROM bank.city
WHERE id = 3;


-- Kustuta asukoht
DELETE FROM bank.location
WHERE id = 5;


-- TÄHTIS:
-- DELETE ilma WHERE tingimuseta kustutab kõik tabeli read

-- Väga ohtlik näide:
-- DELETE FROM bank."user";


-- =====================================================
-- LIKE
-- =====================================================

-- Leia kasutajanimed, mis algavad tähega a
SELECT *
FROM bank."user"
WHERE username LIKE 'a%';

-- % tähendab:
-- pärast a-d võib olla ükskõik milline tekst


-- Leia kasutajanimed, mis lõppevad tähega a
SELECT *
FROM bank."user"
WHERE username LIKE '%a';


-- Leia kasutajanimed, mille sees on "as"
SELECT *
FROM bank."user"
WHERE username LIKE '%as%';


-- =====================================================
-- IN
-- =====================================================

-- Leia kasutajad, kelle role_id on 1 või 2
SELECT *
FROM bank."user"
WHERE role_id IN (1, 2);


-- Sama päringu saab kirjutada ka nii
SELECT *
FROM bank."user"
WHERE role_id = 1
   OR role_id = 2;


-- =====================================================
-- BETWEEN
-- =====================================================

-- Leia asukohad, kus ATM-ide arv on 2 kuni 5
SELECT *
FROM bank.location
WHERE number_of_atms BETWEEN 2 AND 5;


-- =====================================================
-- IS NULL
-- =====================================================

-- Leia asukohad, kus lng väärtus puudub
SELECT *
FROM bank.location
WHERE lng IS NULL;


-- Leia asukohad, kus lng väärtus on olemas
SELECT *
FROM bank.location
WHERE lng IS NOT NULL;


-- =====================================================
-- GROUP BY
-- =====================================================

-- Loenda, mitu asukohta on igas linnas
SELECT
    c.name AS city_name,
    COUNT(l.id) AS location_count
FROM bank.city c
         JOIN bank.location l
              ON l.city_id = c.id
GROUP BY c.name;

-- GROUP BY paneb sama linna read ühte gruppi
-- COUNT loendab iga linna asukohtade arvu


-- =====================================================
-- USER + ROLE + PROFILE
-- =====================================================

-- Näita kasutaja põhiandmeid, rolli ja profiili
SELECT
    u.id,
    u.username,
    u.status,
    r.name AS role,
    p.address,
    p.phone_number
FROM bank."user" u

-- Ühenda kasutaja rolliga
         JOIN bank.role r
              ON r.id = u.role_id

-- Ühenda kasutaja profiiliga
         JOIN bank.profile p
              ON p.user_id = u.id;


-- =====================================================
-- ATM + CITY + TRANSACTION TYPE
-- =====================================================

-- Näita:
-- asukoha nime
-- linna
-- ATM-ide arvu
-- tehingutüüpi

SELECT
    l.name AS location,
    c.name AS city,
    l.number_of_atms,
    tt.name AS transaction_type
FROM bank.location l

-- Ühenda location linnaga
         JOIN bank.city c
              ON c.id = l.city_id

-- Ühenda location vahetabeliga
         JOIN bank.location_transaction_type ltt
              ON ltt.location_id = l.id

-- Ühenda vahetabel transaction_type tabeliga
         JOIN bank.transaction_type tt
              ON tt.id = ltt.transaction_type_id;