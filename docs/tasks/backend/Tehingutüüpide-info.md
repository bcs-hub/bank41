# Tehingutüüpide info

**Teenus:** `GET /api/atm/transaction-types`

**Allikas:** [`docs/balsamic/bank_rest.pdf`](../../balsamic/bank_rest.pdf), lehekülg 14 (STEP-7, "Asukoha lisamine - tühi")

![Lehekülg 14 - Asukoha lisamine (tühi vorm)](./Tehingutüüpide%20info.png)

## Sisendid

Sisendeid ei ole — ei path variable't, request parameetrit ega request body't.

## Response body (200 OK)

```json
[
  {
    "transactionTypeId": 1,
    "transactionTypeName": "raha sisse",
    "isAvailable": false
  },
  {
    "transactionTypeId": 2,
    "transactionTypeName": "raha välja",
    "isAvailable": false
  },
  {
    "transactionTypeId": 3,
    "transactionTypeName": "maksed",
    "isAvailable": false
  }
]
```

## Eesmärk

Uue pangaautomaadi asukoha lisamise vormil (frontend rada `/location`, komponent `LocationView.vue`, alamkomponent `TransactionTypesCheckbox.vue`) tuleb kasutajale kuvada kõik süsteemis defineeritud tehingutüübid checkboxidena, kust admin saab valida, milliseid tehinguid uues asukohas pakutakse.

See teenus täidab checkboxi nimekirja **tühja vormi** puhul (kui vaade avatakse ilma `locationId` parameetrita, s.t uue asukoha lisamiseks). Kuna ühtegi checkboxi pole veel valitud, tagastatakse kõik tehingutüübid `isAvailable: false` väärtusega — see väärtus ei sõltu ühestki konkreetsest asukohast, vaid on alati `false`.

*Märkus: olemasoleva asukoha muutmise vormi puhul (kui `locationId` on olemas) tuleb tehingutüüpide tegelik saadavus kuvada teistsuguse, juba olemasoleva teenuse kaudu (`GET /api/atm/location?locationId=`) — see ei ole selle taski osa.*

## Seotud tabelid

Vt [`docs/database/2_create.sql`](../../database/2_create.sql).

**`transaction_type`** — otsingutabel, mis sisaldab kõiki süsteemis lubatud tehingutüüpe:

```sql
CREATE TABLE transaction_type (
    id serial NOT NULL,
    name varchar(255) NOT NULL,
    CONSTRAINT transaction_type_pk PRIMARY KEY (id)
);
```

Algandmed asuvad failis [`docs/database/3_import.sql`](../../database/3_import.sql):

```sql
INSERT INTO bank.transaction_type (id, name) VALUES (default, 'raha sisse');
INSERT INTO bank.transaction_type (id, name) VALUES (default, 'raha välja');
INSERT INTO bank.transaction_type (id, name) VALUES (default, 'maksed');
```

Teenus **ei loe** tabelit `location_transaction_type` — `isAvailable` on selle endpoindi puhul alati `false` ja ei sõltu ühestki konkreetsest asukohast (vt eelmine punkt).

## Olemasolevad ehitusklotsid (juba koodis olemas)

Suur osa vajalikust loogikast on juba olemas, puudu on ainult controller-tasandi wiring:

- `persistence/transactiontype/TransactionType.java` — JPA entiteet
- `persistence/transactiontype/TransactionTypeRepository.java` — `JpaRepository<TransactionType, Integer>`
- `persistence/transactiontype/TransactionTypeMapper.java` — sisaldab juba meetodit `toTransactionTypeDtos(List<TransactionType>)`, mis mapib `id → transactionTypeId`, `name → transactionTypeName` ja `isAvailable` konstandiga `false`
- `controller/location/dto/TransactionTypeDto.java` — DTO väljadega `transactionTypeId`, `transactionTypeName`, `isAvailable`
- `service/TransactionTypeService.java` — hetkel ainult `getValidTransactionType(Integer transactionTypeId)`; vaja lisada uus meetod kõigi tehingutüüpide tagastamiseks (nt `findTransactionTypes()`)

**Märkus konventsiooni kohta:** `backend/CLAUDE.md` kirjeldab, et igal domeenialal (sh `transactiontype`) peaks olema oma alampakk `controller/`-is koos DTOdega, aga hetkel elab `TransactionTypeDto` `controller/location/dto` paketis. Uue controlleri loomisel kaalu, kas tuleks luua `controller/transactiontype/` pakk (koos DTOga) vastavalt teiste domeenide (`city`, `location`, `login`) eeskujule, või jätkata olemasoleva `location` paketi taaskasutamist — vali projekti konventsiooniga kõige paremini sobiv lahendus.

## Veaolukorrad

Kuna teenusel pole sisendeid ega äriloogilisi valideerimisreegleid, ei ole PDF-i post-itis (`Veateated: —`) kirjas ühtegi äriloogilist veajuhtu. Küll aga tuleks arvestada järgmiste üldiste/tehniliste olukordadega:

| Olukord | HTTP staatus | Response body |
|---|---|---|
| Tehingutüübid leiti (tavajuht) | 200 | JSON massiiv, vt ülal |
| Tabelis `transaction_type` pole ühtegi rida | 200 | `[]` (tühi massiiv — see on süsteemi baasandmete tabel, mitte kasutaja sisestatud andmed, seega **ei** tagastata 404, erinevalt nt `/api/atm/locations` teenusest) |
| Ootamatu serveripoolne viga (nt andmebaasiühendus katkeb) | 500 | Spring Booti vaikimisi veavastus (`{"timestamp":..., "status":500, "error":"Internal Server Error", "path":"/api/atm/transaction-types"}`) — kohandatud `ApiError`/erindiklassi pole vaja, kuna ühtegi äriloogilist erindit ei visata |

## Vastuvõtukriteeriumid

- [ ] Uus endpoint `GET /api/atm/transaction-types` on olemas ja tagastab HTTP 200
- [ ] Endpoint ei nõua ühtegi sisend-parameetrit (ei path variable, request parameter ega request body)
- [ ] Vastus on JSON massiiv objektidest väljadega `transactionTypeId`, `transactionTypeName`, `isAvailable`
- [ ] `isAvailable` on iga elemendi puhul alati `false`
- [ ] Tagastatud tehingutüübid ja nende järjekord vastavad `transaction_type` tabeli sisule (vt `docs/database/3_import.sql`: "raha sisse", "raha välja", "maksed")
- [ ] Kui `transaction_type` tabel on tühi, tagastatakse `200` ja tühi massiiv `[]` (mitte 404)
- [ ] Kasutatud on olemasolevat `TransactionTypeMapper.toTransactionTypeDtos(...)` meetodit (uut mapping-loogikat ei looda)
- [ ] Kood järgib projekti kihistruktuuri (controller → service → persistence) ja `backend/CLAUDE.md` nimetamiskonventsioone (muutujanimed peegeldavad täistüüpi, meetodinimed kirjeldavad tagastatavat objekti)
- [ ] Swagger dokumentatsioon (`@Operation`, `@ApiResponses`) on lisatud vastavalt teiste kontrollerite (nt `CityController`, `LocationController`) eeskujule
- [ ] Endpoint on käsitsi testitud Swagger UI (`/swagger-ui.html`) kaudu või `curl http://localhost:8080/api/atm/transaction-types`
