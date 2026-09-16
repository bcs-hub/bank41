# Juhend: PUT /api/atm/locations/{locationId}

**Taski fail:** `Asukoha-info-muutmine.md`
**Kontroller:** `LocationController.java`
**Implementeerimise voog:** RestController → Service → Repository → Service → Mapper → Repository → Service → RestController

---

## Sissejuhatus

Selle endpointi eesmärk on uuendada olemasoleva pangaautomaadi asukoha andmeid — põhiandmed (linn, nimi, automaatide arv, koordinaadid), seotud tehingutüüpide saadavus ning valikuline pilt. Päring käib läbi kõiki kihte (Controller → Service → Repository → Mapper) ja on keerulisem kui tavaline uuendus, sest ühe päringu käigus tuleb sünkroniseerida **kolm** erinevat andmekogumit: `location` rida ennast, `location_transaction_type` seoseid (lisa/eemalda vastavalt `isAvailable`-le) ja `location_image` kirjet (kolme-harulise loogikaga). Kasulik on teada, et `LocationController` ja `LocationService` sisaldavad juba sarnaseid mustreid (`addLocation`, `getAtmLocationDetailDto`) — need annavad häid vihjeid, kuidas `City`, `TransactionType` ja `LocationImage` seostega juba töötatakse.

---

## Samm 1 — RestController

### Mida teha?

`LocationController` on juba olemas (`backend/src/main/java/ee/bcs/bank/controller/location/LocationController.java`) koos meetoditega `addLocation`, `findAtmLocations`, `findAtmLocationsV2` ja `getAtmLocationDetailDto`. Lisa sinna uus meetod uuendamise jaoks.

Mõtle, milline peaks olema uue meetodi signatuur:
- **Path**: `/atm/locations/{locationId}` — vaata, kuidas `getAtmLocationDetailDto` juba `{locationId}` path variable'it kasutab.
- **Path variable**: `locationId` (Integer)
- **Request body**: `LocationDto` — sama DTO klass, mida kasutab juba `addLocation`!

Alusta meetodist ilma mappingannotatsioonita:

```java
public void meetodiNimi(Integer locationId, LocationDto locationDto) {
    // tühi meetod esialgu
}
```

> **Mõtle:** Mis on hea nimi meetodile, mis *uuendab* olemasolevat asukohta? Vaata, kuidas projektis eristub loomine (`addLocation`) uuendamisest — leia sarnane muster mujal koodibaasis.

Seejärel lisa:
1. **Mappingannotatsioon** — `@PutMapping("/atm/locations/{locationId}")`
2. **Parameetrite annotatsioonid** — `@PathVariable` ja `@RequestBody`
3. **Swagger annotatsioonid** — `@Operation` ja `@ApiResponses`

> **Tagastustüübi otsus:** Taskifaili "Väljund" sektsiooni järgi on vastus `200 OK` **tühja sisuga** (`NONE`). See tähendab, et meetod võib jääda `void`-iks — pole vaja midagi tagastada. Vaata `addLocation` meetodit eeskujuks (ka see on `void`).

Veaolukorrad, mis kuuluvad `@ApiResponses` alla (vt taskifaili "Veaolukorrad" sektsiooni):
- `404 Not Found` — kui `locationId`-ga asukohta ei leita (`PRIMARY_KEY_NOT_FOUND`)
- `500 Internal Server Error` — ootamatu serveripoolne viga

### Service klassi ühendamine

`LocationService` on juba kontrollerisse süstitud (`private final LocationService locationService;`). Kutsu lihtsalt uus service meetod välja:

```java
public void meetodiNimi(Integer locationId, LocationDto locationDto) {
    locationService.meetodiNimi(locationId, locationDto);
}
```

> **IntelliJ vihje:** Kui `locationService.meetodiNimi(...)` on punasega alla joonitud, vajuta **Alt+Enter** → **"Create method in LocationService"**. IntelliJ loob automaatselt meetodi kere `LocationService`-sse!

---

## Samm 2 — Service

### Mida teha?

Ava `LocationService.java` ja mine äsja loodud meetodisse. Kõik vajalikud sõltuvused (`locationRepository`, `locationMapper`, `locationImageRepository`, `locationImageMapper`, `locationTransactionTypeRepository`, `cityService`, `transactionTypeService`) on juba klassi väljadena olemas — sama service, mida kasutab ka `addLocation`.

Mõtle läbi, mis järjekorras see meetod peab toimima (vt Samm 3–7):
1. Leia olemasolev `Location` kirje `locationId` järgi
2. Uuenda `Location` andmed mapperiga
3. Sünkroniseeri `location_transaction_type` kirjed
4. Käsitle `location_image` kolme stsenaariumi

> **rAIn-i märkus:** See on hea koht meelde tuletada Samm 4 kontrollpunkti — `@Transactional` annotatsiooni vajadus. Kuna see meetod teeb mitu erinevat kirjutusoperatsiooni (location, location_transaction_type, location_image), peaks kõik õnnestuma või ebaõnnestuma koos. Vaata, kuidas `addLocation` seda juba kasutab (`@Transactional` klassi impordis `jakarta.transaction.Transactional`).

---

## Samm 3 — Repository (olemasoleva kirje leidmine)

### Mida teha?

Enne uuendamist tuleb olemasolev `Location` kirje andmebaasist leida — muidu pole midagi uuendada.

Hea uudis: see meetod on juba olemas! `LocationService` sisaldab juba `public @NonNull Location getValidLocationBy(Integer locationId)` meetodit (kasutab `getAtmLocationDetailDto` samuti). See viskab `PrimaryKeyNotFoundException`, kui kirjet ei leita — täpselt see, mida taskifaili veaolukordade tabel nõuab.

```java
Location location = getValidLocationBy(locationId);
```

> **Veaolukord on juba lahendatud** — sul pole vaja ise `Optional`-it ega `orElseThrow`-d kirjutada, korduskasuta olemasolevat meetodit.

---

## Samm 4 — Service (leitud entiteediga töötlemine)

### Mida teha?

Leitud `Location` entiteet ja sisendi `LocationDto` lähevad koos mapperile, et olemasolevaid välju uuendada (mitte luua uut objekti).

Mõtle: kuidas leida `City`, millele `locationDto.getCityId()` viitab? Vaata, kuidas `createLocation` meetod `addLocation` voos seda juba teeb (`cityService.getValidCity(...)`) — sama muster kehtib siin.

---

## Samm 5 — Mapper (uuendamine)

### Mida teha?

Ava `LocationMapper.java` (`backend/src/main/java/ee/bcs/bank/persistence/location/LocationMapper.java`). Praegu on seal `toLocation(LocationDto locationDto)` meetod, mis **loob uue** `Location` objekti — see sobib loomiseks (`addLocation`), aga mitte uuendamiseks, sest see ei säilita olemasolevat `id`-d ega `status`-t.

Uuendamiseks vajad **uut** mapper meetodit, mis kirjutab olemasolevale entiteedile väärtused peale, mitte ei loo uut.

### Mapper meetodi loomine

Mõtle: milline MapStructi annotatsioon võimaldab olemasolevat objekti kohapeal muuta, selle asemel et uut luua?

> **rAIn-i kontrollpunkt (mitte unusta!):** Niipea kui meetodi signatuur on paigas, aga `@Mapping` annotatsioone veel pole — anna **kohe** Ctrl+Space vihje (kliki `target = ""` jutumärkide vahele → Ctrl+Space, et näha kõiki `Location` entiteedi välju), **enne** kui hakkad arutlema, millised konkreetsed väljad kuhu lähevad.

Nimeta meetod konventsiooni järgi (nt `uuenda...`, mitte `to...`):

```java
void uuendaLocation(LocationDto locationDto, @MappingTarget Location location);
```

Lisa `@Mapping` annotatsioonid **iga** `Location` entiteedi väljale eraldi — vaata `Location.java` klassi väljade nimekirja (`id`, `city`, `name`, `numberOfAtms`, `status`, `lng`, `lat`):

```java
@Mapping(ignore = true, target = "id")
@Mapping(ignore = true, target = "city")
@Mapping(source = "locationName", target = "name")
@Mapping(source = "numberOfAtms", target = "numberOfAtms")
@Mapping(ignore = true, target = "status")
@Mapping(source = "lng", target = "lng")
@Mapping(source = "lat", target = "lat")
void uuendaLocation(LocationDto locationDto, @MappingTarget Location location);
```

> **Mõtle:** Miks jäävad `id`, `city` ja `status` `ignore = true`-ga?
> - `id` ei tohi kunagi muutuda uuendamisel.
> - `status` pole `LocationDto`-l väli — vaata olemasolevat `toLocation` meetodit, kus see määratakse `expression`-iga. Uuendamisel jääb `status` lihtsalt muutmata (entiteet juba omab väärtust).
> - `city` vajab omaette käsitlust, sest sisendis on ainult `cityId`, mitte `City` objekt — samamoodi nagu `toLocation` meetodi puhul, kus `location.setCity(city)` tehakse mapperi väliselt service kihis pärast `cityService.getValidCity(...)` väljakutset.

> **rAIn-i kontrollpunkt (pärast täitmist):** Kui oled read täitnud, loe fail üle ja kontrolli **iga** `Location` entiteedi välja ükshaaval — kas igaühel on kas `source` või `ignore = true`? Ükski väli ei tohi jääda kaardistamata.

Service meetodis kasuta mapperit ja määra `city` käsitsi (sarnaselt `createLocation` meetodile):

```java
City city = cityService.getValidCity(locationDto.getCityId());
location.setCity(city);
locationMapper.uuendaLocation(locationDto, location);
```

---

## Samm 6 — Repository (salvestamine + seosetabelite uuendamine)

### Mida teha?

Selles sammus on kolm eraldi alamülesannet: `Location` salvestamine, `location_transaction_type` sünkroniseerimine ja `location_image` käsitlemine.

### 6a. Location salvestamine

Kuna entiteet on juba `getValidLocationBy` kaudu andmebaasist laetud ja meetod töötab `@Transactional` konteksti sees, jälgib JPA muutuseid automaatselt (dirty checking) — otsest `save()` väljakutset ei pruugi vajagi. Kui soovid siiski selgesõnalisust, võid `locationRepository.save(location)` lisada — vaata, kuidas `createAndSaveLocation` seda `addLocation` voos teeb.

### 6b. Tehingutüüpide sünkroniseerimine

Mõtle taskifaili reeglite järgi (vt "Vastuvõtu kriteeriumid"):
- `isAvailable: true` → kirje `location_transaction_type` tabelis **peab eksisteerima**
- `isAvailable: false` → kirjet **ei tohi olla**

See tähendab, et pead iga `locationDto.getTransactionTypes()` elemendi kohta otsustama: kas lisada uus `LocationTransactionType` kirje, kustutada olemasolev, või mitte midagi teha (kui olek juba klapib).

**Rusikareegel lihtsaimaks lahenduseks:** kaalu, kas lihtsam on kõigepealt **kõik olemasolevad** selle asukoha `location_transaction_type` kirjed kustutada ja seejärel taasluua ainult need, mille `isAvailable == true` — sarnaselt loogikale, mida `createLocationTransactionTypes` juba `addLocation` voos kasutab uute kirjete loomiseks.

> **JPA Buddy vihje:** Kui vajad uut repository meetodit (nt kõigi kirjete kustutamiseks asukoha järgi), ava `LocationTransactionTypeRepository` ja kasuta JPA Buddy paneeli — vali **Method** → **Query**, meetodi tüüp vastavalt vajadusele (nt kustutamine `location_id` järgi). Vaata olemasolevat `locationTransactionTypeExistsBy` meetodit eeskujuks Named parameters stiili osas.

Uute kirjete loomiseks korduskasuta olemasolevat mustrit `createLocationTransactionType(location, transactionType)` — vaata, kas saad seda otse kutsuda või tuleb veidi kohandada.

### 6c. Pildi käsitlemine

Taskifail kirjeldab kolme stsenaariumi (vt "imageData käitumine"):
1. Andmebaasis **on** pilt + JSON `imageData` **tühi** → kustuta
2. Andmebaasis **pole** pilti + JSON-is **on** sisu → loo uus
3. Andmebaasis **on** pilt + JSON-is **on** sisu → uuenda olemasolevat `data` välja

Mõtle: kuidas kontrollida, kas andmebaasis juba on pilt? Vaata `LocationImageRepository.findByLocation(location)` — see tagastab `Optional<LocationImage>`, täpselt nagu `handleSetImageData` meetodis `getAtmLocationDetailDto` voos juba kasutatakse.

> **Optional käsitlemine:** Sul on nüüd `Optional<LocationImage>` käes ja pead otsustama, mida iga kolme stsenaariumi puhul teha:
> - Kui `Optional` on **present** ja sisend on tühi → kustuta (`locationImageRepository.delete(...)`)
> - Kui `Optional` on **empty** ja sisend pole tühi → loo uus (vaata `createLocationImage` meetodit `addLocation` voost — sama mapperit `LocationImageMapper.toLocationImage(...)` saab siin korduskasutada, kuid pead ka `location` seose käsitsi määrama, nagu `handleCreateAndSaveLocationImage` seda teeb)
> - Kui `Optional` on **present** ja sisend pole tühi → võta olemasolev `LocationImage` objekt `Optional`-ist ja uuenda selle `data` välja (`StringBytesConverter.stringToBytes(...)`, vt `LocationImageMapper`-i `toBytes` abimeetodit)
> - Kui `Optional` on **empty** ja sisend on tühi → ei tehta midagi

> **rAIn-i märkus:** See on koht, kus näen õpilasi tihti unustamas ühte neljast harust (enamasti "ei tehta midagi" juhtumit, sest see tundub "vaikimisi juhtuvat"). Soovitan kirjutada kõik neli haru selgelt lahti (nt `if`/`else if` ahelaga), isegi kui üks neist jääb tühjaks — see teeb loogika selgemaks ja vähem vigaderohkeks.

---

## Samm 7 — tagasi Service'i

### Mida teha?

Service meetod on nüüd valmis, kui kõik kolm alamülesannet (Location uuendus, tehingutüüpide süngroniseerimine, pildi käsitlus) on service meetodis kokku pandud.

Kuna taskifaili järgi tuleb tagastada tühi `200 OK`, jääb service meetod `void`-iks — pole vaja midagi mapperiga tagasi DTO-ks teisendada.

---

## Samm 8 — tagasi RestController'isse

### Mida teha?

Kontrolleri meetod kutsub service meetodit välja ja jääb `void`-iks (vt Samm 1 otsust) — pole `return` lauset vaja.

```java
@PutMapping("/atm/locations/{locationId}")
@Operation(...)
@ApiResponses(...)
public void meetodiNimi(@PathVariable Integer locationId, @RequestBody LocationDto locationDto) {
    locationService.meetodiNimi(locationId, locationDto);
}
```

---

## Samm 9 — kood ilusaks (refactor)

### Make it work → Make it beautiful

Kui kood töötab, on aeg vaadata, kas saab koodi puhtamaks muuta.

**Extract Method IntelliJ'ga:**

Kui service meetod kasvab pikaks (nt kõik kolm alamülesannet ühes meetodis), märgi loogiline plokk (nt pildi käsitlus) → paremklõps → Refactor → Extract Method. Vaata, kuidas `LocationService` juba kasutab `handle`-prefiksiga abimeetodeid (`handleCreateAndSaveLocationImage`, `handleSetImageData`) — järgi sama nimekonventsiooni (vt `backend/CLAUDE.md` "Meetodi nimetamine" reeglit: tingimusliku loogikaga meetodid, mis DTO-d/entiteeti muteerivad, saavad `handle`-prefiksi).

> **Tähelepanu:** IntelliJ kasutab ekstraktimisel kogu objekti parameetrina. Vaata üle, kas helper meetod vajab tegelikult kogu `LocationDto`-d või ainult üht välja (nt `imageData` stringi) — ja tee vajadusel korrektuur.

### Meetodite järjekord

Kontrolli meetodite järjekorda vastavalt Java konventsioonile ja projekti tavale (vt olemasolevat `LocationService` struktuuri):
1. `public` meetodid enne
2. `private` meetodid pärast
3. Järjesta ka väljakutsumise hierarhia järgi — peameetod üleval, helper meetodid all

---

## Kokkuvõte ja kontrollnimekiri

Enne kui pead koodi valmis, kontrolli läbi:

- [ ] `LocationController`-is on uus `@PutMapping("/atm/locations/{locationId}")` meetod `@PathVariable` ja `@RequestBody`-ga
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (200, 404, 500)
- [ ] `LocationService`-s on uus meetod, mis kasutab olemasolevat `getValidLocationBy`-d
- [ ] `Location` väljad (`city`, `name`, `numberOfAtms`, `lng`, `lat`) uuendatakse mapperiga, `id` ja `status` jäävad puutumata
- [ ] Uus MapStructi `uuenda...` meetod `@MappingTarget`-iga on `LocationMapper`-is, kõik target-väljad eksplitsiitselt kaardistatud
- [ ] `location_transaction_type` kirjed sünkroniseeritakse vastavalt `isAvailable` väärtustele (lisa/eemalda)
- [ ] `imageData` kõik neli stsenaariumi on käsitletud (kustuta / loo uus / uuenda / ei tee midagi)
- [ ] Meetod on märgitud `@Transactional`-iga
- [ ] Kui `locationId`-ga asukohta ei eksisteeri, tagastatakse `404 Not Found` (`PRIMARY_KEY_NOT_FOUND`)
- [ ] Meetodite järjekord: `public` enne, `private` pärast — järjesta ka väljakutsumise hierarhia järgi
- [ ] Kood kompileerub ja Swagger UI kaudu on endpoint nähtav
- [ ] Kirjutatud on automaattestid: õnnestunud uuendus (sh tehingutüüpide lisamine/eemaldamine ja kõik kolm `imageData` stsenaariumi) ning 404 juhtum

---

> **Järgmine samm:** Testi endpointi Swagger UI kaudu (`http://localhost:8080/swagger-ui/index.html`) ja kontrolli, et vastus vastab taskifaili näidisandmetele (nt `locationId=1`, "Sikupilli Prisma").
