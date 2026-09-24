# Asukoha detailandmete päring

**Teenus:** `GET /api/atm/locations/{locationId}`

**Kasutav vaade:** `LocationView.vue` (`/location?locationId={id}`)

![Mockup](../../mock-wireframe/pdf-images/LocationView-27.png)

## Sisend

**Path variable:** `locationId` (Integer) — selle asukoha ID, mille andmeid soovitakse muutmisvormil kuvada.

Query parameetreid ega request body't teenusel ei ole.

## Väljund

**Response (200 OK):** `AtmLocationDetailDto` objekt, mis sisaldab kõiki muutmisvormi väljade eeltäitmiseks vajalikke andmeid, sh kõiki süsteemis defineeritud tehingutüüpe koos infoga, kas need on selles asukohas hetkel saadaval.

```json
{
  "locationId": 1,
  "cityId": 2,
  "locationName": "Sikupilli Prisma",
  "numberOfAtms": 5,
  "imageData": null,
  "lng": 24.7795000,
  "lat": 59.4369000,
  "transactionTypes": [
    {
      "transactionTypeId": 1,
      "transactionTypeName": "raha sisse",
      "isAvailable": true
    },
    {
      "transactionTypeId": 2,
      "transactionTypeName": "raha välja",
      "isAvailable": true
    },
    {
      "transactionTypeId": 3,
      "transactionTypeName": "maksed",
      "isAvailable": true
    }
  ]
}
```

> **Märkus andmete kohta:** Mockupis kasutatud näidis (`locationId=14`, "Mustamäe Prisma") ei vasta ühelegi kirjele failis `docs/database/3_import.sql` (seal on ainult `locationId` 1–3). Ülal olev JSON näidis kasutab seetõttu päris andmebaasi seisu: asukohta id=1 ("Sikupilli Prisma", Tallinn), millel `location_transaction_type` tabeli järgi on kõik kolm tehingutüüpi saadaval. `imageData` väärtus on näites `null`, kuna `location_image` tabelis pole `3_import.sql`-s ühtegi kirjet — vt allpool "imageData käitumine".

**imageData käitumine:**

- Kui asukohal on `location_image` tabelis kirje, tagastatakse selle `data` (bytea) väli base64/string kujul `imageData` väljal (vt `StringBytesConverter.bytesToString`, mida kasutatakse ka vastupidises suunas `LocationImageMapper`-is).
- Kui asukohal pilti pole, on `imageData` väärtus `null`.

## Eesmärk

Frontendis kasutatakse asukoha muutmise vormil (`LocationView.vue`, `/location?locationId={id}`) selle teenuse vastust kõigi vormiväljade eeltäitmiseks: linn, asukoha nimi, automaatide arv, pilt ja tehingutüüpide checkbox'id (`TransactionTypesCheckbox.vue` — vastupidiselt tühja vormi juhtumile, siin peab `isAvailable` kajastama asukoha tegelikku hetkeseisu, mitte olema alati `false`).

Vaade püüab `locationId` väärtuse kinni `$route.query.locationId` query stringist ja teeb selle teenuse päringu vormi laadimisel, enne kui kuvab pealkirja "Muuda asukoha infot".

## Seotud andmebaasi tabelid

Vt `docs/database/2_create.sql`.

**`location`** — pangaautomaadi asukoht:

```sql
CREATE TABLE location (
    id serial  NOT NULL,
    city_id int  NOT NULL,
    name varchar(255)  NOT NULL,
    number_of_atms int  NOT NULL,
    status char(1)  NOT NULL,
    lng numeric(10,7),
    lat numeric(10,7),
    CONSTRAINT location_pk PRIMARY KEY (id)
);
```

**`city`** — linn, millele asukoht kuulub (FK `location.city_id`):

```sql
CREATE TABLE city (
    id serial  NOT NULL,
    name varchar(255)  NOT NULL,
    CONSTRAINT city_pk PRIMARY KEY (id)
);
```

**`location_image`** — asukoha valikuline pilt (1:0..1 seos `location`-ga):

```sql
CREATE TABLE location_image (
    id serial  NOT NULL,
    location_id int  NOT NULL,
    data bytea  NOT NULL,
    CONSTRAINT location_image_pk PRIMARY KEY (id)
);
```

**`transaction_type`** — süsteemis defineeritud tehingutüübid (kõik kolm tagastatakse alati, sõltumata asukohast):

```sql
CREATE TABLE transaction_type (
    id serial  NOT NULL,
    name varchar(255)  NOT NULL,
    CONSTRAINT transaction_type_pk PRIMARY KEY (id)
);
```

**`location_transaction_type`** — seostabel, mis määrab, millised tehingutüübid on konkreetses asukohas saadaval (kirje olemasolu ⇒ `isAvailable: true`):

```sql
CREATE TABLE location_transaction_type (
    id serial  NOT NULL,
    location_id int  NOT NULL,
    transaction_type_id int  NOT NULL,
    CONSTRAINT location_transaction_type_pk PRIMARY KEY (id)
);
```

Näidisandmed (`docs/database/3_import.sql`):

| location.id | location.name    | city_id | number_of_atms | location_transaction_type (transaction_type_id) |
|-------------|-------------------|---------|-----------------|---------------------------------------------------|
| 1           | Sikupilli Prisma  | 2 (Tallinn) | 5            | 1, 2, 3 (kõik saadaval)                            |
| 2           | Tondi Selver      | 2 (Tallinn) | 2            | 1, 2 (maksed puudub)                               |
| 3           | Jõe Prisma        | 3 (Tartu)   | 2            | 1, 2 (maksed puudub)                               |

## Veaolukorrad

| Olukord | Status code | Response body |
|---|---|---|
| `locationId` väärtusega asukohta ei eksisteeri | 404 Not Found | `{ "message": "Ei leidnud primary keyd 'locationId' väärtusega: 123", "errorCode": "PRIMARY_KEY_NOT_FOUND" }` |
| Ootamatu serveripoolne viga (nt andmebaasiühenduse tõrge) | 500 Internal Server Error | Standardne vea response body (vastavalt projekti globaalsele error handler'ile) |

404 juhtum vastab olemasolevale `PrimaryKeyNotFoundException` + `RestExceptionHandler` mustrile (vt nt `LocationService.getValidLocationBy`-analoogne meetod, `CityService.getValidCity`).

## Vastuvõtu kriteeriumid

- [ ] Loodud on endpoint `GET /api/atm/locations/{locationId}` path variable'iga `locationId`.
- [ ] Kui `locationId`-ga asukoht eksisteerib, tagastatakse `200 OK` koos `AtmLocationDetailDto` objektiga väljadega `locationId`, `cityId`, `locationName`, `numberOfAtms`, `imageData`, `lng`, `lat`, `transactionTypes`.
- [ ] `transactionTypes` massiiv sisaldab alati kõiki süsteemis defineeritud tehingutüüpe (mitte ainult asukohas saadaolevaid), igaühe `isAvailable` väärtus kajastab, kas `location_transaction_type` tabelis on vastav kirje selle asukoha ja tehingutüübi jaoks.
- [ ] `imageData` on asukoha `location_image` kirje olemasolul selle sisu string kujul, vastasel juhul `null`.
- [ ] Kui `locationId`-ga asukohta ei eksisteeri, tagastatakse `404 Not Found` koos `errorCode: PRIMARY_KEY_NOT_FOUND`.
- [ ] Ootamatu serveripoolse vea korral tagastatakse `500 Internal Server Error`.
- [ ] Teenuse jaoks on kirjutatud automaattestid (nii õnnestunud päringu, tehingutüüpide `isAvailable` õigsuse kui ka 404 juhtumi kohta).
