# Tehingutüüpide loetelu (GET /api/atm/transaction-types)

**Teenus:** `GET /api/atm/transaction-types`

**Sisendid:** puuduvad — teenusel ei ole path variable't, query parameetrit ega request body't.

**Response body näidis (200 OK):**

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

## Ekraanipilt

Mockup pärineb failist `docs/balsamiq/bank_rest.pdf`, lehekülg 14 (STEP-7, Asukoha lisamine - tühi):

![Mockup - STEP-7, lk 14](./tehingutuupide-loetelu.png)

## Eesmärk

Teenuse eesmärk on tagastada kõik süsteemis defineeritud tehingutüübid (transaction types). Frontendis kasutab seda `TransactionTypesCheckbox.vue` komponent, et täita pangaautomaadi asukoha lisamise/muutmise vormi (`LocationView.vue`, frontend tee `/location`) checkbox'ide loetelu.

Kui vorm avatakse tühjana (uue asukoha lisamiseks, ilma `locationId` query parameetrita), peab iga tagastatud tehingutüübi `isAvailable` väärtus olema `false` — kasutaja ei ole vormi avamise hetkel veel ühtegi checkbox'i valinud. Teenus ei tea ega peagi teadma, millise konkreetse asukohaga (location) parasjagu tegeletakse — sellepärast ei kajasta `isAvailable` siin ühegi konkreetse asukoha hetkeseisu, vaid on lihtsalt vaikeväärtus, mida frontend kasutaja valikute põhjal ise `true`-ks muudab.

Teenus peab tagastama kõik `transaction_type` tabelis olevad kirjed, sõltumata sellest, kas mõni asukoht neid parasjagu kasutab või mitte.

## Seotud andmebaasi tabelid

### `transaction_type`

Otsingutabel süsteemis defineeritud tehingutüüpidele (allikas: `docs/database/2_create.sql`):

```sql
CREATE TABLE transaction_type (
    id serial  NOT NULL,
    name varchar(255)  NOT NULL,
    CONSTRAINT transaction_type_pk PRIMARY KEY (id)
);
```

Praegused algandmed (allikas: `docs/database/3_import.sql`):

| id | name |
|----|------|
| 1 | raha sisse |
| 2 | raha välja |
| 3 | maksed |

> Märkus: `location_transaction_type` (many-to-many `location` ↔ `transaction_type`) on samuti seotud tehingutüüpidega, aga seda kasutavad `/api/atm/location` teenused konkreetse asukoha andmete tagastamiseks/salvestamiseks — käesolev teenus seda tabelit ei loe.

## Vealolukorrad

Teenusel puuduvad sisendid, seega valideerimisvigu (nt 400 Bad Request) tekkida ei saa. Ainus arvestatav veaolukord on ootamatu serveri sisemine viga:

| Olukord | HTTP staatus | Response body |
|---|---|---|
| Ootamatu serveri viga (nt andmebaasiühendus katkeb) | 500 Internal Server Error | Vastavalt projekti üldisele veavastuse vormingule (`ErrorResponse` / `ApiError`, vt `RestExceptionHandler`) |

## Vastuvõtukriteeriumid

- [ ] `GET /api/atm/transaction-types` tagastab HTTP staatuse `200 OK` ja JSON massiivi kõigist `transaction_type` tabeli kirjetest.
- [ ] Iga massiivi element sisaldab välju `transactionTypeId` (Integer), `transactionTypeName` (String) ja `isAvailable` (Boolean).
- [ ] `isAvailable` väärtus on alati `false`, kuna teenus ei ole seotud ühegi konkreetse asukohaga.
- [ ] Tagastatavate kirjete arv ja sisu vastavad `transaction_type` tabeli praegusele sisule (hetkel 3 kirjet: "raha sisse", "raha välja", "maksed").
- [ ] Loodud on `TransactionTypeInfoDto` (väljad: `transactionTypeId`, `transactionTypeName`, `isAvailable`), vastavalt projekti DTO-konventsioonile (`controller/transactiontype/dto/` alampakis, vt eeskujuks `controller/city/dto/CityDto.java`).
- [ ] Loodud on `TransactionTypeController` (`controller/transactiontype/` pakis) analoogselt `CityController`-iga — kontroller kutsub teenuseklassi meetodit ja tagastab DTO-de nimekirja.
- [ ] `TransactionTypeService`-sse on lisatud meetod kõigi tehingutüüpide leidmiseks (nt `findTransactionTypes()`), mis kasutab `TransactionTypeRepository`-t ja MapStructi mapperit DTO-deks teisendamiseks.
- [ ] Teenus on dokumenteeritud Swagger/OpenAPI annotatsioonidega (`@Operation`, vajadusel `@ApiResponse`), vt eeskujuks `CityController`/`LocationController`.
- [ ] Lisatud on automaattest(id), mis kontrollivad edukat vastust (staatus `200 OK`, õiged väljad, kõigi kirjete `isAvailable == false`).
