# CLAUDE.md

See fail annab juhiseid Claude Code'ile (claude.ai/code) selles repositooriumis töötamiseks.

**Keel:** Kõik uued kanded sellesse faili kirjutatakse eesti keeles.

## Käsud

```bash
# Ehitamine
./gradlew build

# Käivitamine (nõuab lokaalset PostgreSQL-i)
./gradlew bootRun

# Testide käivitamine
./gradlew test

# Ühe testiklassi käivitamine
./gradlew test --tests "ee.bcs.bank.BankApplicationTests"

# Ainult kompileerimine (käivitab ka MapStructi annotatsiooni töötluse)
./gradlew compileJava
```

## Andmebaasi seadistamine

PostgreSQL peab töötama `localhost`-is järgmiste seadetega:
- Andmebaas: `vali_it`
- Kasutajanimi: `postgres`
- Parool: `student123`
- Mugav DB url: `jdbc:postgresql://localhost:5432/vali_it`

Käivita skriptid järjekorras kaustast `docs/database`:
1. `1_reset_database.sql` — kustutab ja loob uuesti `bank` skeema
2. `2_create.sql` — loob kõik tabelid
3. `3_import.sql` — lisab algandmed

Kõik tabelid asuvad `bank` skeemas.

## Arhitektuur

Tegemist on Spring Boot 4.x / Java 21 REST backendiga pangaautomaatide asukoharakenduse jaoks. Frontend on eraldi Vue 3 SPA (ei ole selles repos).

### Kihtide struktuur

```
controller/       REST endpointid + päringu/vastuse DTOd
service/          Äriloogika
persistence/      JPA entiteedid, repositooriumid, MapStructi mapperid
infrastructure/   Läbivad komponendid: erindi tüübid, veakoodid, globaalne erindite käsitleja
```

Igal domeenialasel (location, city, transactiontype, login) on oma alampakk `controller/`-is koos DTOdega, teenusklass ja persistence pakk.

### Olulised konventsioonid

**DTOd vs entiteedid** — Kontrollerid näevad ainult DTOsid. MapStructi mapperid (liidesed annotatsiooniga `@Mapper`) teisendavad DTOd JPA entiteetideks ja vastupidi. Genereeritud mapperi implementatsioonid tekivad kausta `src/main/generated/`.

**Veakäsitlus** — Teenustest visatakse kohandatud erindeid (`DataNotFoundException`, `ForbiddenException`, `PrimaryKeyNotFoundException`), mille püüab kinni `RestExceptionHandler` (`@ControllerAdvice`). Kõik äriveateated ja numbrilised veakoodid on koondatud `ErrorResponse` enumi.

**Muutujate nimetamine** — Muutuja nimi peab peegeldama täistüüpi: `LocationDetailDto locationDetailDto`, mitte `LocationDetailDto dto`.

**Meetodi nimetamine** — `getX()` lubab kindlat tagastust. Kui meetod sisaldab tingimislikku loogikat ja muteerib DTO-d, kasuta `handle`-prefiksit ja anna DTO parameeter sisse: `handleAddImageData(LocationDetailDto locationDetailDto, Integer locationId)`.

**Entiteedi otsing ID järgi** — `repository.findById()` kasutamine `orElseThrow`-ga peab olema `public getValid<Entiteet>By(Integer <entiteet>Id)` meetodis vastava service klassi all (nt `getValidLocationBy(Integer locationId)` `LocationService`-s, `getValidCityBy(Integer cityId)` `CityService`-s).

**SQL päringud** — Kohandatud päringud on JPQL, kirjutatud otse Spring Data repositooriumi liidesele `@Query` annotatsiooniga. `LocationTransactionTypeRepository` kasutab konstruktori avaldist otse DTOsse projekteerimiseks.

**SQL logimine** — P6Spy on seadistatud (`spy.properties`), nii et täielik parameetritega SQL kuvatakse konsoolis. Selle saab keelata, lülitades `application.properties`-is tagasi kommenteeritud tavaliste PostgreSQL seadetele.

### Domeenikujundus (põhitabelid)

- `location` — pangaautomaadi asukoht; kuulub `city`-le; omab valikulist `location_image` (bytea) ja mitu-mitmele seost `transaction_type`-ga läbi `location_transaction_type`
- `user` — omab `role`-i, `profile`-i ja valikulist `user_image`-i
- `transaction_type` — otsingutabel (sularaha väljavõtmine, sissemakse jne)

### REST API

Baastee: `/api`. Swagger UI on saadaval aadressil `/swagger-ui.html`.

| Meetod | Tee | Kirjeldus |
|--------|-----|-----------|
| GET | `/api/login` | Sisselogimine (tagastab userId ja roleName) |
| POST | `/api/atm/location` | Uue pangaautomaadi asukoha lisamine |
| GET | `/api/atm/locations?cityId=` | Pangaautomaatide asukohtade loetelu (0 = kõik linnad) |
| GET | `/api/atm/location?locationId=` | Pangaautomaadi asukoha detailinfo |
| GET | `/api/cities` | Linnade valikute loetelu |
| GET | `/api/transaction-types` | Tehingutüüpide valikute loetelu |
